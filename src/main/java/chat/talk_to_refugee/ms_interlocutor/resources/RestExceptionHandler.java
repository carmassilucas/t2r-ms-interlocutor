package chat.talk_to_refugee.ms_interlocutor.resources;

import chat.talk_to_refugee.ms_interlocutor.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ProblemDetail commonException(CommonException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(field -> new InvalidParam(field.getField(), field.getDefaultMessage()));

        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("your request parameters didn't validate");
        problem.setProperty("invalid-params", errors);

        return problem;
    }

    private record InvalidParam(String name, String reason) {

    }
}
