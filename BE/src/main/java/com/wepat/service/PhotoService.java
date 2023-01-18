package com.wepat.service;

import com.wepat.dto.PhotoDto;

import java.util.List;

public interface PhotoService {
    // 전체 앨범 가져오기
    List<PhotoDto> getAllPhoto(String calendarId);
    // 특정 사진 선택 시 보여주기
    PhotoDto getPhoto(String photoId);
    // 사진 삭제하기
    PhotoDto deletePhoto(String photoId);
    // 사진 좋아요 반영하기
    PhotoDto addLike(String photoId);
    // 사진 신고 반영하기
    PhotoDto addReport(String photoId);
}
