package com.wepat.repository;

import com.wepat.dto.PhotoDto;
import java.util.List;
public interface PhotoRepository {
    // 전체 앨범 가져오기
    List<PhotoDto> getAllPhoto(String calendarId);
    // 특정 사진 선택 시 보여주기
    PhotoDto getPhoto(String photoId);
}
