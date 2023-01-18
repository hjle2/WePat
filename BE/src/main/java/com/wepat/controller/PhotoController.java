package com.wepat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PhotoController {
    private final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "앨범페이지 이동", notes = "List<PhotoDto> photo", response = List.class)
    public ResponseEntity<?> photoPage(@PathVariable String calendarid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarid}/{photo}")
    @ApiOperation(value = "앨범 상세보기", response = PhotoDto.class)
    public ResponseEntity<?> photoDetail(@PathVariable String calendarid, @PathVariable String photo) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarid}/{photo}")
    @ApiOperation(value = "앨범 댓글 작성")
    public ResponseEntity<?> addComment(@PathVariable String calendarid, @PathVariable String photo,
                                        @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{calendarid}/{photo}/sns")
    @ApiOperation(value = "SNS 업로드")
    public ResponseEntity<?> snsUpload(@PathVariable String calendarid, @PathVariable String photo) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarid}/{photo}")
    @ApiOperation(value = "앨범에서 삭제")
    public ResponseEntity<?> photoDelete(@PathVariable String calendarid, @PathVariable String photo) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
