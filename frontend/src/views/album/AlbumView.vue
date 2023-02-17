<template>
<div id="AlbumView">
  <div v-if="albums.length === 0" class="no-image">등록된 사진이 없습니다. <br />사진을 등록해주세요.</div>
  <AlbumList :albums="albums" />
  <BottomNavbar4 />
  <img src="@/assets/add.png" alt="" class="add-img" @click="previewFiles()" />
  <!-- <div class="modal-bg" @click="popClose()"></div>
  <div class="modal-wrap">
    <p class="psize">사진을 등록해주세요.</p>
    <input type="file" @change="previewFiles" multiple> <br><br><br>
    <div class="row">
      <button class="btn-position btn-colored btn-small col-4" @click="addAlbum()">사진등록</button>
      <button class="btn-cancel-position btn-small btn-colored col-4" @click="popClose()">취소</button>
    </div> -->
</div>
</template>

<script>
import { initializeApp } from "firebase/app";
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage";

import AlbumList from "@/components/album/AlbumList.vue";
import BottomNavbar4 from "@/components/common/BottomNavbar4.vue";
import Swal from "sweetalert2";

export default {
  components: {
    AlbumList,
    BottomNavbar4,
  },
  name: "AlbumView",
  data() {
    return {
      // albums : [],
      photoUrl: "",
      img: [],
      date: "",
    };
  },
  created() {
    let today = new Date();   
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let hours = today.getHours();
    let minutes = today.getMinutes();
    let seconds = today.getSeconds();    
    this.date = (year + (month < 9 ? "0" + month : month) + (date < 9 ? "0" + date : date) + (hours < 9 ? "0" + hours : hours) + (minutes < 9 ? "0" + minutes : minutes) +(seconds < 9 ? "0" + seconds : seconds) )
    this.getAlbum();
  },
  computed: {
    albums() {
      return this.$store.state.albums;
    },
  },
  methods: {
    getAlbum() {
      // const dataSet =this.$store.dispatch("getAlbum2")
      // dataSet.then((getData)=>{
      //   this.albums=getData

      //   return getData
      // })
      this.$store.dispatch("getAlbum");
    },
    async previewFiles() {
      // const { value: file } =
      await Swal.fire({
        title: "이미지를 선택하세요!",
        input: "file",
        inputAttributes: {
          accept: "image/*",
        },
        confirmButtonText: "업로드",
      }).then((res) => {
        if (res.value) {
          this.img = res.value;
          console.log(this.img);
          this.addAlbum();
        }
      });
    },
    addAlbum() {
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
      const randomnum = Math.random();
      const storageRef = ref(storage, `/${randomnum}`);
      const file = this.img;
      this.img = [];
      this.photoUrl = `gs://a607-445bc.appspot.com/${randomnum}`;

      uploadBytes(storageRef, file).then((snapshot) => {
        this.downloadAlbumImage();
        console.log("Uploaded a blob or file!");
        console.log(snapshot);
      });
    },
    downloadAlbumImage() {
      // this.popClose()
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
      const storageRef = ref(storage, `${this.photoUrl}`);

      getDownloadURL(storageRef).then((url) => {
        console.log(url);
        const photoUrl = url;
        const date = this.date;
        Swal.fire({
          // icon: "success",
          title: "업로드 완료!",
          imageUrl: photoUrl,
          // imageHeight: "30%",
        });
        const payload = {
          photoUrl,
          date,
        };
        this.$store.dispatch("sendAlbum", payload);
        this.photoUrl = "";
      });
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
};
</script>

<style lang="scss">
.add-img {
  width: 50px;
  position: fixed;
  bottom: 120px;
  right: 30px;
  z-index: 1;
}
.no-image {
  background: #ffffff;
  padding: 5%;
  width: 100vw;
  height: 100vh;
  margin: 35vh auto;
  position: absolute;
  left: 0%;
  overflow: auto;
  font-size: $font-size-3;
}
</style>
