package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.MovieState;
import mx.tec.lab.entity.User;
import mx.tec.lab.entity.UserMovieWatchList;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.exception.UserUnauthorized;
import mx.tec.lab.model.SimpleMovie;
import mx.tec.lab.repository.MovieRepository;
import mx.tec.lab.repository.UserMovieWatchListRepository;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

        return SimpleMovie.sortMovieList(watchListRepository.findAllByUserId(userId));
    }

    @GetMapping("/watchlist/{username}")
    public List<SimpleMovie> getFriendsWatchlist(@RequestHeader("Token") String token, @PathVariable(value = "username") String username) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserUnauthorized();
        }

        User anyOne = userRepository.findByUsername(username);
        if (anyOne == null) {
            throw new UserNotFoundException();
        }

        return SimpleMovie.sortMovieList(watchListRepository.findAllByUserId(anyOne.getId()));
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
            throw new GenericBadRequest("Movie not saved on local db...");
        }

        UserMovieWatchList existingMovieInList = watchListRepository.findByUserIdAndMovieId(userId, existingMovie.getId());
        if (existingMovieInList != null) {
            throw new GenericBadRequest("Movie not found locally...");
        }

        try {
            UserMovieWatchList userMovie = new UserMovieWatchList();
            userMovie.setUser(existingUser.get());
            userMovie.setMovie(existingMovie);
            if ( MovieState.isValid(requestMap.get(MOVIE_WATCH_STATE_KEY)) ) {
                userMovie.setMovieState(MovieState.valueOf(requestMap.get(MOVIE_WATCH_STATE_KEY)));
            } else {
                userMovie.setMovieState(MovieState.WATCHING);
            }
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
            if ( MovieState.isValid(requestMap.get(MOVIE_WATCH_STATE_KEY)) ) {
                userMovie.setMovieState(MovieState.valueOf(requestMap.get(MOVIE_WATCH_STATE_KEY)));
            } else {
                userMovie.setMovieState(MovieState.WATCHING);
            }
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

}
