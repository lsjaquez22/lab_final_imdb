import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import(/* webpackChunkName: "Home" */ "../views/Home.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/profile",
    name: "Profile",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Profile.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/movie/:movie_name",
    name: "Movie",
    component: () =>
      import(/* webpackChunkName: "movie" */ "../views/MovieDetail.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/user/:user_name",
    name: "User",
    component: () =>
      import(/* webpackChunkName: "user" */ "../views/UserDetail.vue"),
    meta: { requiresAuth: true },
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
    path: "/login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "Login" */ "../views/Login.vue"),
  },
  {
    path: "/signup",
    name: "Signup",
    component: () =>
      import(/* webpackChunkName: "Signup" */ "../views/Signup.vue"),
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

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!sessionStorage.getItem("token")) {
      next({
        name: "Login",
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
