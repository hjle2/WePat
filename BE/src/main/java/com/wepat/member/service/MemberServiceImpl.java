package com.wepat.member.service;

import com.wepat.member.MemberDto;
import com.wepat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Override
    public void signUp(MemberDto member) throws ExecutionException, InterruptedException {
        if (member.getCalendarId() == null) {
            memberRepository.signUp(member);
        } else {
            memberRepository.signUpWithCalendar(member);
        }
    }
    @Override
    public void socialsignup(MemberDto member) throws ExecutionException, InterruptedException {
            memberRepository.socialsignup(member);//무조건 아이디생성

    }
    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepository.signIn(memberId, pwd);
    }

    @Override
    public MemberDto socialSignIn(String memberId, int social) throws ExecutionException, InterruptedException {
        return memberRepository.socialsignin(memberId, social);
    }

    @Override
    public String findId(String email) throws ExecutionException, InterruptedException {
        return memberRepository.findId(email);
    }
    @Override
    public void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException {
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

        memberRepository.changePwdToRandom(randomPassword, memberId);

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("qwas15788@gmail.com");
        message.setSubject("WePat 임시비밀번호 안내 이메일입니다.", "UTF-8");
        String htmlStr = "안녕하세요. WePat 임시비밀번호 관련 안내 이메일입니다. <br>회원님의 임시비밀번호는" + randomPassword + "입니다." +
                "<a href=\"https://www.naver.com\">WePat </a>" +
                "해당 링크에 접속하여 비밀번호를 변경해주세요. 감사합니다.";
        message.setText(htmlStr, "UTF-8", "html");
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(email));

        javaMailSender.send(message);
    }

    @Override
    public void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        memberRepository.modifyPwd(memberId, pwd);
    }

    @Override
    public MemberDto getMemberDetail(String memberId) throws ExecutionException, InterruptedException {
        MemberDto memberDto = memberRepository.getMemberById(memberId);
        memberDto.setPwd(null);
        return memberDto;
    }

    @Override
    public void modifyMemberDetail(String memberId, String nickName) throws ExecutionException, InterruptedException {
        memberRepository.modifyMember(memberId, nickName);
    }

    @Override
    public void deleteMember(String memberId) throws ExecutionException, InterruptedException {
        memberRepository.deleteMember(memberId);
    }

    @Transactional
    @Override
    public void logout(String memberId) throws Exception {
        memberRepository.deleteRefreshToken(memberId);
    }

    @Override
    public void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException {
        memberRepository.modifyCalendarId(memberId, calendarId);
    }

    @Override
    public void addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException {
        memberRepository.addWarnMember(memberId, warnMemberId);
    }

    @Override
    public void addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException {
        memberRepository.addBlockMember(memberId, blockMemberId);
    }

    @Override
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception {
        memberRepository.saveRefreshToken(memberId, refreshToken);
    }

    @Override
    public void modifyCalendarIdAlone(String memberId) throws ExecutionException, InterruptedException {
        memberRepository.modifyCalendarIdAlone(memberId);
    }


}
