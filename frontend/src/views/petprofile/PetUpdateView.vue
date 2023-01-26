<template>
    <div>
        <Chrome v-model="colors"></Chrome>
    </div>
    {{ colors.hex }}
    <br>
    <input type="file" id="image">
    <button @click="upload">파일 업로드</button>
    <br>
    <div> <span>입양일</span> <input type="text" v-model="adaptday">
    </div>
    <div> <span>나이</span> <input type="text" v-model="age">
    </div>
    <div> <span>생일</span> <input type="text" v-model="birthday">
    </div>
    <div> <span>이름</span> <input type="text" v-model="name">
    </div>
    <button @click="petUpdate(pet.id)">반려동물 수정하기</button>
</template>

<script>
import { Chrome } from '@ckpack/vue-color';
import axios from 'axios';
const API_URL = 'http://70.12.247.105:8080'

export default {
components: {
    Chrome,
},
data() {
    return {
    pet:[],
    colors: "",
    adaptday:"",
    age:"",
    birthday:"",
    name:"",
    }
},
created() {
    this.getPetDetail()
  },
methods: {
    getPetDetail() {
      axios({
        method: 'get',
        url: `${API_URL}/pet/${this.$route.params.id}`,
      })
      .then( res => this.pet = res.data )
      .then( () => {this.colors = this.pet.colors
                    this.adaptday = this.pet.adaptday
                    this.age = this.pet.age
                    this.birthday = this.pet.age
                    this.name = this.pet.name})
      .catch( err => console.log(err))
    },
    petUpdate(petId) {
        const colors = this.colors.hex
        const adaptday = this.adaptday
        const age = this.age
        const birthday = this.birthday
        const name = this.name

        axios({
            method: 'put',
            url: `${API_URL}/pet/${petId}`,
            params : {
                colors,
                adaptday,
                age,
                birthday,
                name,
            },
            })
            .then((res) => console.log(res))
            .catch((err) => console.log(err))
    }
}
}
</script>

<style>
</style>