import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import { recomended_movies, recomended_users, friends_users } from "./data";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    isLogged: false,
    friends_user: [...friends_users],
    movies_user: [],
    search_movies: [],
    recommended_movies: [...recomended_movies],
    search_users: [...recomended_users],
    recommended_users: [...recomended_users],
  },
  mutations: {
    FETCH_USER(state, user) {
      state.user = user;
    },
    TOGGLE_LOGGED(state) {
      state.isLogged = true;
    },
    LOGOUT(state) {
      state.isLogged = false;
    },
    search_movies(state, movies_list) {
      state.search_movies = movies_list;
    },
    movies_user(state, movies_list) {
      state.movies_user = movies_list;
    },
  },
  actions: {
    fetchUser({ commit }, data) {
      commit("FETCH_USER", data);
    },
    toggleLogged({ commit }) {
      commit("TOGGLE_LOGGED");
    },
    async logout({ commit }) {
      const res = await axios.get("http://localhost:8080/api/logout", {
        headers: { Token: this.state.user.token },
      });
      if (res.status === 200) {
        sessionStorage.removeItem("token");
      }
      commit("LOGOUT");
    },
    get_search_movies(context, movie_name) {
      movie_name = movie_name.replace(" ", "_");
      axios({
        method: "get",
        url: `http://localhost:8080/api/movie/search?title=${movie_name}`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        context.commit("search_movies", response.data);
      });
    },
    get_user_movies(context) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/watchlist`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        context.commit("movies_user", response.data);
      });
    },
  },
  modules: {},
});
