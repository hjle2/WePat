package com.wepat.controller;

import com.wepat.exception.photo.NotExistImage;
import com.wepat.service.PhotoService;
import lombok.RequiredArgsConstructor;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PhotoController {
    private final static Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "전체 앨범 이미지 얻기", notes = "Photo 에 SNS 체크가 되어있는 사진들 조회", response = List.class)
    public ResponseEntity<?> getAllPhoto(@PathVariable("calendarid") String calendarId) {
        try {
            return new ResponseEntity<>(photoService.getAllPhoto(calendarId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> getPhotoById(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(photoService.getPhotoById(calendarId, photoId), HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 댓글 추가하기")
    public ResponseEntity<?> addCommentByPhoto(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId,
                                                  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarid}/{photoid}/sns")
    @ApiOperation(value = "SNS 에 사진 업로드하기")
    public ResponseEntity<?> updateSNSByPhoto(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범에서 삭제")
    public ResponseEntity<?> deletePhotoById(@PathVariable("calendarid") String calendarId, @PathVariable("photoid") String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
