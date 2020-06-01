import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    isLogged: false,
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
  },
  modules: {},
});
