package mx.tec.lab.repository;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.UserMovieWatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMovieWatchListRepository extends JpaRepository<UserMovieWatchList, Long> {
    @Query("SELECT rel.movie FROM UserMovieWatchList rel WHERE rel.user.id = :userId ")
    List<Movie> findAllMoviesByUserId(@Param("userId") long userID);

    @Query("SELECT rel FROM UserMovieWatchList rel WHERE rel.user.id = :userId ")
    List<UserMovieWatchList> findAllByUserId(@Param("userId") long userID);

    UserMovieWatchList findByMovieId(long id);

    UserMovieWatchList findByUserIdAndMovieId(long userId, long movieId);
}
