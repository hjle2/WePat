<!-- <template>
      <img class="profile-image-small" :id="'a'+pet.photoId"/>
</template>

<script>
import { initializeApp } from "firebase/app";
import { getStorage, ref, getDownloadURL} from "firebase/storage"

export default {
  name: 'AlarmItemImg',
  data() {
    return {
    }
  },
   created() {
       this.download()
  },
  props: {
    pet : Object,
  },
  methods: {
    download() {
      const firebaseConfig = {
                apiKey: "AIzaSyDBscWBxZ3rgirNFIu2HYByRSLX2gsrn2A",
                authDomain: "a607-445bc.firebaseapp.com",
                projectId: "a607-445bc",
                storageBucket: "a607-445bc.appspot.com",
                messagingSenderId: "762555697079",
                appId: "1:762555697079:web:b8d58a52f5c8668ab20a32",
                measurementId: "G-NQ1EKQ108D"
          };
          const app = initializeApp(firebaseConfig);
          const storage = getStorage(app);
          // Your web app's Firebase configuration
          // For Firebase JS SDK v7.20.0 and later, measurementId is optional
          // Initialize Firebase
          const storageRef = ref(storage, `${this.pet.photoUrl}`);

          getDownloadURL(storageRef)
          .then((url) => {
              document.querySelector(`#a${this.pet.photoId}`).src = url;
              const profile = document.querySelector(`#a${this.pet.photoId}`)
              profile.style.border = `${this.pet.color} solid 0.3vh`;
      });
    },
  }
  }
</script>

<style lang="scss">

</style> -->