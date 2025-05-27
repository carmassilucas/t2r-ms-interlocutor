package chat.talk_to_refugee.ms_interlocutor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EmptyBodyException extends CommonException {

    @Override
    public ProblemDetail toProblemDetail() {
        var problem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problem.setTitle("body must not be null");

        return problem;
    }
}
