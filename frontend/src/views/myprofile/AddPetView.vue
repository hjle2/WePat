<template>
  <div>
    <Chrome v-model="colors"></Chrome>
  </div>
  {{ colors.hex }}
  <br />
  <input type="file" id="image" />
  <button @click="upload">파일 업로드</button>
  <br />
  <div><span>입양일</span> <input type="text" v-model="adaptday" /></div>
  <div><span>나이</span> <input type="text" v-model="age" /></div>
  <div><span>생일</span> <input type="text" v-model="birthday" /></div>
  <div><span>이름</span> <input type="text" v-model="name" /></div>
  <button @click="petsignUp()">반려동물 추가하기</button>
</template>

<script>
import { Chrome } from "@ckpack/vue-color"
export default {
  components: {
    Chrome,
  },
  data() {
    return {
      colors: "",
      adaptday: "",
      age: "",
      birthday: "",
      name: "",
    }
  },
  methods: {
    // upload() {
    //   const storage = firebase.storage();

    //   var file = document.querySelector('#image').files[0];
    //   var storageRef = storage.ref();
    //   var storageRoot = storageRef.child('image/' + '파일명');
    //   var uploading = storageRoot.put(file)
    // },
    petsignUp() {
      const colors = this.colors.hex
      const adaptday = this.adaptday
      const age = this.age
      const birthday = this.birthday
      const name = this.name

      const payload = {
        colors,
        adaptday,
        age,
        birthday,
        name,
      }
      this.$store.dispatch("petsignUp", payload)
    },
  },
}
</script>

<style></style>
