package com.wepat.sns;

import com.wepat.domain.photo.PhotoDto;
import com.wepat.domain.pet.PetController;
import io.swagger.annotations.ApiOperation;
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
    private final static Logger logger = LoggerFactory.getLogger(PetController.class);

    @GetMapping("/")
    @ApiOperation(value = "SNS 메인 페이지", notes = "전체 SNS 사진 가져오기", response = List.class)
    public ResponseEntity<?> getSNS() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{photoid}")
    @ApiOperation(value = "이미지 선택", response = PhotoDto.class)
    public ResponseEntity<?> getSNSByPhotoId(@PathVariable("photoid") String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{photoid}")
    @ApiOperation(value = "좋아요 클릭", response = PhotoDto.class)
    public ResponseEntity<?> updateSNSLike(@PathVariable("photoid") String photoId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{photoid}/{memberid}")
    @ApiOperation(value = "신고 클릭")
    public ResponseEntity<?> reportSNS(@PathVariable("photoid") String photoId, @PathVariable("memberid") String memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
