package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.repository.MovieRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class MovieRestController {
    @Resource
    private MovieRepository movieRepository;

    @GetMapping("/movie")
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
}
