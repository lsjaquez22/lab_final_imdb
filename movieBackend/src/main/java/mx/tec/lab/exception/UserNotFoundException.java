package mx.tec.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final String notFoundMessage = "User not found";

    public UserNotFoundException() {
        super(notFoundMessage);
    }
    public UserNotFoundException(final String message) {
        super(message);
    }
}
