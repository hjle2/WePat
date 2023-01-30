package com.wepat.sns.repository;


import com.wepat.photo.PhotoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SNSRepository {
    List<PhotoDto> getSNS(String date) throws ExecutionException, InterruptedException;

    PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    void updateSNSLike(String photoId) throws ExecutionException, InterruptedException;

    void reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException;

    List<PhotoDto> reportList() throws ExecutionException, InterruptedException;

    void blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException;
}
