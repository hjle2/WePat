package com.wepat.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CalendarController {
    private final String COLLECTION_NAME = "calendar";
    @GetMapping("/test")
    public ResponseEntity<?> testCalendar() throws ExecutionException, InterruptedException {
//        Schedule schedule = new Schedule();
//        schedule.setMemo("memo");
//        schedule.setPeriod(3);
//        schedule.setNPeriod(10);
//        schedule.setWeight(3.5);
//        List<String> photoList = new ArrayList<>();
//        photoList.add("hihi");
//        schedule.setPhotoList(photoList);
//        schedule.setTitle("happy byul");
//        schedule.setDate(Timestamp.now().toSqlTimestamp());
//
//        System.out.println(schedule);
//
//        Firestore db = FirestoreClient.getFirestore();
//
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        String docId = "";
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println(document.getId());
//            docId = document.getId();
//        }
//        ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture
//                = db.collection(COLLECTION_NAME).document().set(schedule);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
