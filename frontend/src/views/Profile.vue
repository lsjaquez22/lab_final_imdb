<template>
  <div class="container-profile">
    <div class="home">
      <div class="columns is-gapless">
        <div class="column">
          <InfoUser />
        </div>
        <ListFriends />
      </div>
    </div>
  </div>
</template>

<script>
import ListFriends from "../components/list_friends.vue";
import InfoUser from "../components/info_user.vue";
export default {
  name: "Profile",
  components: { ListFriends, InfoUser }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

#app {
  .container-profile {
    min-height: 100vh;
    background-color: $black;
    padding-top: 3em;
    h1 {
      color: $white;
    }
    .recomended-friends {
      margin: 0 1em;
    }
    .button-edit-info {
      text-align: end;
      padding-right: 3em;
      .is-primary {
        background-color: $purple;
        color: $white;
      }
    }
    .field {
      margin: 0 2em;
      .label {
        color: $white;
        text-align: initial;
      }
    }
  }
}
</style>

