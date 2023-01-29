package com.wepat.sns.service;

import com.wepat.photo.PhotoDto;
import com.wepat.sns.repository.SNSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class SNSServiceImpl implements SNSService {

    private final SNSRepository snsRepository;

    @Override
    public List<PhotoDto> getSNS() throws ExecutionException, InterruptedException {
        return snsRepository.getSNS();
    }

    @Override
    public PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        return snsRepository.getSNSByPhotoId(photoId);
    }

    @Override
    public void updateSNSLike(String photoId) throws ExecutionException, InterruptedException {
        snsRepository.updateSNSLike(photoId);
    }

    @Override
    public void reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException {
        snsRepository.reportSNS(photoId, memberId);
    }

    @Override
    public List<PhotoDto> reportList() throws ExecutionException, InterruptedException {
        return snsRepository.reportList();
    }

    @Override
    public void blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException {
        snsRepository.blockSNSByPhoto(photoId);
    }
}
