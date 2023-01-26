package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberEntity;
import com.wepat.exception.member.*;
import com.wepat.service.MemberService;
import lombok.RequiredArgsConstructor;
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
import utils.JwtUtil;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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
    public MemberEntity signUp(MemberDto member) {
        logger.info("signUp called!");
        try {
            return memberService.signUp(member);
        } catch (ExistEmailException e) {
            throw new ExistEmailException(e.getMessage());
        } catch (ExistIdException e) {
            throw new RuntimeException(e.getMessage());
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
            MemberEntity memberResult = memberService.signIn(memberId, pwd);//유저가 로그인 가능한 유저인지 확인
            String accessToken = null;
            String refreshToken = null;//유저가 로그인 되면 토큰을 생성하여 저장할 String
            if(memberResult != null){//로그인에서 객체를 받아왔다.
                accessToken = memberService.createAccessToken(memberId, pwd);//유저 AT를 생성
                refreshToken = memberService.createRefreshToken(memberId, pwd);//우저 RT를 생성
                memberService.saveRefreshToken(memberId , refreshToken);//
                return new ResponseEntity<String>(accessToken, HttpStatus.OK);
            }
//            return memberService.signIn(memberId, pwd);
        } catch (IdWriteException e) {
            logger.info(e.getMessage());
            throw new IdWriteException(e.getMessage());
        } catch (PwdWriteException e) {
            logger.info(e.getMessage());
            System.out.println(e.getMessage());
            throw new PwdWriteException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/findid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일을 확인하여 해당 아이디 제공", response = String.class)
    public MemberEntity findId(String email) {
        try {
            return memberService.findId(email);
        } catch (NotExistEmailException e) {
            throw new NotExistEmailException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
    public MemberEntity modifyPwd(String memberId, String pwd) {
        try {
            return memberService.modifyPwd(memberId, pwd);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/{memberid}")
    @ApiOperation(value = "마이페이지", notes = "현재 로그인되어있는 회원의 정보 조회", response = MemberDto.class)
    public MemberEntity getMember(@PathVariable("memberid") String memberId) {
        try {
            return memberService.getMember(memberId);
        } catch (NotExistMember e) {
            throw new NotExistMember(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PutMapping("/modify")
    @ApiOperation(value = "회원 정보 수정", notes = "현재 회원의 정보를 수정한다.", response = MemberDto.class)
    public ResponseEntity<?> modifyMember(MemberDto member) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.modifyMember(member);
            System.out.println("modifyMember controller>>> " + memberResult);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{memberid}")
    @ApiOperation(value = "사용자의 정보를 삭제한다.", response = HttpResponse.class)
    public ResponseEntity<?> deleteMember(@PathVariable("memberid") String memberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.deleteMember(memberId);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/logout/{memberid}")
    @ApiOperation(value = "로그아웃", notes = "현재 로그인되어있는 사용자 로그아웃", response = HttpResponse.class)
    public ResponseEntity<?> logout(@PathVariable("memberid") String memberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.logout(memberId);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/warn/{memberid}")
    @ApiOperation(value = "신고한 회원 목록 조회")
    public ResponseEntity<?> warnMember(@PathVariable("memberid") String memberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.warnMember(memberId);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/warn/{memberid}")
    @ApiOperation(value = "회원 신고 추가")
    public ResponseEntity<?> addWarnMember(@PathVariable("memberid") String memberId, String warnMemberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.addWarnMember(memberId, warnMemberId);
            System.out.println(memberResult);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/block/{memberid}")
    @ApiOperation(value = "차단한 회원 목록 조회")
    public ResponseEntity<?> blockMember(@PathVariable("memberid") String memberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.blockMember(memberId);
            return new ResponseEntity<MemberEntity>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/block/{memberid}")
    @ApiOperation(value = "회원 차단 추가")
    public ResponseEntity<?> addBlockMember(@PathVariable("memberid") String memberId, String blockMemberId) {
        MemberEntity memberResult = null;
        try {
            memberResult = memberService.addBlockMember(memberId, blockMemberId);
            return new ResponseEntity<>(memberResult, HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
