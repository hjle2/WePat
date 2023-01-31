package com.wepat.photo.controller;

import com.wepat.exception.photo.AlreadyDeleteImage;
import com.wepat.exception.photo.NotExistImage;
import com.wepat.photo.CommentDto;
import com.wepat.photo.PhotoDto;
import com.wepat.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PhotoController {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "가족 전체 앨범 이미지 조회", notes = "calendar의 전체 이미지 조회", response = List.class)
    public ResponseEntity<?> getAllPhotoById(@PathVariable("calendarid") String calendarId) {
        try {
            return new ResponseEntity<>(photoService.getAllPhotoById(calendarId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // calendarId X
    @GetMapping("/detail/{photoid}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> getPhotoById(@PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(photoService.getPhotoById(photoId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/add/photo/{calendarid}")
    @ApiOperation(value = "이미지 추가", notes = "사용자ID, 사진URL, 등록일 입력")
    public ResponseEntity<?> addPhoto(@PathVariable("calendarid") String calendarId,
                                      @RequestBody PhotoDto photoDto) {

        photoService.addPhoto(calendarId, photoDto);
        return new ResponseEntity<>("이미지 추가 성공", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{photoid}")
    @ApiOperation(value = "이미지 삭제")
    public ResponseEntity<?> deletePhoto(@PathVariable("photoid") String photoId) {
        try {
            photoService.deletePhoto(photoId);
            return new ResponseEntity<>("이미지 삭제 성공", HttpStatus.OK);
        } catch (AlreadyDeleteImage e) {
            throw new AlreadyDeleteImage();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/sns/{photoid}")
    @ApiOperation(value = "SNS 에 사진 업로드하기")
    public ResponseEntity<?> uploadSNSByPhotoId(@PathVariable("photoid") String photoId,
                                              String snsDate) {

        try {
            photoService.uploadSNSByPhotoId(photoId, snsDate);
            return new ResponseEntity<>("업로드 성공", HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // calendarid X
    @PostMapping("/{photoid}")
    @ApiOperation(value = "앨범 댓글 작성")
    public ResponseEntity<?> addCommentByPhotoId(@PathVariable("photoid") String photoId,
                                                  @RequestBody CommentDto commentDto) {

        try {
            photoService.addCommentByPhotoId(photoId, commentDto);
            return new ResponseEntity<>("댓글 작성 성공", HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{photoid}/{commentid}")
    @ApiOperation(value = "앨범 댓글 삭제")
    public ResponseEntity<?> deleteCommentByPhotoId(@PathVariable("photoid") String photoId,
                                                  @PathVariable("commentid") String commentId) {

        try {
            photoService.deleteCommentByPhotoId(photoId, commentId);
            return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/comment/{photoid}/{commentid}")
    @ApiOperation(value = "앨범 댓글 수정")
    public ResponseEntity<?> updateCommentByPhotoId(@PathVariable("photoid") String photoId,
                                                  @PathVariable("commentid") String commentId,
                                                  @RequestBody CommentDto commentDto) {
        try {
            photoService.updateCommentByPhotoId(photoId, commentId, commentDto);
            return new ResponseEntity<>("댓글 수정 완료", HttpStatus.ACCEPTED);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
