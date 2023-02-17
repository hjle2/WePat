<template>
  <div class="holder-vanilla-todo font-lefts">
    <router-link to="/mainpage">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link><br>
    <div
      class=""
      v-for="pet in pets.filter((x) => x.petId === event[0].petId)"
      :key="pet.petId"
    >
      <p class="font-medium">대상 : {{ pet.name }}</p>
      <p class="font-medium">일정 : {{ event[0].title }}</p>

      <div class="holder-category">
        <p class="font-lefts"></p>
      </div>
      <p class="font-small">시작 시간 : {{ event[0].start }}</p>
      <p class="font-small">마침 시간 : {{ event[0].end }}</p>
      <div class="holder-category"></div>

      <span class="font-small">등록 : {{ event[0].whoPlanned }}</span>
      <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <span class="font-small" v-if="event[0].whoCompleted">완료 : {{ event[0].whoCompleted }}</span>
      <img src="@/assets/goodjob.png" v-if="event[0].whoCompleted" class="logo-goodjob" alt="" />
      <div v-if="event[0].memo" class="holder-category"></div>
      <p v-if="event[0].memo" class="font-small">메모 : {{ event[0].memo }}</p>

    </div>
    <div class="holder-category"></div>
    <p v-if="!photoUrl" class="font-mini">
      아래 아이콘을 터치해 인증사진을 첨부하세요!
    </p>

    <div class="todo-img-container">
      <img
        v-if="!photoUrl"
        src="@/assets/todo_img.png"
        class="todo-no-img"
        @click="previewFiles()"
      />
      <img :src="photoUrl" class="todo-img" @click="previewFiles()" />
    </div>

    <!-- 모달 -->
    <!-- <div class="modal-bg" @click="popClose()"></div>
    <div class="modal-wrap">
      <input type="file" @change="previewFiles" multiple />
      <button
        class="btn-position btn-colored btn-medium col-4"
        @click="addScheduleImage()"
      >
        사진등록
      </button>
      <button
        class="btn-cancel-position btn-medium btn-colored col-4"
        @click="popClose()"
      >
        취소
      </button>
    </div> -->
    <div class="comment-div-Todo">
    <div class="comment-input-position">
      <input
        class="comment-input"
        type="text"
        @input="changeKeyword2"
        @submit.prevent="createTodoComment"
        placeholder="댓글을 작성해주세요"
        v-model.trim="content"
      />
      <button
        class="btn-colored-no-shadow btn-comment font-large"
        type="submit"
        v-if="this.content"
        @click="createTodoComment()"
      >
        ↵
      </button>
      <button class="btn-disabled-no-shadow btn-comment font-large" type="submit" v-if="!this.content">
        ↵
      </button>
    </div>
      <div
        v-for="comment in this.todo1.reviewList"
        :key="comment.commentid"
        :comment="comment"
      >
        <div class="comment_in">
          <img
            class="profile-image-small comment_sort_left"
            :src="comment.photoUrl"
          />
          <span class="span-size comment_sort_left2"
            >{{ comment.writter }} {{ comment.timeName }}</span
          >
          <img
            class="delete-img comment_sort_right"
            src="@/assets/delete.png"
            alt=""
            @click="deleteTodoComment(comment.commentId)"
            v-if="
              comment.commentId != this.updateTodoCommentId &&
              this.userMemberId == comment.memberId
            "
          />
          <img
            class="update-img comment_sort_right"
            src="@/assets/update.png"
            alt=""
            @click="goflag(comment.content, comment.commentId)"
            v-if="
              comment.commentId != this.updateTodoCommentId &&
              this.userMemberId == comment.memberId
            "
          />
        </div>
        <div class="comment_in2 comment_sort_left">
          <span
            class="span-size comment_sort_left3"
            v-if="comment.commentId != this.updateTodoCommentId"
            >{{ comment.content }}</span
          >
          <input
            class="underline"
            type="text"
            v-model="updatecontent"
            @input="changeKeyword"
            v-if="comment.commentId == this.updateTodoCommentId"
          />
          <img
            class="updateok-img"
            src="@/assets/update_ok.png"
            alt=""
            v-if="
              this.updatecontent == this.checkcomment &&
              comment.commentId == this.updateTodoCommentId &&
              this.userMemberId == comment.memberId
            "
          />
          <img
            class="updateok-img"
            src="@/assets/update_ok_ok.png"
            alt=""
            @click="updateTodoComment(comment.commentId)"
            v-if="
              this.updatecontent != this.checkcomment &&
              comment.commentId == this.updateTodoCommentId &&
              this.userMemberId == comment.memberId
            "
          />
        </div>
      </div>
    </div>
    
    <br />
    <div class="holder-Todo">
      <button class="btn-large btn-colored at-bottom" @click="changeTodo">수정</button>
      <!-- <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> -->
      <button class="btn-large btn-colored at-bottom" @click="deleteTodo">삭제</button>
    </div>
  </div>
</template>

<script>
import { initializeApp } from "firebase/app"
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage"
// import axios from 'axios';
// import event from "@/components/mainpage/event.json"
// import router from '@/router';
import UpdateTodoView from "./UpdateTodoView.vue"
import Swal from "sweetalert2"

export default {
  name: "DetailTodoView",
  components: {},
  data() {
    return {
      events: [],
      event: [],
      pets: [],
      // title: null,
      // pet: null,
      // start_date: null,
      // start_time: null,
      // end_date: null,
      // ebd_time: null,
      // whoPlanned: null,

      // repeat_cycle: null,
      // now: "00:00",
      photoUrl: "",
      img: "",
      date: "",
      content: "",
      nickName: "",
      updatecontent: "",
      updateTodoCommentId: "",
      userMemberId: "",
      checkcomment: "",
      photoUrl1: "",
      todo1:"",
    }
  },
  computed: {
    // todo1() {
    //   return this.$store.state.todo
    // },
  },
  // created(){
  // },
  created() {
    let today = new Date();   
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let hours = today.getHours();
    let minutes = today.getMinutes();
    let seconds = today.getSeconds();    
    this.date = (year + (month < 9 ? "0" + month : month) + (date < 9 ? "0" + date : date) + (hours < 9 ? "0" + hours : hours) + (minutes < 9 ? "0" + minutes : minutes) +(seconds < 9 ? "0" + seconds : seconds) )

    console.log(this.$route.params)
    this.getUserData()
    this.detailTodo()

    this.getEvent()
    this.getPet()
    this.getMember()
    // this.time()
    // this.start_date=this.$route.params.start
    // this.end_date=this.$route.params.end
    // console.log(this.$data)
    // console.log(this.now)
  },
  methods: {
    getTime() {
      console.log(this)
      const now = new Date()
      this.todo1.reviewList.map((obj) => {
        // console.log(obj.alarmTime)
        var aTime = new Date(
          obj.date.replace(
            /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
            "$1-$2-$3 $4:$5:$6"
          )
        )
        // console.log(aTime)
        // console.log(now + "@@@@@")
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
    async previewFiles() {
      // const { value: file } = 
      await Swal.fire({
        title: '이미지를 선택하세요!',
        input: 'file',
        inputAttributes: {
          'accept': 'image/*',
        },
        confirmButtonText: "업로드",
      }).then((res) => {
        if (res.value) {
          this.img = res.value
        console.log(this.img)
        this.addScheduleImage()
        }
      })
    },
    addScheduleImage() {
      const firebaseConfig = {
        apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
        authDomain: "a607-445bc.firebaseapp.com",
        projectId: "a607-445bc",
        storageBucket: "a607-445bc.appspot.com",
        messagingSenderId: "762555697079",
        appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
        measurementId: "G-NQ1EKQ108D",
      }
      const app = initializeApp(firebaseConfig)
      const storage = getStorage(app)
      const randomnum = Math.random()
      const storageRef = ref(storage, `/${randomnum}`)
      const file = this.img
      this.img = ""
      this.photoUrl = `gs://a607-445bc.appspot.com/${randomnum}`

      uploadBytes(storageRef, file).then((snapshot) => {
        this.downloadScheduleImage()
        console.log("Uploaded a blob or file!")
        console.log(snapshot)
      })
    },
    downloadScheduleImage() {
      // this.popClose()
      const firebaseConfig = {
        apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
        authDomain: "a607-445bc.firebaseapp.com",
        projectId: "a607-445bc",
        storageBucket: "a607-445bc.appspot.com",
        messagingSenderId: "762555697079",
        appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
        measurementId: "G-NQ1EKQ108D",
      }
      const app = initializeApp(firebaseConfig)
      const storage = getStorage(app)
      // Your web app's Firebase configuration
      // For Firebase JS SDK v7.20.0 and later, measurementId is optional
      // Initialize Firebase
      const storageRef = ref(storage, `${this.photoUrl}`)

      getDownloadURL(storageRef).then((url) => {
        this.photoUrl = url
        this.sendScheduleImage()
        Swal.fire ({
                // icon: "success",
                title: "업로드 완료!",
                imageUrl: this.photoUrl,
                // imageHeight: "30%",
                
              })
      })
    },
    sendScheduleImage() {
      const scheduleId = this.$route.params.id
      const photoUrl = this.photoUrl
      const date = this.date

      const payload = {
        scheduleId,
        photoUrl,
        date,
      }
      this.$store.dispatch("sendScheduleImage", payload)
      this.sendAlbum()
    },
    sendAlbum() {
      const photoUrl = this.photoUrl
      const date = this.date
      // this.photoUrl = ""
      const payload = {
        photoUrl,
        date,
      }
      this.$store.dispatch("sendAlbum", payload)
    },

    // popOpen() {
    //   document.getElementsByClassName("modal-wrap")[0].style.display = "block"
    //   document.getElementsByClassName("modal-bg")[0].style.display = "block"
    // },
    // popClose() {
    //   document.getElementsByClassName("modal-wrap")[0].style.display = "none"
    //   document.getElementsByClassName("modal-bg")[0].style.display = "none"
    // },
    changeKeyword(e) {
      this.updatecontent = e.target.value
    },
    changeKeyword2(e) {
      this.content = e.target.value
    },
    getUserData() {
      const dataSet = this.$store.dispatch("getUserData")
      dataSet.then((getData) => {
        this.userMemberId = getData.memberId
        this.photoUrl1 = getData.photoUrl
        console.log("데이터 가져옴")
        return getData.memberId
      })
    },
    goflag(content, commentId) {
      this.updatecontent = content
      this.checkcomment = content
      this.updateTodoCommentId = commentId
    },
    createTodoComment() {
      const content = this.content
      const date = this.date
      const scheduleId = this.$route.params.id
      const photoUrl = this.photoUrl1
      this.content = ""

      const payload = {
        content,
        date,
        scheduleId,
        photoUrl,
      }
      const dataSet = this.$store.dispatch('createTodoComment', payload)
      dataSet.then((getData) => {
        this.todo1 = getData
        this.getTime()
        return getData
      })
    },
    deleteTodoComment(commentId) {
      const scheduleId = this.$route.params.id
      const payload = {
        commentId,
        scheduleId,
      }
      const dataSet = this.$store.dispatch('deleteTodoComment', payload)
      dataSet.then((getData) => {
        this.todo1 = getData
        this.getTime()
        return getData
      })
    },
    updateTodoComment(commentId) {
      const content = this.updatecontent
      const scheduleId = this.$route.params.id
      const date = this.date
      const payload = {
        content,
        scheduleId,
        commentId,
        date,
      }
      const dataSet = this.$store.dispatch('updateTodoComment', payload)
      dataSet.then((getData) => {
        this.todo1 = getData
        this.getTime()
        return getData
      })
      this.updatecontent = ""
      this.updateTodoCommentId = ""
    },
    detailTodo() {
      const dataSet = this.$store.dispatch("detailTodo2", this.$route.params.id)
      dataSet.then((getData) => {
        this.todo1 = getData
        this.getTime()
        return getData
      })
    },
    getMember() {
      this.$store.dispatch("getMember", this.$route.params.id)
    },
    getEvent() {
      const dataSet = this.$store.dispatch("getEvent")
      dataSet.then((getEvents) => {
        console.log(this.events)
        this.events = getEvents
        this.event = this.events.filter(
          (x) => x.scheduleId == this.$route.params.id
        )
        dataSet.then(() => {
          this.time()
          this.photoUrl = this.event[0].photoUrl
        })
      })
    },
    getPet() {
      const dataSet = this.$store.dispatch("getPet2")
      dataSet.then((getData) => {
        console.log("받은 데이터" + getData)
        this.pets = getData
      })
    },
    time() {
      console.log(this.event)
      var startStr = this.event[0].startDate
      var endStr = this.event[0].endDate
      var start = startStr.replace(
        /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
        "$1-$2-$3 $4:$5:$6"
      )
      var end = endStr.replace(
        /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
        "$1-$2-$3 $4:$5:$6"
      )
      this.event[0].start = start
      this.event[0].end = end
    },
    deleteTodo() {
      Swal.fire({
            title: "일정 삭제",
            text: "정말로 삭제하시겠습니까?",
            icon: "warning",

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

            reverseButtons: true, // 버튼 순서 거꾸로
          }).then((result) => {
            if (result.isConfirmed) {
              this.$store.dispatch("deleteTodo", this.$route.params.id)
            }
          })
      // axios({
      //   method: 'post',
      //   url: `${API_URL}/schedule/delete/${this.$route.params.id}`,
      //   headers: {token: this.$store.state.accessToken},
      // })
      // .then( res => {
      //   console.log(res)
      //   this.todo_id = this.$route.params.id
      //   this.event = res.data
      // })
      // .catch( err => console.log(err))
    },
    changeTodo() {
      this.$router.push({
        name: UpdateTodoView,
        params: { id: this.event[0].scheduleId },
      })
    },
  },
}
</script>

<style lang="scss">
.todo-margin {
  position: absolute;
  top: 50px;
  left: 65px;
}
.span-size {
  font-size: $font-size-4;
}
.sns-img {
  width: 60px;
  position: absolute;
  bottom: 610px;
  right: 50px;
}
.update-img {
  width: 40px;
}
.updateok-img {
  width: 60px;
}
.delete-img {
  width: 40px;
}
.trash-img {
  width: 60px;
  position: absolute;
  bottom: 610px;
  right: 120px;
}
.logo-back2 {
  width: 2.4rem;
  height: 2rem;
  top: 3rem;
  left: 1.5rem;
  z-index: 1;
  position: fixed;
}
.logo-goodjob {
  width: 1000px;
  height: auto;
  top: -5vh !important;
  right: -40vw !important;
  // z-index: -1;
  opacity: 10%;
  position: fixed;
}
.comment-div-Todo {
    border: #e7ba66 solid 0.3vh;
    background: #ffffff;
    width: 80vw !important;
    height: 50vh;
    // bottom: 80px;
    left: 0rem !important;
    overflow-y: scoll !important;
    overflow-x: hidden !important;
    font-size: 3rem;
    // margin-top: 13.5vh;
    border-bottom-left-radius: 30px;
    // border-bottom-right-radius: 30px;
    padding: 3vw;
    padding-top: 7vh !important;
    // z-index: 0 !important;
    display: block;
    position: relative;
}

.comment-div-Todo::-webkit-scrollbar {
  width: 10px;
  background-color: black;
}

.comment-div-Todo::-webkit-scrollbar-thumb {
  background-color: #fadc80;
}

.comment-div-Todo::-webkit-scrollbar-track {
  background-color: white;
}

.holder-Todo {
  display: flex;
  justify-content: space-between;
}

.detail_todo_color {
  background: $color-main-12;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  // position: absolute;
}
.comment_sort_left {
  float:left;
}
.comment_sort_left2 {
  float:left;
  position: relative;
  left: 10px;
}
.comment_sort_left3 {
  float: left;
  white-space: wrap;
  margin-left: 40px;
  text-align: left;
}
.comment_sort_right {
  float: right;
}
.comment_in {
  position: relative;
  top: 10px;
  left: 10px;
  width: 100% !important;
}
.comment_in2 {
  min-width: 580px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.comment-input-position {
  width: 80vw;
  height: 7.2vh;
  // bottom: 0rem !important;
  // left: -0.2rem !important;
  position: absolute;  
  top: 0;
  left: -0.25vw !important;
  // display: block;
}

.btn-comment {
  height: 100%;
  width: 10vw;
  right: 0vw;
  position: absolute;
  border: 0.5vh solid $color-main-75 !important;
}
.comment-input {
  position: absolute;
  // float: left;
  left: 0vw;
  font-size: $font-size-4;
  // outline: none;
  background-color: rgb(238, 213, 184);
  width: 70vw;
  height: 100%;
  border-radius: 0 !important;
  border: 5px solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
  // z-index: 2 !important;
}
.underline {
  margin-top: 1vw;
  margin-left: 3vw;
  height: 6rem;
  border-left-width:0;
  border-right-width:0;
  border-top-width:0;
  border-bottom-width:1;
}
.todo-img-container {
  width: 80vw !important;
  height: auto;
  display: block;
}
.todo-img {
  width: 80vw !important;
  height: auto;
  display: block;
}

.todo-no-img {
  display: block;
  position: relative;
  width: 20vw !important;
  height: auto;
  left: 30vw;
  // display: flex !important;
  // justify-content: center !important;
}
</style>
