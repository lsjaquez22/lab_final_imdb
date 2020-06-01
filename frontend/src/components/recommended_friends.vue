<template>
  <div class="column is-3 recomended-friends">
    <p class="subtitle is-4">People You May Know</p>
    <CardUser v-for="user in list_users" :key="user.username" :user="user" />
  </div>
</template>

<script>
import CardUser from "./card_user";
export default {
  name: "RecommendedFriends",
  components: {
    CardUser
  },
  computed: {
    list_users() {
      return this.$store.state.recommended_users;
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