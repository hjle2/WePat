package com.wepat.controller;

import com.wepat.exception.photo.AlreadyDeleteImage;
import com.wepat.exception.photo.NotExistImage;
import com.wepat.service.PhotoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PhotoController {
    private final static Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "가족 전체 앨범 이미지 조회", notes = "Photo 에 SNS 체크가 되어있는 사진들 조회", response = List.class)
    public ResponseEntity<?> getAllPhoto(@PathVariable("calendarid") String calendarId) {
        try {
            return new ResponseEntity<>(photoService.getAllPhoto(calendarId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> getPhotoById(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(photoService.getPhotoById(calendarId, photoId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/add/photo/{calendarid}")
    @ApiOperation(value = "이미지 추가", notes = "사용자ID, 캘린더ID, 사진URL, 등록일 입력")
    public ResponseEntity<?> addPhoto(@PathVariable("calendarid") String calendarId, @RequestBody PhotoDto photoDto) {

        return new ResponseEntity<>(photoService.addPhoto(calendarId, photoDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{calendarid}/{photoid}")
    @ApiOperation(value = "이미지 삭제")
    public ResponseEntity<?> deletePhoto(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(photoService.deletePhoto(calendarId, photoId), HttpStatus.OK);
        } catch (AlreadyDeleteImage e) {
            throw new AlreadyDeleteImage();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/{calendarid}/{photoid}/sns")
    @ApiOperation(value = "SNS 에 사진 업로드하기")
    public ResponseEntity<?> updateSNSByPhoto(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        try {
            return photoService.updateSNSByPhoto(calendarId, photoId);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 댓글 작성")
    public ResponseEntity<?> addCommentByPhoto(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId,
                                                  @RequestBody CommentDto commentDto) {

        try {
            return new ResponseEntity<>(photoService.addCommentByPhoto(calendarId, photoId, commentDto), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
