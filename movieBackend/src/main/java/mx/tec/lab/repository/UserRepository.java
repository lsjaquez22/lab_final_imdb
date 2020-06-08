package mx.tec.lab.repository;

import mx.tec.lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:queryName,'%')")
    List<User> getSimilarUsers(@Param(value = "queryName") String queryName);
}
