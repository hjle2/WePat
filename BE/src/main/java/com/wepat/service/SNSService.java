package com.wepat.service;

import com.wepat.dto.PhotoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SNSService {
    List<PhotoDto> getSNS() throws ExecutionException, InterruptedException;

    PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    ResponseEntity<?> updateSNSLike(String photoId) throws ExecutionException, InterruptedException;

    ResponseEntity<?> reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException;

    List<PhotoDto> reportList() throws ExecutionException, InterruptedException;

    ResponseEntity<?> blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException;
}
