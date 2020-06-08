<template>
  <div class="bg columns">
    <div class="column is-1" />
    <div class="column card1">
      <p class="title is-3 text">
        Make friends and discover
        <br />new movies together.
        <br />Your favorite movie may be
        <br />whithin reach of a click!
      </p>
    </div>
    <div class="column card">
      <p class="title is-4">Login to search for the best movies!</p>
      <div class="field">
        <div class="control has-icons-left has-icons-right">
          <input class="input is-rounded" type="text" placeholder="Username" v-model="username" />
          <span class="icon is-small is-left">
            <i class="fas fa-user"></i>
          </span>
        </div>
      </div>
      <div class="field">
        <div class="control has-icons-left has-icons-right">
          <input
            class="input is-rounded"
            type="password"
            placeholder="Password"
            v-model.trim="password"
          />
          <span class="icon is-small is-left">
            <i class="fas fa-lock"></i>
          </span>
        </div>
      </div>

      <div class="field is-grouped actions">
        <div class="control">
          <button class="button is-primary is-link is-rounded" @click="login()">Login</button>
        </div>
        <div>
          <router-link class="navbar-item" to="/signup">
            <p>Don't have an account?</p>
          </router-link>
        </div>
      </div>
    </div>

    <div class="column is-1" />
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data: () => ({
    username: "",
    password: "",
    errorMessage: "",
    errorFlag: false
  }),
  methods: {
    async login() {
      try {
        const response = await axios.post("http://localhost:8080/api/login", {
          username: this.username,
          password: this.password
        });
        if (response.status === 200) {
          sessionStorage.setItem("token", response.data.token);
          this.$store.dispatch("fetchUser", response.data);
          this.$store.dispatch("toggleLogged");
          this.$router.push({ name: "Home" });
        }
      } catch (err) {
        console.log(err.response.data.message);
        this.errorMessage = err.response.data.message;
        this.errorFlag = true;
        return err;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

.bg {
  background-color: $purple;
  min-height: 100vh;
}
.card {
  background-color: $white;
  margin: 5% 0 5% 0;
  min-height: 80%;
  -webkit-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
}
.card1 {
  background-image: url("https://www.travelrite.com.au/images/cinematic-tours-header.jpg");
  background-size: 100% 100%;
  margin: 5% 0 5% 0;
  min-height: 80%;
  color: $white;
  -webkit-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
}

.title {
  margin-top: 60px;
}
.text {
  color: $white;
}
.is-primary {
  background-color: $purple;
}
.is-primary:hover {
  background-color: $purple;
}
.actions {
  margin: 10% 20% 10% 20%;
}
.control {
  margin: 8% 5% 0 5%;
}
</style>