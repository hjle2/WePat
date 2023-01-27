package com.wepat.repository;

import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import com.wepat.entity.PhotoEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PhotoRepository {
    // 전체 앨범 가져오기
    List<PhotoDto> getAllPhoto(String calendarId) throws ExecutionException, InterruptedException;
    // 특정 사진 선택 시 보여주기
    PhotoEntity getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException;
    // 댓글 추가
    PhotoEntity addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException;
    ResponseEntity<?> updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException;
    // 사진 삭제하기
    ResponseEntity<?> deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException;
    // 사진 좋아요 반영하기
    PhotoDto addLike(String photoId) throws ExecutionException, InterruptedException;
    // 사진 신고 반영하기
    PhotoDto addReport(String photoId) throws ExecutionException, InterruptedException;

    ResponseEntity<?> addPhoto(String PhotoURL);
}
