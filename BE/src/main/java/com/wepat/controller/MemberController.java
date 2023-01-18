package com.wepat.controller;

import com.wepat.dto.MemberDto;
import com.wepat.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.POST })
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("signup")
    public ResponseEntity<?> signUp(MemberDto member) {
        MemberDto memberResult = null;
        logger.info("signUp called!");
        try {
            memberResult = memberService.signUp(member);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @PostMapping("signin")
    public ResponseEntity<?> signIn(String memberid, String pwd) {
        MemberDto memberResult = null;
        logger.info("signIn called!");
        try {
            memberResult = memberService.signIn(memberid, pwd);
            System.out.println(memberResult);
        } catch (ExecutionException e) {
            System.out.println("e1");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("e2");
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @PostMapping("findid")
    public ResponseEntity<?> findId(String name, String email) {
        MemberDto memberResult = null;
        logger.info("findId called!");
        try {
            memberResult = memberService.findId(email);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @PostMapping("findpwd")
    public ResponseEntity<?> modifyPwd(String memberid, String pwd) {
        MemberDto memberResult = null;
        logger.info("findPwd called!");
        try {
            memberService.modifyPwd(memberid, pwd);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @GetMapping("/{memberid}")
    public ResponseEntity<?> getMember(@PathVariable String memberId) {
        MemberDto memberResult = null;
        logger.info("getMember called!");
        try {
            memberResult = memberService.getMember(memberId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @PutMapping("/modify")
    public ResponseEntity<?> modifyMember(MemberDto member) {
        MemberDto memberResult = null;
        logger.info("modifyMember called!");
        try {
            memberResult = memberService.modifyMember(member);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @DeleteMapping("/{memberid}")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId) {
        MemberDto memberResult = null;
        logger.info("deleteMember called!");
        try {
            memberResult = memberService.deleteMember(memberId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @GetMapping("/logout/{memberid}")
    public ResponseEntity<?> logout(@PathVariable String memberId) {
        MemberDto memberResult = null;
        logger.info("logout called!");
        try {
            memberResult = memberService.logout(memberId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @GetMapping("/warn/{memberid}")
    public ResponseEntity<?> warnMember(String memberId) {
        MemberDto memberResult = null;
        logger.info("warnMember called!");
        try {
            memberResult = memberService.warnMember(memberId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
    @GetMapping("/block/{memberid}")
    public ResponseEntity<?> blockMember(String memberId) {
        MemberDto memberResult = null;
        logger.info("blockMember called!");
        try {
            memberResult = memberService.blockMember(memberId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<MemberDto>(memberResult, HttpStatus.OK);
    }
}
