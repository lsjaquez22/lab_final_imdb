package mx.tec.lab.repository;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.MovieComment;
import mx.tec.lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {
    @Query("SELECT comment FROM MovieComment comment WHERE comment.movie = :movie")
    List<MovieComment> findByMovie(Movie movie);
}
