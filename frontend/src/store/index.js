import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import {} from "./data";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    isLogged: false,
    search_found: false,
    friends_user: [],
    movies_user: [],
    search_movies: [],
    recommended_movies: [],
    search_users: [],
    recommended_users: [],
    tempMovie: {},
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
      state.search_found = true;
      state.search_movies = movies_list;
    },
    search_users(state, users_list) {
      state.search_found = true;
      state.search_users = users_list;
    },
    movies_user(state, movies_list) {
      state.movies_user = movies_list;
    },
    friends_user(state, list_friends_users) {
      state.friends_user = list_friends_users;
    },
    recommended_users(state, list_recommended_users) {
      state.recommended_users = list_recommended_users;
    },
    recommended_movies(state, list_recommended_movies) {
      state.recommended_movies = list_recommended_movies;
    },
    not_found(state) {
      state.search_found = false;
    },
    FETCH_TEMP_MOVIE(state, data) {
      state.tempMovie = data;
    },
    UPDATE_MOVIE_SCORE(state, data) {
      state.tempMovie.score = data;
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
        if (response.data.length > 0) {
          context.commit("search_movies", response.data);
        } else {
          context.commit("not_found");
        }
      });
    },
    get_search_users(context, user_name) {
      user_name = user_name.replace(" ", "");
      user_name = user_name.replace("@", "");
      axios({
        method: "get",
        url: `http://localhost:8080/api/users/query/${user_name}`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        if (response.data.length > 0) {
          context.commit("search_users", response.data);
        } else {
          context.commit("not_found");
        }
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
    get_friends_user(context) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/users/friends`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        context.commit("friends_user", response.data);
      });
    },
    get_recommended_users(context) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/users/friends/recommendation`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        context.commit("recommended_users", response.data);
      });
    },
    get_recommended_movies(context) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/movie/recommended`,
        headers: {
          Token: this.state.user.token,
        },
      }).then((response) => {
        context.commit("recommended_movies", response.data);
      });
    },
    fetchTempMovie({ commit }, data) {
      commit("FETCH_TEMP_MOVIE", data);
    },
    updateMovieScore({ commit }, data) {
      commit("UPDATE_MOVIE_SCORE", data);
    },
  },
  modules: {},
});
