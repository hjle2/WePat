<template>
    <div class="holder-vanilla-op">
      <router-link to="/user">
        <img src="@/assets/back.png" class="logo-back" alt="" />
      </router-link>
        <p class="font-small">터치하여 사진을 등록해주세요.</p>
      <div class="font-no-margin">
        <img class="profile-image-large" id="myimg" src="@/assets/pawprint.png" @click="popOpen()" />
      </div>
      <div class="modal-bg" @click="popClose()"></div>
      <div class="modal-wrap">
        <input type="file" @change="previewFiles" multiple>
        <button class="btn-position btn-colored btn-medium col-4" @click="addPetImage()">사진등록</button>
        <button class="btn-cancel-position btn-medium btn-colored col-4" @click="popClose()">취소</button>
      </div>
      <div class="holder-text-input">
          <p class="text-input" for="name">일정 색 선택</p>
      </div>
        <div class="div-size">
          <div @click="red" class="red"></div> 
          <div @click="orange" class="orange"></div> 
          <div @click="yellow" class="yellow"></div> 
          <div @click="green" class="green"></div> 
          <div @click="blue" class="blue"></div> 
          <div @click="darkblue" class="darkblue"></div> 
          <div @click="purple" class="purple"></div>
          <div @click="otherColor" class="otherColor"></div>
        </div>
      <br><br>
      <div class="holder-text-input">
        <p class="text-input" for="types">품종</p>
      </div>
      <input class="input" type="text" v-model="types"/><br>
  
      <div class="holder-text-input">
        <p class="text-input" for="name">이름</p>
      </div>
      <input class="input" type="text" v-model="name"/><br>
  
      <div class="holder-text-input">
        <p class="text-input" for="birthday">생일</p>
      </div>
      <input class="input" type="date" v-model="birthday" placeholder="data"/><br>
  
      <div class="holder-text-input">
        <p class="text-input" for="adaptday">입양일</p>
      </div>
      <input class="input" type="date" v-model="adaptday" placeholder="data"/>
      <br><br>
  
      
        <div>
          <button
          v-if="!types || !name || !birthday || !adaptday || !photoCheck"
          class="btn-disabled btn-medium"
        >
          생성하기
        </button>
          <button v-if="types && name && birthday && adaptday && photoCheck" class="btn-colored btn-medium" @click="addPet()">
            생성하기
          </button>
        </div>
        
    </div>
  </template>
  
  <script>
  import Swal from "sweetalert2";
  import { initializeApp } from "firebase/app";
  import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage"
  
  export default {
    components: {
    },
    data() {
      return {
        photoUrl : "",
        photoCheck : "",
        img : "",
        types: "",
        name: "",
        birthday: "",
        adaptday: "",
        color: "",
      }
    },
    methods: {

      red() {
        this.color = '#fcb9aa'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      orange() {
        this.color = '#ffdbcc'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      yellow() {
        this.color = '#ffffb5'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      green() {
        this.color = '#b6cfb6'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      blue() {
        this.color = '#a2e1db'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      darkblue() {
        this.color = '#55cbcd'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      purple() {
        this.color = '#cbaacd'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      otherColor() {
        this.color = '#eceae4'
        const profile = document.querySelector('#myimg')
        profile.style.border = `${this.color} solid 1vh`;
      },
      previewFiles(event) {
              this.img = event.target.files

      },
      addPetImage() {
          const firebaseConfig = {
              apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
              authDomain: "a607-445bc.firebaseapp.com",
              projectId: "a607-445bc",
              storageBucket: "a607-445bc.appspot.com",
              messagingSenderId: "762555697079",
              appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
              measurementId: "G-NQ1EKQ108D"
          };
          const app = initializeApp(firebaseConfig);
          const storage = getStorage(app);
          const randomnum = Math.random();
          const storageRef = ref(storage, `/${randomnum}`);
          const file = this.img[0]
          this.img = []
          this.photoUrl = `gs://a607-445bc.appspot.com/${randomnum}`
  
          
          uploadBytes(storageRef, file)
          .then((snapshot) => {
              this.downloadPetImage()
              console.log('Uploaded a blob or file!');
              console.log(snapshot);
          });
      },
      downloadPetImage() {
        this.popClose()
        const firebaseConfig = {
                apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
                authDomain: "a607-445bc.firebaseapp.com",
                projectId: "a607-445bc",
                storageBucket: "a607-445bc.appspot.com",
                messagingSenderId: "762555697079",
                appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
                measurementId: "G-NQ1EKQ108D"
            };
            const app = initializeApp(firebaseConfig);
            const storage = getStorage(app);
            // Your web app's Firebase configuration
            // For Firebase JS SDK v7.20.0 and later, measurementId is optional
            // Initialize Firebase
            const storageRef = ref(storage, `${this.photoUrl}`);
  
            getDownloadURL(storageRef)
            .then((url) => {
                document.querySelector('#myimg').src = url;
                this.photoUrl = url
                this.photoCheck = '1'
            });
      },
      addPet() {
        Swal.fire({
              title: "새 가족 등록",
              text: "이대로 등록하시겠습니까?",
              icon: "warning",
  
              showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "예", // confirm 버튼 텍스트 지정
              cancelButtonText: "아니오", // cancel 버튼 텍스트 지정
  
              reverseButtons: true, // 버튼 순서 거꾸로
            }).then((result) => {
              if (result.isConfirmed) {
                const types = this.types
                const name = this.name
                const birthday = this.birthday
                const adaptday = this.adaptday
                const color = this.color
                const photoUrl = this.photoUrl
          
                const payload = {
                  types,
                  name,
                  birthday,
                  adaptday,
                  color,
                  photoUrl
                }
                this.$store.dispatch("addPet", payload)
              }
            })
      },
      popOpen() {
          document.getElementsByClassName("modal-wrap")[0].style.display ='block';
          document.getElementsByClassName("modal-bg")[0].style.display ='block';
      },
      popClose() {
          document.getElementsByClassName("modal-wrap")[0].style.display ='none';
          document.getElementsByClassName("modal-bg")[0].style.display ='none';
      },
    },
  }
  </script>
  
  <style lang="scss">
  
  </style>