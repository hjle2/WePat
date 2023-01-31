package com.wepat.sns.repository;


import com.wepat.photo.PhotoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SNSRepository {
    List<PhotoDto> getSNS(String date) throws ExecutionException, InterruptedException;

    PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    void updateSNSLikeByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    void reportSNSByPhotoId(String photoId, String memberId) throws ExecutionException, InterruptedException;

    List<PhotoDto> getReportedList() throws ExecutionException, InterruptedException;

    void blockSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;
}
