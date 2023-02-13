package com.wepat.member.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.schedule.CalendarEntity;
import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;
import com.wepat.pet.PetEntity;
import com.wepat.photo.PhotoEntity;
import com.wepat.exception.member.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;
import java.util.List;

@Repository
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {

    public enum ReturnType {
        SUCCESS, ExistEmailException, ExistIdException, NotExistCalendarException, IdWriteException, BlockMemberException,
        PwdWriteException, AlreadyAloneCalendarException, NotExistMemberException, WrongPwdException
    }
    private static final String CALENDAR_COLLECTION = "calendar";
    private static final String MEMBER_COLLECTION = "member";
    private static final String PET_COLLECTION = "pet";
    private static final String SCHEDULE_COLLECTION = "schedule";
    private static final String PHOTO_COLLECTION = "photo";
    private static final int zeroSize = 0;

    //캘린더 ID가 있을 때, 회원가입 (ID 값이 틀렸다고 에러)
    @Override
    public void signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        final DocumentReference calDocRef = calCollection.document(member.getCalendarId());

        List<MemberEntity> emailList = memCollection.whereEqualTo("email", member.getEmail()).get().get().toObjects(MemberEntity.class);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get(); //입력한ID 값을 갖는 snapshot
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get(); //입력한 캘린더ID 값을 갖는 snapshot

            if (emailList.size() > zeroSize) {
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
        if (future.get() == ReturnType.ExistEmailException) {
            throw new ExistEmailException();
        } else if (future.get() == ReturnType.ExistIdException) {
            throw new ExistIdException();
        } else if (future.get() == ReturnType.NotExistCalendarException) {
            throw new NotExistCalendarException();
        }
    }

    //캘린더 ID null일 때, 회원가입
    @Override
    public void signUp(MemberDto member) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);


        // memberId인 document 를 가져옴(없으면 생성)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // calendar document 를 생성 (docId 는 랜덤값)
        final DocumentReference calDocRef = calCollection.document();

        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        List<MemberEntity> emailList = memCollection.whereEqualTo("email", member.getEmail()).get().get().toObjects(MemberEntity.class);

        // run an asynchronous transaction
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            // memberId 인 document 를 가져옴
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();

            if (emailList.size() > zeroSize) {
                return ReturnType.ExistEmailException;
            } else {
                if (memSnapshot.exists()) {
                    return ReturnType.ExistIdException;
                } else {
                    // memberEntity 를 FirestoreClient.getFirestore() 에 추가 함
                    member.setCalendarId(calDocRef.getId());
                    transaction.create(memDocRef, new MemberEntity(member));
                    // calendarEntity(memberId 를 갖는)를 FirestoreClient.getFirestore()에 추가 함
                    transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));
                    return ReturnType.SUCCESS;
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (future.get() == ReturnType.ExistEmailException) {
            throw new ExistEmailException();
        } else if (future.get() == ReturnType.ExistIdException) {
            throw new ExistIdException();
        }
    }

    @Override
    public void socialSignUp(MemberDto member) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);


        // memberId인 document 를 가져옴(없으면 생성)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // calendar document 를 생성 (docId 는 랜덤값)
        final DocumentReference calDocRef = calCollection.document();

        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        List<MemberEntity> emailList = memCollection.whereEqualTo("email", member.getEmail()).get().get().toObjects(MemberEntity.class);

        // run an asynchronous transaction
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            // memberId 인 document 를 가져옴
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();

            if (emailList.size() > zeroSize) {
                return ReturnType.ExistEmailException;
            } else {
                if (memSnapshot.exists()) {
                    return ReturnType.ExistIdException;
                } else {
                    // memberEntity 를 FirestoreClient.getFirestore() 에 추가 함
                    member.setCalendarId(calDocRef.getId());
                    transaction.create(memDocRef, new MemberEntity(member));
                    // calendarEntity(memberId 를 갖는)를 FirestoreClient.getFirestore()에 추가 함
                    transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));
                    return ReturnType.SUCCESS;
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (future.get() == ReturnType.ExistEmailException) {
            throw new ExistEmailException();
        } else if (future.get() == ReturnType.ExistIdException) {
            throw new ExistIdException();
        }
    }

    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberDto memberDto = transaction.get(memDocRef).get().toObject(MemberDto.class);

//            System.out.println(memberDto.getPwd());
//            System.out.println(pwd);

            if (!memSnapshot.exists()) { // 멤버ID가 없을 경우
                return ReturnType.IdWriteException;

            } else if (memSnapshot.toObject(MemberEntity.class).isBlock()) { //차단된 계정
                return ReturnType.BlockMemberException;

            } else if (memSnapshot.exists() && memberDto.getPwd().equals(pwd)) { //해당 멤버ID가 있고, pwd가 같다면 로그인 성공
                return memberDto;

            } else { //비밀번호가 다르다면
                return ReturnType.PwdWriteException;

            }
        });
        // 트랜잭션 실행 결과를 반환
        if (future.get() == ReturnType.IdWriteException) {
            throw new IdWriteException();
        } else if (future.get() == ReturnType.BlockMemberException) {
            throw new BlockMemberException();
        } else if (future.get() == ReturnType.PwdWriteException) {
            throw new PwdWriteException();
        } else {
            return (MemberDto) future.get();
        }
    }

    @Override
    public MemberDto socialSignIn(String memberId, int social) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (!memSnapshot.exists()) { // 멤버ID가 없을 경우
                return ReturnType.IdWriteException;

            } else if (memSnapshot.toObject(MemberEntity.class).isBlock()) { //차단된 계정
                return ReturnType.BlockMemberException;
            }
            else if ( !(memberEntity.getSocial()==(social))) { //해당 SNS로 가입된 아이디아니면
                return ReturnType.NotExistMemberException;//존재안하는 아이디로 판단
            }
            else { //다 통과
                MemberDto memberDto = memCollection.document(memberId).get().get().toObject(MemberDto.class);
                return memberDto;
            }
        });
        // 트랜잭션 실행 결과를 반환ㅐ
        if (future.get() == ReturnType.IdWriteException) {
            throw new IdWriteException();
        } else if (future.get() == ReturnType.BlockMemberException) {
            throw new BlockMemberException();
        } else if (future.get() == ReturnType.NotExistMemberException) {
            throw new NotExistMemberException();
        } else {
            return (MemberDto) future.get();
        }
    }

    @Override
    public String findId(String email) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        List<QueryDocumentSnapshot> memDocs = memCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot docs : memDocs) {
            String dtoEmail = docs.toObject(MemberEntity.class).getEmail();
            if (dtoEmail != null && dtoEmail.equals(email)) {
                return docs.getId();
            }
        }
        throw new NotExistEmailException();
    }

    @Override
    public void changePwdToRandom(String randomPassword, String memberId) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberDto memberDto = transaction.get(memDocRef).get().toObject(MemberDto.class);

            if (!memSnapshot.exists()) { // 멤버ID가 없을 경우
                return ReturnType.IdWriteException;

            } else if (memSnapshot.toObject(MemberEntity.class).isBlock()) { //차단된 계정
                return ReturnType.BlockMemberException;

            } else{
                return memberDto;
            }
        });
        // 트랜잭션 실행 결과를 반환
        if (future.get() == ReturnType.IdWriteException) {
            throw new IdWriteException();
        } else if (future.get() == ReturnType.BlockMemberException) {
            throw new BlockMemberException();
        } else {
            MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
            memberEntity.setPwd(randomPassword);
            memCollection.document(memberId).set(memberEntity);
        }
    }

    @Override
    public void modifyPwdById(String memberId, String originPwd, String newPwd) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {

            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);
            if(memberEntity.getPwd().equals(originPwd)) {//현재 비번이 맞으면

                transaction.update(memDocRef, "pwd", newPwd);
                return ReturnType.SUCCESS;
            }
            else{//현재 비번이 틀리면
                return ReturnType.WrongPwdException;
            }
        });
        if (future.get() == ReturnType.SUCCESS) {
        } else {
            throw new WrongPwdException();
        }
    }

    @Override
    public MemberDto getMemberById(String memberId) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        DocumentReference memDocRef = memCollection.document(memberId);

        MemberDto memberdto = memDocRef.get().get().toObject(MemberDto.class);
        if (memberdto != null) {
            memberdto.setMemberId(memberId);
            return memberdto;
        } else {
            throw new NotExistMemberException();
        }
    }

    @Override
    public void modifyMemberById(String memberId, String nickName) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            if (transaction.get(memDocRef).get().exists()) {
                transaction.update(memDocRef, "nickName", nickName);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistMemberException;
            }
        });
        if (future.get() == ReturnType.SUCCESS) {
        } else {
            throw new NotExistMemberException();
        }
    }

    @Override
    public void modifyMemberPhotoById(String memberId, String photoUrl) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        final DocumentReference memDocRef = memCollection.document(memberId);
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            if (transaction.get(memDocRef).get().exists()) {
                transaction.update(memDocRef, "photoUrl", photoUrl);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistMemberException;
            }
        });
        if (future.get() == ReturnType.NotExistMemberException) {
            throw new NotExistMemberException();
        }
    }

    @Override
    public void deleteMember(String memberId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);

        String calendarId = getMemberById(memberId).getCalendarId();
        DocumentReference calendarDocRef = calCollection.document(calendarId);

        CalendarEntity calendar = calendarDocRef.get().get().toObject(CalendarEntity.class);

        // calendar에 member가 없는 경우 데이터 삭제
        if (calendar != null && calendar.getMemberId().size() == 0) {
            // 펫, 일정 삭제
            List<String> petIdList = calendar.getPetId();
            for (String petId : petIdList) {
                List<String> scheduleList = petCollection.document(petId).get().get().toObject(PetEntity.class).getSchedule();
                for (String schedule : scheduleList) {
                    // calendar에 연결된 펫의 일정 삭제
                    scheduleCollection.document(schedule).delete();
                }
                // calendar에 연결된 펫 정보 삭제
                petCollection.document(petId).delete();
            }
            // 사진 삭제
            List<QueryDocumentSnapshot> photoDocList = photoCollection.get().get().getDocuments();
            for (QueryDocumentSnapshot photoList : photoDocList) {
                String photoDtoCalendarId = photoList.toObject(PhotoEntity.class).getCalendarId();
                if (photoDtoCalendarId != null && photoDtoCalendarId.equals(calendarId)) {
                    photoCollection.document(photoList.getId()).delete();
                }
            }
            // calendar삭제
            calCollection.document(calendarId).delete();
        } else {
            calCollection.document(calendarId).update("memberId", calendar.getMemberId());
        }

        // 탈퇴한 회원이 신고한 계정들 신고 기록 무효화
        List<QueryDocumentSnapshot> memDocList = memCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot docs : memDocList) {
            if (!(docs.getId()).equals(memberId)) {
                List<String> reportList = docs.toObject(MemberEntity.class).getReportList();
                if (reportList.contains(memberId));
                    reportList.remove(memberId);
                memCollection.document(docs.getId()).update("reportList", reportList);
            }
        }
        memCollection.document(memberId).delete();
    }

    @Override
    public void modifyCalendarById(String memberId, String calendarId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);

        DocumentReference memDocRef = memCollection.document(memberId);
        DocumentReference calDocRef = calCollection.document(calendarId); // 변경할 캘린더

        MemberDto member = getMemberById(memberId);
        String beforeCalendarId = member.getCalendarId(); // 변경 전 캘린더
        DocumentReference beforeCalDocRef = calCollection.document(beforeCalendarId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
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

                if (beforeMemberIdList.size() == 0) {
                    // 펫, 일정 삭제
                    List<String> petIdList = beforeCalDocRef.get().get().toObject(CalendarEntity.class).getPetId();
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
                        String calendarIdCompare = photoList.toObject(PhotoEntity.class).getCalendarId();

                        if (calendarIdCompare != null && calendarIdCompare.equals(beforeCalendarId)) {
                            photoCollection.document(photoList.getId()).delete();
                        }
                    }

                    beforeCalDocRef.delete();
                }

                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistCalendarException;
            }
        });
        if (future.get() == ReturnType.SUCCESS) {
        } else {
            throw new NotExistCalendarException();
        }
    }

    @Override
    public void addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException {
    }

    @Override
    public void addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException {
    }

    @Override
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
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
    public void deleteRefreshToken(String memberId) throws Exception {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        final DocumentReference memDocRef = memCollection.document(memberId);

//        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
//            if (transaction.get(memDocRzef).get().exists()) {
//                transaction.update(memDocRef, "token", null);
//                return true;
//            }
//            return false;
//        });

//        if (!(boolean) future.get()) {
//            throw new Exception();
//        }
    }

    @Override
    public void addCalendarById(String memberId) throws ExecutionException, InterruptedException {
        CollectionReference memCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        String calendarId = getMemberById(memberId).getCalendarId();
        DocumentReference memDocRef = memCollection.document(memberId);
        DocumentReference calDocRef = calCollection.document(calendarId);
        DocumentReference randomCal = calCollection.document();
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            if (calDocRef.get().get().toObject(CalendarEntity.class).getMemberId().size() == 1) {
                return ReturnType.AlreadyAloneCalendarException;
            } else {
                CalendarEntity calendarEntity = new CalendarEntity(memberId);
                transaction.create(randomCal, calendarEntity);
                transaction.update(memDocRef, "calendarId", randomCal.getId());

                List<String> memberIdList = calDocRef.get().get().toObject(CalendarEntity.class).getMemberId();
                memberIdList.remove(memberId);
                transaction.update(calDocRef, "memberId", memberIdList);
                return ReturnType.SUCCESS;
            }
        });
        if (future.get() == ReturnType.AlreadyAloneCalendarException) {
            throw new AlreadyAloneCalendarException();
        }
    }
}
