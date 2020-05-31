<template>
  <div>
    <div class="container-card">
      <div class="card">
        <div class="card-image">
          <figure class="image is-4by3">
            <img :src="movie.Poster" alt="Placeholder image" />
          </figure>
        </div>
        <div class="card-content">
          <div class="media">
            <div class="media-content">
              <router-link :to="{name:'Movie', params: {movie_name: movie.Title}}">
                <p class="title is-4">{{movie.Title}}</p>
              </router-link>
              <p class="subtitle is-6">{{movie.Year}}</p>
            </div>
          </div>

          <!-- <div class="content">
            <a>@bulmaio</a>.
            <a href="#">#css</a>
            <a href="#">#responsive</a>
            <br />
            <time datetime="2016-1-1">11:09 PM - 1 Jan 2016</time>
          </div>-->
        </div>
        <footer class="card-footer">
          <a href="#" class="card-footer-item">Add Movie</a>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "card-movie",
  props: {
    movie: Object
  }
};
</script>

<style lang="scss" scoped>
.container-card {
  max-width: 260px;
  margin: 0 2em 2em 2em;
  a {
    .title {
      margin-bottom: 10px;
    }
  }
}
</style>