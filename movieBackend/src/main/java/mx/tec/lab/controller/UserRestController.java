package mx.tec.lab.controller;

import mx.tec.lab.entity.User;
import mx.tec.lab.entity.UserMovieWatchList;
import mx.tec.lab.exception.GenericBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.exception.UserSQLException;
import mx.tec.lab.exception.UserUnauthorized;
import mx.tec.lab.model.SimpleMovie;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class UserRestController {

    public static final String KEY_USERNAME = "username";
    private static final String KEY_SHOULD_FOLLOW = "should_follow";

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
            throw new UserUnauthorized();
        }

        User targetUser = userRepository.findByUsername(username);
        if (targetUser == null) {
            throw new UserNotFoundException();
        }
        return new UserWrapper(targetUser, targetUser.getWatchListsAsList());
    }

    @GetMapping("/users/friends")
    public Set<User> getUserFollowers(@RequestHeader("Token") String token) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserUnauthorized();
        }
        return existingUser.get().getFollowingUsers();
    }

    // TODO: Suggested users: friends of friends
    // TODO: Get users with similar name or username

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
            throw new UserUnauthorized();
        }
    }

    @PutMapping("/users/friends")
    public void addOrRemoveFollowList(@RequestHeader("Token") String token, @RequestBody Map<String, String> requestMap) {
        long userId = SessionHandler.getInstance().getUserByKey(token);
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            throw new UserUnauthorized();
        }

        if (!requestMap.containsKey(KEY_USERNAME) || !requestMap.containsKey(KEY_SHOULD_FOLLOW)) {
            throw new GenericBadRequest();
        }

        try {
            User friend = userRepository.findByUsername(requestMap.get(KEY_USERNAME));
            if (friend == null) {
                throw new UserNotFoundException();
            }

            User me = existingUser.get();

            if (me.getFollowingUsers().contains(friend)) {
                throw new GenericBadRequest("You are already following this account.");
            }

            if (me.equals(friend)) {
                throw new GenericBadRequest("You cannot follow yourself.");
            }

            if ( Boolean.valueOf(requestMap.get(KEY_SHOULD_FOLLOW)) ) {
                me.appendUserToFollowingList(friend);
            } else {
                me.removeFromFollowingList(friend);
            }
            userRepository.save(me);
        } catch (IllegalArgumentException e) {
            throw new GenericBadRequest(e.getMessage());
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

