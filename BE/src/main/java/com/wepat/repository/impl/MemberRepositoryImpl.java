package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.MemberDto;
import com.wepat.entity.CalendarEntity;
import com.wepat.entity.MemberEntity;
import com.wepat.exception.member.*;
import com.wepat.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String CALENDAR_COLLECTION = "calendar";
    private final static String MEMBER_COLLECTION = "member";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);
    private final CollectionReference memCollection = db.collection(MEMBER_COLLECTION);

    //캘린더 ID가 있을 때, 회원가입 (ID 값이 틀렸다고 에러)
    @Override
    public MemberEntity signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUpWithCalendar Repo called!");

        //memberId(member Collection)와 CalendarId(calendar Collection)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        final DocumentReference calDocRef = calCollection.document(member.getCalendarId());

        logger.info("memDocRef " + memDocRef.getId()); //입력한 ID
        logger.info("calDocRef " + calDocRef.getId()); //입력한 캘린더ID

        boolean EmailCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        EmailCheck = documents.stream().anyMatch(docs -> (docs.toObject(MemberEntity.class).getEmail()).equals(member.getEmail()));

        // run an asynchronous transaction
        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get(); //입력한ID 값을 갖는 snapshot
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get(); //입력한 캘린더ID 값을 갖는 snapshot

            if (EmailCheck) {
                return "ExistEmailException";
            } else {
                if (memSnapshot.exists()) {
                    return "ExistIdException";
                } else {
                    if (calSnapshot.exists()) {
                        member.setCalendarId(calDocRef.getId());
                        transaction.create(memDocRef, new MemberEntity(member));

                        //트랜잭션을 통해 얻은 calSnapshot의 member배열에 접근
                        List<String> memberId = calSnapshot.toObject(CalendarEntity.class).getMemberId();
                        //회원가입한 member의 Id를 포함하여 업데이트
                        memberId.add(member.getMemberId());
                        transaction.update(calDocRef, "memberId", memberId);

                        return "success";
                    } else {
                        return "NotExistCalendarException";
                    }
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if ((stringApiFuture.get()).equals("ExistEmailException")) {
            throw new ExistEmailException("이미 회원가입된 이메일입니다!");
        } else if ((stringApiFuture.get()).equals("ExistIdException")) {
            throw new ExistIdException("이미 존재하는 아이디입니다!");
        } else if ((stringApiFuture.get()).equals("NotExistCalendarException")) {
            throw new NotExistCalendarException("존재하지 않는 코드입니다!");
        } else {
            return memCollection.document(member.getMemberId()).get().get().toObject(MemberEntity.class);
        }
    }

    //캘린더 ID null일 때, 회원가입
    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUp Repo called!");

        // memberId인 document 를 가져옴(없으면 생성)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // calendar document 를 생성 (docId 는 랜덤값)
        final DocumentReference calDocRef = calCollection.document();
        logger.info("memDocRef " + memDocRef.getId());
        logger.info("calDocRef " + calDocRef.getId());

        boolean EmailCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        EmailCheck = documents.stream().anyMatch(docs -> (docs.toObject(MemberEntity.class).getEmail()).equals(member.getEmail()));

        // run an asynchronous transaction
        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {

            // memberId 인 document 를 가져옴
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();

            if (EmailCheck) {
                return "ExistEmailException";
            } else {
                if (memSnapshot.exists()) {
                    return "ExistIdException";
                } else {
                    // memberEntity 를 db 에 추가 함
                    member.setCalendarId(calDocRef.getId());
                    transaction.create(memDocRef, new MemberEntity(member));
                    // calendarEntity(memberId 를 갖는)를 db에 추가 함
                    transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));
                    return "success";
                }
            }
        });
        // 트랜잭션 실행 결과를 반환
        if ((stringApiFuture.get()).equals("ExistEmailException")) {
            throw new ExistEmailException("이미 회원가입된 이메일입니다!");
        } else if ((stringApiFuture.get()).equals("ExistIdException")) {
            throw new ExistIdException("이미 존재하는 아이디입니다!");
        } else {
            return memCollection.document(member.getMemberId()).get().get().toObject(MemberEntity.class);
        }
    }

    @Override
    public MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("signIn called!");

        final DocumentReference memDocRef = memCollection.document(memberId);
        // run an asynchronous transaction
        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (!memSnapshot.exists()) { // 멤버ID가 없을 경우
                return "IdWriteException";
            } else if (memSnapshot.exists() && memberEntity.getPwd().equals(pwd)) { //해당 멤버ID가 있고, pwd가 같다면 로그인 성공
                logger.info("success");
                return "success";
            } else { //비밀번호가 다르다면
                logger.info("throw exception");
                return "PwdWriteException";
            }
        });

        // 트랜잭션 실행 결과를 반환
        if ((stringApiFuture.get()).equals("IdWriteException")) {
            throw new IdWriteException("존재하지 않는 아이디입니다!");
        } else if ((stringApiFuture.get()).equals("PwdWriteException")) {
            throw new PwdWriteException("비밀번호가 일치하지 않습니다!");
        } else {
            return memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        }
    }

    @Override
    public MemberEntity findId(String email) throws ExecutionException, InterruptedException {
        logger.info("findId called!");

        // asynchronously retrieve the document
        List<MemberEntity> memberEntity = memCollection.whereEqualTo("email", email).get().get().toObjects(MemberEntity.class);

        if (memberEntity.size() > 0) {
            System.out.println(memberEntity.get(0));
            System.out.println(memberEntity);
            return memberEntity.get(0);
        } else {
            // Exception ??
            logger.info("NotExistEmailException");
            throw new NotExistEmailException("존재하지 않는 회원입니다!");
        }
    }

    @Override
    public void findPwd(String randomPassword, String memberId) throws ExecutionException, InterruptedException {
        logger.debug("findPwd called!!!");

        boolean memberIdCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        memberIdCheck = documents.stream().anyMatch(docs -> (docs.getId()).equals(memberId));

        if (memberIdCheck) {
            MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
            memberEntity.setPwd(randomPassword);
            memCollection.document(memberId).set(memberEntity);
        } else {
            throw new NotExistMember("존재하지 않는 회원입니다!");
        }

    }

    @Override
    public MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("modifyPwd called!");

        // run an asynchronous transaction
        final DocumentReference memDocRef = memCollection.document(memberId);

        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {

            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (memDocRef.get().get().exists()) {
                transaction.update(memDocRef, "pwd", pwd);
                return "success";
            } else {
                return "NotExistMember";
            }
        });
        if ((stringApiFuture.get()).equals("success")) {
            return memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        } else {
            throw new NotExistMember("존재하지 않는 회원입니다!");
        }
    }

    @Override
    public MemberEntity getMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("getMember called!");

        // asynchronously retrieve multiple documents
        boolean memberIdCheck;
        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        memberIdCheck = documents.stream().anyMatch(docs -> (docs.getId()).equals(memberId));
        if (memberIdCheck) {
            return memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        } else {
            throw new NotExistMember("존재하지 않는 회원입니다!");
        }
    }

    @Override
    public MemberEntity modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("modifyMember called!");

        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // asynchronously modify a document
        // run an asynchronous transaction
        ApiFuture<?> memberEntityApiFuture = db.runTransaction(transaction -> {

            if (transaction.get(memDocRef).get().exists()) {
                MemberEntity memberEntity = new MemberEntity(member);
                memberEntity.setCalendarId(member.getCalendarId());
                transaction.set(memDocRef, memberEntity);
                System.out.println("modifyMember Transaction호출!!>>>");
                System.out.println("Transaction내부 >>> " + memCollection.document(member.getMemberId()).get().get().toObject(MemberEntity.class));
                return "success";
            } else {
                return "fail";
            }
        });

        if (memberEntityApiFuture.get().equals("success")) {
            return memCollection.document(member.getMemberId()).get().get().toObject(MemberEntity.class);
        } else {
            throw new RuntimeException("서버 오류");
        }
    }

    @Override
    public MemberEntity deleteMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("deleteMember called!");

        MemberEntity member = getMember(memberId);
        // asynchronously delete a document
        List<String> calMember = calCollection.document(member.getCalendarId()).get().get().toObject(CalendarEntity.class).getMemberId();
        calMember.remove(memberId);

        if (calMember.size()==0) {
            calCollection.document(member.getCalendarId()).delete();
        } else {
            calCollection.document(member.getCalendarId()).update("memberId", calMember);
        }
        System.out.println("호출");

        List<QueryDocumentSnapshot> documents = memCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot docs : documents) {
            if (!(docs.getId()).equals(memberId)) {
                System.out.println(docs.getId());
                List<String> warnMemberList = docs.toObject(MemberEntity.class).getWarnMemberList();
                warnMemberList.remove(memberId);
                memCollection.document(docs.getId()).update("warnMemberList", warnMemberList);
                List<String> blockMemberList = docs.toObject(MemberEntity.class).getBlockMemberList();
                blockMemberList.remove(memberId);
                memCollection.document(docs.getId()).update("blockMemberList", blockMemberList);
            }
        }

        ApiFuture<WriteResult> writeResult = memCollection.document(memberId).delete();
        // ...
        logger.info("Update time : " + writeResult.get().getUpdateTime());

        return member;
    }

    @Override
    public MemberEntity logout(String memberId) throws ExecutionException, InterruptedException  {
        logger.info("logout called!");
        return null;
    }

    @Override
    public MemberEntity warnMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("warnMember called!");
        MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        List<String> warnMemberList = memberEntity.getWarnMemberList();
        System.out.println(warnMemberList);
        return memberEntity;
    }

    @Override
    public MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("blockMember called!");
        MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        List<String> blockMemberList = memberEntity.getBlockMemberList();
        System.out.println(blockMemberList);
        return memberEntity;
    }

    @Override
    public MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException {

        final DocumentReference memDocRef = memCollection.document(memberId);
        final DocumentReference warnMemDocRef = memCollection.document(warnMemberId);
        System.out.println("addWarnMember 호출!!!!");

        db.runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            DocumentSnapshot warnMemSnapShot = transaction.get(warnMemDocRef).get();
            if (memSnapshot.exists() && warnMemSnapShot.exists()) {
                List<String> warnMemberList = memDocRef.get().get().toObject(MemberEntity.class).getWarnMemberList();
                if (!(warnMemberList.contains(warnMemberId))) {
                    warnMemberList.add(warnMemberId);
                    transaction.update(memDocRef, "warnMemberList", warnMemberList);
                    return memSnapshot.toObject(MemberEntity.class);
                } else {
                    logger.info("이미 신고한 회원입니다!");
                    return "fail";
                }
            } else {
                logger.info("존재하지 않는 회원입니다!");
                return "fail";
            }
        });
        System.out.println("repo>>>> " + memDocRef.get().get().toObject(MemberEntity.class));
        return memDocRef.get().get().toObject(MemberEntity.class);
    }

    @Override
    public MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException {

        final DocumentReference memDocRef = memCollection.document(memberId);
        final DocumentReference blockMemDocRef = memCollection.document(blockMemberId);

        db.runTransaction(transaction -> {
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            DocumentSnapshot blockMemSnapShot = transaction.get(blockMemDocRef).get();
            if (memSnapshot.exists() && blockMemSnapShot.exists()) {
                List<String> blockMemberList = memDocRef.get().get().toObject(MemberEntity.class).getBlockMemberList();
                if (!(blockMemberList.contains(blockMemberId))) {
                    blockMemberList.add(blockMemberId);
                    transaction.update(memDocRef, "blockMemberList", blockMemberList);
                    return "success";
                } else {
                    logger.info("이미 차단한 회원입니다!");
                    return "fail";
                }
            } else {
                logger.info("존재하지 않는 회원입니다!");
                return "fail";
            }
        });
        return memDocRef.get().get().toObject(MemberEntity.class);
    }
}
