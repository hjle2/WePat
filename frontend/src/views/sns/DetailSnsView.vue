<template>
  <router-link to="/sns">
    <img src="@/assets/back.png" class="logo-back" alt="" />
  </router-link>
  <div class="sns-margin">
    <img :id="'a' + sns.photoId" class="detail-img" />
    <div class="holder-sns">
      <img src="@/assets/declaration.png" class="declaration" @click="goDeclaration(sns.photoId)" />
      <div>
        <img src="@/assets/heart.png" class="heart" @click="goLike(sns.photoId)" />
        <!-- <span class="p-right">{{ this.sns1.like }}</span> -->
        <span class="p-right">{{ sns.like }}</span>
      </div>
    </div>
  </div>

  <!-- <img src="@/assets/declaration.png" alt="" class="declaration-img" @click="popOpen()" />
  <img src="@/assets/heart.png" alt="" class="heart-img" @click="goLike()" />
  <p class="psize4" @click="goLike()">{{ sns.like }}</p>
  <div class="s" @click="popClose()"></div>
  <div class="modal-wrap">
    <p class="psize">진짜로 신고하실렵니꺼?</p>
    <button class="btn-position btn-colored btn-medium" @click="goDeclaration()">신고하기</button>
    <button class="btn-cancel-position btn-medium btn-colored" @click="popClose()">취소</button>
  </div> -->
  <BottomNavbar3 />
</template>

<script>
import { initializeApp } from "firebase/app";
import { getStorage, ref, getDownloadURL } from "firebase/storage";
import BottomNavbar3 from "@/components/common/BottomNavbar3.vue";
import Swal from "sweetalert2";

export default {
  name: "DetailSnsView",
  data() {
    return {
    }
  },
  computed: {
    sns() {
      return this.$store.state.sns;
    },
    snsss() {
      return this.$store.state.snsss;
    },
  },
  components: {
    BottomNavbar3,
  },
  created() {
    this.detailSns();
  },
  beforeUpdate() {
    this.download();
  },

  methods: {
    detailSns() {
      this.$store.dispatch("detailSns", this.$route.params.id);
    },
    goLike() {
      const photoId = this.$route.params.id;
      this.$store.dispatch("goLike", photoId);
      this.sns.like += 1
    },
    // goDeclaration() {
    //   this.popClose();
    //   const photoId = this.$route.params.id;
    //   this.$store.dispatch("goDeclaration", photoId);
    // },
    goDeclaration(payload) {
      console.log(payload);
      Swal.fire({
        title: "게시물 신고",
        text: "이 게시물을 신고하시겠습니까?",
        icon: "warning",

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
        cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
        confirmButtonText: "예", // confirm 버튼 텍스트 지정
        cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
      }).then((result) => {
        if (result.isConfirmed) {
          console.log(result);
          console.log("goDeclaration>>>>>>>>>>>>>>>");
          console.log(payload);
          // const photoId = this.reportPhotoId;
          this.$store.dispatch("goDeclaration2", payload);
        }
      });
    },
    download() {
      const firebaseConfig = {
        apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
        authDomain: "a607-445bc.firebaseapp.com",
        projectId: "a607-445bc",
        storageBucket: "a607-445bc.appspot.com",
        messagingSenderId: "762555697079",
        appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
        measurementId: "G-NQ1EKQ108D",
      };
      const app = initializeApp(firebaseConfig);
      const storage = getStorage(app);
      // Your web app's Firebase configuration
      // For Firebase JS SDK v7.20.0 and later, measurementId is optional
      // Initialize Firebase
      const storageRef = ref(storage, `${this.sns.photoUrl}`);

      getDownloadURL(storageRef).then((url) => {
        document.querySelector(`#a${this.sns.photoId}`).src = url;
      });
    },
    popOpen() {
      document.getElementsByClassName("modal-wrap")[0].style.display = "block";
      document.getElementsByClassName("modal-bg")[0].style.display = "block";
    },
    popClose() {
      document.getElementsByClassName("modal-wrap")[0].style.display = "none";
      document.getElementsByClassName("modal-bg")[0].style.display = "none";
    },
  },
};
</script>

<style lang="scss">
// .sns-margin {
//   margin-top: 80px;
// }
.sns-margin {
  margin-top: 5vh;
  width: 100vw;
  position: relative;
}
.heart-img {
  width: 100px;
  position: fixed;
  bottom: 405px;
  right: 60px;
  z-index: 1;
}
.declaration-img {
  width: 70px;
  position: fixed;
  bottom: 415px;
  right: 165px;
  z-index: 1;
}
.psize4 {
  width: 100px;
  position: fixed;
  bottom: 400px;
  right: 60px;
  font-size: $font-size-3;
  z-index: 1;
}
.detail-img {
  width: 100vw;
  height: auto;
  margin-top: 3vh;
}
</style>
