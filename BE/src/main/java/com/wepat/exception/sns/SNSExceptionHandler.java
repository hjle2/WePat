package com.wepat.exception.sns;

import com.wepat.controller.SNSController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {SNSController.class})
public class SNSExceptionHandler {

    @ExceptionHandler(MemberDeleteImage.class)
    public ResponseEntity<?> MemberDeleteImage(MemberDeleteImage e) {
        ErrorDto errorDto = new ErrorDto("MemberDeleteImage", "업로드 삭제된 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadMemberBlock.class)
    public ResponseEntity<?> UploadMemberBlock(UploadMemberBlock e) {
        ErrorDto errorDto = new ErrorDto("UploadMemberBlock", "게시물 작성한 회원이 비공개처리 되었습니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotExistCalendarException.class)
    public ResponseEntity<?> NotExistCalendarException(NotExistCalendarException e) {
        ErrorDto errorDto = new ErrorDto("NotExistCalendarException", "존재하지 않는 캘린더입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistImage.class)
    public ResponseEntity<?> NotExistImage(NotExistImage e) {
        ErrorDto errorDto = new ErrorDto("NotExistImage", "존재하지 않는 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyReportImage.class)
    public ResponseEntity<?> AlreadyReportImage(AlreadyReportImage e) {
        ErrorDto errorDto = new ErrorDto("AlreadyReportImage", "이미 신고한 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        System.out.println("익셉션 핸들러 호출!!!!!!!!!!!!");
        return new ErrorDto("Exception", "서버에 오류가 발생했습니다. 죄송합니다.");
    }

}
