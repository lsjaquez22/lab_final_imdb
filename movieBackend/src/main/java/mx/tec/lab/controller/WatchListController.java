package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.MovieState;
import mx.tec.lab.entity.User;
import mx.tec.lab.entity.UserMovieWatchList;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.repository.MovieRepository;
import mx.tec.lab.repository.UserMovieWatchListRepository;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class WatchListController {

    private static final String IMDB_ID_KEY = "imdbID";
    private static final String MOVIE_WATCH_STATE_KEY = "state";

    @Resource
    private UserMovieWatchListRepository watchListRepository;

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private UserRepository userRepository;

    @GetMapping("/watchlist")
    public List<SimpleMovie> getWatchedMovies(@RequestHeader("Token") String token) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        return sortMovieList(watchListRepository.findAllByUserId(userId));
    }

    @PostMapping("/watchlist")
    public String appendMovieToWatchList(@RequestHeader("Token") String token, @RequestBody Map<String, String> requestMap) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!requestMap.containsKey(IMDB_ID_KEY) || !requestMap.containsKey(MOVIE_WATCH_STATE_KEY)) {
            throw new GenericBadRequest();
        }

        Movie existingMovie = movieRepository.findByImdbID(requestMap.get(IMDB_ID_KEY));
        if (existingMovie == null) {
            throw new GenericBadRequest();
        }

        UserMovieWatchList existingMovieInList = watchListRepository.findByUserIdAndMovieId(userId, existingMovie.getId());
        if (existingMovieInList != null) {
            throw new GenericBadRequest("Movie already on list...");
        }

        try {
            UserMovieWatchList userMovie = new UserMovieWatchList();
            userMovie.setUser(existingUser.get());
            userMovie.setMovie(existingMovie);
            userMovie.setMovieState(MovieState.WATCHING);
            watchListRepository.save(userMovie);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }

        return "ok uwu";
    }

    @PutMapping("/watchlist")
    public String updateMovieToWatchList(@RequestHeader("Token") String token, @RequestBody Map<String, String> requestMap) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!requestMap.containsKey(IMDB_ID_KEY) || !requestMap.containsKey(MOVIE_WATCH_STATE_KEY)) {
            throw new GenericBadRequest();
        }

        Movie existingMovie = movieRepository.findByImdbID(requestMap.get(IMDB_ID_KEY));
        if (existingMovie == null) {
            throw new GenericBadRequest();
        }

        UserMovieWatchList userMovie = watchListRepository.findByUserIdAndMovieId(userId, existingMovie.getId());

        if (userMovie == null) {
            throw new GenericBadRequest("Movie not found");
        }

        try {
            userMovie.setMovieState(MovieState.valueOf(requestMap.get(MOVIE_WATCH_STATE_KEY)));
            watchListRepository.save(userMovie);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }

        return "ok uwu";
    }

    @DeleteMapping("/watchlist/{imdbID}")
    public void deleteMovieWatchList(@RequestHeader("Token") String token, @PathVariable(value = "imdbID") String imdbID) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Movie existingMovie = movieRepository.findByImdbID(imdbID);
        if (existingMovie == null) {
            throw new GenericBadRequest();
        }
        try {
            long relationId = watchListRepository.findByUserIdAndMovieId(userId, existingMovie.getId()).getId();
            watchListRepository.deleteById(relationId);
        } catch (IllegalArgumentException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }

    private List<SimpleMovie> sortMovieList(List<UserMovieWatchList> watchList) {
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

    private List<SimpleMovie> prepareMovieListToSimple(List<UserMovieWatchList> watchList) {
        List<SimpleMovie> result = new ArrayList<>();
        watchList.forEach(o -> result.add(new SimpleMovie(o.getMovie(), o.getMovieState())));
        return result;
    }

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
}
