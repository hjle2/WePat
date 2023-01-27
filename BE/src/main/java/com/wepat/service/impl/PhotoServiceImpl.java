package com.wepat.service.impl;

import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import com.wepat.entity.PhotoEntity;
import com.wepat.repository.PhotoRepository;
import com.wepat.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    @Override
    public List<PhotoDto> getAllPhoto(String calendarId) throws ExecutionException, InterruptedException {
        return photoRepository.getAllPhoto(calendarId);
    }

    @Override
    public PhotoEntity getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        return photoRepository.getPhotoById(calendarId, photoId);
    }

    @Override
    public PhotoEntity addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        return photoRepository.addCommentByPhoto(calendarId, photoId, commentDto);
    }

    @Override
    public ResponseEntity<?> updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        return photoRepository.updateSNSByPhoto(calendarId, photoId);
    }

    @Override
    public ResponseEntity<?> deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        return photoRepository.deletePhoto(calendarId, photoId);
    }

    @Override
    public PhotoDto addLike(String photoId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public PhotoDto addReport(String photoId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public ResponseEntity<?> addPhoto(String PhotoURL) {
        return photoRepository.addPhoto(PhotoURL);
    }
}
