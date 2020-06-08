package mx.tec.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserUnauthorized extends RuntimeException {
    private static final String unauthorizedMessage = "Invalid credentials";

    public UserUnauthorized() {
        super(unauthorizedMessage);
    }
    public UserUnauthorized(final String message) {
        super(message);
    }
}
