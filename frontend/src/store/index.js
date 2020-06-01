import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import { recomended_movies } from "./data";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    search_movies: [],
    recommended_movies: [...recomended_movies],
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
        console.log(response);
        context.commit("search_movies", response.data.Search);
      });
    },
  },
  modules: {},
});
