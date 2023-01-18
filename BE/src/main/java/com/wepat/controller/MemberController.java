package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
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
    @ApiOperation(value = "로그인 시도",  notes = "로그인 요청을 한다.",response = MemberDto.class)
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
    @ApiOperation(value = "회원가입", notes = "정보를 받아 회원가입 시도한다.", response = MemberDto.class)
    public ResponseEntity<?> signUp(MemberDto member) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/findid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일을 확인하여 해당 아이디 제공", response = String.class)
    public ResponseEntity<?> findid(MemberDto memberDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("findpwd")
    @ApiOperation(value = "비밀번호 찾기", notes = "아이디, 이메일 인증 성공 시," +
            "해당 이메일로 임시 비밀번호 제공 및 임시 비밀번호로 정보 변경", response = HttpResponse.class)
    public ResponseEntity<?> findpw(MemberDto memberDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mypage/{memberid}")
    @ApiOperation(value = "마이페이지", notes = "현재 로그인되어있는 회원의 정보 조회", response = MemberDto.class)
    public ResponseEntity<?> detail(@PathVariable String memberid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/modify/{memberid}")
    @ApiOperation(value = "회원 정보 수정", notes = "현재 회원의 정보를 수정한다.", response = MemberDto.class)
    public ResponseEntity<?> modify(@PathVariable String memberid) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{memberid}")
    @ApiOperation(value = "사용자의 정보를 삭제한다.", response = HttpResponse.class)
    public ResponseEntity<?> delete(@PathVariable String memberid) {
//        try {
//            MemberDto response = memberService.signIn(member);
//            return new ResponseEntity<MemberDto>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }a
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/logout/{memberid}")
    @ApiOperation(value = "로그아웃", notes = "현재 로그인되어있는 사용자 로그아웃", response = HttpResponse.class)
    public ResponseEntity<?> logout(@PathVariable String memberid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("modify/{memberid}/pwd")
    @ApiOperation(value = "비밀번호 변경", notes = "로그인되어있는 멤버의 비밀번호를 변경한다.", response = HttpResponse.class)
    public ResponseEntity<?> modifyPwd(@PathVariable String memberid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
