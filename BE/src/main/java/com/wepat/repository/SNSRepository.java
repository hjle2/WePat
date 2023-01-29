package com.wepat.repository;

import com.wepat.dto.PhotoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SNSRepository {
    List<PhotoDto> getSNS() throws ExecutionException, InterruptedException;

    PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    void updateSNSLike(String photoId) throws ExecutionException, InterruptedException;

    void reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException;

    List<PhotoDto> reportList() throws ExecutionException, InterruptedException;

    void blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException;
}
