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

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "전체 앨범 이미지 얻기", notes = "List<PhotoDto> photo", response = List.class)
    public ResponseEntity<?> photoPage(@PathVariable String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> photoDetail(@PathVariable String calendarId, @PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범 댓글 추가하기")
    public ResponseEntity<?> addComment(@PathVariable String calendarId, @PathVariable String photoId,
                                        @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{calendarid}")
    @ApiOperation(value = "SNS에 사진 업로드하기")
    public ResponseEntity<?> snsUpload(String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarid}/{photoid}")
    @ApiOperation(value = "앨범에서 삭제")
    public ResponseEntity<?> photoDelete(@PathVariable String calendarid, @PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
