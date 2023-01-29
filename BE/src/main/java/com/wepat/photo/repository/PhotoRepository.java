package com.wepat.photo.repository;

import com.wepat.photo.CommentDto;
import com.wepat.photo.PhotoDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PhotoRepository {
    // 전체 앨범 가져오기
    List<PhotoDto> getAllPhotoById(String calendarId) throws ExecutionException, InterruptedException;
    // 특정 사진 선택 시 보여주기
    PhotoDto getPhotoById(String photoId) throws ExecutionException, InterruptedException;
    // 이미지 추가
    void addPhoto(String calendarId, PhotoDto photoDto);
    // 사진 삭제하기
    void deletePhoto(String photoId) throws ExecutionException, InterruptedException;
    // SNS 업로드
    void updateSNSByPhotoId(String photoId, boolean upload) throws ExecutionException, InterruptedException;
    // 댓글 작성
    void addCommentByPhotoId(String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException;
    // 댓글 삭제
    void deleteCommentByPhotoId(String photoId, String commentId) throws ExecutionException, InterruptedException;
    // 댓글 수정
    void updateCommentByPhotoId(String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException;

}
