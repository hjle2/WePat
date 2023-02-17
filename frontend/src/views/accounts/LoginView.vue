<template>
  <div>
    <form @submit.prevent="logIn" class="holder-vanilla-login">
      <br />
      <div class="logo-wepat">
        <img
          src="@/assets/logo/dog_logo_with_wepat_orange.png"
          alt=""
          style="width: 27vw"
        />
      </div>
      <br />
      <transition name="slide-fade" mode="out-in">
        <div class="">
          <label
            class="text-login font-small"
            @keyup.enter="logIn"
            for="memberId"
          >
            아이디 </label
          ><br />
          <input
            class="font-medium input input-login"
            type="text"
            id="memberId"
            v-model="memberId"
            placeholder="아이디 입력"
          /><br />
          <label class="text-login font-small" @keyup.enter="logIn" for="pwd"
            ><br />
            비밀번호 </label
          ><br />
          <input
            class="font-medium input input-login"
            type="password"
            id="pwd"
            v-model="pwd"
            placeholder="비밀번호 입력"
          /><br /><br />
          <div class="">
            <button class="btn-colored btn-medium input-btn" type="submit">
              로그인
            </button>
          </div>
        </div>
      </transition>
      <br />
      <div class="logo-login-div">
        <img
          class="logo-login"
          src="@/assets/social/google.png"
          alt="goole"
          @click="googleLogin()"
        />
        <img
          class="logo-login"
          src="@/assets/social/facebook.png"
          alt="goole"
          @click="facebookLogin()"
        />
        <img
          class="logo-login"
          src="@/assets/social/twitter.png"
          alt="goole"
          @click="twitterLogin()"
        />
        <!-- <img class="logo-login" src="@/assets/social/google.png" alt="goole" @click="LoginSoon()">
        <img class="logo-login" src="@/assets/social/facebook.png" alt="goole" @click="LoginSoon()">
        <img class="logo-login" src="@/assets/social/twitter.png" alt="goole" @click="LoginSoon()"> -->
      </div>
      <div class="logo-login-div">
        <router-link to="/findid" class="font-vanila">
          <a class="btn-invisible font-small font-vanila">아이디찾기</a>
        </router-link>
        <span>&nbsp;&nbsp;&nbsp;</span>
        <router-link to="/findpw" class="font-vanila">
          <a class="btn-invisible font-small font-vanila">비밀번호찾기</a>
        </router-link>
        <span>&nbsp;&nbsp;&nbsp;</span>
        <router-link to="/signup" class="font-vanila">
          <a class="btn-invisible font-small font-vanila">회원가입</a>
        </router-link>
      </div>
    </form>
    <br />
  </div>
</template>

<script>
import { initializeApp } from "firebase/app"
import {
  getAuth,
  signInWithPopup,
  GoogleAuthProvider,
  FacebookAuthProvider,
  TwitterAuthProvider,
} from "firebase/auth"
// import anime from 'animejs/lib/anime.es.js'
// anime({
//   targets: 'div',
//   translateX: 250,
//   rotate: '1turn',
//   backgroundColor: '#FFF',
//   duration: 800
// })
export default {
  name: "LoginView",
  data() {
    return {
      memberId: null,
      pwd: null,
    }
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin
    },
  },
  methods: {
    logIn() {
      const memberId = this.memberId
      const pwd = this.pwd
      if (memberId.length === 0) {
        alert("아이디를 입력해주세요")
        return
      } else if (pwd.length === 0) {
        alert("비밀번호를 입력해주세요")
        return
      } else {
        const payload = {
          memberId: memberId,
          pwd: pwd,
        }
        // console.log(payload)
        this.$store.dispatch("login", payload)
      }
    },

    LoginSoon() {
      this.$store.dispatch("loginSoon")
    },
    //소셜로그인
    googleLogin() {
      const googleProvider = new GoogleAuthProvider()
      this.callLoginAPI(googleProvider)
    },
    facebookLogin() {
      console.log("faceaaaa")
      const facebookProvider = new FacebookAuthProvider()
      this.callLoginAPI(facebookProvider)
    },
    twitterLogin() {
      const twitterLogin = new TwitterAuthProvider()
      this.callLoginAPI(twitterLogin)
    },
    callLoginAPI(provider) {
      const auth = getAuth()
      console.log(auth)
      signInWithPopup(auth, provider).then((result) => {
        // This gives you a Google Access Token. You can use it to access the Google API.
        // const credential = GoogleAuthProvider.credentialFromResult(result)
        // const token = credential.accessToken

        //const rtoken = credential.refresh.token;
        // The signed-in user info.const user = result.user
        const user = result.user
        const snstype = user.providerData[0].providerId
        let sns = 0
        if (snstype.startsWith("google")) {
          sns = 1
        } else if (snstype.startsWith("facebook")) {
          sns = 2
        } else if (snstype.startsWith("twitter")) {
          sns = 3
        } else if (snstype.startsWith("github")) {
          sns = 4
        }
        const payload = {
          email: user.email,
          id: user.uid,
          SNS: sns,
          nickName: user.displayName,
        }
        this.$store.dispatch("SNSlogin", payload)
      })
    },
    Check() {
      if (this.isLogin) {
        console.log("로그인 되어 있음. 메인페이지로 보냄")
        this.$router.replace({ path: "/mainpage" })
      }
    },
    SignUp() {
      this.$router.push({ name: "SignUpView" })
    },
  },
  created() {
    const firebaseConfig = {
      apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
      authDomain: "a607-445bc.firebaseapp.com",
      projectId: "a607-445bc",
      storageBucket: "a607-445bc.appspot.com",
      messagingSenderId: "762555697079",
      appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
      measurementId: "G-NQ1EKQ108D",
    }

    initializeApp(firebaseConfig)
    this.Check()
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

.text-login {
  // text-align: left !important;
  margin-left: -40vw !important;
  // left: 0vw !important;
}
</style>
