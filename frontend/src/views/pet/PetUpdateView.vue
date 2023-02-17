<template>
<div class="holder-vanilla-op">
    <router-link to="/pet">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link>
    <br>
      <p class="font-mini">새 사진으로 수정하려면 사진을 터치해주세요.</p>
    <div class="font-no-margin holder-profile">
      <img class="profile-image-large" id="myimg" src="@/assets/pawprint.png" @click="previewFiles()" />
    </div>
    <!-- <div class="modal-bg" @click="popClose()"></div>
    <div class="modal-wrap">
      <input type="file" @change="previewFiles" multiple>
      <button class="btn-position btn-colored btn-medium col-4" @click="addPetImage()">사진등록</button>
      <button class="btn-cancel-position btn-medium btn-colored col-4" @click="popClose()">취소</button>
    </div> -->
    <div class="holder-text-input">
        <p class="text-input font-no-margin" for="name">일정 색 선택</p>
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
    <!-- <br> -->
    <br>
    <div class="holder-text-input">
      <p class="text-input" for="types">품종</p>
    </div>
    <input class="input input-edit" type="text" v-model="types"/><br />

    <div class="holder-text-input">
      <p class="text-input" for="name">이름</p>
    </div>
    <input class="input input-edit" type="text" v-model="name"/><br />

    <div class="holder-text-input">
      <p class="text-input" for="birthday">생일</p>
    </div>
    <input class="input input-edit" type="text" v-model="birthday"/><br />

    <div class="holder-text-input">
      <p class="text-input" for="adaptday">입양일</p>
    </div>
    <input class="input input-edit" type="text" v-model="adaptday"/>
    <br><br>

    
      <div>
        <button
        v-if="!types || !name || !birthday || !adaptday "
        class="btn-disabled btn-medium btn-Todo"
      >
        수정하기
      </button>
        <button v-if="types && name && birthday && adaptday " class="btn-colored btn-medium btn-Todo" @click="updatePet()">
          수정하기
        </button>
      </div>
      
  </div>
    <!-- <BottomNavbar5 /> -->
</template>

<script>
import Swal from "sweetalert2";
import { initializeApp } from "firebase/app";
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage"
// import BottomNavbar5 from "@/components/common/BottomNavbar5.vue"

export default {
    components: {
    //   BottomNavbar5
    },
    data() {
        return {
            img : "",
            types: "",
            name: "",
            birthday: "",
            adaptday: "",
            color: "",
            photoUrl : "",
        }
    },
    computed: {
        pet() {
            return this.$store.state.pet
        },
    },
    created() {
        this.detailPet()

        console.log(this.photoUrl)
        console.log(this.color)
    },
    methods: {
        detailPet() {
            this.$store.dispatch('detailPet', this.$route.params.id)
            this.types = this.pet.types
            this.name = this.pet.name
            this.birthday = this.pet.birthday
            this.adaptday = this.pet.adaptday
            this.color = this.pet.color
            this.photoUrl = this.pet.photoUrl
            // const profile = document.querySelector('#myimg')
            // profile.style.border = `${this.color} solid 1vh`;
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
        this.addPetImage()
        }
      })
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
        const file = this.img
        this.img = ""
        this.photoUrl = `gs://a607-445bc.appspot.com/${randomnum}`

        
        uploadBytes(storageRef, file)
        .then((snapshot) => {
            this.downloadPetImage()
            console.log('Uploaded a blob or file!');
            console.log(snapshot);
        });
    },
    downloadPetImage() {
      // this.popClose()
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
              Swal.fire ({
                // icon: "success",
                title: "업로드 완료!",
                imageUrl: this.photoUrl,
                // imageHeight: "30%",
                
              })
          });
    },
        updatePet() {
            // this.popClose()
            const types = this.types
            const name = this.name
            const birthday = this.birthday
            const adaptday = this.adaptday
            const color = this.color
            const photoUrl = this.photoUrl
            const petId = this.$route.params.id

            const payload = {
                types,
                name,
                birthday,
                adaptday,
                color,
                photoUrl,
                petId
            }
            this.$store.dispatch("updatePet", payload)
        },
    
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
        // popOpen() {
        //     document.getElementsByClassName("modal-wrap")[0].style.display ='block';
        //     document.getElementsByClassName("modal-bg")[0].style.display ='block';
        // },
        // popClose() {
        //     document.getElementsByClassName("modal-wrap")[0].style.display ='none';
        //     document.getElementsByClassName("modal-bg")[0].style.display ='none';
        // },
    }
}
</script>

<style lang="scss">
.input-edit {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
}
</style>