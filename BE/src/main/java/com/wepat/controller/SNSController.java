package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.dto.PhotoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sns")
@CrossOrigin(origins = "*")
public class SNSController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);

    @GetMapping("/")
    @ApiOperation(value = "SNS 메인 페이지", notes = "전체 SNS 사진 가져오기", response = List.class)
    public ResponseEntity<?> getSNS() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{photoId}")
    @ApiOperation(value = "이미지 선택", response = PhotoDto.class)
    public ResponseEntity<?> getSNSByPhotoId(@PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{photoId}")
    @ApiOperation(value = "좋아요 클릭", response = PhotoDto.class)
    public ResponseEntity<?> updateSNSLike(@PathVariable String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{photoId}/{memberId}")
    @ApiOperation(value = "신고 클릭")
    public ResponseEntity<?> reportSNS(@PathVariable String photoId, String memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
