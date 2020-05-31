package mx.tec.lab.controller;

import mx.tec.lab.entity.User;
import mx.tec.lab.exception.UserBadRequest;
import mx.tec.lab.exception.UserNotFoundException;
import mx.tec.lab.exception.UserUnauthorized;
import mx.tec.lab.repository.UserRepository;
import mx.tec.lab.service.SessionHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/*")
public class SessionRestController {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    @Resource
    private UserRepository userRepository;

    @PostMapping("/login")
    public TokenSerializable login(@RequestBody Map<String, String> userResponse) {

        User user = null;
        String hashedPassword = null;

        if (userResponse.containsKey(KEY_EMAIL) && userResponse.containsKey(KEY_PASSWORD)) {
            user = userRepository.findByEmail(userResponse.get(KEY_EMAIL));
            hashedPassword = SessionHandler.getInstance().hashedString(userResponse.get(KEY_PASSWORD));
        } else {
            throw new UserBadRequest();
        }


        if (user == null) {
            throw new UserNotFoundException();
        } else if (!user.getPassword().equals(hashedPassword)) {
            throw new UserUnauthorized();
        }

        TokenSerializable tokenSerializable = new TokenSerializable();
        tokenSerializable.setToken(SessionHandler.getInstance().addNewSession(user));
        tokenSerializable.setUser(user);

        return tokenSerializable;
    }

    @GetMapping("/logout")
    public void deleteSession(@RequestHeader("Token") String token) {
        SessionHandler.getInstance().removeSession(token);
    }

    /* Inner Token Class */
    public class TokenSerializable {

        private String token;
        private User user;

        public TokenSerializable() { }

        public TokenSerializable(String token) {
            this.token = token;
        }

        public String getToken() { return token; }

        public void setToken(String token) { this.token = token; }

        public User getUser() { return user; }

        public void setUser(User user) { this.user = user; }

    }

}