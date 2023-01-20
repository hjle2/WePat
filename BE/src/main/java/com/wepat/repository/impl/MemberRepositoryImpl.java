package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
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

    @Override
    public MemberEntity signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUp called!");

        final DocumentReference memDocRef = memCollection.document(member.getMemberId());
        final DocumentReference calDocRef = calCollection.document();

        // run an asynchronous transaction
        db.runTransaction(transaction -> {

            DocumentSnapshot memSnapshot = transaction.get(memDocRef).get();
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();

            if (!memSnapshot.exists() && !calSnapshot.exists()) {

                transaction.create(memDocRef, new MemberEntity(member));
                transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));

                // calendar가 이미 있으면, 캘린더 ID 에러 발생

                // member가 이미 있으면, 멤버 ID 에러 발생
                return "success";
            } else {
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
