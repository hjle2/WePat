<template>
  <div class="holder-vanilla-default">
    <router-link to="/">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link>
    <!-- <div class="holder-category">
      <strong >필수 입력</strong>
    </div> -->
    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="email">이메일</p>
    </div>
    <input class="input-login" type="text" v-model="email" /><br />

    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="memberId">아이디</p>
    </div>
    <input class="input-login" type="text" v-model="memberId" /><br />
    
    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="nickName">
        닉네임 (중복가능)
      </p>
    </div>
    <input class="input-login" type="text" v-model="nickName" /><br />
    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="pwd">비밀번호</p>
    </div>
    <input class="input-login" type="password" v-model="pwd" /><br />
    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="pwd2">비밀번호 확인</p>
    </div>
    <input class="input-login" type="password" v-model="pwd2" /><br />
    <!-- <div class="holder-category">
      <strong>선택 입력</strong>
    </div> -->
    <div class="holder-text-input">
      <p class="text-input" @keyup.enter="find" for="calendarId">
        캘린더 코드(선택)
      </p>
    </div>
    <input class="input-login" type="text" id="calendarId" v-model="calendarId" /><br />
    <p class="font-small font-grey">
      입력하지 않으면 새 캘린더 코드가 생성됩니다.
    </p>
    <div class="">
      <input
        class="form-check-input"
        type="checkbox"
        value=""
        v-model="checked"
        id="checked"  
      />
      <span>&nbsp;&nbsp;</span>
      <label class="font-lefts form-check-label font-small text-decoration-underline" @click="agree">
      <!-- <label class="font-lefts form-check-label font-small text-decoration-underline"> -->
       개인정보 이용약관 동의
      </label>
      <span>&nbsp;&nbsp;></span>
    </div><br>
    <!-- <button class="btn-colored btn-medium col-10" >약관 보기</button>
    <br /><br /> -->
    <div>
      <button
        v-if="!email || !memberId || !nickName || !pwd || !pwd2 || !checked"
        class="btn-disabled btn-medium input-btn-disabled"
      >
        가입하기
      </button>
      <p 
      v-if="email && memberId && nickName && pwd && pwd2 && checked"
      >
        <button class="btn-colored btn-medium input-btn" @click="signUp()">
          가입하기
        </button>
      </p>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2"
export default {
  data: function () {
    return {
      email: "",
      memberId: "",
      nickName: "",
      calendarId: "",
      pwd: "",
      pwd2: "",
      checked: false,
      // eslint-disable-next-line
      reg: /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/ ]/gim, //pwd를 제외한 모든 입력에선 특수문자 사용 X
      // eslint-disable-next-line
      reg_pwd: /[`~()_|+\-=?;:'",.<>\{\}\[\]\\\/ ]/gim, //pwd에 사용가능한 문자 제거
      // eslint-disable-next-line
      reg_email: /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/gim,
    }
  },
  methods: {
    agree() {
      Swal.fire({
            title: '이용약관',
            text: "< A607 >('https://i8a607.p.ssafy.io'이하 'WePat')은(는) 「개인정보 보호법」 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다. ○ 이 개인정보처리방침은 2023년 2월 14부터 적용됩니다. 제1조(개인정보의 처리 목적) < A607 >('https://i8a607.p.ssafy.io'이하 'WePat')은(는) 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며 이용 목적이 변경되는 경우에는 「개인정보 보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다. 1. 홈페이지 회원가입 및 관리 회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 서비스 부정이용 방지 목적으로 개인정보를 처리합니다. 2. 민원사무 처리 민원인의 신원 확인, 민원사항 확인, 처리결과 통보 목적으로 개인정보를 처리합니다. 3. 재화 또는 서비스 제공 서비스 제공, 콘텐츠 제공을 목적으로 개인정보를 처리합니다. 4. 마케팅 및 광고에의 활용 서비스의 유효성 확인 등을 목적으로 개인정보를 처리합니다. 제2조(개인정보의 처리 및 보유 기간) ① < A607 >은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다. ② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다. 1.<홈페이지 회원가입 및 관리> <홈페이지 회원가입 및 관리>와 관련한 개인정보는 수집.이용에 관한 동의일로부터<3년>까지 위 이용목적을 위하여 보유.이용됩니다. 보유근거 : 보유근거 : 「개인정보보호법」 제15조(개인정보의 수집·이용) 제1항 관련법령 : 예외사유 : 제3조(처리하는 개인정보의 항목) ① < A607 >은(는) 다음의 개인정보 항목을 처리하고 있습니다. 1< 홈페이지 회원가입 및 관리 > 필수항목 : 이메일, 비밀번호, 로그인ID 선택항목 : 닉네임, 캘린더코드 제4조(만 14세 미만 아동의 개인정보 처리에 관한 사항) ① <개인정보처리자명>은(는) 만 14세 미만 아동에 대해 개인정보를 수집할 때 법정대리인의 동의를 얻어 해당 서비스 수행에 필요한 최소한의 개인정보를 수집합니다. • 필수항목 : 법정 대리인의 성명, 관계, 연락처 ② 또한, <개인정보처리자명>의 <처리목적> 관련 홍보를 위해 아동의 개인정보를 수집할 경우에는 법정대리인으로부터 별도의 동의를 얻습니다. ③ <개인정보처리자명>은(는) 만 1 4세 미만 아동의 개인정보를 수집할 때에는 아동에게 법정대리인의 성명, 연락처와 같이 최소한의 정보를 요구할 수 있으며, 다음 중 하나의 방법으로 적법한 법정대리인이 동의하였는지를 확인합니다. • 동의 내용을 게재한 인터넷 사이트에 법정대리인이 동의 여부를 표시하도록 하고 개인정보처리자가 그 동의 표시를 확인했음을 법정대리인의 휴대전화 문자 메시지로 알리는 방법 • 동의 내용을 게재한 인터넷 사이트에 법정대리인이 동의 여부를 표시하도록 하고 법정대리인의 신용카드·직불카드 등의 카드정보를 제공받는 방법 • 동의 내용을 게재한 인터넷 사이트에 법정대리인이 동의 여부를 표시하도록 하고 법정대리인의 휴대전화 본인인증 등을 통해 본인 여부를 확인하는 방법 • 동의 내용이 적힌 서면을 법정대리인에게 직접 발급하거나, 우편 또는 팩스를 통하여 전달하고 법정대리인이 동의 내용에 대하여 서명날인 후 제출하도록 하는 방법 • 동의 내용이 적힌 전자우편을 발송하여 법정대리인으로부터 동의의 의사표시가 적힌 전자우편을 전송받는 방법 • 전화를 통하여 동의 내용을 법정대리인에게 알리고 동의를 얻거나 인터넷주소 등 동의 내용을 확인할 수 있는 방법을 안내하고 재차 전화 통화를 통하여 동의를 얻는 방법 • 그 밖에 위와 준하는 방법으로 법정대리인에게 동의 내용을 알리고 동의의 의사표시를 확인하는 방법 제7조(개인정보의 파기절차 및 파기방법) ① < A607 > 은(는) 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다. ② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다. 1. 법령 근거 : 2. 보존하는 개인정보 항목 : 계좌정보, 거래날짜 ③ 개인정보 파기의 절차 및 방법은 다음과 같습니다. 1. 파기절차 < A607 > 은(는) 파기 사유가 발생한 개인정보를 선정하고, < A607 > 의 개인정보 보호책임자의 승인을 받아 개인정보를 파기합니다. 2. 파기방법 전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다. 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다 제8조(정보주체와 법정대리인의 권리·의무 및 그 행사방법에 관한 사항) ① 정보주체는 A607에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다. ② 제1항에 따른 권리 행사는A607에 대해 「개인정보 보호법」 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 A607은(는) 이에 대해 지체 없이 조치하겠습니다. ③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다.이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다. ④ 개인정보 열람 및 처리정지 요구는 「개인정보 보호법」 제35조 제4항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다. ⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다. ⑥ A607은(는) 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다. 제9조(개인정보의 안전성 확보조치에 관한 사항) < A607 >은(는) 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다. 1. 내부관리계획의 수립 및 시행 개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다. 2. 개인정보의 암호화 이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다. 3. 개인정보에 대한 접근 제한 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다. 4. 문서보안을 위한 잠금장치 사용 개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다. 제10조(개인정보를 자동으로 수집하는 장치의 설치·운영 및 그 거부에 관한 사항) A607 은(는) 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다. 제11조(행태정보의 수집·이용·제공 및 거부 등에 관한 사항) 행태정보의 수집·이용·제공 및 거부등에 관한 사항 <개인정보처리자명>은(는) 온라인 맞춤형 광고 등을 위한 행태정보를 수집·이용·제공하지 않습니다. 제12조(추가적인 이용·제공 판단기준) < A607 > 은(는) ｢개인정보 보호법｣ 제15조제3항 및 제17조제4항에 따라 ｢개인정보 보호법 시행령｣ 제14조의2에 따른 사항을 고려하여 정보주체의 동의 없이 개인정보를 추가적으로 이용·제공할 수 있습니다. 이에 따라 < A607 > 가(이) 정보주체의 동의 없이 추가적인 이용·제공을 하기 위해서 다음과 같은 사항을 고려하였습니다. ▶ 개인정보를 추가적으로 이용·제공하려는 목적이 당초 수집 목적과 관련성이 있는지 여부 ▶ 개인정보를 수집한 정황 또는 처리 관행에 비추어 볼 때 추가적인 이용·제공에 대한 예측 가능성이 있는지 여부 ▶ 개인정보의 추가적인 이용·제공이 정보주체의 이익을 부당하게 침해하는지 여부 ▶ 가명처리 또는 암호화 등 안전성 확보에 필요한 조치를 하였는지 여부 ※ 추가적인 이용·제공 시 고려사항에 대한 판단기준은 사업자/단체 스스로 자율적으로 판단하여 작성·공개함 제14조 (개인정보 보호책임자에 관한 사항) ① A607 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다. ▶ 개인정보 보호책임자 성명 :이정규 직책 :QA 연락처 : kue411@naver.com, ※ 개인정보 보호 담당부서로 연결됩니다. ② 정보주체께서는 A607 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. A607 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다. 제17조(정보주체의 권익침해에 대한 구제방법) 정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다. 1. 개인정보분쟁조정위원회 : (국번없이) 1833-6972 (www.kopico.go.kr) 2. 개인정보침해신고센터 : (국번없이) 118 (privacy.kisa.or.kr) 3. 대검찰청 : (국번없이) 1301 (www.spo.go.kr) 4. 경찰청 : (국번없이) 182 (ecrm.cyber.go.kr) 「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다. ※ 행정심판에 대해 자세한 사항은 중앙행정심판위원회(www.simpan.go.kr) 홈페이지를 참고하시기 바랍니다. 제19조(개인정보 처리방침 변경) ① 이 개인정보처리방침은 2023년 2월 14부터 적용됩니다. ② 이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다. 예시 ) - 20XX. X. X ~ 20XX. X. X 적용 (클릭)",
            // icon: 'warning',
            
            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: '#faa960', // confrim 버튼 색깔 지정
            cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
            confirmButtonText: '동의합니다', // confirm 버튼 텍스트 지정
            cancelButtonText: '취소', // cancel 버튼 텍스트 지정
            reverseButtons: true, // 버튼 순서 거꾸로
            showCloseButton: true,
            
         }).then((result) => {
            // console.log(result)
            if (result.isConfirmed === true) {
              this.checked = true
            } else {
              this.checked = false
            }
          })
    },
    signUp() {
      if (this.pwd.length < 8 || this.pwd2.length < 8) {
        alert("비밀번호 8자리 이상 적어주세요")
      } else if (this.reg_pwd.test(this.pwd)) {
        alert(
          "비밀번호 특수문자는 특수문자는 !, @, #, $, %, ^, &, * 만 사용 가능합니다."
        )
        return
      } else if (this.reg.test(this.memberId) || this.reg.test(this.nickName)) {
        alert("비밀번호를 제외한 입력값은 특수문자를 사용할 수 없습니다.")
        return
      } else if (!this.reg_email.test(this.email)) {
        alert("이메일 양식이 올바르지 않습니다. 양식에 맞게 입력해주세요.")
        return
      } else if (this.pwd === this.pwd2) {
        const email = this.email
        const memberId = this.memberId
        const nickName = this.nickName
        const calendarId = this.calendarId
        const pwd = this.pwd
        const photoUrl = "https://firebasestorage.googleapis.com/v0/b/a607-445bc.appspot.com/o/default_member_img.png?alt=media&token=e599eb6b-e6a1-4306-9cfa-7c412145cd37"

        const payload = {
          email,
          memberId,
          nickName,
          calendarId,
          pwd,
          photoUrl
        }
        this.$store.dispatch("signUp", payload)
      } else {
        alert("비밀번호 확인해주세요")
      }
    },
  },
}
</script>

<style lang="scss">
.input-login {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
}

.input-btn {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  // padding-left: 5vw;
}

.input-btn-disabled {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  // border: 1vh solid $color-main-75;
  margin: 0;
  // padding-left: 5vw;
}

.form-check-input:checked {
  background-color: #faa960 !important;
  border-color: #faa960 !important;
}
</style>
