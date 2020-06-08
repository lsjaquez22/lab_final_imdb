package mx.tec.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserSQLException extends RuntimeException {
    public UserSQLException(final String message) {
        super(message);
    }
}
