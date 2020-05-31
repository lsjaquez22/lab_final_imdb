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
      <p class="title is-4">Sign Up to start searching for the best movies!</p>
      <div class="field">
        <div class="control has-icons-left has-icons-right">
          <input class="input is-rounded" type="text" placeholder="Userame" v-model="username" />
          <span class="icon is-small is-left">
            <i class="fas fa-user"></i>
          </span>
        </div>
      </div>

      <div class="field">
        <div class="control has-icons-left has-icons-right">
          <input class="input is-rounded" type="email" placeholder="Email" v-model="email" />
          <span class="icon is-small is-left">
            <i class="fas fa-envelope"></i>
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
      <div class="field">
        <div class="control has-icons-left has-icons-right">
          <input
            class="input is-rounded"
            type="password"
            placeholder="Confirm Password"
            v-model.trim="confirmPassword"
          />
          <span class="icon is-small is-left">
            <i class="fas fa-lock"></i>
          </span>
        </div>
      </div>

      <div class="field is-grouped">
        <div class="control">
          <button class="button is-primary is-link is-rounded">Create Account</button>
        </div>
        <router-link class="navbar-item" to="/login">
          <p>Already have an account?</p>
        </router-link>
      </div>
    </div>

    <div class="column is-1" />
  </div>
</template>

<script>
export default {
  name: "Login",
  data: () => ({
    username: "",
    password: "",
    confirmPassword: "",
    email: ""
  })
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

.bg {
  background-color: $purple;
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
  background-image: url("https://a9ofi49mlrn3i0rh13iivxwd-wpengine.netdna-ssl.com/wp-content/uploads/2018/05/cinematicconflict.jpg");
  background-size: 100% 100%;
  margin: 5% 0 5% 0;
  min-height: 80%;
  color: $white;
  -webkit-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
}
.center {
  margin-top: 5%;
}
.title {
  margin-top: 50px;
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
</style>