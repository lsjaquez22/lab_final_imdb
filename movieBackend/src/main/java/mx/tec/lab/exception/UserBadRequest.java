package mx.tec.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserBadRequest extends RuntimeException {
    private static final String errorMsg = "Missing params";

    public UserBadRequest() {
        super(errorMsg);
    }
    public UserBadRequest(final String message) {
        super(message);
    }
}
