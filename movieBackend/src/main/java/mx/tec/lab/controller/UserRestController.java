package mx.tec.lab.controller;

import mx.tec.lab.entity.User;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.exception.UserSQLException;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


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

    @PostMapping("/users")
    public User newUser(@Valid @RequestBody User newUser) {
        try {
            String hashedPassword = SessionHandler.getInstance().hashedString(newUser.getPassword());
            newUser.setPassword(hashedPassword);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserSQLException("That email already exists");
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
            throw new UserNotFoundException("User not found with email " + tempUser.getEmail());
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
}

