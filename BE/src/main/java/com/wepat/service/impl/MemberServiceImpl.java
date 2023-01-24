package com.wepat.service.impl;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberEntity;
import com.wepat.repository.MemberRepository;
import com.wepat.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutionException;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepo;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepo, JavaMailSender javaMailSender) {
        this.memberRepo = memberRepo;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        if (member.getCalendarId()==null) {
            return memberRepo.signUp(member);
        } else {
            return memberRepo.signUpWithCalendar(member);
        }
    }

    @Override
    public MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.signIn(memberId, pwd);
    }

    @Override
    public MemberEntity findId(String email) throws ExecutionException, InterruptedException {
        return memberRepo.findId(email);
    }

    @Override
    public MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.modifyPwd(memberId, pwd);
    }

    @Override
    public MemberEntity getMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.getMember(memberId);
    }

    @Override
    public MemberEntity modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.modifyMember(member);
    }

    @Override
    public MemberEntity deleteMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.deleteMember(memberId);
    }

    @Override
    public MemberEntity logout(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.logout(memberId);
    }

    @Override
    public MemberEntity warnMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.warnMember(memberId);
    }

    @Override
    public MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.blockMember(memberId);
    }

    @Override
    public void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException {
        System.out.println("service호출!");
        /**
         * 링크 사용시 사용
         */
        String randomPassword = "";
        String[] word = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int idx = 0;
        for(int i=0; i<10; i++) {
            idx = (int) (word.length*Math.random());
            randomPassword += word[idx];
        }

        memberRepo.findPwd(randomPassword, memberId);

        System.out.println("메일 전송!!!!!");
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("qwas15788@gmail.com");
        message.setSubject("WePat 임시비밀번호 안내 이메일입니다.", "UTF-8");
        String htmlStr = "안녕하세요. WePat 임시비밀번호 관련 안내 이메일입니다. <br>회원님의 임시비밀번호는" + randomPassword + "입니다." +
                "<a href=\"https://www.naver.com\">WePat </a>" +
                "해당 링크에 접속하여 비밀번호를 변경해주세요. 감사합니다.";
        message.setText(htmlStr, "UTF-8", "html");
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(email));

        System.out.println(">>>!!" + message);
        javaMailSender.send(message);
        System.out.println("success");
    }

    @Override
    public MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException {
        return memberRepo.addWarnMember(memberId, warnMemberId);
    }

    @Override
    public MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException {
        return memberRepo.addBlockMember(memberId, blockMemberId);
    }

}
