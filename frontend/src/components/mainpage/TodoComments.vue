<template>
  <div class="row" style="margin: 0">

    <div style="width: 50%; text-align: center">
      <hr />
      <div
        v-for="comment in comments"
        :key="comment.id"
        style="display: flex; justify-content: space-between"
      >
        <!-- {{ comment.user_region }} -->
        <!-- {{ comment.user_gender }} -->
        {{ comment.user_nickname }}[{{ comment.user_gender }}]:
        {{ comment.content }}
        <a
          style="margin-left: 0%; width: 60px"
          @click="deletereview(comment.id)"
          v-if="comment.user == $store.state.user_id"
        >
          삭제
        </a>
        <hr />
      </div>
    </div>
    <div style="width: 50%; text-align: center; padding-right: 0%">
      <input
        v-model="newcomment"
        cols="50"
        rows="2"
        placeholder="댓글을 입력하세요"
        @keyup.enter="createComment"
      />
      <span><button @click="createComment">작성</button></span>
    </div>
  </div>
</template>

<script>
import axios from "axios"

const API_URL = "https://i8a607.p.ssafy.io/api"
// const API_URL = "http://70.12.247.102:8080/api"
// const API_URL = 'http://192.168.100.12:8080/api'
// const API_URL = "http://i8a607.p.ssafy.io:8080/api"
// const API_URL = "http://192.168.59.51:8080/api"
// const API_URL = "http://70.12.247.105:8080"
// const API_URL = "http://70.12.247.136:8080"
// const API_URL = "http://3.35.205.124:8080"

export default {
  name: "TodoComments",
  // props: {
  //   movie_id: Object,
  // },
  data() {
    return {
      comments: [],
      newcomment: null,
      comments_id: null,
    }
  },
  computed: {
    // token() {
    //   return this.$store.state.token
    // },
  },
  methods: {
    getTodoComment() {
      axios({
        method: "get",
        url: `${API_URL}//${this.id}/`,
        // headers: {
        //   //   Authorization: `Token 953478ef404ae03d3fbc263ae258d254d310b1fc`,
        //   Authorization: `Token ${this.token}`,
        // },
      })
        .then((res) => {
          console.log(res.data)
          // this.comments = res.data
        })
        .catch((err) => console.log(err))
    },
    deletereview(e) {
      axios({
        method: "delete",
        url: `${API_URL}/`,
        // headers: {
        //   //   Authorization: `Token 953478ef404ae03d3fbc263ae258d254d310b1fc`,
        //   Authorization: `Token ${this.token}`,
        // },
      })
        .then((res) => {
          console.log(res.data)
          // this.users = res.data
          this.getTodoComment()
        })
        .catch((err) => console.log(err))
    },
    createComment() {
      const content = this.newcomment
      axios({
        method: "post",
        url: `${API_URL}/`,
        // headers: {
        //   //   Authorization: `Token 953478ef404ae03d3fbc263ae258d254d310b1fc`,
        //   Authorization: `Token ${this.token}`,
        // },
        data: {
          content: content,
        },
      })
        .then((res) => {
          console.log(res.data)
          this.getTodoComment()
          this.newcomment = null
        })
        .catch((err) => console.log(err))
    },
  },
  created() {
    this.getTodoComment()
  },
}
</script>

<style>

</style>
