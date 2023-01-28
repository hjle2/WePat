package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.exception.member.ExistEmailException;
import com.wepat.exception.member.*;
import com.wepat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MemberController {
    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "정보를 받아 회원가입 시도한다.", response = MemberDto.class)
    public ResponseEntity<?> signUp(MemberDto member) {
        try {
            memberService.signUp(member);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        } catch (ExistEmailException e) {
            throw new ExistEmailException(e.getMessage());
        } catch (ExistIdException e) {
            throw new ExistIdException(e.getMessage());
        } catch (NotExistCalendarException e) {
            throw new NotExistCalendarException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @PostMapping("/signin")
    @ApiOperation(value = "로그인 시도",  notes = "로그인 요청을 한다.",response = MemberDto.class)
    public ResponseEntity<?> signIn(String memberId, String pwd) {
        try {
            MemberDto memberResult = memberService.signIn(memberId, pwd);
            if(memberResult!=null){//로그인에서 객체를 받아왔다.
                String memberToken = memberService.createJwt(memberId, pwd);
                return new ResponseEntity<String>(memberToken, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IdWriteException e) {
            throw new IdWriteException(e.getMessage());
        } catch (BlockMember e) {
            throw new BlockMember(e.getMessage());
        } catch (PwdWriteException e) {
            throw new PwdWriteException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/findid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일을 확인하여 해당 아이디 제공", response = String.class)
    public ResponseEntity<?> findId(String email) {
        try {
            return new ResponseEntity<>(memberService.findId(email), HttpStatus.OK);
        } catch (NotExistEmailException e) {
            throw new NotExistEmailException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PostMapping("/findpwd")
    @ApiOperation(value = "비밀번호 찾기", notes = "아이디, 이메일 인증 성공 시," +
            "해당 이메일로 임시 비밀번호 제공 및 임시 비밀번호로 정보 변경", response = HttpResponse.class)
    public ResponseEntity<?> findPwd(String memberId, String email) throws ExecutionException, InterruptedException {
        try {
            memberService.findPwd(memberId, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PutMapping("modifypwd")
    @ApiOperation(value = "비밀번호 변경", response = HttpResponse.class)
    public ResponseEntity<?> modifyPwd(String memberId, String pwd) {
        try {
            memberService.modifyPwd(memberId, pwd);
            return new ResponseEntity<>("비밀번호 변경 성공", HttpStatus.OK);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/{memberid}")
    @ApiOperation(value = "마이페이지", notes = "현재 로그인되어있는 회원의 정보 조회", response = MemberDto.class)
    public ResponseEntity<?> getMember(@PathVariable("memberid") String memberId) {
        try {
            return new ResponseEntity<>(memberService.getMember(memberId), HttpStatus.OK);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PutMapping("/modify")
    @ApiOperation(value = "회원 정보 수정", notes = "현재 회원의 정보를 수정한다.", response = MemberDto.class)
    public ResponseEntity<?> modifyMember(String memberId, String nickName) {
        try {
            memberService.modifyMember(memberId, nickName);
            return new ResponseEntity<>("회원 정보 수정 완료", HttpStatus.OK);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @DeleteMapping("/{memberid}")
    @ApiOperation(value = "사용자의 정보를 삭제한다.", response = HttpResponse.class)
    public ResponseEntity<?> deleteMember(@PathVariable("memberid") String memberId) {
        try {
            memberService.deleteMember(memberId);
            return new ResponseEntity<>("회원 탈퇴 성공", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @GetMapping("/logout/{memberid}")
    @ApiOperation(value = "로그아웃", notes = "현재 로그인되어있는 사용자 로그아웃", response = HttpResponse.class)
    public ResponseEntity<?> logout(@PathVariable("memberid") String memberId) {
        try {
            memberService.logout(memberId);
            return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/modify/calendar")
    @ApiOperation(value = "캘린더 변경")
    public ResponseEntity<?> modifyCalendarId(String memberId, String calendarId) {
        try {
            memberService.modifyCalendarId(memberId, calendarId);
            return new ResponseEntity<>("캘린더 변경 완료", HttpStatus.OK);
        } catch (NotExistCalendarException e) {
            throw new NotExistCalendarException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @PutMapping("/warn/{memberid}")
//    @ApiOperation(value = "회원 신고")
//    public ResponseEntity<?> addWarnMember(@PathVariable("memberid") String memberId, String warnMemberId) {
//        MemberEntity memberResult = null;
//        try {
//            return new ResponseEntity<>(memberService.addWarnMember(memberId, warnMemberId), HttpStatus.OK);
//        } catch (AlreadyWarnMember e) {
//            throw new AlreadyWarnMember(e.getMessage());
//        } catch (NotExistMember e) {
//            throw new NotExistMember(e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }
//
//    @GetMapping("/warn/admin")
//    @ApiOperation(value = "3번이상 신고당한 유저 목록(관리자용)")
//    public ResponseEntity<?> warnMember() {
//        try {
//            return new ResponseEntity<>(memberService.warnMember(), HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }
//
//    @PutMapping("/block/admin/{blockmemberid}")
//    @ApiOperation(value = "회원 차단 추가")
//    public ResponseEntity<?> addBlockMember(@PathVariable("blockmemberid") String blockMemberId) {
//
//        try {
//            return new ResponseEntity<>(memberService.addBlockMember(blockMemberId), HttpStatus.OK);
//        } catch (NotExistMember e) {
//            throw new NotExistMember(e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


//    @GetMapping("/block/{memberid}")
//    @ApiOperation(value = "차단한 회원 목록 조회")
//    public ResponseEntity<?> blockMember(@PathVariable("memberid") String memberId) {
//        MemberEntity memberResult = null;
//        try {
//            memberResult = memberService.blockMember(memberId);
//            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

}
