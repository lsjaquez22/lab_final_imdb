<template>
  <div class="container-movies-list">
    <div class="columns is-gapless">
      <div class="column"></div>
      <div class="column is-4">
        <h1 class="title">List of Users</h1>
        <div class="user-container">
          <CardUser v-for="element in list_users" :key="element.username" :user="element" />
        </div>
      </div>
      <div class="column"></div>
    </div>
  </div>
</template>

<script>
import CardUser from "../components/card_user.vue";
export default {
  name: "Movielist",
  components: {
    CardUser
  },
  data() {
    return {};
  },
  computed: {
    list_users() {
      return this.$store.state.search_users;
    }
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.container-movies-list {
  min-height: 100vh;
  background-color: $black;
  h1 {
    color: $white;
    margin-top: 2em;
    margin-bottom: 1em;
  }
  .movies-container {
    margin-top: 2em;
    // display: flex;
    // flex-wrap: wrap;
    // justify-content: center;
  }
}
</style>