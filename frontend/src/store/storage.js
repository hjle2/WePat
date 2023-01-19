import { createStore } from 'vuex';
import axios from 'axios'
import router from '@/router/index'

const API_URL = 'http://70.12.247.105:8080'

const store = createStore({
    state: {
        // token: null,
        calendarIdtest: null,
      },
    
    getters: {
        isLogin() {
        // isLogin(state) {
            // 일단 토큰 생성이 보류이므로, 
            // 로그인 성공할 시 로컬스토리지에 로그인한 사용자의 고윳값 중 이메일을 저장해놓음
            return localStorage.getItem("EMAIL") ? true : false
            // return state.token ? true : false
        }
      },    
    mutations: {
        // 회원가입 && 로그인
        SAVE_TOKEN(state, token) {
        //   state.token = token
          localStorage.setItem("NICKNAME", token.nickName)
          localStorage.setItem("EMAIL", token.email)
          router.push({ path: '/mainpage' })
        },
        SAVE_TOKEN_SIGNUP(state, token){
            state.token = token
            state.calendarId = token
            router.push({path:"/mainpage"})
        },
        // 토큰 구현되면 다시 복원할 것
        // DELETE_TOKEN(state) {
        //   state.token = null
        //   state.selected = true
        //   state.recommend = []
        //   router.push({ name: 'LoginView'})
        // },
        CHANGE_CHECKED(state) {
          state.selected = false
        }
      },
      actions: {
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

              // 일단 토큰 생성이 보류이므로, 
              // 로그인 성공할 시 로컬스토리지에 로그인한 사용자의 고윳값 중 이메일을 저장해놓음
              
              context.commit('SAVE_TOKEN', res.data)
              //context.commit('SAVE_TOKEN', res.data.key)
            })
            .catch((err) => {
              console.log(payload)
              console.log(err)
              alert('존재하지 않는 계정이거나 잘못된 비밀번호 입니다!')
            })
        },
        logOut() {
            localStorage.removeItem("NICKNAME")
            localStorage.removeItem("EMAIL")
            alert('로그아웃이 완료되었습니다. 로그인 페이지로 이동합니다.')
            router.push({ path: '/' })
          },
        // 토큰 구현하면 다시 복원할 것 
        // logOut(context) {
        //   axios({
        //     method: 'post',
        //     url: `${API_URL}/member/logout/${memberId}`,
        //     headers: {
        //       Authorization: `Token ${context.state.token}`
        //     }
        //   })
        //     .then((res) => {
        //       console.log('로그아웃', res)
        //       context.commit('DELETE_TOKEN')
        //     })
        //     .catch((err) => {
        //       console.log(err)
        //     }) 
        // },
        signUp(context, payload) {
            const email = payload.email
            const memberId = payload.memberId
            const nickName = payload.nickName
            const pwd = payload.pwd
      
            axios({
              method:'post',
              url: `${API_URL}/member/signup`,
              params : {
                memberId, 
                email, 
                nickName, 
                pwd
              }
            })
            //   .then(res => context.commit('SAVE_TOKEN_SIGNUP', res.data.key))
              .then(res => console.log(res))
              .catch( err => console.log(err) )
          },
        petsignUp(context, payload) {
            const adaptday = payload.adaptday
            const age = payload.age
            const birthday = payload.birthday
            const calendarId = 'DGRmgb4qxh4jLQgLRaq4'
            const name = payload.name
        
            axios({
                method:'post',
                url: `${API_URL}/pet/add`,
                params : {
                    adaptday, 
                    age, 
                    birthday, 
                    calendarId,
                    name,
                }
            })
            //   .then(res => context.commit('SAVE_TOKEN_SIGNUP', res.data.key))
                .then(res => console.log(res))
                .catch( err => console.log(err) )
        },
        DeleteUser(context, user_id) {
          console.log('전달받음')
          console.log(user_id)
          axios({
            method: 'delete',
            url: `${API_URL}/member/mypage/delete`,
            headers: {
              Authorization: `Token ${context.state.token}`
            }
          })
            .then(() => {
              alert('회원탈퇴 되었습니다!')
              context.commit('DELETE_TOKEN')
            })
            .catch((err) => {
              console.log(err)
              
            })
        },

        // 비밀번호 변경 미구현
        // change(context, payload) {
        //       axios({
        //         method: 'post',
        //         url: `${API_URL}/accounts/pwd/change/`,
        //         data: {
        //           new_password1: payload.newpassword,
        //           new_password2: payload.newpassword2,
        //           // old_password: payload.original,
        //         },
        //         headers: {
        //           Authorization: `Token ${context.state.token}`
        //         }
        //       })
        //         .then(() => {
        //           context.commit('DELETE_TOKEN')
        //           alert('비밀번호가 변경되었습니다. 다시 로그인하세요.')
        //         })
        //         .catch((err) => {
        //           console.log(err)
        //         })
        //     },
      },
      modules: {
      }
    })

    export default store;
