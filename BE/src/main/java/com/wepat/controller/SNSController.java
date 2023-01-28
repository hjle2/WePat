package com.wepat.controller;

import com.wepat.dto.PhotoDto;
import com.wepat.exception.sns.AlreadyReportImage;
import com.wepat.exception.sns.NotExistImage;
import com.wepat.service.SNSService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SNSController {
    private final static Logger logger = LoggerFactory.getLogger(PetController.class);

    private final SNSService snsService;

    @GetMapping("/")
    @ApiOperation(value = "SNS 전체 사진 조회", response = List.class)
    public ResponseEntity<?> getSNS() {
        try {
            return new ResponseEntity<>(snsService.getSNS(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{photoid}")
    @ApiOperation(value = "이미지 선택", response = PhotoDto.class)
    public ResponseEntity<?> getSNSByPhotoId(@PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(snsService.getSNSByPhotoId(photoId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/like/{photoid}")
    @ApiOperation(value = "좋아요 클릭", response = PhotoDto.class)
    public ResponseEntity<?> updateSNSLike(@PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(snsService.updateSNSLike(photoId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/report/{photoid}/{memberid}")
    @ApiOperation(value = "신고 클릭")
    public ResponseEntity<?> reportSNS(@PathVariable("photoid") String photoId, @PathVariable("memberid") String memberId) {
        try {
            return new ResponseEntity<>(snsService.reportSNS(photoId, memberId), HttpStatus.OK);
        } catch (AlreadyReportImage e) {
            throw new AlreadyReportImage();
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/report")
    @ApiOperation(value = "3번 이상 신고 당한 게시물")
    public ResponseEntity<?> reportList() {
        try {
            return new ResponseEntity<>(snsService.reportList(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/block/{photoid}")
    @ApiOperation(value = "게시물 차단")
    public ResponseEntity<?> blockSNSByPhoto(@PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(snsService.blockSNSByPhoto(photoId), HttpStatus.OK);
        } catch (NotExistImage e) {
            throw new NotExistImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
