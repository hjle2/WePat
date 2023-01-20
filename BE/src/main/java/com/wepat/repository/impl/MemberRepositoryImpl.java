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
    public MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUp called!");

        DocumentReference memDocRef = memCollection.document(member.getMemberId());
        DocumentReference calDocRef = calCollection.document(member.getMemberId());

        // run an asynchronous transaction
        ApiFuture<Void> futureTransaction =
                db.runTransaction(
                        transaction -> {
                            // retrieve document and increment population field
                            if (memCollection.whereEqualTo("memberid", member.getMemberId()).get().get().toObjects(MemberEntity.class).isEmpty()) {
                                transaction.create(memDocRef, member);
                                transaction.create(calDocRef, new CalendarEntity(member.getMemberId()));
                            } else {
                                throw new InterruptedException();
                            }
                            return null;
                        });
        // block on transaction operation using transaction.get()

        // Initialize doc
//        ApiFuture<WriteResult> future = memCollection.document(member.getMemberId()).set(member);

        logger.info(calDocRef.getId());
        return getMember(member.getMemberId());
    }

    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("signIn called!");

        DocumentReference docRef = memCollection.document(memberId);
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // block on response
        DocumentSnapshot document = future.get();

        MemberDto member = null;
        if (document.exists()) {
            // convert document to POJO
            member = document.toObject(MemberDto.class);
            if (member.getPwd().equals(pwd)) {
                return member;
            } else {
                return null;
            }
        } else {
            logger.info("wrong info");
            return null;
        }
    }

    @Override
    public MemberDto findId(String email) throws ExecutionException, InterruptedException {
        logger.info("findId called!");

        // asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = memCollection.whereEqualTo("email", email).get();

        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (DocumentSnapshot document : documents) {
            logger.info(document.getId() + " => " + document.toObject(MemberDto.class));
            return document.toObject(MemberDto.class);
        }
        return null;
    }

    @Override
    public MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("modifyPwd called!");

        return null;
    }

    @Override
    public MemberDto getMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("getMember called!");

        // asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = memCollection.whereEqualTo("memberid", memberId).get();

        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            logger.info(document.getId() + " => " + document.toObject(MemberDto.class));
            return document.toObject(MemberDto.class);
        }
        return null;
    }

    @Override
    public MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("modifyMember called!");

        // asynchronously modify a document
        ApiFuture<WriteResult> writeResult = memCollection.document(member.getMemberId()).set(member);

        logger.info("Update time : " + writeResult.get().getUpdateTime());
        return getMember(member.getMemberId());
    }

    @Override
    public MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("deleteMember called!");

        MemberDto member = getMember(memberId);
        // asynchronously delete a document
        ApiFuture<WriteResult> writeResult = memCollection.document(memberId).delete();
        // ...
        logger.info("Update time : " + writeResult.get().getUpdateTime());

        return member;
    }

    @Override
    public MemberDto logout(String memberId) throws ExecutionException, InterruptedException  {
        logger.info("logout called!");
        return null;
    }

    @Override
    public MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException {
        logger.info("warnMember called!");
        return null;
    }

    @Override
    public MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException {
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
