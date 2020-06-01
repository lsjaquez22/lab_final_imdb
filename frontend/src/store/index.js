import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import {
  recomended_movies,
  recomended_users,
  user_info,
  friends_users,
  user_movies,
} from "./data";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: { ...user_info },
    friends_user: [...friends_users],
    movies_user: [...user_movies],
    search_movies: [],
    recommended_movies: [...recomended_movies],
    search_users: [...recomended_users],
    recommended_users: [...recomended_users],
  },
  mutations: {
    search_movies(state, movies_list) {
      state.search_movies = movies_list;
    },
  },
  actions: {
    get_search_movies(context, movie_name) {
      movie_name = movie_name.replace(" ", "_");

      axios({
        method: "get",
        url: `http://www.omdbapi.com/?s=${movie_name}&apikey=fac0fe09`,
      }).then((response) => {
        context.commit("search_movies", response.data.Search);
      });
    },
  },
  modules: {},
});
