package mx.tec.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_movie_watch_list")
public class UserMovieWatchList {
    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    private MovieState movieState;


    /* Getters and Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieState getMovieState() {
        return movieState;
    }

    public void setMovieState(MovieState movieState) {
        this.movieState = movieState;
    }
}
