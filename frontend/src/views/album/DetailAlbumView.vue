<template>
<div class="holder-vanilla-todo">
  <router-link to="/album">
      <img src="@/assets/back.png" class="logo-back" alt="" />
  </router-link><br>
  <div class="">
    <img :src='this.album.photoUrl' class="album-photo"/>
  </div>
  <div class="comment-div">
      <div class="comment-input-position">
        <input class="comment-input"
        type="text"
        @input="changeKeyword2"
        @submit.prevent="createAlbumComment"
        placeholder="댓글을 작성해주세요"
        v-model.trim="content">
        <button class="btn-colored-no-shadow btn-comment font-large" type="submit" v-if="this.content" @click="createAlbumComment()">↵</button>
        <button class="btn-disabled-no-shadow btn-comment font-large" type="submit" v-if="!this.content">↵</button>
      </div>
      <div v-for="comment in this.album.commentList"
        :key="comment.commentid"
        :comment="comment"> 
        <!-- <div class="holder-category">
        </div> -->
        <div class="comment_in">
          <img class="profile-image-small comment_sort_left" :src="comment.photoUrl" id="myimg" />
          <span class="span-size comment_sort_left2">{{ comment.writter }} {{ comment.timeName }}</span>
          <img class="delete-img comment_sort_right" src="@/assets/delete.png" alt="" @click="deleteAlbumComment(comment.commentId)" v-if="comment.commentId != this.updateAlbumCommentId && this.userMemberId == comment.memberId" />
          <img class="update-img comment_sort_right" src="@/assets/update.png" alt="" @click="goflag(comment.content, comment.commentId)" v-if="comment.commentId != this.updateAlbumCommentId && this.userMemberId == comment.memberId" />
        </div>
        <div class="comment_in2 comment_sort_left">
          <span class="span-size comment_sort_left3" v-if="comment.commentId != this.updateAlbumCommentId">{{ comment.content }}</span>
          <input class="underline" type="text" v-model="updatecontent" @input="changeKeyword" v-if="comment.commentId == this.updateAlbumCommentId">
          <img class="updateok-img" src="@/assets/update_ok.png" alt=""  v-if="this.updatecontent==this.checkcomment && comment.commentId == this.updateAlbumCommentId && this.userMemberId == comment.memberId" />
          <img class="updateok-img" src="@/assets/update_ok_ok.png" alt="" @click="updateAlbumComment(comment.commentId)" v-if="this.updatecontent!=this.checkcomment && comment.commentId == this.updateAlbumCommentId && this.userMemberId == comment.memberId" />
        </div>
        <br>
        <br>
      </div>
    </div>
  <div>
    <img src="@/assets/trash.png" alt="" class="trash-img" @click="deleteAlbum()" />
    <img src="@/assets/goSns.png" alt="" class="sns-img" @click="goSNS()" v-if="!album.sns" />
    <img src="@/assets/stopSns.png" alt="" class="sns-img" @click="stopSNS()" v-if="album.sns" />
  </div>
</div>
</template>

<script>
import Swal from "sweetalert2"

// import BottomNavbar4 from "@/components/common/BottomNavbar4.vue"

export default {
  name: 'DetailAlbumView',
  data() {
    return {
      date : "",
      content : "",
      nickName: "",
      updatecontent: "",
      updateAlbumCommentId:"",
      userMemberId: "",
      checkcomment: "",
      photoUrl:"",
      album:""
    }
  },
  components: {
    // BottomNavbar4
  },
  computed: {
    album1() {
      return this.$store.state.album
    }
  },
  created() {
    let today = new Date();   
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let hours = today.getHours();
    let minutes = today.getMinutes();
    let seconds = today.getSeconds();    
    this.date = (year + (month < 9 ? "0" + month : month) + (date < 9 ? "0" + date : date) + (hours < 9 ? "0" + hours : hours) + (minutes < 9 ? "0" + minutes : minutes) +(seconds < 9 ? "0" + seconds : seconds) )
    this.detailAlbum()
    this.getUserData()
  },
  methods: {
    getTime() {
      console.log(this)
      const now = new Date()
      this.album.commentList.map((obj) => {
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
    changeKeyword(e) {
      this.updatecontent = e.target.value
    },
    changeKeyword2(e) {
      this.content = e.target.value
    },
    getUserData() {
      const dataSet = this.$store.dispatch('getUserData')
      dataSet.then((getData) => {
        this.userMemberId = getData.memberId
        this.photoUrl = getData.photoUrl
        console.log("데이터 가져옴")
        return getData.memberId
      })
    },
    goflag(content, commentId) {
      this.updatecontent = content
      this.checkcomment = content
      this.updateAlbumCommentId = commentId
    },
    // getUserData() {
    //   const dataSet = this.$store.dispatch('getUserData')      
    //   dataSet.then((getData) => {
    //     this.nickName = getData.nickName
    //     this.calendarId = getData.calendarId
    //       return getData.calendarId
    //   })
    // },

    createAlbumComment() {
      const content = this.content
      const date = this.date
      const photoId = this.$route.params.id
      const photoUrl = this.photoUrl
      this.content = ""

      const payload = {
        content,
        date,
        photoId,
        photoUrl
      }
      const dataSet = this.$store.dispatch('createAlbumComment', payload)
      dataSet.then((getData) => {
        this.album = getData
        this.getTime()
        return getData
      })
  
    },
    deleteAlbumComment(commentId) {
      const photoId = this.$route.params.id
      const payload = {
        commentId,
        photoId
      }
      const dataSet = this.$store.dispatch('deleteAlbumComment', payload)
      dataSet.then((getData) => {
        this.album = getData
        this.getTime()
        return getData
      })
    },

    updateAlbumComment(commentId) {
      const content = this.updatecontent
      const photoId = this.$route.params.id
      const date = this.date
      const payload = {
        content,
        photoId,
        commentId,
        date
      }
      const dataSet = this.$store.dispatch('updateAlbumComment', payload)
      dataSet.then((getData) => {
        this.album = getData
        this.getTime()
        return getData
      })
      this.updatecontent = ""
      this.updateAlbumCommentId =""
    },
    detailAlbum() {
      this.$store.dispatch('detailAlbum', this.$route.params.id)
      const dataSet = this.$store.dispatch('detailAlbum2', this.$route.params.id)
      dataSet.then((getData) => {
        this.album = getData
        this.getTime()
        return getData
      })
    },
    deleteAlbum() {
      Swal.fire({
            title: "사진 삭제",
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
              this.$store.dispatch('deleteAlbum', this.$route.params.id)
            }
          })
    },
    goSNS() {
      Swal.fire({
            title: "등록 완료",
            text: "사진이 SNS에 등록되었습니다.",
            icon: "success",

            // showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            // cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "확인", // confirm 버튼 텍스트 지정
            // cancelButtonText: "아니오", // cancel 버튼 텍스트 지정
            // reverseButtons: true, // 버튼 순서 거꾸로
            timer: 1000,
            timerProgressBar: true,
          })
      const photoId = this.$route.params.id
      const snsDate = this.date
      const payload = {
        photoId,
        snsDate
      }
      this.album.sns = true
      this.$store.dispatch('goSNS', payload)
    },
    stopSNS() {
      Swal.fire({
            title: "취소 완료",
            text: "사진의 SNS 등록이 취소되었습니다.",
            icon: "success",

            // showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            // cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "확인", // confirm 버튼 텍스트 지정
            // cancelButtonText: "아니오", // cancel 버튼 텍스트 지정
            // reverseButtons: true, // 버튼 순서 거꾸로
            timer: 1000,
            timerProgressBar: true,
          })
      const photoId = this.$route.params.id
      this.album.sns = false
      this.$store.dispatch('stopSNS', photoId)
    },

}
}
</script>

<style lang="scss">
.album-photo {
  width: 80vw !important;
  height: auto;
}
.album-margin {
  position: absolute;
  top: 50px;
  left: 65px;
}
.span-size {
  font-size: $font-size-4;
}
.sns-img {
  width: 40px;
  position: absolute;
  // top: 1%;
  right: 30%;
  top: 30px;
  // left: 15px;
}
.update-img {
  width: 40px;
}
.updateok-img{
  width: 60px;  
}
.delete-img {
  width: 40px;
}
.trash-img {
  width: 40px;
  position: absolute;
  // top: 1%;
  right: 9%;
  top: 30px;
  // left: 15px;
}

.comment-div {
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
    padding-top: 8vh !important;
    // z-index: 0 !important;
    display: block;
    position: relative;
}

.comment-div::-webkit-scrollbar {
  width: 10px;
  background-color: black;
}

.comment-div::-webkit-scrollbar-thumb {
  background-color: #fadc80;
}

.comment-div::-webkit-scrollbar-track {
  background-color: white;
}


.detail_album_color {
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
  border: 0.5vh solid $color-main-75 !important;
}
.comment-input {
  float: left;
  font-size: $font-size-4;
  outline: none;
  background-color: rgb(238, 213, 184);
  width: 70vw;
  height: 100%;
  border-radius: 0 !important;
  border: 5px solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
  z-index: 2 !important;
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
</style>