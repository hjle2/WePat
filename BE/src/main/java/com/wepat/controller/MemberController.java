package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(origins = "*")
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "정보를 받아 회원가입 시도한다.", response = MemberDto.class)
    public ResponseEntity<?> signUp(MemberDto member) {
        logger.info("signUp called!");
        MemberDto memberResult = null;
        try {
            memberResult = memberService.signUp(member);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/signin")
    @ApiOperation(value = "로그인 시도",  notes = "로그인 요청을 한다.",response = MemberDto.class)
    public ResponseEntity<?> signIn(String memberId, String pwd) {
        logger.info("signIn called! parameter >> member Id : " + memberId + " pwd : " + pwd);
        MemberDto memberResult = null;
        try {
            memberResult = memberService.signIn(memberId, pwd);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/findid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일을 확인하여 해당 아이디 제공", response = String.class)
    public ResponseEntity<?> findId(String name, String email) {
        logger.info("findId called! parameter >> name : " + name + " email : " + email);
        MemberDto memberResult = null;
        try {
            memberResult = memberService.findId(email);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/modifypwd")
    @ApiOperation(value = "비밀번호 찾기", notes = "아이디, 이메일 인증 성공 시," +
            "해당 이메일로 임시 비밀번호 제공 및 임시 비밀번호로 정보 변경", response = HttpResponse.class)
    public ResponseEntity<?> modifyPwd(String memberId, String pwd) {
        logger.info("findPwd called! parameter >> member Id : " + memberId + " pwd : " + pwd);
        MemberDto memberResult = null;
        try {
            memberService.modifyPwd(memberId, pwd);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{memberid}")
    @ApiOperation(value = "마이페이지", notes = "현재 로그인되어있는 회원의 정보 조회", response = MemberDto.class)
    public ResponseEntity<?> getMember(@PathVariable("memberid") String memberId) {
        logger.info("getMember called! parameter >> member Id : " + memberId);
        MemberDto memberResult = null;
        try {
            memberResult = memberService.getMember(memberId);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/modify")
    @ApiOperation(value = "회원 정보 수정", notes = "현재 회원의 정보를 수정한다.", response = MemberDto.class)
    public ResponseEntity<?> modifyMember(MemberDto member) {
        logger.info("modifyMember called! >> Member : " + member.toString());
        MemberDto memberResult = null;
        try {
            memberResult = memberService.modifyMember(member);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{memberid}")
    @ApiOperation(value = "사용자의 정보를 삭제한다.", response = HttpResponse.class)
    public ResponseEntity<?> deleteMember(@PathVariable("memberid") String memberId) {
        logger.info("deleteMember called!");
        MemberDto memberResult = null;
        try {
            memberResult = memberService.deleteMember(memberId);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/logout/{memberid}")
    @ApiOperation(value = "로그아웃", notes = "현재 로그인되어있는 사용자 로그아웃", response = HttpResponse.class)
    public ResponseEntity<?> logout(@PathVariable("memberid") String memberId) {
        logger.info("logout called!");
        MemberDto memberResult = null;
        try {
            memberResult = memberService.logout(memberId);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/warn/{memberid}")
    public ResponseEntity<?> warnMember(@PathVariable("memberid") String memberId) {
        logger.info("warnMember called!");
        MemberDto memberResult = null;
        try {
            memberResult = memberService.warnMember(memberId);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/block/{memberid}")
    public ResponseEntity<?> blockMember(@PathVariable("memberid") String memberId) {
        logger.info("blockMember called!");
        MemberDto memberResult = null;
        try {
            memberResult = memberService.blockMember(memberId);
            return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
