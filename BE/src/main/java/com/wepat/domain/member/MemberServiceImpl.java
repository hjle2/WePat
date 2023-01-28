package com.wepat.domain.member;

import com.wepat.domain.security.OpenCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepo;
    private final JavaMailSender javaMailSender;

    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        //비밀번호 값 암호화
//        member.setPwd(OpenCrypt.getSHA256(member.getPwd(),"salt").toString());

        if (member.getCalendarId() == null) {
            return memberRepo.signUp(member);
        } else {
            return memberRepo.signUpWithCalendar(member);
        }
    }

    @Override
    public MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        //입력받은 비밀번호 값도 암호화
//        pwd = OpenCrypt.getSHA256(pwd,"salt").toString();
        return memberRepo.signIn(memberId, pwd);
    }

    @Override
    public MemberEntity findId(String email) throws ExecutionException, InterruptedException {
        return memberRepo.findId(email);
    }

    @Override
    public MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {

        //비밀번호 값 암호화
        String pwdEncode = OpenCrypt.getSHA256(pwd,"salt").toString();
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
//
//    @Override
//    public String createAccessToken(String memberId, String pwd) {
//        Long expireMs = 1000 * 60 * 60*3L;//유지 시간을 3시간(임의)으로 설정
//        return createJwt(memberId,"Access", pwd,expireMs);
//    }
//    @Override
//    public String createRefreshToken(String memberId, String pwd) {
//        Long expireMs = 1000 * 60 * 60*24*365L;//1년간 유지(임의)
//        return createJwt(memberId,"Refresh", pwd,expireMs);
//    }



//    @Override
//    public void saveRefreshToken(String memberId, String refreshToken) {
//       // memberRepo.signUpWithCalendar(member); 일단 킵해두고 member에 RT 컬럼을 추가하는걸로
//    }

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
        return null;
    }

    @Override
    public void saveRefreshToken(String userid, String refreshToken) throws Exception {

    }

    @Override
    public Object getRefreshToken(String userid) throws Exception {
        return null;
    }

    @Override
    public void deleRefreshToken(String userid) throws Exception {

    }
}
