package mx.tec.lab.repository;

import mx.tec.lab.entity.MovieScore;
import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScoreRepository extends JpaRepository<MovieScore, Long> {
    @Query("SELECT score FROM MovieScore score WHERE score.movie = :movie")
    List<MovieScore> findByMovie(Movie movie);

    @Query("SELECT score FROM MovieScore score WHERE score.movie = :movie and score.user = :user")
    MovieScore findScore(User user,Movie movie);
}
