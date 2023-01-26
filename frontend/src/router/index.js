import { createRouter, createWebHistory } from "vue-router"

// 연결할 각 컴포넌트 import (src/views폴더 아래 컴포넌트들 생성해둠)
import MainView from "@/views/mainpage/MainView.vue"
import AddTodoView from "@/views/mainpage/AddTodoView.vue"
import AlarmView from "@/views/mainpage/AlarmView.vue"
import DetailTodoView from "@/views/mainpage/DetailTodoView.vue"
import UpdateTodoView from "@/views/mainpage/UpdateTodoView.vue"
import PetProfileView from "@/views/petprofile/PetProfileView.vue"
import FinancialView from "@/views/financial/FinancialView.vue"
import PlusFinancialView from "@/views/financial/PlusFinancialView.vue"
import SnsView from "@/views/sns/SnsView.vue"
import DetailSnsView from "@/views/sns/DetailSnsView.vue"
import AlbumView from "@/views/album/AlbumView.vue"
import DetailAlbumView from "@/views/album/DetailAlbumView.vue"
import UserProfileView from "@/views/myprofile/UserProfileView.vue"
import AddPetView from "@/views/myprofile/AddPetView.vue"
import ChangePwView from "@/views/myprofile/ChangePwView.vue"
import HelpView from "@/views/myprofile/HelpView.vue"
import LinkView from "@/views/myprofile/LinkView.vue"
import PetListView from "@/views/myprofile/PetListView.vue"
import VersionView from "@/views/myprofile/VersionView.vue"
import LoginView from "@/views/accounts/LoginView.vue"
import SignUpView from "@/views/accounts/SignUpView.vue"
import AgreeView from "@/views/accounts/AgreeView.vue"
import FindIdView from "@/views/accounts/FindIdView.vue"
import FindPwView from "@/views/accounts/FindPwView.vue"
import PageNotFound from "@/views/errors/404View.vue"
import PetUpdateView from "@/views/petprofile/PetUpdateView.vue"


// 라우터 설계
const routes = [
  { path: "/mainpage", name: MainView, component: MainView },
  { path: "/calendar/add/:calendarid", name : AddTodoView, component: AddTodoView },
  { path: "/alarm", name : AlarmView, component: AlarmView },
  { path: "/todo/detail", name : DetailTodoView, component: DetailTodoView },
  { path: "/todo/update", name : UpdateTodoView, component: UpdateTodoView },
  { path: "/petprofile/:id", name : PetProfileView, component: PetProfileView },
  { path: "/financial", name : FinancialView, component: FinancialView },
  { path: "/financial/add", name : PlusFinancialView, component: PlusFinancialView },
  { path: "/sns", name : SnsView, component: SnsView },
  { path: "/sns/detail", name : DetailSnsView, component: DetailSnsView },
  { path: "/album", name : AlbumView, component: AlbumView },
  { path: "/album/detail", name : DetailAlbumView, component: DetailAlbumView },
  { path: "/userprofile", name : UserProfileView, component: UserProfileView },
  { path: "/pet/add", name : AddPetView, component: AddPetView },
  { path: "/myprofile/changepw", name : ChangePwView, component: ChangePwView },
  { path: "/help", name : HelpView, component: HelpView },
  { path: "/link", name : LinkView, component: LinkView },
  { path: "/pet/list", name : PetListView, component: PetListView },
  { path: "/version", name : VersionView, component: VersionView },
  { path: "/", name : LoginView, component: LoginView },
  { path: "/signup", name : SignUpView, component: SignUpView },
  { path: "/agree", name : AgreeView, component: AgreeView },
  { path: "/findid", name : FindIdView, component: FindIdView },
  { path: "/findpw", name : FindPwView, component: FindPwView },
  { path: "/404", name : PageNotFound, component: PageNotFound },

  { path: '/:pathMatch(.*)*', redirect: "/404"},
  { path: "/petupdate/:id", name : PetUpdateView, component: PetUpdateView },
  
]

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 라우터 추출 (main.js에서 import)
export default router;