package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.MemberDto;
import com.wepat.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final String COLLECTION_NAME = "member";
    private final CollectionReference collection = FirestoreClient.getFirestore().collection(COLLECTION_NAME);
    @Override
    public MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException {
        logger.info("signUp called!");
        ApiFuture<WriteResult> future = collection.document(member.getMemberId()).set(member);
        return getMember(member.getMemberId());
    }

    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.info("signIn called!");
        DocumentReference docRef = collection.document(memberId);
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
            logger.debug("wrong debug");
            return null;
        }
    }

    @Override
    public MemberDto findId(String email) throws ExecutionException, InterruptedException {
        logger.debug("findId called!");

        // asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = collection.whereEqualTo("email", email).get();

        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (DocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(MemberDto.class));
            return document.toObject(MemberDto.class);
        }
        return null;
    }

    @Override
    public MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        logger.debug("modifyPwd called!");

        return null;
    }

    @Override
    public MemberDto getMember(String memberId) throws ExecutionException, InterruptedException {
        logger.debug("getMember called!");

        // asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = collection.whereEqualTo("memberid", memberId).get();

        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(MemberDto.class));
            return document.toObject(MemberDto.class);
        }
        return null;
    }

    @Override
    public MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        logger.debug("modifyMember called!");

        // asynchronously modify a document
        ApiFuture<WriteResult> writeResult = collection.document(member.getMemberId()).set(member);

        logger.debug("Update time : " + writeResult.get().getUpdateTime());
        return getMember(member.getMemberId());
    }

    @Override
    public MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException {
        logger.debug("deleteMember called!");

        MemberDto member = getMember(memberId);
        // asynchronously delete a document
        ApiFuture<WriteResult> writeResult = collection.document(memberId).delete();
        // ...
        logger.debug("Update time : " + writeResult.get().getUpdateTime());

        return member;
    }

    @Override
    public MemberDto logout(String memberId) throws ExecutionException, InterruptedException  {
        logger.debug("logout called!");
        return null;
    }

    @Override
    public MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException {
        logger.debug("warnMember called!");
        return null;
    }

    @Override
    public MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException {
        logger.debug("blockMember called!");
        return null;
    }
}
