package mx.tec.lab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.User;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.MovieSQLException;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.repository.MovieRepository;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class MovieRestController {

    private static final String API_KEY = "fac0fe09";
    public static final String OMDB_BASE_URL = "http://www.omdbapi.com/";
    public static final String OMDB_URL_API_KEY = "http://www.omdbapi.com/?apikey=" + API_KEY;

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private UserRepository userRepository;

    private RestTemplate restTemplate;

    @Autowired
    public MovieRestController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping("/movie")
    public List<Movie> getMovies(@RequestHeader("Token") String token) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }
        return movieRepository.findAll();
    }

    @GetMapping("/movie/search")
    public Object getMovieSuggestion(@RequestHeader("Token") String token,
                                                 @RequestParam("title") String qTitle,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") String qPageResult) {

        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }


        return movieSearchFromOMDB(qTitle, qPageResult);
    }

    @GetMapping("/movie/{imdbID}")
    public Movie getMoviesByImdbID(@RequestHeader("Token") String token, @PathVariable(value = "imdbID") String imdbID) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Movie existingMovie = movieRepository.findByImdbID(imdbID);

        if (existingMovie != null) {
            return existingMovie;
        }

        Movie newMovie = getMovieFromOMDBByID(imdbID);
        try {
            movieRepository.save(newMovie);
        } catch (DataIntegrityViolationException e) {
            System.out.println(MovieRestController.class.getSimpleName() + ": " + e.getMessage());
            throw new MovieSQLException("Couldn't save movie into local db.");
        }

        return newMovie;
    }

    private Movie getMovieFromOMDBByID(String imdbID) {
        String url = OMDB_URL_API_KEY + "&i=" + imdbID;
        return this.restTemplate.getForObject(url, Movie.class);
    }

    private List<?> movieSearchFromOMDB(String queryTitle, String queryPage) {
        String url = OMDB_URL_API_KEY + "&s=" + queryTitle + "&page=" + queryPage;
        String json = this.restTemplate.getForObject(url, String.class);

        Map<String,Object> map;
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new GenericBadRequest();
        }

        if (map.get("Response").equals("True") && map.containsKey("Search")) {
            return (List<?>) map.get("Search");
        }

        return new ArrayList<>();
    }
}
