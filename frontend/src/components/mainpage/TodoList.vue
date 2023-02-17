<script>
import TodoListItem from "@/components/mainpage/TodoListItem.vue"

export default {
  name: "TodoList",
  props: {
    pets: Object,
    events: Object,
    calendarId: String,
    nickName: String,
  },
  components: {
    TodoListItem,
  },
  data() {
    return {
      selecteddate: null,
    }
  },
  created() {
    let today = new Date()
    let year = today.getFullYear() // 년도
    let month = today.getMonth() + 1 // 월
    let date = today.getDate() // 날짜

    this.selecteddate =
      year +
      "-" +
      (month < 9 ? "0" + month : month) +
      "-" +
      (date <= 9 ? "0" + date : date)
    console.log(this.selecteddate)
    // this.getEvent()
  },
  mounted() {
    this.emitter.on("selectdate", (selstr) => {
      // let s = selstr.replace(/-/g, "")
      this.selecteddate = selstr
      // let a = new Date(s)
      // let today = new Date()
      // let year = today.getFullYear() // 년도
      // let month = today.getMonth() + 1 // 월
      // let date = today.getDate() // 날짜
      console.log(this.selecteddate)
      // console.log(a)
    })
    // this.calendar.gotoDate(this.selecteddate)
  },
  methods: {
    getEvent() {
      this.$store.dispatch("getEvent")
    },
    // gotoDate(){
    //   this.calendarOptions1.initialDate = String(this.selecteddate)
    //   console.log(this.calendarOptions1.initialDate)
    // }
  },
}
</script>

<template>
  <p class="gray-bar">{{ selecteddate }}</p>

  <TodoListItem
    :tododate="selecteddate"
    :pets="pets"
    :events="events"
    :calendarId="calendarId"
    :nickName="nickName"
  />
</template>

<style lang="scss">
.gray-bar {
  background: #fff;
  text-align: left;
  height: 4vh;
  margin: 1px 0px 0px !important;
  border-top-color: black !important;
  border-top-width: 2px !important;
  border-top-style: solid !important;
  border-bottom-color: black !important;
  border-bottom-width: 2px !important;
  border-bottom-style: solid !important;
  font-size: $font-size-5;
  padding-left: 15px;
  width: 100vw !important;
}
</style>
