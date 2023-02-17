<template>
  <!-- {{ notification }} -->
  <!-- {{ notification }} -->
  <!-- {{ memberId }} -->
  <!-- {{ alarm }} -->
  <!-- {{ pets }} -->
  <!-- {{ events }} -->
  <router-link to="/mainpage">
    <img src="@/assets/back.png" class="logo-back" alt="" />
  </router-link>
  <br />
  <br />

  <br />
  <br />
  <ul class="alarm-ul">
    <li
      class="alarm-li"
      v-show="notification.length != 0"
      style="list-style: none"
    ></li>
  </ul>
  <ul class="alarm-ul">
    <li
      v-for="noti in notification
        .filter((x) => x.memberId === memberId)
        .sort((a, b) => (a.date < b.date ? 1 : -1))"
      :key="noti.scheduleId"
      class="alarm-li"
      style="justify-content: space-between"
    >
      <p style="text-align: right">
        <span @click="deleteNoti(noti.notificationId)">x</span>
      </p>
      <span
        :id="noti.scheduleId"
        @click="goToDetail(noti.scheduleId)"
        class="aaa"
      >
        <span class="todo-span">
          <span
            v-for="schedule in events.filter(
              (x) => x.scheduleId === noti.scheduleId
            )"
            :key="schedule.scheduleId"
          >
            <span
              v-for="pet in pets.filter((x) => x.petId === schedule.petId)"
              :key="pet.petId"
            >
              <span class="pet-margin">
                <img class="profile-image-small" :src="pet.photoUrl" />
              </span>
            </span>
            <span style="margin-left: 40px">
              {{ schedule.title }}
              <span v-show="noti.notificationType === 1"
                >일정이 추가되었습니다.</span
              >
              <span v-show="noti.notificationType === 2"
                >일정이 수정되었습니다.</span
              >
              <span v-show="noti.notificationType === 3"
                >일정이 완료되었습니다.</span
              >
              <span v-show="noti.notificationType === 4"
                >일정 완료가 취소되었습니다.</span
              >
            </span>
          </span>
          <br />
        </span>
        <span
          class="go-right"
          v-for="schedule in events.filter(
            (x) => x.scheduleId === noti.scheduleId
          )"
          :key="schedule.scheduleId"
          style="margin-right: 10px"
        >
          {{ noti.timeName }}
        </span>

        <br />
        <br />
        <p></p>
      </span>
    </li>
  </ul>
</template>

<script>
import alarm from "./alarm.json"
import DetailTodoView from "@/views/mainpage/DetailTodoView.vue"

export default {
  name: "AlarmItem",
  data() {
    return {
      alarm,
      memberId: null,
      events: [],
      pets: [],
    }
  },
  props: {
    notification: Array,
  },
  methods: {
    getMemberId() {
      const memberId = localStorage.getItem("memberId")
      this.memberId = memberId
    },
    getUserData() {
      this.nickName = this.$store.state.nickName
      this.calendarId = this.$store.state.calendarId
      console.log(this.nickName)
      console.log(this.calendarId)
      const dataSet = this.$store.dispatch("getEvent")
      dataSet.then((getEvents) => {
        this.events = getEvents
        this.getTime()
        dataSet.then(() => {
          this.getPet()
        })
        // console.log(this.events)
      })
    },
    getTime() {
      const now = new Date()
      this.notification.map((obj) => {
        var aTime = new Date(
          obj.date.replace(
            /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
            "$1-$2-$3 $4:$5:$6"
          )
        )
        obj.time = (now - aTime) / 1000
        if (obj.time < 60) return (obj.timeName = `방금 전`)
        const minutes = obj.time / 60
        if (minutes < 60) return (obj.timeName = `${Math.floor(minutes)}분 전`)
        const hours = minutes / 60
        if (hours < 24) return (obj.timeName = `${Math.floor(hours)}시간 전`)
        const days = hours / 24
        if (days < 7) return (obj.timeName = `${Math.floor(days)}일 전`)
        const weeks = days / 7
        if (weeks < 5) return (obj.timeName = `${Math.floor(weeks)}주 전`)
        const months = days / 30
        if (months < 12) return (obj.timeName = `${Math.floor(months)}개월 전`)
        const years = days / 365
        if (years < 7) return (obj.timeName = `${Math.floor(years)}년 전`)
      })
    },
    getPet() {
      const dataSet = this.$store.dispatch("getPet2")
      dataSet.then((getData) => {
        console.log(getData)
        this.pets = getData
      })
    },
    goToDetail(scheduleId) {
      console.log(scheduleId)
      // console.log(a.event._def.publicId)
      this.$router.push({
        name: DetailTodoView,
        params: { id: scheduleId },
      })
    },
    deleteNoti(notificationId) {
      this.$store.dispatch("deleteNoti", notificationId)
    },
  },
  created() {
    this.getMemberId()
    this.getUserData()
    this.getPet()
  },
  // mounted: {

  // }
}
</script>

<style lang="scss">
div.pet-list-collection {
  background: rgba(128, 128, 128, 0.203);
  text-align: left;
  display: flex;
  /* justify-content: space-between !important; */
  cursor: pointer;
  border-bottom-color: black !important;
  border-bottom-width: 1px !important;
  border-bottom-style: solid !important;
  font-size: $font-size-4;
}

.alarm-ul {
  list-style: none;
  margin: 0px 8px !important;
  padding: 0px !important;
  justify-content: space-between;
}

.alarm-li {
  margin: auto !important;
  border-bottom-color: black !important;
  border-bottom-width: 1px !important;
  border-bottom-style: solid !important;
  font-size: $font-size-5;
  text-align: left;
}

.checkbox1 {
  display: inline-block; /* 영역적용가능해짐 */
  width: 30px;
  height: 30px;
  border: 2px solid #333;
  box-sizing: border-box;
  border-radius: 50px !important; /* 모서리둥글게 처리 */
  position: relative;
  top: 4px;
  cursor: pointer; /* 마우스 올렸을때 손모양 처리 */
  margin-top: 0px;
  text-align: center;
}

.aaa {
  cursor: pointer;
}

.todo-span {
  float: left;
}

.todo-margin {
  margin-right: 50px;
}

.go-right {
  float: right;
}
</style>
