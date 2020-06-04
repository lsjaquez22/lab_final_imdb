<template>
  <div class="container-movie">
    <div class="home">
      <div class="columns is-gapless">
        <div class="column">
          <div class="columns is-gapless is-vcentered">
            <div class="column">
              <p class="title is-2">
                <b>@{{user_name}}</b>
              </p>
              <p class="title is-3">{{user_info.name}}</p>
            </div>
            <div class="column" v-if="!my_friends">
              <button class="button is-primary" v-on:click="follow_user()">
                <strong>Add Friend</strong>
              </button>
            </div>
            <div class="column" v-else>
              <button class="button is-danger" v-on:click="unfollow_user()">
                <strong>Delete Friend</strong>
              </button>
            </div>
          </div>
          <p class="title is-3">Movies</p>
          <div class="movies-container">
            <CardUserMovie v-for="element in user_movies" :key="element.imdbID" :movie="element" />
          </div>
        </div>
        <RecommendedFriends />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import CardUserMovie from "../components/card_user_movie";
import RecommendedFriends from "../components/recommended_friends.vue";
export default {
  name: "User",
  components: {
    CardUserMovie,
    RecommendedFriends
  },
  data() {
    return {
      user_name: this.$route.params.user_name,
      my_friends: false,
      user_info: {},
      user_movies: []
    };
  },
  mounted() {
    axios({
      method: "get",
      url: `http://localhost:8080/api/users/${this.user_name}`,
      headers: {
        Token: this.$store.state.user.token
      }
    }).then(response => {
      this.user_info = response.data.user;
      this.user_movies = response.data.watchList;
      this.my_friends = response.data.friend;
    });
  },
  methods: {
    follow_user() {
      axios({
        method: "put",
        url: `http://localhost:8080/api/users/friends`,
        headers: {
          Token: this.$store.state.user.token
        },
        data: {
          username: this.user_info.username,
          should_follow: "true"
        }
      }).then(() => {
        this.my_friends = true;
        this.$store.dispatch("get_recommended_users");
        this.$store.dispatch("get_friends_user");
        this.$store.dispatch("get_recommended_movies");
      });
    },
    unfollow_user() {
      axios({
        method: "put",
        url: `http://localhost:8080/api/users/friends`,
        headers: {
          Token: this.$store.state.user.token
        },
        data: {
          username: this.user_info.username,
          should_follow: "false"
        }
      }).then(() => {
        this.my_friends = false;
        this.$store.dispatch("get_recommended_users");
        this.$store.dispatch("get_friends_user");
        this.$store.dispatch("get_recommended_movies");
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

#app {
  .home {
    min-height: 100vh;
    background-color: $black;
    color: $white;
    padding-top: 3em;
    .subtitle {
      color: $white;
      font-weight: bold;
    }
    .recomended-friends {
      margin: 0 1em;
    }
    .movies-container {
      margin-top: 2em;
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }
    .is-primary {
      background-color: $purple;
      strong {
        color: $white;
      }
    }
    .is-danger {
      background-color: $red;
      strong {
        color: $white;
      }
    }
  }
}

.container-movie {
  min-height: 100vh;
  background-color: $black;
  p {
    color: $white;
  }
}
</style>