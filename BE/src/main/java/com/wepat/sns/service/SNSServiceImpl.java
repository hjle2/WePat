package com.wepat.sns.service;

import com.wepat.photo.PhotoDto;
import com.wepat.sns.repository.SNSRepository;
import com.wepat.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class SNSServiceImpl implements SNSService {
    private final SNSRepository snsRepository;
    private final int CALENDAR_UNIT = 1;

    @Override
    public List<PhotoDto> getSNS(int before) throws ExecutionException, InterruptedException {
        Date date = new Date();
        date = DateUtil.addDays(date, -before, Calendar.DATE);
        String strDate  = DateUtil.getStringDate(date);

        return snsRepository.getSNS(strDate);
    }

    @Override
    public PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        return snsRepository.getSNSByPhotoId(photoId);
    }

    @Override
    public void updateSNSLikeByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        snsRepository.updateSNSLikeByPhotoId(photoId);
    }

    @Override
    public void reportSNSByPhotoId(String photoId, String memberId) throws ExecutionException, InterruptedException {
        snsRepository.reportSNSByPhotoId(photoId, memberId);
    }

    @Override
    public List<PhotoDto> getReportedList() throws ExecutionException, InterruptedException {
        return snsRepository.getReportedList();
    }

    @Override
    public void blockSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        snsRepository.blockSNSByPhotoId(photoId);
    }
}
