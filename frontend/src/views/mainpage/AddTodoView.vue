<template>
  <p>일정 추가페이지</p>
  <form action="">
    <span>일정 </span>
    <input
      v-model="todoname"
      type="text"
      placeholder="일정 이름을 입력하세요"
      id="todoname"
    />
    <br />
    <br />

    <span>반려동물 </span>
    <select name="pet" id="pet">
      <option value="" selected disabled hidden>선택해주세요.</option>
      <option value="1">콩이</option>
      <option value="2">콩순이</option>
    </select>
    <br />
    <br />

    <span>시작 시간 </span>
    <input type="date" id="start_date" v-model="start_date"/>
    <input type="time" id="start_time" v-model="now"/>
    <span> ~ </span>
    <br>  
    <span>종료 시간 </span>
    <input type="date" id="end_date" v-model="end_date"/>
    <input type="time" id="end_time" v-model="now"/>
    <br />
    <br />

    <span>담당자 </span>
    <select name="manager" id="manager">
      <option value="" selected disabled hidden>선택해주세요.</option>
      <option value="1">아빠</option>
      <option value="2">엄마</option>
    </select>
    <br />
    <br />

    <span>주기 </span>
    <select name="repeat_cycle" id="repeat_cycle">
      <option value="" selected disabled hidden>선택해주세요.</option>
      <option value="1">매일</option>
      <option value="2">매주</option>
      <option value="3">매달</option>
      <option value="4">매분기</option>
      <option value="5">매년</option>
    </select>
    <br />
    <br />

    <router-link to="/mainpage">
      <button>취소</button>
    </router-link>
    <button type="submit">추가</button>
  </form>
</template>

<script>

import axios from 'axios';
const API_URL = 'http://70.12.247.105:8080'

export default {
  name: "AddTodoView",
  components: {},
  data() {
    return {
      todoname: null,
      pet: null,
      start_date: null,
      start_time: null,
      end_date: null,
      ebd_time: null,
      manager: null,
      repeat_cycle: null,
      now: "00:00",
    }
  },
  computed: {},
  // created(){
  // },
  created() {
    console.log(this.$route.params)
    this.addCalTodo()
    this.time()
    this.start_date=this.$route.params.start
    this.end_date=this.$route.params.end
    console.log(this.$data)
    console.log(this.now) 
  },
  methods: {
    addCalTodo() {
      axios({
        method: 'get',
        url: `${API_URL}/calendar/add/${this.$route.params.calendarid}`,
      })
      .then( res => console.log(res) )
      .catch( err => console.log(err))
    },
  // },
  // methods: {
    addTodo() {
      const todoname = this.todoname

      console.log(todoname)
      // const pwd = this.pwd
      // if (memberId.length === 0) {
      //   alert('아이디를 입력해주세요')
      //   return
      // } else if (pwd.length === 0) {
      //   alert('비밀번호를 입력해주세요')
      //   return
      // }
      // else {
      //   const payload = {
      //     memberId: memberId,
      //     pwd: pwd,
      //   }
      //   // console.log(payload)
      //   this.$store.dispatch('login', payload)
      // }
    },
    time(){
      var date = new Date();

      this.now = date.getHours() + ":"
      + date.getMinutes()},
  },
}
</script>

<style></style>
