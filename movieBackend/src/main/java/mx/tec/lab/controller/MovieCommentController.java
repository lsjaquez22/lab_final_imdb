package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.MovieComment;
import mx.tec.lab.entity.User;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.repository.MovieRepository;
import mx.tec.lab.repository.MovieCommentRepository;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class MovieCommentController {

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private MovieCommentRepository movieCommentRepository;

    @GetMapping("/movie/{imdbID}/comment")
    public List<MovieComment> getMovieScore(@RequestHeader("Token") String token, @PathVariable(value = "imdbID") String imdbID) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Movie existingMovie = movieRepository.findByImdbID(imdbID);

        if (existingMovie == null) {
            return null;
        }

        return movieCommentRepository.findByMovie(existingMovie);
    }

    @PostMapping("/movie/{imdbID}/comment")
    public MovieComment createMovieComment(@RequestHeader("Token") String token,@PathVariable(value = "imdbID") String imdbID, @RequestBody MovieComment comment) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        if (comment.getDate() == null || comment.getComment() == null) {
            throw new GenericBadRequest();
        }

        try {
            Movie existingMovie = movieRepository.findByImdbID(imdbID);

            if (existingMovie == null) {
                throw new GenericBadRequest();
            }

            comment.setUser(existingUser.get());
            comment.setMovie(existingMovie);

            return movieCommentRepository.save(comment);

        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }

    @PutMapping("/movie/comment/{commentId}")
    public MovieComment updateMovieScore(@RequestHeader("Token") String token, @RequestBody MovieComment comment, @PathVariable(value = "commentId") String commentId) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Optional<MovieComment> existingComment = movieCommentRepository.findById(Long.parseLong(commentId));

        if (!existingComment.isPresent()) {
            throw new GenericBadRequest();
        }

        if (comment.getComment() == null) {
            throw new GenericBadRequest();
        }

        try {
            MovieComment updatedComment = existingComment.get();
            updatedComment.setComment(comment.getComment());

            return movieCommentRepository.save(updatedComment);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }

    @DeleteMapping("/movie/comment/{commentId}")
    public void deleteMovieScore(@RequestHeader("Token") String token, @PathVariable(value = "commentId") String commentId) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException();
        }

        Optional<MovieComment> existingComment = movieCommentRepository.findById(Long.parseLong(commentId));

        if (!existingComment.isPresent()) {
            throw new GenericBadRequest();
        }

        MovieComment comment = existingComment.get();

        if (comment.getUser().getId() != existingUser.get().getId()) {
            throw new GenericBadRequest();
        }

        try {
            movieCommentRepository.delete(comment);
        } catch (DataIntegrityViolationException e) {
            throw new GenericBadRequest(e.getMessage());
        }
    }
}
