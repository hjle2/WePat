<template>
   <div v-for="(album,index) in albums" :key="album.id" :album="album" class="album-float" :style="
                    index == 0 || (index-1 >= 0 && albums[index-1].date.slice(0,8) != albums[index].date.slice(0,8)) ? 'clear:both' : ''">
      <p class="psize1" v-if="index == 0 || (index-1 >= 0 && albums[index-1].date.slice(0,8) != albums[index].date.slice(0,8))">{{ album.date.slice(2,4) }}년 {{ album.date.slice(4,6) }}월  {{ album.date.slice(6,8) }}일</p>
      <p class="psize2" 
      :style="index-4 >= 0 && albums[index-4].date.slice(0,8) == albums[index].date.slice(0,8) ? 'display:none' : ''"
      v-if="index != 0 && (index-1 >= 0 && albums[index-1].date.slice(0,8) == albums[index].date.slice(0,8))"> &nbsp; </p>
      <AlbumListItem :album="album"/>
    </div>
</template>

<script>
import AlbumListItem from './AlbumListItem.vue';

export default {
  name: 'AlbumList',
  data() {
    return {
    }
  },
  props: {
    albums : Object,
  },
  created() {
  },
  computed: {
  },
  components: {
      AlbumListItem,
  },
  methods: {
  }
}
</script>

<style lang="scss">
.album-float {
  float: left;
  width: 22vw;
  margin-left: 1vw;
  margin-top: 1vw;
  margin-right: 1.5vw;
}

.album-jump {
  clear: both;
}

.psize1 {
  font-size: 2.8vh;
  height: 5vh;
  width: 100vw;
  border-bottom: 3px solid $color-main-50;
}

.psize2 {
  font-size: 6vw;
  height: 5vh;
}
</style>
