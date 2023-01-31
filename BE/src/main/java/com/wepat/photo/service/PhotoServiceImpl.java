package com.wepat.photo.service;

import com.wepat.photo.CommentDto;
import com.wepat.photo.PhotoDto;
import com.wepat.photo.repository.PhotoRepository;
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
        return photoRepository.getAllPhotoById(calendarId);
    }

    @Override
    public PhotoDto getPhotoById(String photoId) throws ExecutionException, InterruptedException {
        return photoRepository.getPhotoById(photoId);
    }

    @Override
    public void addPhoto(String calendarId, PhotoDto photoDto) {
        photoRepository.addPhoto(calendarId, photoDto);
    }

    @Override
    public void deletePhoto(String photoId) throws ExecutionException, InterruptedException {
        photoRepository.deletePhoto(photoId);
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
    public void updateSNSByPhotoId(String photoId, boolean upload, String snsDate) throws InterruptedException, ExecutionException {
        photoRepository.updateSNSByPhotoId(photoId, upload, snsDate);
    }

    @Override
    public void addCommentByPhotoId(String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        photoRepository.addCommentByPhotoId(photoId, commentDto);
    }

    @Override
    public void deleteCommentByPhotoId(String photoId, String commentId) throws ExecutionException, InterruptedException {
        photoRepository.deleteCommentByPhotoId(photoId, commentId);
    }

    @Override
    public void updateCommentByPhotoId(String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        photoRepository.updateCommentByPhotoId(photoId, commentId, commentDto);
    }


}
