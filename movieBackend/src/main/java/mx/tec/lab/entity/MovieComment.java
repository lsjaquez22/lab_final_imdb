package mx.tec.lab.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.User;

@Entity
@Table(name = "user_movie_comment")
public class MovieComment {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    String comment;

    @JsonFormat(pattern = "yyyy-M-dd", timezone = "GMT-6")
    Calendar date;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getDate() { return date; }

    public void setDate(Calendar date) { this.date = date; }
}
