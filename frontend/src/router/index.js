import { createRouter, createWebHistory } from "vue-router"

// 연결할 각 컴포넌트 import (src/views폴더 아래 컴포넌트들 생성해둠)
import MainView from "@/views/mainpage/MainView.vue"
import DetailTodoView from "@/views/mainpage/DetailTodoView.vue"
import AddTodoView from "@/views/mainpage/AddTodoView.vue"
import UpdateTodoView from "@/views/mainpage/UpdateTodoView.vue"
import AlarmView from "@/views/mainpage/AlarmView.vue"

import FinancialView from "@/views/financial/FinancialView.vue"
import PlusFinancialView from "@/views/financial/PlusFinancialView.vue"
import FinanceDetailView from "@/views/financial/FinanceDetail.vue"

import SnsView from "@/views/sns/SnsView.vue"
import DetailSnsView from "@/views/sns/DetailSnsView.vue"

import AlbumView from "@/views/album/AlbumView.vue"
import DetailAlbumView from "@/views/album/DetailAlbumView.vue"

import PetListView from "@/views/pet/PetListView.vue"
import PetProfileView from "@/views/pet/PetProfileView.vue"
import AddPetView from "@/views/pet/AddPetView.vue"
import PetUpdateView from "@/views/pet/PetUpdateView.vue"

import UserProfileView from "@/views/myprofile/UserProfileView.vue"
import ChangePwView from "@/views/myprofile/ChangePwView.vue"
import ChangeNickView from "@/views/myprofile/ChangeNickView.vue"
import UserDetailView from "@/views/myprofile/UserDetailView.vue"
import LinkView from "@/views/myprofile/LinkView.vue"
import CalendarCreateView from "@/views/myprofile/CalendarCreateView.vue"

import LoadingView from "@/views/accounts/LoadingView.vue"
import LoginView from "@/views/accounts/LoginView.vue"
import SignUpView from "@/views/accounts/SignUpView.vue"
import AgreeView from "@/views/accounts/AgreeView.vue"
import FindIdView from "@/views/accounts/FindIdView.vue"
import FindPwView from "@/views/accounts/FindPwView.vue"

import HelpView from "@/views/myprofile/HelpView.vue"
import InviteView from "@/views/myprofile/InviteView.vue"
import VersionView from "@/views/myprofile/VersionView.vue"

import PageNotFound from "@/views/errors/404View.vue"
import aaa from "@/views/mainpage/a.vue"


// 라우터 설계
const routes = [
  { path: "/mainpage", name: MainView, component: MainView },
  { path: "/aaa", name: aaa, component: aaa },
  { path: "/mainpage/todo/detail/:id", name : DetailTodoView, component: DetailTodoView },
  { path: "/mainpage/todo/add/", name : AddTodoView, component: AddTodoView },
  { path: "/mainpage/todo/update/:id", name : UpdateTodoView, component: UpdateTodoView },
  { path: "/mainpage/alarm", name : AlarmView, component: AlarmView },

  { path: "/financial", name : FinancialView, component: FinancialView },
  { path: "/financial/add", name : PlusFinancialView, component: PlusFinancialView },
  { path: "/financial/detail/", name : FinanceDetailView, component: FinanceDetailView, props: true },
  
  { path: "/sns", name : SnsView, component: SnsView },
  { path: "/sns/detail/:id", name : DetailSnsView, component: DetailSnsView },
  
  { path: "/album", name : AlbumView, component: AlbumView },
  { path: "/album/detail/:id", name : DetailAlbumView, component: DetailAlbumView },
  
  
  { path: "/pet", name : PetListView, component: PetListView },
  { path: "/pet/detail/:id", name : PetProfileView, component: PetProfileView },
  { path: "/pet/add", name : AddPetView, component: AddPetView },
  { path: "/pet/update/:id", name : PetUpdateView, component: PetUpdateView },
  
  { path: "/user", name : UserProfileView, component: UserProfileView },
  { path: "/user/pw", name : ChangePwView, component: ChangePwView },
  { path: "/user/nick", name : ChangeNickView, component: ChangeNickView },
  { path: "/user/detail", name : UserDetailView, component: UserDetailView },
  { path: "/user/link", name : LinkView, component: LinkView },
  { path: "/user/new" , name : CalendarCreateView, component: CalendarCreateView },
  
  { path: "/loading", name : LoadingView, component: LoadingView },
  { path: "/", name : LoginView, component: LoginView },
  { path: "/signup", name : SignUpView, component: SignUpView },
  { path: "/agree", name : AgreeView, component: AgreeView },
  { path: "/findid", name : FindIdView, component: FindIdView },
  { path: "/findpw", name : FindPwView, component: FindPwView },

  { path: "/help", name : HelpView, component: HelpView },
  { path: "/invite", name : InviteView, component: InviteView },
  { path: "/version", name : VersionView, component: VersionView },
  
  { path: "/404", name : PageNotFound, component: PageNotFound },
  { path: '/:pathMatch(.*)*', redirect: "/404"},
]

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 라우터 추출 (main.js에서 import)
export default router;