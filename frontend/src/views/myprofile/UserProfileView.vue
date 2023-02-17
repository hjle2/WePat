<template>
<div id="UserProfileView">
  <div class="holder-vanilla-op">
    <div class="">
      <p>나의 프로필</p>
      <div class="holder-profile">
        <img :v-if="this.photoUrl" class="profile-image-large" :src="this.photoUrl" @click="previewFiles()" />
      </div>
      <!-- <div class="modal-bg" @click="popClose()"></div>
      <div class="modal-wrap">
        <input type="file" @change="previewFiles" multiple>
        <button class="btn-position btn-colored btn-medium" @click="changeImage()">등록</button>
        <button class="btn-cancel-position btn-medium btn-colored" @click="popClose()">취소</button>
      </div> -->
      <div>
        <p v-if="!photoUrl" class="font-grey font-small" @click="previewFiles()"> 터치하여 프로필 사진을 등록하세요! </p>
      </div>
      <div class="holder-category">
        
      </div>
      <div class="font-vanila font-small at-bottom">
      <router-link
        class="font-vanila holder-asset"
        to="/user/detail"
      >
        <div class="">
          <img class="logo-profile" src="@/assets/user.png" alt="" />
          <span class="">회원 정보</span>
        </div>
        <p class="">
          >
        </p>
      </router-link>
        <router-link
          class="font-vanila holder-asset"
          to="/pet"
        >
          <div class="">
            <img class="logo-profile" src="@/assets/i_pet_.png" alt="" />
            <span class=" ">반려동물 목록</span>
          </div>
          <p class="">
          >
        </p>
        </router-link>
        <router-link
          class="font-vanila font-vanila holder-asset"
          to="/pet/add"
        >
          <div class="">
            <img
              class="logo-profile-pet"
              src="@/assets/i_pet_plus.png"
              alt=""
            />
            <span class=" ">반려동물 추가</span>
          </div>
          <p class="">
          >
        </p>
        </router-link>
        <router-link
          class="font-vanila font-vanila holder-asset"
          to="/user/link"
        >
          <div class="">
            <img class="logo-profile" src="@/assets/share.png" alt="" />
            <span class=" ">캘린더 코드 공유</span>
          </div>
          <p class="">
          >
        </p>
        </router-link>
        <!-- <br /><br />

        <div class="holder-category">
          
        </div> -->

        <!-- <router-link
          class="font-vanila font-vanila holder-asset"
          to="/help"
        >
          <div class="">
            <img class="logo-profile" src="@/assets/edit.png" alt="" />
            <span class=" ">문의하기</span>
          </div>
          <img
            class=" logo-profile-to"
            src="@/assets/go.png"
            alt=""
          />
        </router-link> -->

        <router-link
          class="font-vanila font-vanila holder-asset"
          to="/version"
        >
          <div class="">
            <img class="logo-profile" src="@/assets/info.png" alt="" />
            <span class=" ">버전 정보</span>
          </div>
          <p class="">
          >
        </p>
        </router-link>
      </div>
    </div>
    <br />
  </div>
  <BottomNavbar5 />
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { initializeApp } from "firebase/app"
import { getStorage, ref, getDownloadURL, uploadBytes } from "firebase/storage"
import BottomNavbar5 from "@/components/common/BottomNavbar5.vue"

export default {
  name: "UserProfileView",
  data() {
    return {
      nickName: null,
      email: null,
      calendarId: null,
      warnMemberList: null,
      blockMemberList: null,
      photoUrl: null,
      img: "",
    }
  },
  components: {
    BottomNavbar5,
  },
  computed: {},
  methods: {
    download() {
      const firebaseConfig = {
        apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
        authDomain: "a607-445bc.firebaseapp.com",
        projectId: "a607-445bc",
        storageBucket: "a607-445bc.appspot.com",
        messagingSenderId: "762555697079",
        appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
        measurementId: "G-NQ1EKQ108D",
      }
      const app = initializeApp(firebaseConfig)
      const storage = getStorage(app)
      // Your web app's Firebase configuration
      // For Firebase JS SDK v7.20.0 and later, measurementId is optional
      // Initialize Firebase
      const storageRef = ref(storage, `${this.photoUrl}`)

      getDownloadURL(storageRef).then((url) => {
        console.log('>>>>>>>>')
        this.photoUrl = url
        this.sendImage()
      })
    },
    async previewFiles() {
      // const { value: file } = 
      await Swal.fire({
        title: '이미지를 선택하세요!',
        input: 'file',
        inputAttributes: {
          'accept': 'image/*',
        },
        confirmButtonText: "업로드",
      }).then((res) => {
        if (res.value) {
          this.img = res.value
        console.log(this.img)
        this.changeImage()
        }
      })
    },
    changeImage() {
      const firebaseConfig = {
        apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
        authDomain: "a607-445bc.firebaseapp.com",
        projectId: "a607-445bc",
        storageBucket: "a607-445bc.appspot.com",
        messagingSenderId: "762555697079",
        appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
        measurementId: "G-NQ1EKQ108D",
      }
      const app = initializeApp(firebaseConfig)
      const storage = getStorage(app)
      const randomnum = Math.random()
      const storageRef = ref(storage, `/${randomnum}`)
      const file = this.img
      this.img = ""
      this.photoUrl = `gs://a607-445bc.appspot.com/${randomnum}`

      uploadBytes(storageRef, file).then((snapshot) => {
        this.download()
        // this.popClose()
        // console.log("Uploaded a blob or file!")
        console.log(snapshot)
      })
    },
    sendImage() {
      this.$store.dispatch("changeImage", this.photoUrl)
    },
    logOut() {
      this.$store.dispatch("logOut")
    },
    deleteUser() {
      this.$store.dispatch("deleteUser")
    },

    getProfile() {
        const dataSet = this.$store.dispatch("getUserData")
          dataSet.then((getData) => {
          // console.log(getData)
          this.nickName = getData.nickName
          this.calendarId = getData.calendarId
          this.warnMemberList = getData.warnMemberList
          this.blockMemberList = getData.blockMemberList
          this.email = getData.email
          this.photoUrl = getData.photoUrl
        })
    },
    // popOpen() {
    //     document.getElementsByClassName("modal-wrap")[0].style.display ='block';
    //     document.getElementsByClassName("modal-bg")[0].style.display ='block';
    // },
    // popClose() {
    //     document.getElementsByClassName("modal-wrap")[0].style.display ='none';
    //     document.getElementsByClassName("modal-bg")[0].style.display ='none';
    // },
  },
  created() {
    this.getProfile()
  },
}
</script>

<style lang="scss">

</style>