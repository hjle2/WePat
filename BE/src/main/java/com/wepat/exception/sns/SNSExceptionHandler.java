package com.wepat.exception.sns;

import com.wepat.controller.SNSController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {SNSController.class})
public class SNSExceptionHandler {

    @ExceptionHandler(MemberDeleteImage.class)
    public ResponseEntity<?> MemberDeleteImage(MemberDeleteImage e) {
        ErrorDto errorDto = new ErrorDto("MemberDeleteImage", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadMemberBlock.class)
    public ResponseEntity<?> UploadMemberBlock(UploadMemberBlock e) {
        ErrorDto errorDto = new ErrorDto("UploadMemberBlock", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyWarnMember.class)
    public ResponseEntity<?> AlreadyWarnMember(AlreadyWarnMember e) {
        ErrorDto errorDto = new ErrorDto("AlreadyWarnMember", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
