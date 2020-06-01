package mx.tec.lab.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @Column(name = "imdbID", unique = true)
    private String imdbID;

    @JsonAlias("Title")
    private String title;

    @JsonAlias("Year")
    private String year;

    @JsonAlias("Released")
    private String released;

    @JsonAlias("Genre")
    private String genre;

    @JsonAlias("Director")
    private String director;

    @JsonAlias("Writer")
    private String writer;

    @JsonAlias("Actors")
    private String actors;

    @JsonAlias("Plot")
    private String plot;

    @JsonAlias("Language")
    private String language;

    @JsonAlias("Country")
    private String country;

    @JsonAlias("Awards")
    private String awards;

    @JsonAlias("Poster")
    private String poster;

    private float score;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private Set<UserMovieWatchList> usersThatWatched;

    /* Getters and Setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.length() > 255) {
            this.title = title.substring(0,250) + "...";
        } else {
            this.title = title;
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        if(writer.length() > 250) {
            this.writer = writer.substring(0,245) + "...";
        } else {
            this.writer = writer;
        }
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        if(actors.length() > 255) {
            this.actors = actors.substring(0,250) + "...";
        } else {
            this.actors = actors;
        }
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        if(plot.length() > 255) {
            this.plot = plot.substring(0,250) + "...";
        } else {
            this.plot = plot;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        if(awards.length() > 255) {
            this.awards = awards.substring(0,250) + "...";
        } else {
            this.awards = awards;
        }
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Set<UserMovieWatchList> getUsersThatWatched() {
        return usersThatWatched;
    }

    public void setUsersThatWatched(Set<UserMovieWatchList> usersThatWatched) {
        this.usersThatWatched = usersThatWatched;
    }

}
