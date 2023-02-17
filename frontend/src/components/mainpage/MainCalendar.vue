<script>
import FullCalendar from "@fullcalendar/vue3"
import dayGridPlugin from "@fullcalendar/daygrid"
import interactionPlugin from "@fullcalendar/interaction"
import timeGridPlugin from "@fullcalendar/timegrid"
import AddTodoView from "@/views/mainpage/AddTodoView.vue"
import AlarmView from "@/views/mainpage/AlarmView.vue"
import DetailTodoView from "@/views/mainpage/DetailTodoView.vue"

// import Event from "./event.json"
// import PetIdList from "./petidlist.json"
// import Vue from 'vue'

export default {
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  props: {
    calendarId: String,
    events: Object,
    pets: Object,
    notificationId: String,
  },
  data: function () {
    return {
      currentEvents: [],
      // calendar.render();
    }
  },
  computed: {
    calendarOptions() {
      return {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        locale: "ko",
        buttonIcons: {
          // prev: '@/assets/add_todo.png',
          // addevent: this.prevImage,
        },
        headerToolbar: {
          left: "justbutton",
          center: "prev title next",
          // right: "",
          right: "gotoalarm alarmnum",
        },
        buttonText: {
          today: "",
        },
        initialView: "dayGridMonth",
        // initialEvents: Event,
        // initialEvents: this.events,
        events: this.events,
        // refetchEvents: this.currentEvents,
        eventsSet: this.currentEvents,
        // eventClick: this.goToDetail,
        // eventColor: this.pets.color,
        // eventTextColor: PetIdList.textColor,
        // eventDisplay: Event.eventDisplay,
        eventDisplay: "block",
        selectable: true,
        formatDay: this.formatDay,
        select: this.addEvent,
        height: 700,
        navLinks: true,
        navLinkDayClick: this.selectDay,
        editable: false,
        weekends: true,
        dayMaxEvents: true,
        displayEventTime: false,
        customButtons: {
          addevent: {
            text: "",
            click: this.addEvent2,
          },
          justbutton: {
            text: "",
          },
          gotoalarm: {
            text: "",
            click: this.gotoAlarm,
          },
          alarmnum: {
            text: this.notificationId,
          },
        },
        // events: [
        //   // { title: 'Meeting', start: new Date() }
        // ],
      }
    },
  },
  methods: {
    handleEvents(events) {
      this.currentEvents = events
    },
    addEvent(start) {
      console.log(start)
      let enddate = start.end
      let date = new Date(enddate)
      date.setDate(enddate.getDate() - 1)

      let endstr =
        date.getFullYear() +
        "-" +
        (date.getMonth() + 1 <= 9
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) +
        "-" +
        (date.getDate() <= 9 ? "0" + date.getDate() : date.getDate())

      this.$router.push({
        name: AddTodoView,
        params: {
          calendarId: this.calendarId,
          start: start.startStr,
          end: endstr,
          id: 1,
        },
      })
    },
    addEvent2(start) {
      console.log(start)
      // let enddate = start.end
      let date = new Date()
      console.log(date)
      // date.setDate(enddate.getDate() - 1)

      let datestr =
        date.getFullYear() +
        "-" +
        (date.getMonth() + 1 <= 9
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) +
        "-" +
        (date.getDate() <= 9 ? "0" + date.getDate() : date.getDate())

      this.$router.push({
        name: AddTodoView,
        params: {
          calendarId: this.calendarId,
          start: datestr,
          end: datestr,
          id: 1,
        },
      })
    },
    // getEvent(){
    //   this.$store.dispatch('getEvent')
    //   console.log(this.$stroe.state.events)
    // },
    gotoAlarm() {
      this.$router.push({
        name: AlarmView,
        params: { calendarId: this.calendarId },
      })
    },
    goToDetail(a) {
      // console.log(a.event._def.publicId)
      this.$router.push({
        name: DetailTodoView,
        params: { id: a.event._def.publicId },
      })
    },
    selectDay(date) {
      let selectdate = date
      let selecteddate = new Date(selectdate)
      selecteddate.setDate(selectdate.getDate())

      let selstr =
        selecteddate.getFullYear() +
        "-" +
        (selecteddate.getMonth() + 1 <= 9
          ? "0" + (selecteddate.getMonth() + 1)
          : selecteddate.getMonth() + 1) +
        "-" +
        (selecteddate.getDate() <= 9
          ? "0" + selecteddate.getDate()
          : selecteddate.getDate())

      this.emitter.emit("selectdate", selstr)
    },
    // addEvent1() {
    //   // let startday = this.date
    //   // console.log(todayStr)
    //   this.$router.push({
    //     name: AddTodoView,
    //     params: { calendarid: "aaa" },
    //   })
    // },
  },
}
</script>

<template>
  <!-- {{ this.events }} -->
  <!-- {{ this.notificationId }} -->
  <FullCalendar
    :options="calendarOptions"
    id="calendar"
    formatDay="(locale, date)=>date.toLocaleString('en', { day: 'numeric' })"
  />
</template>

<style lang="css">
h2 {
  /* margin: 0; */
  margin: 0px 10px 0px 21px !important;
  font-size: 12px;
  justify-content: center !important;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

/* .custom-add-icon{ */
/* content: url('@/assets/add_todo.png'); */
/* } */
/* .custom-add-icon .fc-icon {
    background-image: url(@/assets/add_todo.png);
} */

.app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 18px;
}

.app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  /* max-width: 1100px; */
  margin: 0 0;
}

.fc-toolbar-title {
  font-size: 4.5vh !important;
}

.fc-next-button {
  height: 54px; 
  margin-bottom: 15px !important;
}

.fc-prev-button {
  height: 54px;
  margin-bottom: 15px !important;
}

/* 요일 폰트 */
.fc-col-header-cell-cushion {
  font-size: 22px !important;
}

/*  */
.fc-header-toolbar {
  height: 70px;
  margin: 15px 0 !important;
  /* margin-bottom: 8px !important; */
}

.fc-toolbar-chunk {
  text-align: center !important;
  justify-content: end !important;
  vertical-align: bottom;
  /* border: "none"; */
}

/* 날짜 폰트 */
.fc-daygrid-day-top {
  font-size: 3vh !important;
  /* font-weight: bolder; */
  text-align: left !important;
  padding-top: 0;
  height: 3vh;
  z-index: 1 !important;
}

.fc-daygrid-day-number {
  text-align: left !important;
  float: left;
  padding: 0.2vh !important;
  top: -1vh !important;
  /* background-color: red !important; */
  /* color: red !important; */
}

.fc-dayGridMonth-view {
  border: #faa960;
}

.fc-view {
  border: #faa960;
}

.fc-daygrid {
  border: #faa960;
}

.fc-toolbar-title {
  display: inline;
  text-align: center !important;
  justify-content: center !important;
}

.fc-addevent-button {
  background-image: url("@/assets/add_todo.png");
  display: block;
  width: 80px;
  height: 80px;
  background-size: 70 70px;
  text-emphasis-color: black !important;
  background-color: white !important;
  margin-bottom: 15px !important;
  box-shadow: none !important;
}
.fc-addevent-button span {
  display: none;
}

/* 알람 */
.fc-gotoalarm-button {
  background-image: url("@/assets/icon_bell.png");
  display: block;
  width: 55px;
  height: 55px;
  background-size: 55px 55px;
  text-emphasis-color: black !important;
  background-color: white !important;
  /* margin-bottom: 15px !important; */
  margin: 0 0 15px 0 !important;
}

.fc-gotoalarm-button:hover {
  border: none;
}

.fc-gotoalarm-button span {
  display: none;
}

/* 알람 숫자 */
.fc-alarmnum-button {
  /* display: block; */
  width: 40px;
  height: 40px;
  /* background-size: 7vw 7vw; */
  font-size: 26px !important;
  background-color: #ff0000 !important;
  text-emphasis-color: black !important;
  /* background-color: white !important; */
  border: 0.5px solid #ff0000 !important;
  border-radius: 50% !important;
  margin: 0 0 50px -20px !important;
  padding: 0 0 0 0 !important;
}

/* .fc-alarmnum-button::after {
  display: none;
  position: absolute;
  width: 5px;
  height: 5px;
  background-color: none;
  box-sizing:border-box;
  outline: none;
  margin: 0 30px 15px 0 !important;  
} */

.fc-today-button {
  width: 11vw;
  height: 9vw;
  background: white !important;
  display: block;
  background-size: 11vw 9vw;
  text-emphasis-color: black !important;
  background-color: white !important;
  margin: 0 0 15px 10px !important;
  border: none;
}

.fc-justbutton-button {
  width: 11vw;
  height: 9vw;
  background: white !important;
  display: block;
  cursor: default !important;
  background-size: 11vw 9vw;
  text-emphasis-color: black !important;
  background-color: white !important;
  margin: 0 0 15px 10px !important;
  border: none;
  box-shadow: none !important;
}

.fc-justbutton-button:focus {
  border: 0px !important;
  outline: none !important;
  box-shadow: none !important;
}
.fc-justbutton-button:active {
  border: 0px !important;
  outline: none !important;
  box-shadow: none !important;
}

.fc-justbutton-button:hover {
  border: 0px !important;
  outline: none !important;
  box-shadow: none !important;
}

.fc-justbutton-button span {
  display: none !important;
}

.fc-event-title-container {
  font-size: 0px;
}

.fc-daygrid-event-harness {
  height: 10px;
  /* height: 1.5vw;
  width: 1.5vw;
  border-radius: 60%;
  z-index: 0 !important;
  float: left !important;
  display: flex !important;

  border: 0.5vw solid; */
  /* border-color: #3788d8; */
  /* background: #3788d8; */
  /* position: relative !important; */
}

/* 일반 날짜 */
.fc-day a {
  color: black;
  text-decoration: none;
}

/* 일요일 날짜 빨간색 */
.fc-day-sun a {
  color: red;
  text-decoration: none;
}

/* 토요일 날짜 파란색 */
.fc-day-sat a {
  color: blue;
  text-decoration: none;
}

:root {
  --fc-small-font-size: 0.8em;
  --fc-page-bg-color: #fff;
  --fc-neutral-bg-color: rgba(208, 208, 208, 0.3);
  --fc-neutral-text-color: #808080;
  --fc-border-color: #ddd;

  --fc-button-text-color: #fff;
  --fc-button-bg-color: #faa960 !important;
  --fc-button-border-color: white !important;
  --fc-button-hover-bg-color: #faa960 !important;
  --fc-button-hover-border-color: white !important;
  --fc-button-active-bg-color: #faa960 !important;
  --fc-button-active-border-color: white !important;

  --fc-event-bg-color: #3788d8;
  --fc-event-border-color: #3788d8;
  --fc-event-font-color: #fff;
  --fc-event-selected-overlay-color: rgba(0, 0, 0, 0.25);

  --fc-more-link-bg-color: #d0d0d0;
  --fc-more-link-text-color: inherit;

  --fc-event-resizer-thickness: 8px;
  --fc-event-resizer-dot-total-width: 8px;
  --fc-event-resizer-dot-border-width: 0;

  --fc-non-business-color: rgba(215, 215, 215, 0.3);
  --fc-bg-event-color: rgb(143, 223, 130);
  --fc-bg-event-opacity: 0.3;
  --fc-highlight-color: rgba(188, 232, 241, 0.3);
  --fc-today-bg-color: rgba(255, 220, 40, 0.15);
  --fc-now-indicator-color: red;

  /* --fc-daygrid-event-harness-border-color: #3788d8; */
  /* --fc-daygrid-event-harness-background-color: #3788d8; */
}
</style>
