<template>
  <article class="message">
    <div class="message-body">
      <div class="user-info">
        <router-link :to="{name:'User', params: {user_name: user.user.username}}">
          <strong>@{{user.user.username}}</strong>
          <p>{{user.user.name}}</p>
        </router-link>
      </div>

      <div v-if="!user.friend" class="icon-add-friend">
        <i class="fas fa-user-plus" v-on:click="follow_user()"></i>
      </div>
      <div v-else class="icon-remove-friend">
        <i class="fas fa-times" v-on:click="unfollow_user()"></i>
      </div>
    </div>
  </article>
</template>

<script>
import axios from "axios";
export default {
  name: "CardUser",
  props: {
    user: Object,
    profile: Boolean,
    search: Boolean
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
          username: this.user.user.username,
          should_follow: "true"
        }
      }).then(() => {
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
          username: this.user.user.username,
          should_follow: "false"
        }
      }).then(() => {
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
  .message-body {
    border-color: $purple;
    border-width: 0 0 0 6px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .user-info {
      text-align: initial;
      a {
        text-decoration: none;
      }
    }
    i:hover {
      cursor: pointer;
    }
  }
}
</style>