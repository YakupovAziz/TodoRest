package todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TodoAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public TodoAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
