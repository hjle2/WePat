<template>
  <!-- {{ events }} -->
  <!-- {{ pets }} -->
  <div>
    <div v-for="pet in pets" :key="pet.petId">
      <div>
        <div
          v-on:click="pet.show = !pet.show"
          class="pet-list-collection"
          :style="{ background: pet.color }"
        >
          <span
            v-if="pet.show == true"
            style="margin: 1.1vh; font-size: 2vh; cursor: pointer"
            >▼
          </span>
          <span
            v-if="pet.show == false"
            style="margin: 1.1vh; font-size: 2vh; cursor: pointer"
            >▷
          </span>
          {{ pet.name }}
        </div>
      </div>
      <ul v-show="pet.show" class="todo-ul">
        <li
          v-for="event in events.filter((x) => x.petId === pet.petId)"
          :key="event.scheduleid"
          v-show="
            new Date(event.startDatea) <= new Date(tododate) &&
            new Date(tododate) <= new Date(event.endDatea)
          "
          class="todo-li row"
        >
          <div class="todo-span todo-span-ckbox col-1">
            <input
              type="checkbox"
              v-model="event.completed"
              class="checkbox1 form-check-input"
              @click="completeTodo(event.scheduleId, !event.completed)"
            />
          </div>

          <span
            :id="event.scheduleId"
            @click="goToDetail(event.scheduleId)"
            class="aaa col-11"
          >
            <!-- <div class="todo-span no-padding"> -->
            <div class="row">
              <div
                class="font-medium font-lefts col-7 title-todo"
                text-
                :style="event.completed ? 'text-decoration: line-through' : ''"
              >
                {{ event.title }}
              </div>
              <div class="col-5">
                <div
                  v-if="!event.completed"
                  class="font-mini short-div font-lefts"
                >
                  담당: {{ event.whoPlanned }}
                </div>
                <div
                  v-if="event.completed"
                  class="font-mini short-div font-lefts"
                >
                  완료: {{ event.whoCompleted }}
                </div>
                <div
                  class="font-mini font-lefts short-div justify-content-center"
                >
                  <img
                    src="@/assets/timer.png"
                    alt="시계 아이콘"
                    class="logo-timer"
                  />
                  {{ event.startTime }}
                </div>
              </div>
            </div>
            <!-- <span class="todo-span-pet">
              <div class="circle-test" :style={background:pet.color} ></div>
            </span> -->
            <!-- </div> -->
          </span>
          <br />
          <br />
          <hr />
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import DetailTodoView from "@/views/mainpage/DetailTodoView.vue"

// import events from "./event.json"
// import petidlists from "./petidlist.json"

export default {
  name: "TodoListItem",
  props: {
    tododate: String,
    nickName: String,
    calendarId: String,
    events: Object,
    pets: Object,
  },
  data() {
    return {
      // events: events,
      // pets: this.$store.state.pets,
      showlist: [],
      // circlecolor: { backgroundColor: this.pet.color },
    }
  },
  methods: {
    // over: function(){
    //   this.circlecolor.backgroundColor=this.pet.color;
    // },
    detailSchedule(scheduleId) {
      this.$router.push({ name: DetailTodoView, params: { id: scheduleId } })
    },
    showRemove(petId) {
      this.showlist.remove(petId)
    },
    showAdd(petId) {
      this.showlist.push(petId)
    },
    goToDetail(scheduleId) {
      console.log(scheduleId)
      // console.log(a.event._def.publicId)
      this.$router.push({
        name: DetailTodoView,
        params: { id: scheduleId },
      })
    },
    completeTodo(scheduleId, completed) {
      const nowDate = new Date()
      let now =
        nowDate.getFullYear() +
        (nowDate.getMonth() + 1 <= 9
          ? "0" + (nowDate.getMonth() + 1)
          : nowDate.getMonth() + 1) +
        (nowDate.getDate() <= 9 ? "0" + nowDate.getDate() : nowDate.getDate()) +
        (nowDate.getHours() <= 9
          ? "0" + nowDate.getHours()
          : nowDate.getHours()) +
        (nowDate.getMinutes() <= 9
          ? "0" + nowDate.getMinutes()
          : nowDate.getMinutes()) +
        (nowDate.getSeconds() <= 9
          ? "0" + nowDate.getSeconds()
          : nowDate.getSeconds())
      let whoCompleted
      if (this.events[0].whoCompleted != "") {
        whoCompleted = ""
      } else {
        whoCompleted = this.nickName
      }
      const payload = { scheduleId, completed, now, whoCompleted }
      console.log(payload)
      this.$store.dispatch("completeTodo", payload)
    },
  },
}
</script>

<style lang="scss">
div.pet-list-collection {
  background: rgba(128, 128, 128, 0.203);
  text-align: left;
  display: flex;
  /* justify-content: space-between !important; */
  cursor: pointer;

  border-top-color: black !important;
  border-top-width: 2px !important;
  border-top-style: solid !important;

  border-bottom-color: black !important;
  border-bottom-width: 2px !important;
  border-bottom-style: solid !important;
  font-size: $font-size-4;
  width: 100vw;
}

.todo-ul {
  list-style: none;
  padding: 0px !important;
  margin: 0px !important;
  justify-content: space-between;
  width: 100vw !important;
  border-bottom-color: rgba(0, 0, 0) !important;
  border-bottom-width: 0px !important;
  border-bottom-style: solid !important;
}

.todo-li {
  margin: auto !important;
  font-size: $font-size-5;
  text-align: left;
  height: 10vh !important;
  width: 100vw !important;
}

.checkbox1 {
  display: inline-block; /* 영역적용가능해짐 */
  width: auto;
  height: auto;
  border: 1px solid #000;
  background: white;
  box-sizing: border-box;
  border-radius: 50px !important; /* 모서리둥글게 처리 */
  position: relative;
  top: 3vh;
  cursor: pointer; /* 마우스 올렸을때 손모양 처리 */
  margin: 0px 0px 0px 10px;
  text-align: center;
}

.aaa {
  cursor: pointer;
}

.todo-span {
  // float: left;
  height: 6vh !important;
  // overflow-x: scroll !important;
  // overflow-y: hidden !important;
  // justify-content: ;
  text-align: center;
  margin-left: 0% !important;
  margin-right: 0% !important;
  // border: 0px solid #ffffff00;
}

.todo-span-ckbox {
  // margin: 15px;
  // margin-right: 3vw;
  padding: 0 !important;
  // text-align: center !important;
  justify-content: center !important;
}

.todo-span-pet {
  float: right;
  margin: 15px;
  margin-right: 1em;
}

.logo-timer {
  margin: 0;
  width: 2.2vh;
  height: 2.2vh;
  margin-top: 0.9vh;
}

.circle-test {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-top: 17px;
}

</style>
