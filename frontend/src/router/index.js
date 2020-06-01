import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import(/* webpackChunkName: "Home" */ "../views/Home.vue"),
  },
  {
    path: "/profile",
    name: "Profile",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Profile.vue"),
  },
  {
    path: "/movie/:movie_name",
    name: "Movie",
    component: () =>
      import(/* webpackChunkName: "movie" */ "../views/MovieDetail.vue"),
  },
  {
    path: "/user/:user_name",
    name: "User",
    component: () =>
      import(/* webpackChunkName: "user" */ "../views/UserDetail.vue"),
  },
  {
    path: "/search-movie",
    name: "SearchMovie",
    component: () =>
      import(/* webpackChunkName: "searchMovie" */ "../views/MoviesList.vue"),
  },
  {
    path: "/search-user",
    name: "SearchUser",
    component: () =>
      import(/* webpackChunkName: "searchuSER" */ "../views/UserList.vue"),
  },
  {
    path: "*",
    name: "404",
    component: () => import(/* webpackChunkName: "404" */ "../views/404.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
