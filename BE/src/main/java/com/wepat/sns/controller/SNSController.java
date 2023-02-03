package com.wepat.sns.controller;

import com.wepat.exception.photo.NotExistImageException;
import com.wepat.exception.sns.AlreadyReportImageException;
import com.wepat.photo.PhotoDto;
import com.wepat.pet.controller.PetController;
import com.wepat.sns.service.SNSService;
import com.wepat.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sns")
@RequiredArgsConstructor
public class SNSController {
    private final SNSService snsService;

    @GetMapping("/")
    @ApiOperation(value = "SNS 전체 사진 조회", response = List.class)
    public ResponseEntity<?> getSNS(@RequestParam int before) {
        try {
            return new ResponseEntity<>(snsService.getSNS(before), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{photoid}")
    @ApiOperation(value = "이미지 선택", response = PhotoDto.class)
    public ResponseEntity<?> getSNSByPhotoId(@PathVariable("photoid") String photoId) {
        try {
            return new ResponseEntity<>(snsService.getSNSByPhotoId(photoId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/like/{photoid}")
    @ApiOperation(value = "좋아요 클릭", response = PhotoDto.class)
    public ResponseEntity<?> updateSNSLikeByPhotoId(@PathVariable("photoid") String photoId) {

        try {
            snsService.updateSNSLikeByPhotoId(photoId);
            return new ResponseEntity<>("좋아요 클릭", HttpStatus.ACCEPTED);
        } catch (NotExistImageException e) {
            throw new NotExistImageException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/report/{photoid}")
    @ApiOperation(value = "신고 클릭")
    public ResponseEntity<?> reportSNSByPhotoId(HttpServletRequest request, @PathVariable("photoid") String photoId) {
        try {
            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            snsService.reportSNSByPhotoId(photoId, memberId);
            return new ResponseEntity<>("신고 성공", HttpStatus.ACCEPTED);
        } catch (AlreadyReportImageException e) {
            throw new AlreadyReportImageException();
        } catch (NotExistImageException e) {
            throw new NotExistImageException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/report")
    @ApiOperation(value = "3번 이상 신고 당한 게시물")
    public ResponseEntity<?> getReportedList() {
        try {
            return new ResponseEntity<>(snsService.getReportedList(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/block/{photoid}")
    @ApiOperation(value = "게시물 차단")
    public ResponseEntity<?> blockSNSByPhotoId(@PathVariable("photoid") String photoId) {
        try {
            snsService.blockSNSByPhotoId(photoId);
            return new ResponseEntity<>("게시물 차단 성공", HttpStatus.ACCEPTED);
        } catch (NotExistImageException e) {
            throw new NotExistImageException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
