package com.wepat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*")
public class PhotoController {
    private final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @GetMapping("/{calendarId}")
    @ApiOperation(value = "전체 앨범 이미지 얻기", notes = "Photo 에 SNS 체크가 되어있는 사진들 조회", response = List.class)
    public ResponseEntity<?> getAllPhoto(@PathVariable String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarId}/{photoId}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> getPhotoById(@PathVariable String calendarId, @PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarId}/{photoId}")
    @ApiOperation(value = "앨범 댓글 추가하기")
    public ResponseEntity<?> updateCommentByPhoto(@PathVariable String calendarId, @PathVariable String photoId,
                                        @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarId}/{photoId}/sns")
    @ApiOperation(value = "SNS 에 사진 업로드하기")
    public ResponseEntity<?> updateSNSByPhoto(@PathVariable String calendarId, @PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarId}/{photoId}")
    @ApiOperation(value = "앨범에서 삭제")
    public ResponseEntity<?> deletePhotoById(@PathVariable String calendarId, @PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
