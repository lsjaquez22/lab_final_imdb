<template>
  <div class="column is-3 recomended-friends">
    <p class="subtitle is-4">Friends</p>
    <CardUser v-for="user in list_friends" :key="user.username" :user="user" :profile="true" />
  </div>
</template>

<script>
import CardUser from "./card_user";
export default {
  name: "ListFriends",
  props: {},
  components: {
    CardUser
  },
  computed: {
    list_friends() {
      return this.$store.state.friends_user;
    }
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.recomended-friends {
  margin: 0 1em;
  .subtitle {
    color: $white;
    font-weight: bold;
  }
}
</style>