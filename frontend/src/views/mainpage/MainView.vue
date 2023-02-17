<template>
 <div id="MainView">
  <div>
    <!-- <div :v-if="this.loading === true">
      <LoadingView />
    </div> -->
    <!-- {{this.events}} -->
    <!-- {{this.pets}} -->
    <MainCalendar :calendarId="this.calendarId" :events="events" :pets="pets" :notificationId="notificationId"/>
    <!-- <MainCalendar /> -->
    <!-- {{ this.calendarId }} -->
    <TodoList
      :calendarId="this.calendarId"
      :events="events"
      :pets="pets"
      :nickName="nickName"
    />
  </div>
  <div class="main-bottom">
  </div>
  <div class="pet-main" id="petIcons">
    <PetIconMain :pets="pets" />
  </div>
  <br />
  <BottomNavbar1 />
  <img src="@/assets/add.png" alt="" class="add-img" @click="addEvent"/>
  </div>
</template>

<script>
import MainCalendar from "@/components/mainpage/MainCalendar.vue"
import TodoList from "@/components/mainpage/TodoList.vue"
import PetIconMain from "@/components/mainpage/PetIconMain.vue"
import BottomNavbar1 from "@/components/common/BottomNavbar1.vue"
import AddTodoView from "@/views/mainpage/AddTodoView.vue"
// import LoadingView from "@/views/accounts/LoadingView.vue"


// window.onscroll = scrolls()
// function scrolls (e) {
//   console.log(e.target.scrollTop)
//   console.log("body" + document.body.scrollTop)
//   console.log("ele" + document.documentElement.scrollTop)
//   if(document.body.scrollTop > 200 || document.documentElement.scrollTop > 200 ) {
//     document.getElementById('petIcons').style.bottom = '0px';
//   } else {
//   document.getElementById('petIcons').style.bottom = '100px';
//   }
// }

export default {
  name: "MainView",
  data() {
    return {
      pets: [],
      events: [],
      nickName: null,
      calendarId: null,
      notificationId: null,
      loading: true,
    }
  },
  components: {
    MainCalendar,
    TodoList,
    PetIconMain,
    BottomNavbar1,
    // LoadingView,
},
  computed: {},
  methods: {
    
    getPet() {
      const dataSet = this.$store.dispatch("getPet2")
      dataSet.then((getData) => {
        this.pets = getData
        this.addShow()
        return getData
      })
    },
    addShow() {
      this.pets.map((obj) => {
        obj.show = true
        // this.loading = false
      })
    },
    addColor() {
      this.events.map((obj) => {
        obj.color = "grey"
        for (let i = 0; i < this.pets.length; i++) {
          if (obj.petId === this.pets[i].petId) {
            obj.color = this.pets[i].color
          }
        }
      })
      // this.loading = false
    },
    time() {
      this.events.map((obj) => {
        var startStr = obj.startDate
        var endStr = obj.endDate
        var startDateStr = obj.startDate.substr(0, 8)
        var endDateStr = obj.endDate.substr(0, 8)
        var startTimeStr = obj.startDate.substr(8, 4)
        var endTimeStr = obj.endDate.substr(8, 4)
        // var now = new Date()

        // now =
        //   now.getFullYear() +
        //   (now.getMonth() + 1 <= 9
        //     ? "0" + (now.getMonth() + 1)
        //     : now.getMonth() + 1) +
        //   (now.getDate() <= 9 ? "0" + now.getDate() : now.getDate()) +
        //   (now.getHours() <= 9 ? "0" + now.getHours() : now.getHours()) +
        //   (now.getMinutes() <= 9 ? "0" + now.getMinutes() : now.getMinutes()) +
        //   (now.getSeconds() <= 9 ? "0" + now.getSeconds() : now.getSeconds())
        // startam =
        var starta = new Date(
          startStr.replace(
            /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
            "$1-$2-$3 $4:$5:$6"
          )
        )
        var enda = new Date(
          endStr.replace(
            /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
            "$1-$2-$3 $4:$5:$6"
          )
        )
        var startDatea = startDateStr.replace(
          /^(\d{4})(\d\d)(\d\d)$/,
          "$1-$2-$3"
        )
        var endDatea = endDateStr.replace(/^(\d{4})(\d\d)(\d\d)$/, "$1-$2-$3")
        var startTimea = startTimeStr.replace(/^(\d\d)(\d\d)$/, "$1시$2분")
        var endTimea = endTimeStr.replace(/^(\d\d)(\d\d)$/, "$1시$2분")
        var start = starta.toISOString()
        var end = enda.toISOString()
        obj.start = start
        obj.end = end
        obj.startDatea = startDatea
        obj.endDatea = endDatea
        obj.startTime = startTimea
        obj.endTime = endTimea
      })
    },
    getUserData() {
      const dataSet = this.$store.dispatch("getUserData")
      dataSet.then((getData) => {
        this.nickName = getData.nickName
        this.calendarId = getData.calendarId
        // this.nickName = this.$store.state.nickName
        // this.calendarId = this.$store.state.calendarId
        const dataSet = this.$store.dispatch("getEvent", this.calendarId)
        dataSet.then((getEvents) => {
          this.events = getEvents
          this.time()
          this.addColor()
        })
      })
    },
    addEvent(start) {
      console.log(start)
      // let enddate = start.end
      let date = new Date()
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
    getNotificationId() {
      const dataSet = this.$store.dispatch("getNotificationId")
      dataSet.then((getData) => {
        this.notificationId = getData
        return getData
      })
    },
  },
  created() {
    // this.aaa()
    this.getPet()
    this.getUserData()
    this.getNotificationId()
  },
  mounted() {
    this.getPet()
    this.getUserData()
    this.getNotificationId()
  },
}
</script>
<style lang="scss">
.add-img {
  width: 50px;
  position: fixed;
  bottom: 120px;
  right: 30px;
  z-index: 1;
}
.pet-main {
  width: 100vw;
  z-index: 1;
  bottom: 100px !important;
  // bottom: 0px;
  position: fixed;
  transition: bottom 0.5s;
}
.main-bottom {
  height : 100px;
}
</style>
