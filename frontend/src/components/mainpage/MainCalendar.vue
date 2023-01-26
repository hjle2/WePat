<script>
import FullCalendar from "@fullcalendar/vue3"
import dayGridPlugin from "@fullcalendar/daygrid"
import interactionPlugin from "@fullcalendar/interaction"
import timeGridPlugin from "@fullcalendar/timegrid"
import AddTodoView from "@/views/mainpage/AddTodoView.vue"

export default {
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  data: function () {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        locale: "ko",
        headerToolbar: {
          left: "today",
          center: "prev title next",
          right: "addevent",
        },
        initialView: "dayGridMonth",
        selectable: true,
        selectHelper: true,
        select: this.addEvent,
        editable: true,
        weekends: true,
        buttonIcons: true,
        navLinks: true,
        dayMaxEvents: false,
        customButtons: {
          addevent: {
            img: "@/assets/add_todo.png",
            text: "일정 추가",
            click: function () {
              // addEvent2
              // console.log(document.addEvent2)
              alert("clicked custom button 1!")
            },
          },
        },
        events: [
          // { title: 'Meeting', start: new Date() }
        ],
      },
    }
  },
  computed: {
    // addEvent2() {
    //   return this.addEvent1()
    // },
  },
  methods: {
    addEvent(start) {
      // let startday = this.date
      // console.log(todayStr)
      this.$router.push({
        name: AddTodoView,
        params: { calendarid: "aaa", start: start.startStr, end: start.endStr },
      })
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
  <FullCalendar :options="calendarOptions" id="calendar" />

  <div id="calendar-container">
    <div id="calendar"></div>
  </div>
</template>

<style lang="css">
h2 {
  margin: 0;
  font-size: 16px;
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

.app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  max-width: 1100px;
  margin: 0 auto;
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
  --fc-small-font-size: 0.85em;
  --fc-page-bg-color: #fff;
  --fc-neutral-bg-color: rgba(208, 208, 208, 0.3);
  --fc-neutral-text-color: #808080;
  --fc-border-color: #ddd;

  --fc-button-text-color: #fff;
  --fc-button-bg-color: #d57412;
  --fc-button-border-color: #;
  --fc-button-hover-bg-color: #1a252f;
  --fc-button-hover-border-color: #1a252f;
  --fc-button-active-bg-color: #1a252f;
  --fc-button-active-border-color: #151e27;

  --fc-event-bg-color: #3788d8;
  --fc-event-border-color: #3788d8;
  --fc-event-text-color: #fff;
  --fc-event-selected-overlay-color: rgba(0, 0, 0, 0.25);

  --fc-more-link-bg-color: #d0d0d0;
  --fc-more-link-text-color: inherit;

  --fc-event-resizer-thickness: 8px;
  --fc-event-resizer-dot-total-width: 8px;
  --fc-event-resizer-dot-border-width: 1px;

  --fc-non-business-color: rgba(215, 215, 215, 0.3);
  --fc-bg-event-color: rgb(143, 223, 130);
  --fc-bg-event-opacity: 0.3;
  --fc-highlight-color: rgba(188, 232, 241, 0.3);
  --fc-today-bg-color: rgba(255, 220, 40, 0.15);
  --fc-now-indicator-color: red;
}
</style>
