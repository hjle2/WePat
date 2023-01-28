package com.wepat.service;

import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PhotoService {
    // 전체 앨범 가져오기
    List<PhotoDto> getAllPhoto(String calendarId) throws ExecutionException, InterruptedException ;
    // 특정 사진 선택 시 보여주기
    PhotoDto getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException ;
    // 이미지 추가
    ResponseEntity<?> addPhoto(String calendarId, PhotoDto photoDto);
    // 사진 삭제하기
    ResponseEntity<?> deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException ;
    // SNS 업로드
    ResponseEntity<?> updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException;
    // 댓글 작성
    ResponseEntity<?> addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException;
    // 댓글 삭제
    ResponseEntity<?> deleteCommentByPhoto(String calendarId, String photoId, String commentId) throws ExecutionException, InterruptedException;
    // 댓글 수정
    ResponseEntity<?> updateCommentByPhoto(String calendarId, String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException;


}
