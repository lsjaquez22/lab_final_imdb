package mx.tec.lab.model;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.MovieState;
import mx.tec.lab.entity.UserMovieWatchList;

import java.io.Serializable;
import java.util.*;

public class SimpleMovie implements Serializable {
    private String title;
    private String year;
    private String imdbID;
    private String poster;
    private MovieState state;

    public SimpleMovie(Movie movie, MovieState state) {
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.imdbID = movie.getImdbID();
        this.poster = movie.getPoster();
        this.state = state;
    }

    public static List<SimpleMovie> sortMovieList(List<UserMovieWatchList> watchList) {
        List<SimpleMovie> onSortedList = prepareMovieListToSimple(watchList);
        Map<MovieState, List<SimpleMovie>> cuteMap = new HashMap<>();

        // Divide by state
        for (SimpleMovie simp : onSortedList) {
            if (cuteMap.containsKey(simp.getState())) {
                cuteMap.get(simp.getState()).add(simp);
            } else {
                cuteMap.put(simp.getState(), new ArrayList<>());
                cuteMap.get(simp.getState()).add(simp);
            }
        }

        List<SimpleMovie> completeList = new ArrayList<>();
        for (MovieState state : MovieState.values()) {
            if (cuteMap.containsKey(state)) {
                cuteMap.get(state).sort(Comparator.comparing(SimpleMovie::getTitle));
                completeList.addAll(cuteMap.get(state));
            }
        }

        return completeList;
    }

    public static List<SimpleMovie> prepareMovieListToSimple(List<UserMovieWatchList> watchList) {
        List<SimpleMovie> result = new ArrayList<>();
        watchList.forEach(o -> result.add(new SimpleMovie(o.getMovie(), o.getMovieState())));
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieState getState() {
        return state;
    }

    public void setState(MovieState state) {
        this.state = state;
    }
}
