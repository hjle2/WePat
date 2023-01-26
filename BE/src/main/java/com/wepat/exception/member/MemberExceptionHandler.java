package com.wepat.exception.member;

import com.wepat.controller.MemberController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {MemberController.class})
public class MemberExceptionHandler {

    @ExceptionHandler(IdWriteException.class)
    public ResponseEntity<?> IdWriteException(IdWriteException e) {
        ErrorDto errorDto = new ErrorDto("IdWriteException", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PwdWriteException.class)
    public ResponseEntity<?> PwdWriteException(PwdWriteException e) {
        ErrorDto errorDto = new ErrorDto("PwdWriteException", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistEmailException.class)
    public ResponseEntity<?> ExistEmailException(ExistEmailException e) {
        ErrorDto errorDto = new ErrorDto("ExistEmailException", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistIdException.class)
    public ResponseEntity<?> ExistIdException(ExistIdException e) {
        ErrorDto errorDto = new ErrorDto("ExistIdException", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistEmailException.class)
    public ResponseEntity<?> NotExistEmailException(NotExistEmailException e) {
        ErrorDto errorDto = new ErrorDto("NotExistEmailException", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistMember.class)
    public ResponseEntity<?> NotExistMember(NotExistMember e) {
        ErrorDto errorDto = new ErrorDto("NotExistMember", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageError.class)
    public ResponseEntity<?> MessageError(MessageError e) {
        ErrorDto errorDto = new ErrorDto("MessageError", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(MessageError.class)
    public ResponseEntity<?> AlreadyWarnMember(AlreadyWarnMember e) {
        ErrorDto errorDto = new ErrorDto("AlreadyWarnMember", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(BlockMember.class)
    public ResponseEntity<?> BlockMember(BlockMember e) {
        ErrorDto errorDto = new ErrorDto("BlockMember", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
