package mx.tec.lab.controller;

import mx.tec.lab.entity.Movie;
import mx.tec.lab.entity.User;
import mx.tec.lab.entity.UserMovieWatchList;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.exception.UserSQLException;
import mx.tec.lab.model.SimpleMovie;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class UserRestController {
    @Resource
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{username}")
    public UserWrapper getUsersByUsername(@RequestHeader("Token") String token, @PathVariable(value = "username") String username) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("Unauthorized feature...");
        }

        User targetUser = userRepository.findByUsername(username);
        if (targetUser == null) {
            throw new UserNotFoundException();
        }
        return new UserWrapper(targetUser, targetUser.getWatchListsAsList());
    }

    @PostMapping("/users")
    public User newUser(@Valid @RequestBody User newUser) {
        try {
            String hashedPassword = SessionHandler.getInstance().hashedString(newUser.getPassword());
            newUser.setPassword(hashedPassword);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserSQLException("That email or username already exists");
        }
        return newUser;
    }

    @PutMapping("/users")
    public User updateUser(@RequestHeader("Token") String token, @RequestBody User tempUser) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            User updatedUser = userRepository.getOne(userId);
            updatedUser.setName(tempUser.getName());
            return userRepository.save(updatedUser);
        } else {
            throw new UserNotFoundException("User not found with username " + tempUser.getUsername());
        }
    }

    @GetMapping("/user")
    public User getUserById(@RequestHeader("Token") String token) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            return userRepository.getOne(userId);
        } else {
            throw new UserNotFoundException();
        }
    }

    public class UserWrapper implements Serializable {
        private User user;
        private List<SimpleMovie> watchList;
        private boolean isFriend;

        public  UserWrapper(User user, List<UserMovieWatchList> watchList) {
            this.user = user;
            this.watchList = SimpleMovie.sortMovieList(watchList);
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<SimpleMovie> getWatchList() {
            return watchList;
        }

        public void setWatchList(List<SimpleMovie> watchList) {
            this.watchList = watchList;
        }

        public boolean isFriend() {
            return isFriend;
        }

        public void setFriend(boolean friend) {
            isFriend = friend;
        }
    }
}

