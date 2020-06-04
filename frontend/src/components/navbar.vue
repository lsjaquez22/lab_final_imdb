<template>
  <div>
    <!-- Revisar si esta registrado v-if="isLogged" -->
    <div v-if="isLogged" id="app" class="collapse" aria-expanded="true">
      <nav
        class="navbar is-fixed-top is-transparent"
        role="navigation"
        aria-label="main navigation"
      >
        <div class="navbar-brand">
          <router-link class="navbar-item" to="/">
            <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28" />
          </router-link>

          <a
            role="button"
            class="navbar-burger burger"
            aria-label="menu"
            aria-expanded="false"
            data-target="navbarBasicExample"
            v-on:click="showNav = !showNav"
          >
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
          </a>
        </div>

        <div id="navbarBasicExample" class="navbar-menu" v-bind:class="{ 'is-active' : showNav }">
          <div class="navbar-start">
            <div class="search-bar">
              <p class="control has-icons-left has-icons-right">
                <input
                  class="input"
                  type="text"
                  placeholder="@Username / Movie"
                  v-model="to_search"
                />
                <span class="icon is-small is-left">
                  <i class="fas fa-search"></i>
                </span>
              </p>
            </div>
            <div class="navbar-item">
              <button class="button is-primary" v-on:click="search()" :disabled="valid_search">
                <strong>Search</strong>
              </button>
            </div>
          </div>

          <div class="navbar-end">
            <router-link class="navbar-item" to="/profile">
              <button class="button is-primary">
                <strong>Profile</strong>
              </button>
            </router-link>
            <div class="navbar-item">
              <button v-on:click="onClick()" class="button is-primary">
                <strong>Log Out</strong>
              </button>
            </div>
          </div>
        </div>
      </nav>
    </div>
    <div v-else id="app" class="collapse" aria-expanded="true">
      <nav
        class="navbar is-fixed-top is-transparent"
        role="navigation"
        aria-label="main navigation"
      >
        <div class="navbar-brand">
          <a class="navbar-item" href="https://bulma.io">
            <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28" />
          </a>

          <a
            role="button"
            class="navbar-burger burger"
            aria-label="menu"
            aria-expanded="false"
            data-target="navbarBasicExample"
            v-on:click="showNav = !showNav"
          >
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
          </a>
        </div>

        <div id="navbarBasicExample" class="navbar-menu" v-bind:class="{ 'is-active' : showNav }">
          <div class="navbar-end">
            <router-link class="navbar-item" to="/signup">
              <button class="button is-primary">
                <strong>Sign Up</strong>
              </button>
            </router-link>
            <router-link class="navbar-item" to="/login">
              <button class="button is-primary">
                <strong>Login</strong>
              </button>
            </router-link>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  name: "navbar",
  data() {
    return {
      showNav: false,
      to_search: ""
    };
  },
  computed: {
    isLogged() {
      return this.$store.state.isLogged;
    },
    valid_search() {
      if (this.to_search[0] == "@" && this.to_search.length > 1) {
        return false;
      } else if (this.to_search[0] != "@" && this.to_search.length > 0) {
        return false;
      }
      return true;
    }
  },
  methods: {
    showButtton(state) {
      return state;
    },

    onClick() {
      this.$store.dispatch("logout");
      this.$router.push({ name: "Login" });
    },
    search() {
      if (this.to_search[0] != "@") {
        this.$store.dispatch("get_search_movies", this.to_search);
        if (this.$route.path != "/search-movie") {
          this.$router.push({ name: "SearchMovie" });
        }
      } else {
        this.$store.dispatch("get_search_users", this.to_search);
        if (this.$route.path != "/search-user") {
          this.$router.push({ name: "SearchUser" });
        }
      }
      this.to_search = "";
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

a {
  font-size: 20px;
  font-weight: bold;
}

#app {
  .navbar {
    background-color: $black;
  }
  a {
    background-color: $black;
  }
  .is-primary {
    background-color: $purple;
    strong {
      color: $white;
    }
  }
}

.search-bar {
  margin: auto;
  padding: 0 10px;
}
</style>