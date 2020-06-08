package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.User;
import mx.tec.lab.entity.MovieScore;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.repository.MovieRepository;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.repository.MovieScoreRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class MovieScoreController {
    private static final String IMDB_ID_KEY = "imdbID";
    private static final String MOVIE_SCORE = "score";

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private MovieScoreRepository movieScoreRepository;

    @GetMapping("/movie/{imdbID}/score")
    public float getMovieScore(@RequestHeader("Token") String token, @PathVariable(value = "imdbID") String imdbID) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Movie existingMovie = movieRepository.findByImdbID(imdbID);

        if (existingMovie == null) {
            return 0.0f;
        }

        MovieScore movieScore = movieScoreRepository.findScore(existingUser.get(), existingMovie);

        if (movieScore == null) {
            return 0.0f;
        }

        return movieScore.getScore();
    }

    @PostMapping("/movie/score")
    public void setMovieScore(@RequestHeader("Token") String token, @RequestBody Map<String, String> requestMap) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!requestMap.containsKey(IMDB_ID_KEY) || !requestMap.containsKey(MOVIE_SCORE)) {
            throw new GenericBadRequest();
        }

        try {
            String imdbID = requestMap.get(IMDB_ID_KEY);
            String scoreString = requestMap.get(MOVIE_SCORE);
            float score = Float.parseFloat(scoreString);

            Movie existingMovie = movieRepository.findByImdbID(imdbID);

            if (existingMovie == null) {
                throw new GenericBadRequest();
            }

            MovieScore existingMovieScore = movieScoreRepository.findScore(existingUser.get(), existingMovie);

            if (existingMovieScore != null) {
                throw new GenericBadRequest();
            }
            
            MovieScore movieScore = new MovieScore();

            movieScore.setUser(existingUser.get());
            movieScore.setMovie(existingMovie);
            movieScore.setScore(score);

            movieScoreRepository.save(movieScore);

            this.updateMovie(existingMovie);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }

    @PutMapping("/movie/score")
    public void updateMovieScore(@RequestHeader("Token") String token, @RequestBody Map<String, String> requestMap) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!requestMap.containsKey(IMDB_ID_KEY) || !requestMap.containsKey(MOVIE_SCORE)) {
            throw new GenericBadRequest();
        }

        try {
            String imdbID = requestMap.get(IMDB_ID_KEY);
            String scoreString = requestMap.get(MOVIE_SCORE);
            float score = Float.parseFloat(scoreString);

            Movie existingMovie = movieRepository.findByImdbID(imdbID);

            if (existingMovie == null) {
                throw new GenericBadRequest();
            }

            MovieScore movieScore = movieScoreRepository.findScore(existingUser.get(), existingMovie);

            if (movieScore == null) {
                throw new GenericBadRequest();
            }

            movieScore.setScore(score);

            movieScoreRepository.save(movieScore);

            this.updateMovie(existingMovie);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }

    public void updateMovie(Movie movie){
        List<MovieScore> allScores = movieScoreRepository.findByMovie(movie);

        double total = allScores.stream().mapToDouble(MovieScore::getScore).sum();

        double average = total / allScores.size();

        movie.setScore((float) average);

        movieRepository.save(movie);
    }
}
