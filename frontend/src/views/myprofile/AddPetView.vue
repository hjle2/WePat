<template>
  <div> <span>입양일</span> <input type="text" v-model="adaptday">
  </div>
  <div> <span>나이</span> <input type="text" v-model="age">
  </div>
  <div> <span>생일</span> <input type="text" v-model="birthday">
  </div>
  <div> <span>이름</span> <input type="text" v-model="name">
  </div>
  <button @click="petsignUp()">반려동물 추가하기</button>
</template>

<script>
export default {
  data: function () {
    return {
    adaptday:"",
    age:"",
    birthday:"",
    name:"",
    }
  },
  methods:{
    petsignUp() {
        const adaptday = this.adaptday
        const age = this.age
        const birthday = this.birthday
        const name = this.name

        const payload = {
          adaptday, age, birthday, name
        }
        this.$store.dispatch('petsignUp', payload)
      }
    },
  }
</script>

<style>

</style>