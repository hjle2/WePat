package com.wepat.service.impl;

import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import com.wepat.repository.PhotoRepository;
import com.wepat.service.PhotoService;
import lombok.RequiredArgsConstructor;
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
    public PhotoDto getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        return photoRepository.getPhotoById(calendarId, photoId);
    }

    @Override
    public void addPhoto(String calendarId, PhotoDto photoDto) {
        photoRepository.addPhoto(calendarId, photoDto);
    }

    @Override
    public void deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        photoRepository.deletePhoto(calendarId, photoId);
    }

    @Override
    public void updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        photoRepository.updateSNSByPhoto(calendarId, photoId);
    }

    @Override
    public void addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        photoRepository.addCommentByPhoto(calendarId, photoId, commentDto);
    }

    @Override
    public void deleteCommentByPhoto(String calendarId, String photoId, String commentId) throws ExecutionException, InterruptedException {
        photoRepository.deleteCommentByPhoto(calendarId, photoId, commentId);
    }

    @Override
    public void updateCommentByPhoto(String calendarId, String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        photoRepository.updateCommentByPhoto(calendarId, photoId, commentId, commentDto);
    }
}
