import { createStore } from "vuex"
import axios from "axios"
import router from "@/router"

const API_URL = "http://70.12.247.124:8080"

const store = createStore({
    state: {
        accessToken: null,
        calendarId: null,
        // pets: [],     <-- 여기다가 펫 생성 시 담아야함 ㅠ
        pets: [{id:'2', name:'jun', adaptday:'woo', calendarid:'12'}],
        albums : [],
        calendarIdtest: null,
      },
    
    getters: {
        isLogin() {
            return localStorage.getItem("token") ? true : false
        }
      },    
    mutations: {
        // 회원가입 && 로그인
        SAVE_TOKEN(state, data) {
          console.log("받은거" + data)
          state.accessToken = data[0]
          localStorage.setItem("token", data[1])
          console.log("ID " + localStorage.getItem("memberId"))
          console.log("액세스 " + state.accessToken)
          console.log("리프레쉬 " + localStorage.getItem("token"))
          router.push({ path: '/mainpage' })
        },
        DELETE_TOKEN(state) {
          state.accessToken = null
          localStorage.removeItem("token")
          localStorage.removeItem("memberId")
          localStorage.removeItem("calenderId")
          alert("로그아웃 되었습니다. 로그인 페이지로 이동합니다.")
          router.push({ path: '/' })
        },
      },
      actions: {
        //토큰 갱신
        refresh() {
          const refreshToken = localStorage.getItem("token")
          axios({
            method: 'get',
            url: `${API_URL}/refresh/${localStorage.getItem("memberId")}`,
            headers: {Authorization: refreshToken},
          })
            .then((res) => {
              console.log("갱신완료"  + res)
              this.state.accessToken = res
              console.log(this.state)
            })
          },
        //회원정보 요청
        getUserData(context) {
          axios({
            method: 'get',
            url: `${API_URL}/member/${localStorage.getItem("memberId")}`,
            headers: {Authorization: this.state.accessToken},
          })
            .then((res) => {
              console.log(res)
              this.dispatch(`${context}`, res)
            })
          },
        //로그인
        login(context, payload) {
            const memberId = payload.memberId
            const pwd = payload.pwd
          axios({
            method: 'post',
            url: `${API_URL}/member/signin`,
            params: {
                memberId,
                pwd,
                }
          })
            .then((res) => {
              console.log(res.data)
              localStorage.setItem("memberId", payload.memberId)
              context.commit('SAVE_TOKEN', res.data)

              // 임시적으로 로컬스토리지에 저장
              axios({
                method: 'get',
                url: `${API_URL}/member/${localStorage.getItem("memberId")}`,
                headers: {Authorization: this.state.accessToken},
              })
              .then((res) => {
                console.log(res.data)
                console.log(res.data.calendarId)
                localStorage.setItem("calendarId", res.data.calendarId)
              })
            })
            .catch((err) => {
              console.log('로그인 에러')
              console.log(payload)
              console.log(err)
              console.log(err.response.data.message)
              alert('존재하지 않는 계정이거나 잘못된 비밀번호 입니다!')
            })
        },
        //로그아웃
        logOut(context) {
          axios({
            method: 'get',
            url: `${API_URL}/member/logout/${localStorage.getItem("memberId")}`,
          })
            .then((res) => {
              console.log('로그아웃', res)
              context.commit('DELETE_TOKEN')
            })
            .catch((err) => {
              console.log(err)
            }) 
        },
        //회원가입
        signUp(context, payload) {
            const email = payload.email
            const memberId = payload.memberId
            const nickName = payload.nickName
            const calendarId = payload.calendarId
            const pwd = payload.pwd
      
            axios({
              method:'post',
              url: `${API_URL}/member/signup`,
              params : {
                calendarId,
                email, 
                memberId, 
                nickName, 
                pwd
              }
            })
              .then(res => {
                console.log(res)
                alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.')
                router.push({ path: '/' })
              })
              .catch( err => console.log(err) )
          },
        petsignUp(context, payload) {
            const colors = payload.colors
            const adaptday = payload.adaptday
            const age = payload.age
            const birthday = payload.birthday
            const calendarId = 'DGRmgb4qxh4jLQgLRaq4'
            const name = payload.name
        
            axios({
                method:'post',
                url: `${API_URL}/pet/add`,
                params : {
                    colors,
                    adaptday, 
                    age, 
                    birthday, 
                    calendarId,
                    name,
                }
            })
                .then(res => console.log(res))
                .catch( err => console.log(err) )
        },
        //회원탈퇴
        DeleteUser() {
          axios({
            method: 'delete',
            url: `${API_URL}/member/${localStorage.getItem("memberId")}`,
          })
            .then((res) => {
              console.log(res)
              alert('회원탈퇴 되었습니다. 이용해 주셔서 감사합니다.')
              this.store.dispatch('logOut')
            })
            .catch((err) => {
              console.log(err)
            })
        },
        // 비밀번호 변경
        changePwd(context, payload) {
              const newPwd = payload.newPwd
              const memberId = localStorage.getItem("memberId")
              axios({ 
                method: 'put',
                url: `${API_URL}/member/modifypwd/`,
                headers: {Authorization: this.state.accessToken},
                params : {
                  memberId,
                  newPwd,
                },
              })
                .then(() => {
                  context.commit('DELETE_TOKEN')
                  alert('비밀번호가 변경되었습니다. 다시 로그인하세요.')
                })
                .catch((err) => {
                  console.log(err)
                  console.log(err.response.status)
                  if (err.response.status === 401) {
                    console.log("토큰 만료")
                    if (localStorage.getItem("token")) {
                      this.dispatch('refresh', localStorage.getItem("token"))
                      this.dispatch('changePwd', payload)
                    }
                  }
                })
            },

            //닉네임 수정
            ChangeNickname(context, payload) {
              const memberId = localStorage.getItem("memberId")
              const nickName = payload.nickName
              axios({
                method: 'put',
                url: `${API_URL}/member/modify/`,
                headers: {Authorization: this.state.accessToken},
                params : {
                  memberId,
                  nickName,
                },
              })
                .then(() => {
                  context.commit('DELETE_TOKEN')
                  alert('회원정보가 변경되었습니다. 다시 로그인하세요.')
                })
                .catch((err) => {
                  console.log(err)
                  if (err.response.status === 401) {
                    console.log("토큰 만료")
                    if (localStorage.getItem("token")) {
                      this.dispatch('refresh', localStorage.getItem("token"))
                      this.dispatch('ChangeNickname', payload)
                    }
                  }
                })
            },

            //캘린더 불러오기
            ChangeCalendar(context, payload) {
              const calendarId = payload.calendarId
              axios({
                method: 'put',
                url: `${API_URL}/member/modify/${localStorage.getItem("memberId")}`,
                headers: {Authorization: this.state.accessToken},
                params : {
                  calendarId,
                },
              })
                .then(() => {
                  context.commit('DELETE_TOKEN')
                  alert('회원정보가 변경되었습니다. 다시 로그인하세요.')
                })
                .catch((err) => {
                  console.log(err)
                  if (err.response.status === 401) {
                    if (localStorage.getItem("token")) {
                      this.dispatch('refresh', localStorage.getItem("token"))
                      this.dispatch('ChangeCalenar', payload)
                    }
                  }
                })
            },
      },
      modules: {
      },
    })


export default store
