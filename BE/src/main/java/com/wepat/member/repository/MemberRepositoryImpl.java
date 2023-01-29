package com.wepat.member.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.calendar.CalendarEntity;
import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;
import com.wepat.security.OpenCrypt;
import com.wepat.exception.member.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    public enum ReturnType {
        SUCCESS, ExistEmailException, ExistIdException, NotExistCalendarException, IdWriteException, BlockMember,
        PwdWriteException, NotExistMember
    }
    private static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private static final String CALENDAR_COLLECTION = "calendar";
    private static final String MEMBER_COLLECTION = "member";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);
    private final CollectionReference memCollection = db.collection(MEMBER_COLLECTION);
    private final CollectionReference petCollection = db.collection(PET_COLLECTION);
    private final CollectionReference scheduleCollection = db.collection(SCHEDULE_COLLECTION);
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);

    //캘린더 ID가 있을 때, 회원가입 (ID 값이 틀렸다고 에러)
    @Override
    public void signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException {

        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        final DocumentReference calDocRef = calCollection.document(member.getCalendarId());

        boolean EmailCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        EmailCheck = documents.stream().anyMatch(docs -> (docs.toObject(MemberEntity.class).getEmail()).equals(member.getEmail()));

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get(); //입력한ID 값을 갖는 snapshot
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get(); //입력한 캘린더ID 값을 갖는 snapshot

            if (EmailCheck) {
                return ReturnType.ExistEmailException;
            } else {
                if (memSnapshot.exists()) {
                    return ReturnType.ExistIdException;
                } else {
                    if (calSnapshot.exists()) {
                        member.setCalendarId(calDocRef.getId());
                        transaction.create(memDocRef, new MemberEntity(member));

                        //트랜잭션을 통해 얻은 calSnapshot의 member배열에 접근
                        List<String> memberId = calSnapshot.toObject(CalendarEntity.class).getMemberId();
                        //회원가입한 member의 Id를 포함하여 업데이트
                        memberId.add(member.getMemberId());
                        transaction.update(calDocRef, "memberId", memberId);

                        return ReturnType.SUCCESS;
                    } else {
                        return ReturnType.NotExistCalendarException;
                    }
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (returnTypeApiFuture.get()==ReturnType.ExistEmailException) {
            throw new ExistEmailException();
        } else if (returnTypeApiFuture.get()==ReturnType.ExistIdException) {
            throw new ExistIdException();
        } else if (returnTypeApiFuture.get()==ReturnType.NotExistCalendarException) {
            throw new NotExistCalendarException();
        } else {
            returnTypeApiFuture.get();
        }
    }

    //캘린더 ID null일 때, 회원가입
    @Override
    public void signUp(MemberDto member) throws ExecutionException, InterruptedException {

        // memberId인 document 를 가져옴(없으면 생성)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // calendar document 를 생성 (docId 는 랜덤값)
        final DocumentReference calDocRef = calCollection.document();

        boolean EmailCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        EmailCheck = documents.stream().anyMatch(docs -> (docs.toObject(MemberEntity.class).getEmail()).equals(member.getEmail()));

        // run an asynchronous transaction
        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {

            // memberId 인 document 를 가져옴
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();

            if (EmailCheck) {
                return ReturnType.ExistEmailException;
            } else {
                if (memSnapshot.exists()) {
                    return ReturnType.ExistIdException;
                } else {
                    // memberEntity 를 db 에 추가 함
                    member.setCalendarId(calDocRef.getId());
                    transaction.create(memDocRef, new MemberEntity(member));
                    // calendarEntity(memberId 를 갖는)를 db에 추가 함
                    transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));
                    return ReturnType.SUCCESS;
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (returnTypeApiFuture.get()==ReturnType.ExistEmailException) {
            throw new ExistEmailException();
        } else if (returnTypeApiFuture.get()==ReturnType.ExistIdException) {
            throw new ExistIdException();
        } else {
            returnTypeApiFuture.get();
        }
    }

    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (!memSnapshot.exists()) { // 멤버ID가 없을 경우
                return ReturnType.IdWriteException;
            } else if (memSnapshot.toObject(MemberEntity.class).isBlock()) { //차단된 계정
                return ReturnType.BlockMember;
            } else if (memSnapshot.exists() && memberEntity.getPwd().equals(pwd)) { //해당 멤버ID가 있고, pwd가 같다면 로그인 성공
                return ReturnType.SUCCESS;
            } else { //비밀번호가 다르다면
                return ReturnType.PwdWriteException;
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (returnTypeApiFuture.get()==ReturnType.IdWriteException) {
            throw new IdWriteException();
        } else if (returnTypeApiFuture.get()==ReturnType.BlockMember) {
            throw new BlockMember();
        } else if (returnTypeApiFuture.get()==ReturnType.PwdWriteException) {
            throw new PwdWriteException();
        } else {
            return memCollection.document(memberId).get().get().toObject(MemberDto.class);
        }
    }

    @Override
    public String findId(String email) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> memDocs = memCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot docs : memDocs) {
            if (docs.toObject(MemberEntity.class).getEmail().equals(email)) {
                return docs.getId();
            }
        }
        throw new NotExistEmailException();
    }

    @Override
    public void findPwd(String randomPassword, String memberId) throws ExecutionException, InterruptedException {

        boolean exists = memCollection.document(memberId).get().get().exists();
        if (exists) {
            MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
            memberEntity.setPwd(randomPassword);
            memCollection.document(memberId).set(memberEntity);
        } else {
            throw new NotExistMember();
        }

    }

    @Override
    public void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {

        // run an asynchronous transaction
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {

            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (memDocRef.get().get().exists()) {
                transaction.update(memDocRef, "pwd", pwd);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistMember;
            }
        });
        if (returnTypeApiFuture.get()==ReturnType.SUCCESS) {
            returnTypeApiFuture.get();
        } else {
            throw new NotExistMember();
        }
    }

    @Override
    public MemberDto getMember(String memberId) throws ExecutionException, InterruptedException {
        // asynchronously retrieve multiple documents
        boolean exists = memCollection.document(memberId).get().get().exists();
        if (exists) {
            return memCollection.document(memberId).get().get().toObject(MemberDto.class);
        } else {
            throw new NotExistMember();
        }
    }

    @Override
    public void modifyMember(String memberId, String nickName) throws ExecutionException, InterruptedException {

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            if (transaction.get(memDocRef).get().exists()) {
                transaction.update(memDocRef, "nickName", nickName);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistMember;
            }
        });
        if (returnTypeApiFuture.get()==ReturnType.SUCCESS) {
            returnTypeApiFuture.get();
        } else {
            throw new NotExistMember();
        }
    }

    @Override
    public void deleteMember(String memberId) throws ExecutionException, InterruptedException {

        MemberDto member = getMember(memberId);
        List<String> calMember = calCollection.document(member.getCalendarId()).get().get().toObject(CalendarEntity.class).getMemberId();
        calMember.remove(memberId);

        if (calMember.size() == 0) {
            // 펫, 일정 삭제
            List<String> petIdList = calCollection.document(member.getCalendarId()).get().get().toObject(CalendarEntity.class).getPetId();
            for (String petId : petIdList) {
                List<String> scheduleList = petCollection.document(petId).get().get().toObject(PetEntity.class).getSchedule();
                for (String schedule : scheduleList) {
                    scheduleCollection.document(schedule).delete();
                }
                petCollection.document(petId).delete();
            }
            // 사진 삭제
            List<QueryDocumentSnapshot> photoDocList = photoCollection.get().get().getDocuments();
            for (QueryDocumentSnapshot photoList : photoDocList) {
                if (photoList.toObject(PhotoEntity.class).getCalendarId().equals(member.getCalendarId())) {
                    photoCollection.document(photoList.getId()).delete();
                }
            }
            calCollection.document(member.getCalendarId()).delete();
        } else {
            calCollection.document(member.getCalendarId()).update("memberId", calMember);
        }

        List<QueryDocumentSnapshot> memDocList = memCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot docs : memDocList) {
            if (!(docs.getId()).equals(memberId)) {
                List<String> reportList = docs.toObject(MemberEntity.class).getReportList();
                reportList.remove(memberId);
                memCollection.document(docs.getId()).update("reportList", reportList);
            }
        }
        memCollection.document(memberId).delete();
    }

    @Override
    public void logout(String memberId) throws ExecutionException, InterruptedException  {

    }

    @Override
    public void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException {
        DocumentReference memDocRef = memCollection.document(memberId);
        DocumentReference calDocRef = calCollection.document(calendarId); // 변경할 캘린더
        MemberDto member = getMember(memberId);
        String beforeCalendarId = member.getCalendarId(); // 변경 전 캘린더
        DocumentReference beforeCalDocRef = calCollection.document(beforeCalendarId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            DocumentSnapshot beforeCalSnapshot = transaction.get(beforeCalDocRef).get();

            if (calSnapshot.exists() && beforeCalSnapshot.exists()) {
                // 새로운 달력에 멤버 추가
                List<String> afterMemberIdList = calDocRef.get().get().toObject(CalendarEntity.class).getMemberId();
                afterMemberIdList.add(memberId);
                transaction.update(calDocRef, "memberId", afterMemberIdList);

                // 이전 달력에서 멤버 삭제
                List<String> beforeMemberIdList = beforeCalDocRef.get().get().toObject(CalendarEntity.class).getMemberId();
                beforeMemberIdList.remove(memberId);
                transaction.update(beforeCalDocRef, "memberId", beforeMemberIdList);
                transaction.update(memDocRef, "calendarId", calendarId);

                if (beforeMemberIdList.size()==0) {
                    // 펫, 일정 삭제
                    List<String> petIdList = calCollection.document(beforeCalendarId).get().get().toObject(CalendarEntity.class).getPetId();
                    for (String petId : petIdList) {
                        List<String> scheduleList = petCollection.document(petId).get().get().toObject(PetEntity.class).getSchedule();
                        for (String schedule : scheduleList) {
                            scheduleCollection.document(schedule).delete();
                        }
                        petCollection.document(petId).delete();
                    }

                    // 사진 삭제
                    List<QueryDocumentSnapshot> photoDocList = photoCollection.get().get().getDocuments();
                    for (QueryDocumentSnapshot photoList : photoDocList) {
                        if (photoList.toObject(PhotoEntity.class).getCalendarId().equals(beforeCalendarId)) {
                            photoCollection.document(photoList.getId()).delete();
                        }
                    }

                    calCollection.document(beforeCalendarId).delete();
                }

                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistCalendarException;
            }
        });
        if (returnTypeApiFuture.get()==ReturnType.SUCCESS) {
            returnTypeApiFuture.get();
        } else {
            throw new NotExistCalendarException();
        }
    }

    @Override
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception {
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = db.runTransaction(transaction -> {
           if (transaction.get(memDocRef).get().exists()) {
               transaction.update(memDocRef, "token", refreshToken);
               return true;
           }
           return false;
        });
        if (!(boolean) future.get()) {
            throw new Exception();
        }
    }

    @Override
    public String getRefreshToken(String memberId) throws Exception {
        MemberEntity memDocRef = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        if (memDocRef == null) {
            throw new Exception();
        }
        return memDocRef.getToken();
    }

    @Override
    public void deleteRefreshToken(String memberId) throws Exception {
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = db.runTransaction(transaction -> {
            if (transaction.get(memDocRef).get().exists()) {
                transaction.update(memDocRef, "token", null);
                return true;
            }
            return false;
        });

        if (!(boolean) future.get()) {
            throw new Exception();
        }
    }
}
