package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.POST })
public class MemberController {

//    @Autowired
//    private MemberService memberService;
    @PostMapping("/signin")
    @ApiOperation(value = "로그인 정보를 확인한다 .", response = MemberDto.class)
    public ResponseEntity<?> signIn(MemberDto member) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/signup")
    @ApiOperation(value = "로그인한 사용자 정보를 반환한다.", response = MemberDto.class)
    public ResponseEntity<?> signUp(MemberDto member) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping("/modify")
    @ApiOperation(value = "수정된 사용자의 정보를 반환한다.", response = MemberDto.class)
    public ResponseEntity<?> modify(MemberDto member) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "사용자의 정보를 삭제한다.", response = MemberDto.class)
    public ResponseEntity<?> delete(MemberDto member) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }a
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/findid")
    @ApiOperation(value = "사용자의 아이디를 반환한다.", response = MemberDto.class)
    public ResponseEntity<?> findId(MemberDto member) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/setpwd")
    @ApiOperation(value = "사용자의 비밀번호를 변경한다.", response = MemberDto.class)
    public ResponseEntity<?> setPwd(String pwd) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/{memberid}")
    @ApiOperation(value = "사용자의 정보를 반환한다.", response = MemberDto.class)
    public ResponseEntity<?> getMemberById(@PathVariable String memberId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
