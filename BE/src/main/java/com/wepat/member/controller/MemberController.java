package com.wepat.member.controller;

import com.wepat.exception.member.*;
import com.wepat.member.MemberDto;
import com.wepat.member.service.MemberService;
import com.wepat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class    MemberController {
    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "정보를 받아 회원가입 시도한다.", response = MemberDto.class)
    public ResponseEntity<?> signUp(MemberDto member) {
        try {
            memberService.signUp(member);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
            Map<String, String> resultMap = new HashMap<>();
            MemberDto memberResult = memberService.signIn(memberId, pwd);//유저가 로그인 가능한 유저인지 확인
            String accessToken = null;
            String refreshToken = null;//유저가 로그인 되면 토큰을 생성하여 저장할 String
            if(memberResult != null){//로그인에서 객체를 받아왔다.
                accessToken = jwtUtil.createAccessToken("memberId", memberId);
                refreshToken = jwtUtil.createRefreshToken("memberId", memberId);
                memberService.saveRefreshToken(memberId, refreshToken);

                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    @PostMapping("/socialsignin")
    @ApiOperation(value = "로그인 시도",  notes = "로그인 요청을 한다.",response = MemberDto.class)
    public ResponseEntity<?> snsSignIn(String memberId, String pwd) {
        try {
            Map<String, String> resultMap = new HashMap<>();
            MemberDto memberResult = memberService.signIn(memberId, pwd);//유저가 로그인 가능한 유저인지 확인
            String accessToken = null;
            String refreshToken = null;//유저가 로그인 되면 토큰을 생성하여 저장할 String
            if(memberResult != null){//로그인에서 객체를 받아왔다.
                accessToken = jwtUtil.createAccessToken("memberId", memberId);
                refreshToken = jwtUtil.createRefreshToken("memberId", memberId);
                memberService.saveRefreshToken(memberId, refreshToken);

                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
            return new ResponseEntity<>(memberService.getMemberDetail(memberId), HttpStatus.OK);
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
            memberService.modifyMemberDetail(memberId, nickName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @GetMapping("/logout/{memberid}")
    @ApiOperation(value = "로그아웃", notes = "현재 로그인되어있는 사용자 로그아웃", response = HttpResponse.class)
    public ResponseEntity<?> logout(@PathVariable("memberid") String memberId) {
        try {
            memberService.logout(memberId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
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

    @PostMapping("/gettoken")
    public ResponseEntity<?> getAccessToken(String memberId, String refreshToken) {
        String accessToken = jwtUtil.createAccessToken("memberId", memberId);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

}