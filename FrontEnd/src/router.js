import Vue from "vue";
import Router from "vue-router";
import Index from "./pages/Index.vue";
import HouseDeal from "./pages/HouseDeal.vue";
import Login from "./pages/Login.vue";
import Profile from "./pages/Profile.vue";
import Board from "./pages/Board.vue";
import Notice from "./pages/Notice.vue";
// import Join from "./pages/Join.vue";
import Modify from "./pages/components/user/Modify.vue";
import PasswordModify from "./pages/components/user/PasswordModify.vue";
import Register from "./pages/components/user/Register.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";

Vue.use(Router);

export default new Router({
  linkExactActiveClass: "active",
  mode: "history",
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/housedeal",
      name: "housedeal",
      components: {
        default: HouseDeal,
        header: MainNavbar,
      },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/register",
      name: "register",
      components: { default: Register, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/passmodify",
      name: "passmodify",
      components: { default: PasswordModify, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/profile",
      name: "profile",
      components: { default: Profile, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/profilemodify",
      name: "profilemodify",
      components: {
        default: Modify,
        header: MainNavbar,
      },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/board",
      name: "board",
      components: { default: Board, header: MainNavbar },
      redirect: "/board/list",
      props: {
        header: { colorOnScroll: 400 },
      },
      children: [
        {
          path: "list",
          name: "boardlist",
          component: () => import("@/pages/components/board/BoardList"),
        },
        {
          path: "write",
          // path: "/write",
          name: "boardwrite",
          component: () => import("@/pages/components/board/BoardWrite"),
        },
        {
          path: "view/:articleNo",
          name: "boardview",
          component: () => import("@/pages/components/board/BoardView"),
        },
        {
          path: "modify",
          name: "boardmodify",
          component: () => import("@/pages/components/board/BoardModify"),
        },
        {
          path: "delete/:articleNo",
          name: "boarddelete",
          component: () => import("@/pages/components/board/BoardDelete"),
        },
      ],
    },
    {
      path: "/notice",
      name: "notice",
      components: { default: Notice, header: MainNavbar, footer: MainFooter },
      redirect: "/notice/list",
      props: {
        header: { colorOnScroll: 400 },
      },
      children: [
        {
          path: "list",
          name: "noticelist",
          component: () => import("@/pages/components/notice/NoticeList"),
        },
        {
          path: "write",
          name: "noticewrite",
          component: () => import("@/pages/components/notice/NoticeWrite"),
        },
        {
          path: "view/:articleno",
          name: "noticeview",
          component: () => import("@/pages/components/notice/NoticeView"),
        },
        {
          path: "modify",
          name: "noticemodify",
          component: () => import("@/pages/components/notice/NoticeModify"),
        },
        {
          path: "delete/:articleno",
          name: "noticedelete",
          component: () => import("@/pages/components/notice/NoticeDelete"),
        },
      ],
    },
  ],
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  },
});
