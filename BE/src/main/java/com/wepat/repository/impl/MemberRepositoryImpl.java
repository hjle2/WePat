package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.CalendarDto;
import com.wepat.dto.MemberDto;
import com.wepat.entity.CalendarEntity;
import com.wepat.entity.MemberEntity;
import com.wepat.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.concurrent.ExecutionException;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final String CALENDAR_COLLECTION = "calendar";
    private final String MEMBER_COLLECTION = "member";
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

        // run an asynchronous transaction
        db.runTransaction(transaction -> {
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get(); //입력한ID 값을 갖는 snapshot
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get(); //입력한 캘린더ID 값을 갖는 snapshot

            //memberId가 없고 calendarId가 있을 때
            if (!memSnapshot.exists() && calSnapshot.exists()) {
                System.out.println("signup 호출!!!");

                //멤버에 calendarID값 넣고
                member.setCalendarId(calDocRef.getId());
                //트랜잭션을 통해 생성
                transaction.create(memDocRef, new MemberEntity(member));
                System.out.println("member 생성 완료");

                //트랜잭션을 통해 얻은 calSnapshot의 member배열에 접근
                List<String> memberId = calSnapshot.toObject(CalendarEntity.class).getMemberId();
                //회원가입한 member의 Id를 포함하여 업데이트
                memberId.add(member.getMemberId());
                transaction.update(calDocRef, "memberId", memberId);


                // calendar가 이미 있으면, 캘린더 ID 에러 발생

                // member가 이미 있으면, 멤버 ID 에러 발생
                return "success";
            } else {
                logger.info("Fail!!");
                return "fail";
            }
        });
        // 트랜잭션 실행 결과를 반환
        return memDocRef.get().get().toObject(MemberEntity.class);
    }

    //캘린더 ID null일 때, 회원가입
    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUp Repo called!");
        logger.info(String.valueOf(member));
        System.out.println(member);

        // memberId인 document 를 가져옴(없으면 생성)
        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // calendar document 를 생성 (docId 는 랜덤값)
        final DocumentReference calDocRef = calCollection.document();
        logger.info("memDocRef " + memDocRef.getId());
        logger.info("calDocRef " + calDocRef.getId());
        // run an asynchronous transaction
        db.runTransaction(transaction -> {

            // memberId 인 document 를 가져옴
            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
//            DocumentSnapshot documentSnapshot = transaction.get(memDocRef).get().toObject(MemberEntity.class).getPwd();
//            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            logger.info("memSnapshot " + memSnapshot.getId());
//            logger.info("calSnapshot " + calSnapshot);
            if (!memSnapshot.exists()) {
                logger.info("create호출!!");
                // memberEntity 를 db 에 추가 함
                member.setCalendarId(calDocRef.getId());
                transaction.create(memDocRef, new MemberEntity(member));
                // calendarEntity(memberId 를 갖는)를 db에 추가 함
                transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));

//                transaction.get(memDocRef).get().toObject(MemberEntity.class).setCalendarId(String.valueOf(calDocRef));
                logger.info("success");
                return "success";
            } else {
                logger.info("Fail!!");
                return "fail";
            }
        });
        // 트랜잭션 실행 결과를 반환
        return memDocRef.get().get().toObject(MemberEntity.class);
    }

    @Override
    public MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("signIn called!");

        final DocumentReference memDocRef = memCollection.document(memberId);
        // run an asynchronous transaction
        db.runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (!memSnapshot.exists() && memberEntity.getPwd().equals(pwd)) {
                // 로그인 성공 처리 ??
                return "success";
            } else {
                logger.info("throw exception");
                throw new InterruptedException();
            }
        });
        // 트랜잭션 실행 결과를 반환
        MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);
        System.out.println(memberEntity);
        if (memberEntity == null) {
            throw new InterruptedException();
        }
        return memberEntity;
    }

    @Override
    public MemberEntity findId(String email) throws ExecutionException, InterruptedException {
        logger.info("findId called!");

        // asynchronously retrieve the document
        List<MemberEntity> memberEntity = memCollection.whereEqualTo("email", email).get().get().toObjects(MemberEntity.class);

        if (memberEntity.size() > 0) {
            return memberEntity.get(0);
        } else {
            // Exception ??
            return null;
        }
    }

    @Override
    public MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("modifyPwd called!");

        // run an asynchronous transaction
        final DocumentReference memDocRef = memCollection.document(memberId);

        db.runTransaction( transaction -> {

            MemberEntity memberEntity = transaction.get(memDocRef).get().toObject(MemberEntity.class);

            if (memDocRef.get().get().exists()) {
                transaction.update(memDocRef, "pwd", pwd);
            }
            return null;
            // ??
        });
        return memDocRef.get().get().toObject(MemberEntity.class);
    }

    @Override
    public MemberEntity getMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("getMember called!");

        // asynchronously retrieve multiple documents
        MemberEntity memberEntity = memCollection.document(memberId).get().get().toObject(MemberEntity.class);

        return memberEntity;
    }

    @Override
    public MemberEntity modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("modifyMember called!");


        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        // asynchronously modify a document
        // run an asynchronous transaction
        db.runTransaction( transaction -> {

            if (transaction.get(memDocRef).get().exists()) {
                MemberEntity memberEntity = new MemberEntity(member);
                transaction.set(memDocRef, memberEntity);
                return "success";
            }
            // ??
            return null;
        });
        return memDocRef.get().get().toObject(MemberEntity.class);
    }

    @Override
    public MemberEntity deleteMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("deleteMember called!");

        MemberEntity member = getMember(memberId);
        // asynchronously delete a document
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
        return null;
    }

    @Override
    public MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("blockMember called!");
        return null;
    }

    @Override
    public void findPwd(String randomPassword, String memberId) throws ExecutionException, InterruptedException {
        logger.debug("findPwd called!!!");
        MemberDto memberDto = memCollection.document(memberId).get().get().toObject(MemberDto.class);
        memberDto.setPwd(randomPassword);
        memCollection.document(memberId).set(memberDto);
    }
}
