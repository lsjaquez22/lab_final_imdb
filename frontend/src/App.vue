<template>
  <div id="app">
    <navbar></navbar>
    <router-view :key="$route.path" />
  </div>
</template>

<script>
import navbar from "./components/navbar.vue";
export default {
  components: {
    navbar
  }
};
</script>

<style lang="scss">
@import "./styles/app.scss";
</style>
