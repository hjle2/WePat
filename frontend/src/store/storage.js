import { createStore } from "vuex"
import axios from "axios"
import router from "@/router"
import createPersistedState from "vuex-persistedstate"
import PetListView from "@/views/pet/PetListView.vue"
import PetProfileView from "@/views/pet/PetProfileView.vue"
import AlbumView from "@/views/album/AlbumView.vue"
import MainView from "@/views/mainpage/MainView.vue"
import Swal from "sweetalert2"

// const swal = new SweetAlert2();

// const  = 'http://192.168.0.12:8080'
// const API_URL = "http://70.12.247.105:8080/api"
// const API_URL = "http://localhost:8080"
// const API_URL = "http://i8a607.p.ssafy.io:8080/api"
// const API_URL = "http://i8a607.p.ssafy.io:8080/api"
const API_URL = "https://i8a607.p.ssafy.io/api"
// const API_URL = "http://192.168.59.51:8080/api"

// const API_URL = "http://172.16.101.40:8080"
// const API_URL = "http://70.12.247.102:8080/api"
// const API_URL = "http://i8a607.p.ssafy.io:8080/api"
// const API_URL = "http://192.168.59.51:8080/api"
// const API_URL = "http://70.12.247.105:8080"
// //팀장님이 등록한거
// const API_URL = "http://3.35.205.124:8080"

//내 로컬
// const API_URL = "http://localhost:8080"

const store = createStore({
  plugins: [createPersistedState()],
  state: {
    accessToken: null,
    calendarId: null,
    scheduleId: null,
    notificationId: null,
    nickName: null,
    eventSource: Object,
    inconnect: Object,
    pets: [],
    pet: [],
    albums: [],
    album: [],
    snss1: [],
    snss2: [],
    snss3: [],
    snss4: [],
    snss: [],
    snsss: [],
    sns: [],
    todo: [],
    calendarIdtest: null,
    loadingStatus: false,
  },

  getters: {
    isLogin() {
      return localStorage.getItem("token") ? true : false
    },
  },
  mutations: {
    // 회원가입 && 로그인
    SAVE_TOKEN(state, data) {
      console.log("받은거" + data)
      state.accessToken = data["access-token"]
      localStorage.setItem("token", data["refresh-token"])
      // console.log("ID " + localStorage.getItem("memberId"))
      console.log("액세스 " + state.accessToken)
      console.log("리프레쉬 " + localStorage.getItem("token"))
      router.push({ path: "/mainpage" })
    },
    DELETE_TOKEN(state) {
      state.accessToken = null
      localStorage.removeItem("token")
      localStorage.removeItem("memberId")
      localStorage.removeItem("calenderId")
      router.push({ path: "/" })
    },
    LOADING_STATUS(state, loadingStatus) {
      state.loadingStatus = loadingStatus
    },
  },
  actions: {
    //토큰 갱신
    refresh() {
      const refreshToken = localStorage.getItem("token")
      console.log("갱신 시작")
      axios({
        method: "post",
        url: `${API_URL}/member/gettoken`,
        headers: { token: refreshToken },
        // params: {
        //   refreshToken,
        // },
      })
        .then((res) => {
          console.log("갱신완료" + res)
          console.log(res)
          console.log(res.data)
          this.state.accessToken = res.data
          console.log(this.state.accessToken)
          console.log("리프레쉬 끝")
          return res.data
        })
        .catch((res) => {
          console.log("갱신완료..\\" + res)
          console.log(res)
        })
    },

    //회원정보 요청
    getUserData() {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            this.state.calendarId = res.data.calendarId
            this.state.nickName = res.data.nickName
            resolve(res.data)
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                console.log(this.state.accessToken)
                this.dispatch("getUserData")
              }
            } else reject(err)
          })
      })
    },

    //로그인
    login(context, payload) {
      const memberId = payload.memberId
      const pwd = payload.pwd
      axios({
        method: "post",
        url: `${API_URL}/member/signin`,
        params: {
          memberId,
          pwd,
        },
      })
        .then((res) => {
          // console.log(res)
          console.log(res.data)
          localStorage.setItem("memberId", payload.memberId)
          context.commit("SAVE_TOKEN", res.data)
          // this.dispatch("getUserData")
        })
        .catch((err) => {
          localStorage.removeItem("vuex")
          console.log("로그인 에러")
          console.log(payload)
          console.log(err)
          console.log(err.response)
          const message = err.response.data.message
          this.state.loadingStatus = false
          if (err.response.status) {
            Swal.fire({
              icon: "error",
              title: "로그인 오류",
              text: message,
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // footer: '<a :href="/findpw">비밀번호 찾기</a>'
            })
          }
        })
    },
    SNSlogin(context, payload) {
      //SNS를 이용한 로그인

      const id = payload.id
      const SNS = payload.SNS
      // const email = payload.email

      console.log(payload)

      axios({
        method: "post",
        url: `${API_URL}/member/socialsignin`,
        params: {
          memberId: id,
          // email,
          social: SNS,
        },
      })
        .then((res) => {
          console.log(res.data)
          //res가 null이면 회원가입으로 보내고싶다..
          localStorage.setItem("token", res.data["refresh-token"])
          localStorage.setItem("memberId", payload.id)
          this.state.accessToken = res.data["access-token"]
          router.push({ path: "/mainpage" })

          //context.commit('SAVE_TOKEN', res.data)
          // context.commit("SAVE_TOKEN", res)
        })
        .catch((err) => {
          localStorage.removeItem("vuex")
          console.log(context)
          console.log(err)
          Swal.fire({
            title: "해당 SNS로 회원가입된 내역이 없습니다.",
            text: "회원가입 하시겠습니까?",
            icon: "warning",

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

            reverseButtons: true, // 버튼 순서 거꾸로
          }).then((result) => {
            if (result.isConfirmed) {
              this.dispatch("socialSignUp", payload)
            }
          })
        })
    },
    socialSignUp(context, payload) {
      const email = payload.email
      const memberId = payload.id
      const nickName = payload.nickName
      const social = payload.SNS

      console.log("social Sign Up")
      console.log(payload)
      axios({
        method: "post",
        url: `${API_URL}/member/socialsignup`,
        params: {
          memberId,
          email,
          nickName,
          social,
        },
      })
        .then((res) => {
          console.log(res)
          alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.")
          this.dispatch("SNSlogin")
        })
        .catch((err) => console.log(err))
    },
    loginSoon() {
      Swal.fire({
        title: "소셜 로그인",
        text: "업데이트 예정입니다.",
        icon: "warning",

        //showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
        cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
        confirmButtonText: "확인", // confirm 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
      })
    },
    //로그아웃
    logOut(context) {
      axios({
        method: "get",
        url: `${API_URL}/member/logout`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log("로그아웃", res)
          Swal.fire({
            title: "로그아웃 완료",
            text: "또 만나요!",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
          context.commit("DELETE_TOKEN")
        })
        .then(() => {
          const inconnect = document.getElementById("logout")
          this.inconnect = inconnect
          inconnect.onclick = () => {
            console.log("close")
            this.eventSource.close()
          }
        })
        .catch((err) => {
          console.log(err)
          console.log(err.response.status)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 500 ||
            err.response.status === 410
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("logOut")
            }
          }
        })
    },
    //회원가입
    signUp(context, payload) {
      const email = payload.email
      const memberId = payload.memberId
      const nickName = payload.nickName
      const calendarId = payload.calendarId
      const pwd = payload.pwd
      const photoUrl = payload.photoUrl

      axios({
        method: "post",
        url: `${API_URL}/member/signup`,
        params: {
          calendarId,
          email,
          memberId,
          nickName,
          pwd,
          photoUrl,
        },
      })
        .then((res) => {
          console.log(res)
          Swal.fire({
            icon: "success",
            title: "회원가입이 완료되었습니다!",
            text: "로그인 페이지로 이동합니다.",
          })
          router.push({ path: "/" })
        })
        .catch((err) => {
          console.log(err)
          console.log(err.response.status)
          console.log(err.response.data.message)
          const message = err.response.data.message
          this.state.loadingStatus = false
          if (err.response.status) {
            Swal.fire({
              icon: "error",
              title: "회원가입 오류",
              text: message,
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // footer: '<a :href="/findpw">비밀번호 찾기</a>'
            })
          }
        })
    },
    //회원탈퇴
    deleteUser(context) {
      axios({
        method: "delete",
        url: `${API_URL}/member/`,
        headers: { token: this.state.accessToken },
      })
        .then(() => {
          Swal.fire({
            title: "회원탈퇴 완료",
            text: "이용해 주셔서 감사합니다.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
          context.commit("DELETE_TOKEN")
        })
        .catch((err) => {
          console.log(err)
          console.log(err.response.status)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 500 ||
            err.response.status === 410
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deleteUser")
            }
          }
        })
    },
    // 비밀번호 변경
    changePwd(context, payload) {
      const originPwd = payload.pwd
      const newPwd = payload.newPwd
      const memberId = localStorage.getItem("memberId")
      // console.log(memberId, pwd)
      axios({
        method: "put",
        url: `${API_URL}/member/modifypwd`,
        headers: { token: this.state.accessToken },
        params: {
          memberId,
          originPwd,
          newPwd,
        },
      })
        .then(() => {
          context.commit("DELETE_TOKEN")
          Swal.fire({
            icon: "success",
            title: "변경 완료",
            text: "다시 로그인해 주세요.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
        })
        .catch((err) => {
          console.log(err)
          console.log(err.response.status)
          const message = err.response.data.message
          this.state.loadingStatus = false
          if (err.response.status) {
            Swal.fire({
              icon: "error",
              title: "변경 오류",
              text: message,
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // footer: '<a :href="/findpw">비밀번호 찾기</a>'
            })
          } else if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("changePwd", payload)
            }
          }
        })
    },

    //닉네임 수정
    changeNickname(context, payload) {
      const nickName = payload
      axios({
        method: "put",
        url: `${API_URL}/member/modify`,
        headers: { token: this.state.accessToken },
        params: {
          // memberId,
          nickName,
        },
      })
        .then(() => {
          context.commit("DELETE_TOKEN")
          Swal.fire({
            icon: "success",
            title: "변경 완료",
            text: "다시 로그인해 주세요.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
        })
        .catch((err) => {
          console.log(err)
          this.state.loadingStatus = false
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("changeNickname", payload)
            }
          }
        })
    },

    //이미지 수정
    changeImage(context, payload) {
      const memberId = localStorage.getItem("memberId")
      const photoUrl = payload
      axios({
        method: "put",
        url: `${API_URL}/member/photo`,
        headers: { token: this.state.accessToken },
        params: {
          memberId,
          photoUrl,
        },
      })
        .then(() => {
          Swal.fire({
            icon: "success",
            title: "변경 완료",
            text: "이미지 변경완료되었습니다.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("changeImage", payload)
            }
          }
        })
    },

    // 아이디 찾기
    findId(context, payload) {
      return new Promise((resolve, reject) => {
        console.log(payload)
        const email = payload
        axios({
          method: "post",
          url: `${API_URL}/member/findid`,
          params: {
            email,
          },
        })
          .then((res) => {
            console.log(res)
            resolve(res.data)
          })
          .catch((err) => {
            console.log(err)
            reject(err)
            Swal.fire({
              icon: "error",
              title: "찾기 오류",
              text: "등록되지 않은 계정입니다.",
              confirmButtonText: "예", // confirm 버튼 텍스트 지정
              // timer: 1500,
              // timerProgressBar: true,
              // showConfirmButton: false,
            }).then((result) => {
              if (result.isConfirmed) {
                router.go(0)
                // localStorage.setItem("vuex", {"loadingStatus":false})
                this.loadingStatus = false
              }
            })
          })
      })
    },

    // 비밀번호 찾기
    findPwd(context, payload) {
      console.log(payload)
      const email = payload.email
      const memberId = payload.memberId
      axios({
        method: "post",
        url: `${API_URL}/member/findpwd`,
        params: {
          email,
          memberId,
        },
      })
        .then((res) => {
          console.log(res)
          Swal.fire({
            title: "임시 비밀번호가 발송되었습니다.",
            text: "관련 사이트로 이동하시겠습니까?",
            icon: "warning",

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

            reverseButtons: true, // 버튼 순서 거꾸로
          }).then((result) => {
            if (result.isConfirmed) {
              const site = email.split("@")
              window.open("https://" + site[1], "_blank")
              localStorage.removeItem("vuex")
              router.go(-1)
            }
          })
        })
        .catch((err) => {
          // console.log(err)
          // console.log(err.response.status)
          const message = err.response.data.message
          console.log(message)
          Swal.fire({
            icon: "error",
            title: "찾기 오류",
            text: "다시 시도해 주세요.",
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            // timer: 1500,
            // timerProgressBar: true,
            // showConfirmButton: false,
          }).then((result) => {
            if (result.isConfirmed) {
              localStorage.removeItem("vuex")
              router.go(0)
              // localStorage.setItem("vuex", {"loadingStatus":false})
              this.loadingStatus = false
            }
          })
        })
    },

    //캘린더 새로 적용하기
    changeCalendar(context, payload) {
      const calendarId = payload
      console.log(calendarId)
      axios({
        method: "put",
        url: `${API_URL}/member/modify/calendar`,
        headers: { token: this.state.accessToken },
        params: {
          calendarId,
        },
      })
        .then(() => {
          context.commit("DELETE_TOKEN")
          Swal.fire({
            icon: "success",
            title: "변경 완료",
            text: "다시 로그인해 주세요.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("changeCalendar", payload)
            }
          }
        })
    },

    //캘린더 새로 만들기
    createCalendar(context) {
      axios({
        method: "put",
        url: `${API_URL}/member/modify/calendar/alone`,
        headers: { token: this.state.accessToken },
      })
        .then(() => {
          context.commit("DELETE_TOKEN")
          Swal.fire({
            icon: "success",
            title: "변경 완료",
            text: "다시 로그인해 주세요.",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("createCalendar")
            }
          }
        })
    },

    // 가계부 정보 불러오기
    getFinanceData() {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            const calendarId = res.data.calendarId
            console.log(res.data)
            axios({
              method: "get",
              url: `${API_URL}/finance/${calendarId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            console.log(this.state.accessToken)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                console.log(localStorage.getItem("token"))
                this.dispatch("getFinanceData")
              }
            } else reject(err)
          })
      })
    },

    // 가계부 세부 정보 추가하기
    addFinance(context, payload) {
      const category = payload.category
      const date = payload.date
      const income = payload.income
      const memo = payload.memo
      const money = payload.money
      // console.log("넘어온 정보" + payload)
      // console.log("토큰" + this.state.accessToken)
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const nickName = res.data.nickName
          const calendarId = res.data.calendarId

          // console.log(res.data)
          // console.log(this.state.accessToken)
          // const temp_params = {
          //   category,
          //   date,
          //   income,
          //   memo,
          //   money,
          //   nickName,
          // };
          // console.log(temp_params);
          // console.log("아이디" + calendarId);
          axios({
            method: "post",
            url: `${API_URL}/finance/add/${calendarId}`,
            headers: { token: this.state.accessToken },
            data: {
              category,
              date,
              income,
              memo,
              money,
              nickName,
            },
          })
            .then((res) => {
              console.log(res.data)
              router.push({ path: "/financial" })
            })
            .catch((err) => {
              console.log("등록불가" + err)
            })
        })
        .catch((err) => {
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("addFinance", payload)
            }
          }
        })
    },

    // 가계부 세부 정보 삭제하기
    deleteFinance(context, payload) {
      const calendarId = payload.calendarId
      const financeId = payload.financeId
      console.log("지울아이디" + calendarId)
      console.log("지울아이디" + financeId)

      axios({
        method: "delete",
        url: `${API_URL}/finance/delete/${calendarId}/${financeId}`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          Swal.fire({
            icon: "success",
            title: "삭제 완료",
            // text: '다시 로그인해 주세요.',
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
          router.push({ path: "/financial" })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deleteFinance", payload)
            }
          }
        })
    },

    // 가계부 세부 정보 조회하기
    detailFinance(context, payload) {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            console.log("회원정보" + res.data)
            const calendarId = res.data.calendarId
            const financeId = payload
            console.log("캘린더아이디" + calendarId)
            console.log("받아온 가계부아이디" + financeId)
            axios({
              method: "get",
              url: `${API_URL}/finance/detail/${calendarId}/${financeId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              console.log(res)
              console.log(res.data)
              console.log("조회성공")
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("detailFinance", payload)
              }
            } else reject(err)
          })
      })
    },

    // 가계부 세부 정보 변경하기
    changeFinance(context, payload) {
      const calendarId = payload.calendarId
      const category = payload.category
      const date = payload.date
      const financeId = payload.financeId
      const income = payload.income
      const memo = payload.memo
      const money = payload.money
      const nickName = payload.nickName
      console.log("수정할 캘린더" + calendarId)
      console.log("수정할 내역" + financeId)
      axios({
        method: "put",
        url: `${API_URL}/finance/modify/${calendarId}/${financeId}`,
        headers: { token: this.state.accessToken },
        data: {
          category,
          date,
          financeId,
          income,
          memo,
          money,
          nickName,
        },
      })
        .then((res) => {
          console.log(res)
          Swal.fire({
            icon: "success",
            title: "수정 완료",
            // text: '다시 로그인해 주세요.',
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
          router.push({ path: "/financial" })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("changeFinance", payload)
            }
          }
        })
    },

    // 캘린더 멤버 불러오기
    getMember() {
      return new Promise((resolve) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            const calendarId = res.data.calendarId
            axios({
              method: "get",
              url: `${API_URL}/schedule/member/${calendarId}`,
              headers: { token: this.state.accessToken },
              params: {
                calendarId: calendarId,
              },
            })
              .then((res) => {
                console.log(res.data)
                resolve(res.data)
              })
              .catch((err) => console.log(err))
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh", localStorage.getItem("token"))
                this.dispatch("getMember")
              }
            }
          })
      })
    },

    // 일정 불러오기
    getEvent() {
      return new Promise((resolve) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            const calendarId = res.data.calendarId
            axios({
              method: "get",
              url: `${API_URL}/schedule/${calendarId}`,
              headers: { token: this.state.accessToken },
              params: {
                calendarId: calendarId,
              },
            })
              .then((res) => {
                console.log(res.data)
                resolve(res.data)
              })
              .catch((err) => console.log(err))
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh", localStorage.getItem("token"))
                this.dispatch("getEvent")
              }
            }
          })
      })
    },

    // notificationId 불러오기

    getNotificationId() {
      // const memberId = this.memberId
      // console.log(memberId)
      return new Promise((resolve) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then(() => {
            // const memberId = res.data.memberId
            axios({
              method: "get",
              url: `${API_URL}/notification/count`,
              headers: { token: this.state.accessToken },
            })
              .then((res) => {
                console.log(res.data)
                resolve(res.data)
              })
              .catch((err) => console.log(err))
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh", localStorage.getItem("token"))
                this.dispatch("getNotificationId")
              }
            }
          })
      })
    },

    getNotification() {
      return new Promise((resolve) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then(() => {
            axios({
              method: "get",
              url: `${API_URL}/notification/getall`,
              headers: { token: this.state.accessToken },
            })
              .then((res) => {
                console.log(res.data)
                resolve(res.data)
              })
              .catch((err) => console.log(err))
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 500 ||
              err.response.status === 410
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh", localStorage.getItem("token"))
                this.dispatch("getNotification")
              }
            }
          })
      })
    },

    // 알림 삭제

    deleteNoti(context, notificationId) {
      axios({
        method: "delete",
        url: `${API_URL}/notification/delete/${notificationId}`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          router.go()
          // this.dispatch("getNotification")
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500 ||
            this.state.accessToken === null
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deleteNoti", notificationId)
            }
          }
        })
    },

    // // 알람 불러오기
    // getAlarm() {
    //   const calendarId = this.calendarId
    //   console.log(calendarId)
    //   return new Promise((resolve) => {
    //     axios({
    //       method: "get",
    //       url: `${API_URL}/member`,
    //       headers: { token: this.state.accessToken },
    //     })
    //       .then((res) => {
    //         const calendarId = res.data.calendarId
    //         axios({
    //           method: "get",
    //           url: `${API_URL}/get/${notificationId}`,
    //           headers: { token: this.state.accessToken },
    //           params: {
    //             calendarId: calendarId,
    //           },
    //         })
    //           .then((res) => {
    //             console.log(res.data)
    //             resolve(res.data)
    //           })
    //           .catch((err) => console.log(err))
    //       })
    //       .catch((err) => {
    //         console.log(err)
    //         if (err.response.status === 410) {
    //           if (localStorage.getItem("token")) {
    //             this.dispatch("refresh", localStorage.getItem("token"))
    //             this.dispatch("getAlarm")
    //           }
    //         }
    //       })
    //   })
    // },

    // 일정 추가
    addCalTodo(context, payload) {
      const title = payload.title
      const category = payload.category
      const petId = payload.petId
      const startDate = payload.startDate
      const endDate = payload.endDate
      const nowDate = payload.nowDate
      const repeatUnit = payload.repeatUnit
      const repeatAmount = payload.repeatAmount
      const repeatEndDate = payload.repeatEndDate
      const alarm = payload.alarm
      const display = payload.display
      const memo = payload.memo
      const whoPlanned = payload.whoPlanned
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId

          console.log(res.data + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
          console.log(this.state.accessToken)
          const temp_params = {
            title,
            category,
            petId,
            startDate,
            endDate,
            repeatUnit,
            repeatAmount,
            repeatEndDate,
            alarm,
            display,
            memo,
            whoPlanned,
          }
          console.log(temp_params)
          console.log(calendarId)
          axios({
            method: "post",
            url: `${API_URL}/schedule/add/${calendarId}`,
            headers: { token: this.state.accessToken },
            params: {
              nowDate: nowDate,
            },
            data: {
              title,
              category,
              petId,
              startDate,
              endDate,
              repeatUnit,
              repeatAmount,
              repeatEndDate,
              alarm,
              display,
              memo,
              whoPlanned,
            },
          })
            .then((res) => {
              console.log("=============res>>>>>>>>>>>>")
              console.log(res)
              router.push({ path: "/mainpage" })
            })
            .catch((err) => console.log(err))
        })
        .catch((err) => {
          console.log("토큰만료" + err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh", localStorage.getItem("token"))
              this.dispatch("addCalTodo", payload)
            }
          }
        })
    },

    // 일정 수정
    updateTodo(context, payload) {
      const title = payload.title
      const category = payload.category
      const petId = payload.petId
      const startDate = payload.startDate
      const endDate = payload.endDate
      const nowDate = payload.nowDate
      const repeatUnit = payload.repeatUnit
      const repeatAmount = payload.repeatAmount
      const repeatEndDate = payload.repeatEndDate
      const alarm = payload.alarm
      const display = payload.display
      const memo = payload.memo
      const whoPlanned = payload.whoPlanned
      const scheduleId = payload.scheduleId
      const photoUrl = payload.photoUrl
      const reviewList = payload.reviewList
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const temp_params = {
            title,
            category,
            petId,
            startDate,
            endDate,
            repeatUnit,
            repeatAmount,
            repeatEndDate,
            alarm,
            display,
            memo,
            whoPlanned,
          }
          console.log(temp_params)
          console.log(scheduleId)

          axios({
            method: "put",
            url: `${API_URL}/schedule/modify/${res.data.calendarId}/${scheduleId}`,
            headers: { token: this.state.accessToken },
            params: {
              nowDate: nowDate,
            },
            data: {
              title,
              category,
              petId,
              startDate,
              endDate,
              repeatUnit,
              repeatAmount,
              repeatEndDate,
              alarm,
              display,
              memo,
              whoPlanned,
              photoUrl,
              reviewList,
            },
          })
            .then((res) => {
              console.log(res)
              router.push({ path: `/mainpage/todo/detail/${scheduleId}` })
            })
            .catch((err) => console.log(err))
        })
        .catch((err) => {
          console.log("토큰만료" + err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh", localStorage.getItem("token"))
              this.dispatch("updateTodo", payload)
            }
          }
        })
    },

    // Todo 디테일 조회

    detailTodo(context, payload) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const calendarId = res.data.calendarId
          const scheduleId = payload
          axios({
            method: "get",
            url: `${API_URL}/schedule/detail/${calendarId}/${scheduleId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res.data)
            this.state.todo = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("detailTodo", payload)
            }
          }
        })
    },

    detailTodo2(context, payload) {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            console.log(res)
            const calendarId = res.data.calendarId
            const scheduleId = payload
            axios({
              method: "get",
              url: `${API_URL}/schedule/detail/${calendarId}/${scheduleId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              console.log(res.data)
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("detailTodo2", payload)
              }
            } else reject(err)
          })
      })
    },

    // 일정 완료, 완료자 수정

    completeTodo(context, payload) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const calendarId = res.data.calendarId
          const whoCompleted = payload.whoCompleted
          const scheduleId = payload.scheduleId
          const completed = payload.completed
          const nowDate = payload.now
          // const now
          axios({
            method: "put",
            url: `${API_URL}/schedule/complete/${calendarId}/${scheduleId}`,
            headers: { token: this.state.accessToken },
            params: {
              whoCompleted: whoCompleted,
              completed: completed,
              nowDate: nowDate,
            },
          }).then((res) => {
            console.log(res)
            router.go()
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("completeTodo", payload)
            }
          }
        })
    },

    // Todo 삭제

    deleteTodo(context, payload) {
      const scheduleId = payload
      const calendarId = this.state.calendarId
      axios({
        method: "delete",
        url: `${API_URL}/schedule/delete/${calendarId}/${scheduleId}`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          router.push({ name: MainView })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deleteTodo", payload)
            }
          }
        })
    },

    // Schedule 사진 최신화

    sendScheduleImage(context, payload) {
      const photoUrl = payload.photoUrl
      const scheduleId = payload.scheduleId
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "put",
            url: `${API_URL}/schedule/photo/${calendarId}/${scheduleId}`,
            headers: { token: this.state.accessToken },
            params: {
              photoUrl,
            },
          })
            .then((res) => {
              console.log(res)
              console.log(">>>>>>>>>>>>>>>>>>>>>>>")
              this.dispatch("detailTodo")
            })
            .catch((err) => console.log(err))
        })
        .catch((err) => console.log(err))
    },

    // Schedule 댓글 작성

    createTodoComment(context, payload) {
      return new Promise((resolve, reject) => {
        const content = payload.content
        const date = payload.date
        const scheduleId = payload.scheduleId
        const photoUrl = payload.photoUrl
        if (!content) {
          alert("내용을 입력해주세요")
        } else {
          axios({
            method: "get",
            url: `${API_URL}/member`,
            headers: { token: this.state.accessToken },
          })
            .then((res) => {
              const writter = res.data.nickName
              const memberId = res.data.memberId
              const calendarId = res.data.calendarId
              axios({
                method: "post",
                url: `${API_URL}/schedule/comment/${calendarId}/${scheduleId}`,
                headers: { token: this.state.accessToken },
                data: {
                  content,
                  date,
                  writter,
                  memberId,
                  photoUrl,
                },
              }).then((res) => {
                console.log(res)
                resolve(res.data)
              })
            })
            .catch((err) => {
              console.log(err)
              if (
                this.state.accessToken === undefined ||
                err.response.status === 410 ||
                err.response.status === 500
              ) {
                console.log("토큰 만료")
                console.log(localStorage.getItem("token"))
                if (localStorage.getItem("token")) {
                  this.dispatch("refresh")
                  this.dispatch("createTodoComment", payload)
                }
              } else reject(err)
            })
        }
      })
    },

    // Schedule 댓글 삭제

    deleteTodoComment(context, payload) {
      return new Promise((resolve, reject) => {
        const commentId = payload.commentId
        const scheduleId = payload.scheduleId
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        }).then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "delete",
            url: `${API_URL}/schedule/comment/${calendarId}/${scheduleId}/${commentId}`,
            headers: { token: this.state.accessToken },
          })
            .then((res) => {
              console.log(res)
              resolve(res.data)
            })
            .catch((err) => {
              console.log(err)
              if (
                this.state.accessToken === undefined ||
                err.response.status === 410 ||
                err.response.status === 500
              ) {
                if (localStorage.getItem("token")) {
                  this.dispatch("refresh")
                  this.dispatch("deleteTodoComment", payload)
                }
              } else reject(err)
            })
        })
      })
    },

    // Schedule 댓글 수정

    updateTodoComment(context, payload) {
      return new Promise((resolve, reject) => {
        const content = payload.content
        const scheduleId = payload.scheduleId
        const commentId = payload.commentId
        const date = payload.date
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        }).then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "put",
            url: `${API_URL}/schedule/comment/${calendarId}/${scheduleId}/${commentId}`,
            headers: { token: this.state.accessToken },
            data: {
              content,
              date,
            },
          })
            .then((res) => {
              console.log(res)
              resolve(res.data)
            })
            .catch((err) => {
              console.log(err)
              if (
                this.state.accessToken === undefined ||
                err.response.status === 410 ||
                err.response.status === 500
              ) {
                console.log("토큰 만료")
                console.log(localStorage.getItem("token"))
                if (localStorage.getItem("token")) {
                  this.dispatch("refresh")
                  this.dispatch("updateTodoComment", payload)
                }
              } else reject(err)
            })
        })
      })
    },

    // Pet 전체 조회

    getPet() {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "get",
            url: `${API_URL}/pet/all/${calendarId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res.data)
            this.state.pets = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getPet")
            }
          }
        })
    },

    getPet2() {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            const calendarId = res.data.calendarId
            axios({
              method: "get",
              url: `${API_URL}/pet/all/${calendarId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              console.log(res.data)
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("getPet2")
              }
            } else reject(err)
          })
      })
    },

    // 새로고침 이슈 고치기 위해 억지로 만든 갯펫,,,,

    getPetToListView() {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "get",
            url: `${API_URL}/pet/all/${calendarId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res.data)
            this.state.pets = res.data
            router.push({ name: PetListView })
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getPetToListView")
            }
          }
        })
    },

    // Pet 상세

    detailPet(context, payload) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          const petId = payload
          console.log(calendarId)
          axios({
            method: "get",
            url: `${API_URL}/pet/${petId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            this.state.pet = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("detailPet", payload)
            }
          }
        })
    },

    // Pet 수정

    updatePet(context, payload) {
      const types = payload.types
      const name = payload.name
      const birthday = payload.birthday
      const adaptday = payload.adaptday
      const color = payload.color
      const photoUrl = payload.photoUrl
      const petId = payload.petId

      axios({
        method: "put",
        url: `${API_URL}/pet/modify/${petId}`,
        headers: { token: this.state.accessToken },
        data: {
          types,
          name,
          birthday,
          adaptday,
          color,
          photoUrl,
          petId,
        },
      })
        .then((res) => {
          console.log(res)
          router.push({ name: PetProfileView, params: { id: petId } })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("updatePet", payload)
            }
          }
        })
    },

    // Pet 삭제

    deletePet(context, payload) {
      const petId = payload
      axios({
        method: "delete",
        url: `${API_URL}/pet/delete/${petId}`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          router.push({ name: PetListView })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === null ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deletePet", payload)
            }
          }
        })
    },

    // Pet 추가

    addPet(context, payload) {
      const types = payload.types
      const name = payload.name
      const birthday = payload.birthday
      const adaptday = payload.adaptday
      const color = payload.color
      const photoUrl = payload.photoUrl
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId

          axios({
            method: "post",
            url: `${API_URL}/pet/add`,
            headers: { token: this.state.accessToken },
            params: {
              calendarId,
              age: 10,
              types,
              name,
              birthday,
              adaptday,
              color,
              photoUrl,
            },
          })
        })
        .then((res) => {
          console.log(res)
          this.dispatch("getPetToListView")
        })
        .catch((err) => {
          console.log(err)
          console.log(err.response.status)
          if (
            this.state.accessToken === null ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("addPet", payload)
            }
          }
        })
    },

    //Pet 몸무게 추가

    addPetWeight(context, payload) {
      const date = payload.date
      const weight = payload.weight
      const petId = payload.petId

      axios({
        method: "put",
        url: `${API_URL}/pet/add/weight`,
        headers: { token: this.state.accessToken },
        params: {
          petId,
        },
        data: {
          date,
          weight,
        },
      })
        .then((res) => {
          console.log(res)
          this.dispatch("detailPet", petId)
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("addPetWeight", payload)
            }
          }
        })
    },

    // Album 전체 조회

    getAlbum() {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          axios({
            method: "get",
            url: `${API_URL}/photo/${calendarId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res.data)
            this.state.albums = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getAlbum")
            }
          }
        })
    },

    getAlbum2() {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            const calendarId = res.data.calendarId
            axios({
              method: "get",
              url: `${API_URL}/photo/${calendarId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              console.log(res.data)
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 500 ||
              err.response.status === 410
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("getAlbum2")
              }
            } else reject(err)
          })
      })
    },

    // Album 상세

    detailAlbum(context, payload) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const photoId = payload
          axios({
            method: "get",
            url: `${API_URL}/photo/detail/${photoId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res.data)
            this.state.album = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("detailAlbum", payload)
            }
          }
        })
    },

    detailAlbum2(context, payload) {
      return new Promise((resolve, reject) => {
        axios({
          method: "get",
          url: `${API_URL}/member`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            console.log(res)
            const photoId = payload
            axios({
              method: "get",
              url: `${API_URL}/photo/detail/${photoId}`,
              headers: { token: this.state.accessToken },
            }).then((res) => {
              console.log(res.data)
              resolve(res.data)
            })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("detailAlbum2", payload)
              }
            } else reject(err)
          })
      })
    },

    // Album 삭제

    deleteAlbum(context, payload) {
      const photoId = payload
      axios({
        method: "delete",
        url: `${API_URL}/photo/delete/${photoId}`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          router.push({ name: AlbumView })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("deleteAlbum", payload)
            }
          }
        })
    },

    // Album -> SNS 등록

    goSNS(context, payload) {
      const photoId = payload.photoId
      const snsDate = payload.snsDate
      axios({
        method: "put",
        url: `${API_URL}/photo/sns/${photoId}`,
        headers: { token: this.state.accessToken },
        params: {
          snsDate,
        },
      })
        .then((res) => {
          console.log(res)
          this.dispatch("detailAlbum", photoId)
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("goSNS", payload)
            }
          }
        })
    },

    // Album -> SNS 등록 취소

    stopSNS(context, payload) {
      const photoId = payload
      axios({
        method: "put",
        url: `${API_URL}/photo/sns/${photoId}`,
        headers: { token: this.state.accessToken },
        params: {
          snsDate: "0",
        },
      })
        .then((res) => {
          console.log(res)
          this.dispatch("detailAlbum", photoId)
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("stopSNS", payload)
            }
          }
        })
    },

    // Album 댓글 작성

    createAlbumComment(context, payload) {
      return new Promise((resolve, reject) => {
        const content = payload.content
        const date = payload.date
        const photoId = payload.photoId
        const photoUrl = payload.photoUrl

        if (!content) {
          alert("내용을 입력해주세요")
        } else {
          axios({
            method: "get",
            url: `${API_URL}/member`,
            headers: { token: this.state.accessToken },
          })
            .then((res) => {
              const writter = res.data.nickName
              const memberId = res.data.memberId
              axios({
                method: "post",
                url: `${API_URL}/photo/comment/${photoId}`,
                headers: { token: this.state.accessToken },
                data: {
                  content,
                  date,
                  writter,
                  memberId,
                  photoUrl,
                },
              }).then((res) => {
                console.log(res)
                resolve(res.data)
              })
            })
            .catch((err) => {
              console.log(err)
              if (
                this.state.accessToken === undefined ||
                err.response.status === 410 ||
                err.response.status === 500
              ) {
                console.log("토큰 만료")
                console.log(localStorage.getItem("token"))
                if (localStorage.getItem("token")) {
                  this.dispatch("refresh")
                  this.dispatch("createAlbumComment", payload)
                }
              } else reject(err)
            })
        }
      })
    },

    // Album 댓글 삭제

    deleteAlbumComment(context, payload) {
      return new Promise((resolve, reject) => {
        const commentId = payload.commentId
        const photoId = payload.photoId

        axios({
          method: "delete",
          url: `${API_URL}/photo/comment/${photoId}/${commentId}`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            console.log(res)
            resolve(res.data)
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("deleteAlbumComment", payload)
              }
            } else reject(err)
          })
      })
    },

    // Album 댓글 수정

    updateAlbumComment(context, payload) {
      return new Promise((resolve, reject) => {
        const content = payload.content
        const photoId = payload.photoId
        const commentId = payload.commentId
        const date = payload.date

        axios({
          method: "put",
          url: `${API_URL}/photo/comment/${photoId}/${commentId}`,
          headers: { token: this.state.accessToken },
          data: {
            content,
            date,
          },
        })
          .then((res) => {
            console.log(res)
            resolve(res.data)
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined ||
              err.response.status === 410 ||
              err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("updateAlbumComment", payload)
              }
            } else reject(err)
          })
      })
    },

    // SNS 전체 조회

    getSns1(context, day) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const memberId = res.data.memberId
          axios({
            method: "get",
            url: `${API_URL}/sns/`,
            headers: { token: this.state.accessToken },
            params: {
              before: day,
              memberId,
            },
          }).then((res) => {
            console.log(res)
            console.log("storage!!!!!! " + day)
            this.state.snss1 = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getSns1")
            }
          }
        })
    },
    getSns2(context, day) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const memberId = res.data.memberId
          axios({
            method: "get",
            url: `${API_URL}/sns/`,
            headers: { token: this.state.accessToken },
            params: {
              memberId,
              before: day,
            },
          }).then((res) => {
            console.log(res)
            res.data.sort(function (a, b) {
              return b.like - a.like
            })
            this.state.snss2 = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getSns2")
            }
          }
        })
    },
    getSns3(context, day) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const memberId = res.data.memberId
          console.log(res)
          axios({
            method: "get",
            url: `${API_URL}/sns/`,
            headers: { token: this.state.accessToken },
            params: {
              before: day,
              memberId,
            },
          }).then((res) => {
            console.log(res)
            res.data.sort(function (a, b) {
              return b.like - a.like
            })
            this.state.snss3 = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getSns3")
            }
          }
        })
    },
    getSns4(context, day) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const memberId = res.data.memberId
          axios({
            method: "get",
            url: `${API_URL}/sns/`,
            headers: { token: this.state.accessToken },
            params: {
              before: day,
              memberId,
            },
          }).then((res) => {
            console.log(res)
            res.data.sort(function (a, b) {
              return b.like - a.like
            })
            this.state.snss4 = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("getSns4")
            }
          }
        })
    },

    // SNS 상세

    detailSns(context, payload) {
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          console.log(res)
          const photoId = payload
          axios({
            method: "get",
            url: `${API_URL}/sns/${photoId}`,
            headers: { token: this.state.accessToken },
          }).then((res) => {
            console.log(res)
            this.state.snsss = res.data
            this.state.sns = res.data
          })
        })
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("detailSns", payload)
            }
          }
        })
    },

    // SNS 좋아요

    goLike(context, payload) {
      return new Promise((resolve, reject) => {
        const photoId = payload
        axios({
          method: "put",
          url: `${API_URL}/sns/like/${photoId}`,
          headers: { token: this.state.accessToken },
        })
          .then((res) => {
            // this.loadingStatus = false
            // console.log(res)
            // this.dispatch("detailSns", photoId)
            resolve(res.data)
            // context.commit('LOADING_STATUS', false)
            // Swal.fire({
            //   title: "완료",
            //   text: "좋아요~~~",
            //   timer: 1500,
            //   timerProgressBar: true,
            //   showConfirmButton: false,
            // })
          })
          .catch((err) => {
            console.log(err)
            if (
              this.state.accessToken === undefined
              // err.response.status === 410 ||
              // err.response.status === 500
            ) {
              console.log("토큰 만료")
              console.log(localStorage.getItem("token"))
              if (localStorage.getItem("token")) {
                this.dispatch("refresh")
                this.dispatch("goLike", payload)
              }
            } else reject(err)
          })
      })
    },

    // SNS 신고하기

    goDeclaration(context, payload) {
      const photoId = payload
      axios({
        method: "put",
        url: `${API_URL}/sns/report/${photoId}`,
        headers: { token: this.state.accessToken },
      })
        .then(() => router.go())
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("goDeclaration", payload)
            }
          } else {
            console.log("stop")
          }
        })
    },

    goDeclaration2(context, payload) {
      const photoId = payload
      axios({
        method: "put",
        url: `${API_URL}/sns/report/${photoId}`,
        headers: { token: this.state.accessToken },
      })
        .then(() => router.go(-1))
        .catch((err) => {
          console.log(err)
          if (
            this.state.accessToken === undefined ||
            err.response.status === 410 ||
            err.response.status === 500
          ) {
            console.log("토큰 만료")
            console.log(localStorage.getItem("token"))
            if (localStorage.getItem("token")) {
              this.dispatch("refresh")
              this.dispatch("goDeclaration2", payload)
            }
          } else {
            console.log("stop")
          }
        })
    },

    // 추가한 photoUrl 백에 전달

    sendAlbum(context, payload) {
      const photoUrl = payload.photoUrl
      const date = payload.date
      axios({
        method: "get",
        url: `${API_URL}/member`,
        headers: { token: this.state.accessToken },
      })
        .then((res) => {
          const calendarId = res.data.calendarId
          const memberId = res.data.memberId
          axios({
            method: "post",
            url: `${API_URL}/photo/add/photo/${calendarId}`,
            headers: { token: this.state.accessToken },
            data: {
              photoUrl,
              date,
              memberId,
            },
          })
            .then((res) => {
              console.log(res)
              this.dispatch("getAlbum")
            })
            .catch((err) => console.log(err))
        })
        .catch((err) => console.log(err))
    },
  },
  modules: {},
})

export default store
