<template>
  <div class="div-album">
    <img :src='album.photoUrl' @click="albumDetail(album.photoId)" class="img-album"/>
  </div>
</template>

<script>
import DetailAlbumView from '@/views/album/DetailAlbumView.vue';

export default {
  name: 'AlbumListItem',
  data() {
    return {
    }
  },
   created() {
      },
      props: {
        album : Object,
      },
      methods: {
        albumDetail(photoId) {
          this.$router.push({name:DetailAlbumView, params:{id:photoId}})
        },
  }
}
</script>

<style>
.div-album {
  height: 15vh;
  width: 24vw;
  border: 1px solid #ccc;
  border-radius: 30px;
}
.img-album {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 30px;
}
</style>