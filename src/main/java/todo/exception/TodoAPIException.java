package todo.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class TodoAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

}
