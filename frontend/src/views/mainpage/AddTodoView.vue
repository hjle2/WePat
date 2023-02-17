<template>
  <div class="holder-vanilla-todo font-lefts">
    <router-link to="/mainpage">
      <img src="@/assets/back.png" class="logo-back" alt="" /> </router-link
    ><br />
    <div class="input-relative">
      <span class="font-medium font-lefts">카테고리</span>
      <select
        class="input-category input font-medium"
        name="category"
        id="category"
        v-model="category"
      >
        <option class="font-me5ium" value="0" selected disabled hidden>
          선택해주세요.
        </option>
        <option class="font-medium" value="1">미용</option>
        <option class="font-medium" value="2">병원</option>
        <option class="font-medium" value="3">산책</option>
        <option class="font-medium" value="4">기타</option>
      </select>
    </div>
    <br />
    <div class="input-relative">
      <span class="font-medium font-lefts">일정명</span>
      <input
        class="input-category font-medium input"
        v-model="title"
        name="title"
        type="text"
        placeholder="이름을 입력하세요"
        id="title"
      />
    </div>
    <br />
    <div class="input-relative">
      <span class="font-medium font-lefts">대상</span>
      <select
        class="input-category input font-medium"
        name="petId"
        id="petId"
        v-model="petId"
      >
        <option class="font-medium" value="0" selected disabled hidden>
          선택해주세요.
        </option>
        <option
          class="font-medium"
          v-for="pet in pets"
          :value="pet.petId"
          v-bind:key="pet.name"
        >
          {{ pet.name }}
        </option>
      </select>
    </div>
    <br />
    <!-- {{ members }} -->
    <div class="input-relative">
      <span class="font-medium font-lefts">담당자 </span>
      <select
        class="input-category input font-medium"
        name="whoPlanned"
        id="whoPlanned"
        v-model="whoPlanned"
      >
        <option
          class="font-small"
          v-for="member in members"
          :value="member"
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
    <div class="input-relative-time">
      <span class="font-medium"> 시작 </span>
      <input
        class="input-category input input-day font-lefts font-small"
        type="date"
        id="startDate"
        v-model="startDate"
        max="endDate"
      />
      <input
        class="input-category input font-small input-time"
        type="time"
        id="startTime"
        v-model="now"
      />
    </div>
    <br />
    <div class="input-relative-time">
      <span class="font-medium"> 마침</span>
      <input
        class="input-category input input-day font-lefts font-small"
        type="date"
        id="endDate"
        v-model="endDate"
        min="startDate"
      />
      <input
        class="input-category input font-small input-time"
        type="time"
        id="endTime"
        v-model="after"
      />
    </div>
    <br />
    <div class="input-relative">
      <span class="font-medium"> 반복 </span>
      <input
        v-show="repeatUnit != 0"
        type="number"
        class="input input-cycle font-medium"
        name="repeatAmount"
        id="repeatAmount"
        v-model="repeatAmount"
        min="1"
        max="30"
      />
      <select
        class="input-category input font-medium"
        name="repeatUnit"
        id="repeatUnit"
        v-model="repeatUnit"
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
    <div v-show="repeatUnit != 0" class="input-relative-time">
      <span class="font-medium">기한</span>
      <input
        type="date"
        class="input-category input input-day font-lefts font-small"
        id="repeatEndDate"
        v-model="repeatEndDate"
        min="endDate"
      />
      <!-- <input
        type="time"
        class="input-category input font-small input-time"
        id="repeatEndTime"
        v-model="repeatEndTime"
      /> -->
    </div>
    <br />

    <div class="holder-category">
      <p class="font-lefts"></p>
    </div>
    <br />
    <div class="input-relative">
      <span class="font-medium font-lefts">알림 </span>
      <select
        class="input-category input font-medium"
        name="alarm"
        id="alarm"
        v-model="alarm"
      >
        <option class="font-medium" value="" selected disabled hidden>
          선택해주세요.
        </option>
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
      <span class="font-medium font-lefts">캘린더</span>
      <select
        class="input-category input font-medium"
        name="display"
        id="display"
        v-model="display"
      >
        <option class="font-medium" value="" selected disabled hidden>
          선택해주세요.
        </option>
        <option class="font-medium" value="true">표시</option>
        <option class="font-medium" value="none">표시안함</option>
      </select>
    </div>
    <br />

    <div class="">
      <span class="font-medium font-lefts">메모</span>
      <textarea
        class="holder-white-mini"
        name="memo"
        id="memo"
        v-model="memo"
      ></textarea>
    </div>

    <div class="">
      <button class="btn-colored btn-medium btn-Todo" @click="addCalTodo">
        추가하기
      </button>
    </div>
    <br />
  </div>
</template>

<script>
// import MainView from "@/views/mainpage/MainView.vue"
// import router from "@/router"

export default {
  name: "AddTodoView",
  components: {},
  data() {
    return {
      members: [],
      pets: [],
      title: null,
      category: 0,
      petId: 0,
      startDate: null,
      startTime: null,
      endDate: null,
      endTime: null,
      start: null,
      end: null,
      repeatUnit: "0",
      repeatAmount: 1,
      repeatEndDate: null,
      repeatEndTime: "23:59",
      repeatEnd: null,
      alarm: 0,
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
    this.getUserData()
    console.log(this.$route.params)
    // this.addCalTodo()
    this.time()
    this.startDate = this.$route.params.start
    this.startTime = this.now
    this.endDate = this.$route.params.end
    this.endTime = this.after
    this.repeatEndDate = this.$route.params.end
    // this.repeatEndTime = this.after
    console.log(this.$data)
    console.log(this.now)
    console.log(this.after)
  },
  methods: {
    getPet() {
      const dataSet = this.$store.dispatch("getPet2")
      dataSet.then((getData) => {
        console.log("받은 데이터" + getData)
        this.pets = getData
      })
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
        console.log("++++++++++++++++++++")
        console.log(this.userData)
        this.whoPlanned = this.userData.nickName
        console.log(this.whoPlanned)
      })
    },
    addCalTodo() {
      let startdd = this.startDate + this.startTime + "00"
      let startd = startdd.replace(/-/g, "")
      let start = startd.replace(/:/g, "")
      let nowDated = startd.replace(/:/g, "")
      let enddd = this.endDate + this.endTime + "00"
      let endd = enddd.replace(/-/g, "")
      let end = endd.replace(/:/g, "")
      let repeatEnddd = this.repeatEndDate + this.repeatEndTime + "00"
      let repeatEndd = repeatEnddd.replace(/-/g, "")
      let repeatEnd = repeatEndd.replace(/:/g, "")
      console.log(this.repeatEndDate)
      console.log(repeatEnd)

      console.log(startdd)
      console.log(nowDated + "1df23qrweasdfr3q2rweabttq42war")
      const title = this.title
      const category = this.category
      const petId = this.petId
      const startDate = start
      const endDate = end
      const nowDate = nowDated
      const repeatUnit = this.repeatUnit
      const repeatAmount = this.repeatAmount
      const repeatEndDate = repeatEnd
      const alarm = this.alarm
      const display = this.display
      const memo = this.memo
      const whoPlanned = this.whoPlanned

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
      }
      console.log(payload)
      this.$store.dispatch("addCalTodo", payload)
    },
    time() {
      var date = new Date()
      this.nowdate = date
      this.now =
        (date.getHours() <= 9 ? "0" + date.getHours() : date.getHours()) +
        ":" +
        (date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes())
      this.after =
        (date.getHours() + 1 < 9
          ? "0" + (date.getHours() + 1)
          : date.getHours() + 1 == 24
          ? "00"
          : date.getHours() + 1) +
        ":" +
        (date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes())
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
.input-time {
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
