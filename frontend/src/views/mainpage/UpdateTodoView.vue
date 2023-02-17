<template>
  <div class="holder-vanilla-todo font-lefts">
    {{ this.$store.state.todo.commentList }}
    <router-link to="/mainpage">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link>
    <div class="input-relative">
      <span class="font-medium font-lefts ">카테고리</span>
        <select class="input-category input font-medium" name="category" id="category" v-model="this.$store.state.todo.category">
          <option class="font-medium" value="1">미용</option>
          <option class="font-medium" value="2">병원</option>
          <option class="font-medium" value="3">산책</option>
          <option class="font-medium" value="4">기타</option>
        </select>
    </div>
        <br>
    <div class="input-relative">
        <span class="font-medium font-lefts">일정명</span>
        <input class="input-category font-medium input"
        v-model="this.$store.state.todo.title"
        name="title"
        type="text"
        id="title"
        />  
    </div>
      <br>
    <div class="input-relative">
        <span class="font-medium font-lefts ">대상</span>
        <select class="input-category input font-medium" name="petId" id="petId" v-model="this.$store.state.todo.petId">
          <option class="font-medium" value="0" selected>공통</option>
          <option class="font-medium" v-for="pet in pets" :value="pet.petId" v-bind:key="pet.name">
            {{ pet.name }}
          </option>
        </select>
    </div>
        <br>
    <div class="input-relative">
        <span class="font-medium font-lefts">담당자 </span>
        <select
          class="input-category input font-medium"
          name="whoPlanned"
          id="whoPlanned"
          v-model="this.$store.state.todo.whoPlanned"
        >
          <option class="font-small" value="" selected disabled hidden>
            선택해주세요.
          </option>
          <option
          class="font-small"
          v-for="member in members"
          v-bind:key="member.key"
        >
          {{ member }}
        </option>
      </select>
    </div>
    <br />
    <div class="holder-category">
      <p class="font-lefts"></p>
    </div>
    <br />
    <!-- {{ this.$store.state.todo.startDate }}
    {{ this.$store.state.todo.endDate }}
    {{ this.$store.state.todo.repeatEndDate }}
    {{ this.startDate }} -->
    <div class="">
      <div class="input-relative-time">
        <span class="font-medium"> 시작 </span>
        <input
          class="input-category input input-day font-lefts font-small"
          type="date"
          id="startDate"
          v-model="this.startDate"
          max="endDate"
        />
        <input
          class="input-category input font-small input-time"
          type="time"
          id="startTime"
          v-model="this.startTime"
        />
      </div>
      <br />
      <div class="input-relative-time">
        <span class="font-medium col-2"> 마침</span>
        <input
          class="input-category input input-day font-lefts font-small"
          type="date"
          id="endDate"
          v-model="this.endDate"
          min="startDate"
        />
        <input
          class="input-category input font-small input-time"
          type="time"
          id="endTime"
          v-model="this.endTime"
        />
      </div>
      <br />
      <div class="input-relative">
        <span class="font-medium"> 반복 </span>
        <input
          v-show="this.$store.state.todo.repeatUnit != 0"
          type="number"
          class="input input-cycle font-medium"
          name="repeatAmount"
          id="repeatAmount"
          v-model="this.$store.state.todo.repeatAmount"
          min="1"
          max="30"
        />
        <select
          class="input-category input font-medium"
          name="repeatUnit"
          id="repeatUnit"
          v-model="this.$store.state.todo.repeatUnit"
        >
          <option class="font-medium" value="" selected disabled hidden>
            선택해주세요.
          </option>
          <option class="font-medium" value="0">반복 안 함</option>
          <option class="font-medium" value="6">일마다</option>
          <option class="font-medium" value="3">주마다</option>
          <option class="font-medium" value="2">달마다</option>
          <option class="font-medium" value="1">년마다</option>
        </select>
      </div>
      <br />
      <div v-show="this.$store.state.todo.repeatUnit != 0" class="input-relative-time">
        <span class="font-medium">기한</span>
        <input
          type="date"
          class="input-category input input-day font-lefts font-small"
          id="repeatEndDate"
          v-model="this.repeatEndDate"
          min="endDate"
        />
        <!-- <input type="time" class="input-category input font-small input-time" id="repeatEndTime" v-model="this.$store.state.todo.repeatEndTime" /> -->
      </div>
      <br />
    </div>

    <div class="holder-category">
      <p class="font-lefts"></p>
    </div>
    <br />
    <div class="input-relative">
      <span class="font-medium  font-lefts">알림 </span>
      <select
        class="input-category input font-medium"
        name="alarm"
        id="alarm"
        v-model="this.$store.state.todo.alarm"
      >
        <option class="font-medium" value="-1">알림 없음</option>
        <option class="font-medium" value="0">일정 시작시간</option>
        <option class="font-medium" value="10">10분 전</option>
        <option class="font-medium" value="60">1시간 전</option>
        <option class="font-medium" value="1200">1일 전</option>
        <!-- <option value="">직접 설정</option> -->
      </select>
      </div>
      <br />
    <div class="input-relative">
      <span class="font-medium  font-lefts">캘린더</span>
      <select
        class="input-category input font-medium"
        name="display"
        id="display"
        v-model="this.$store.state.todo.display"
      >
        <option class="font-medium" value="true">표시</option>
        <option class="font-medium" value="none">표시안함</option>
      </select>
      </div>  
      <br />

    <div class="">
      <span class="font-medium  font-lefts">메모</span>
      <textarea
        class="holder-white-mini"
        name="memo"
        id="memo"
        v-model="this.$store.state.todo.memo"
      ></textarea>
    </div>
    
    <div class="">
      <button class="btn-colored btn-medium btn-Todo" @click="updateTodo">
        수정하기
      </button>
    </div>
    <br />
  </div>
</template>

<script>
// import MainView from "@/views/mainpage/MainView.vue"
// import router from "@/router"
import Swal from "sweetalert2"

export default {
  name: "UpdateTodoView",
  components: {},
  data() {
    return {
      schedule: [],
      members: [],
      pets: [],
      title: null,
      category: null,
      petId: null,
      startDate: null,
      startTime: null,
      endDate: null,
      endTime: null,
      start: null,
      end: null,
      repeatUnit: "0",
      repeatAmount: "1",
      repeatEndDate: null,
      repeatEndTime: null,
      repeatEnd: null,
      alarm: "-1",
      display: true,
      memo: null,
      whoPlanned: null,
      now: "00:00",
      after: "00:00",
      aaa: 23,
      userData: [],
    }
  },
  computed: {},
  // created(){
  // },
  created() {
    this.getPet()
    this.getMembers()
    this.detailTodo()
    console.log(this.$route.params)
    this.stringToTime()
    this.timeToString()
  },
  methods: {
    getPet() {
      const dataSet = this.$store.dispatch("getPet2")
      dataSet.then((getData) => {
        console.log("받은 데이터" + getData)
        this.pets = getData
      })
    },
    detailTodo() {
      this.$store.dispatch("detailTodo", this.$route.params.id)
    },
    getMembers() {
      const dataSet = this.$store.dispatch("getMember")
      dataSet.then((getData) => {
        this.members = getData
      })
    },
    getUserData() {
      const dataSet = this.$store.dispatch("getUserData")
      dataSet.then((getData) => {
        this.userData = getData
      })
    },
    updateTodo() {
      Swal.fire({
        title: "일정 변경",
        text: "정말로 변경하시겠습니까?",
        icon: "warning",

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
        cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
        confirmButtonText: "예", // confirm 버튼 텍스트 지정
        cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
      }).then((result) => {
        console.log("this.schedule.scheduleId")
        console.log(this.$store.state.todo.scheduleId)
        
        if (result.isConfirmed) {
          let startdd = this.startDate + this.startTime + "00"
          let startd = startdd.replace(/-/g, "")
          let start = startd.replace(/:/g, "")
          let nowDated = this.now.replace(/:/g, "")
          let enddd = this.endDate + this.endTime + "00"
          let endd = enddd.replace(/-/g, "")
          let end = endd.replace(/:/g, "")
          let repeatEnddd = this.repeatEndDate
          let repeatEndd = repeatEnddd.replace(/-/g, "")
          let repeatEnd = repeatEndd.replace(/:/g, "")
          console.log(repeatEnd)
          console.log("{{{{{{{{{{{{{{{{{{{{{{{{{_______________________________________}}}}}}}}}}}}}}}}}}}}}}}}}")
          console.log(start)

          const title = this.$store.state.todo.title
          const category = this.$store.state.todo.category
          const petId = this.$store.state.todo.petId
          const startDate = start
          const endDate = end
          const nowDate = nowDated
          const repeatUnit = this.$store.state.todo.repeatUnit
          const repeatAmount = this.$store.state.todo.repeatAmount
          const repeatEndDate = repeatEnd
          const alarm = this.$store.state.todo.alarm
          const display = this.$store.state.todo.display
          const memo = this.$store.state.todo.memo
          const whoPlanned = this.$store.state.todo.whoPlanned
          const scheduleId = this.$store.state.todo.scheduleId
          const photoUrl = this.$store.state.todo.photoUrl
          const reviewList = this.$store.state.todo.reviewList

          const payload = {
            title: title,
            category: category,
            petId: petId,
            startDate: startDate,
            endDate: endDate,
            nowDate: nowDate,
            repeatUnit: repeatUnit,
            repeatAmount: repeatAmount,
            repeatEndDate: repeatEndDate,
            alarm: alarm,
            display: display,
            memo: memo,
            whoPlanned: whoPlanned,
            scheduleId: scheduleId,
            photoUrl,
            reviewList
          }
          console.log(payload)
          this.$store.dispatch("updateTodo", payload)
          this.$router.go(-1)
        }
      })
    },
    timeToString() {
      var date = new Date()
      this.nowdate = date
      console.log(date)
      this.now =
      date.getFullYear() +
      (date.getMonth() + 1 <= 9
      ? "0" + (date.getMonth() + 1)
      : date.getMonth() + 1) +
      (date.getDate() <= 9 ? "0" + date.getDate() : date.getDate()) +
      (date.getHours() <= 9 ? "0" + date.getHours() : date.getHours()) +
      (date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes()) +
      (date.getSeconds() <= 9 ? "0" + date.getSeconds() : date.getSeconds())
      console.log(this.now)
    },
    stringToTime() {
      console.log(this.event)
      var startDateStr = this.$store.state.todo.startDate.substr(0, 8)
      var endDateStr = this.$store.state.todo.endDate.substr(0, 8)
      var repeatEndDateStr = this.$store.state.todo.repeatEndDate.substr(0, 8)
      var startTimeStr = this.$store.state.todo.startDate.substr(8, 4)
      var endTimeStr = this.$store.state.todo.endDate.substr(8, 4)
      var startDatea = startDateStr.replace(/^(\d{4})(\d\d)(\d\d)$/, "$1-$2-$3")
      var endDatea = endDateStr.replace(/^(\d{4})(\d\d)(\d\d)$/, "$1-$2-$3")
      var repeatEndDatea = repeatEndDateStr.replace(
        /^(\d{4})(\d\d)(\d\d)$/,
        "$1-$2-$3"
      )
      var startTimea = startTimeStr.replace(/^(\d\d)(\d\d)$/, "$1:$2")
      var endTimea = endTimeStr.replace(/^(\d\d)(\d\d)$/, "$1:$2")
      this.startDate = startDatea
      this.endDate = endDatea
      this.startTime = startTimea
      this.endTime = endTimea
      this.repeatEndDate = repeatEndDatea
      // this.startDate = start
      // this.end = end
    },
  },
}
</script>

<style lang="scss">
.input-category {
  width: 50vw !important;
  // float: right;
  right: 0vw;
  position: absolute;
}
.input-day {
  top: 0;
}
.input-time{
  bottom: 0;
}
.input-cycle {
  top: 0vh !important;
  position: absolute;
  right: 50vw;
  margin-top: 0vh !important;
}
.input-relative {
  position: relative;
  height: 7vh;
}
.input-relative-time {
  position: relative;
  height: 14vh;
}
</style>
