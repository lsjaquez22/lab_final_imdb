import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    suggested_movies: [],
  },
  mutations: {
    suggested_movies(state, movies_list) {
      state.suggested_movies = movies_list;
    },
  },
  actions: {
    get_suggested_movies(context, movie_name) {
      movie_name = movie_name.replace(" ", "_");

      axios({
        method: "get",
        url: `http://www.omdbapi.com/?s=${movie_name}&apikey=fac0fe09`,
      }).then((response) => {
        console.log(response);
        context.commit("suggested_movies", response.data.Search);
      });
    },
  },
  modules: {},
});
