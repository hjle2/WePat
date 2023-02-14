package com.wepat.sns.service;

import com.wepat.photo.PhotoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SNSService {
    List<PhotoDto> getSNS(int before) throws ExecutionException, InterruptedException;

    PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    int updateSNSLikeByPhotoId(String photoId) throws ExecutionException, InterruptedException;

    void reportSNSByPhotoId(String photoId, String memberId) throws ExecutionException, InterruptedException;

    List<PhotoDto> getReportedList() throws ExecutionException, InterruptedException;

    void blockSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException;
}
