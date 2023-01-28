package com.wepat.service.impl;

import com.wepat.dto.PhotoDto;
import com.wepat.repository.SNSRepository;
import com.wepat.service.SNSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> updateSNSLike(String photoId) throws ExecutionException, InterruptedException {
        return snsRepository.updateSNSLike(photoId);
    }

    @Override
    public ResponseEntity<?> reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException {
        return snsRepository.reportSNS(photoId, memberId);
    }

    @Override
    public List<PhotoDto> reportList() throws ExecutionException, InterruptedException {
        return snsRepository.reportList();
    }
}
