package fr.koi.wikiapi.config;

import fr.koi.wikiapi.dto.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * To handle all not managed throwables.
     *
     * @return The corresponding problem detail
     */
    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleThrowable() {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("error.internal-server");
        problem.setDetail("Internal server error");

        return problem;
    }

    /**
     * Handle all koi-wiki exceptions.
     *
     * @param e The exception
     *
     * @return The corresponding problem detail
     */
    @ExceptionHandler(BaseException.class)
    public ProblemDetail handleBaseException(final BaseException e) {
        ProblemDetail problem = ProblemDetail.forStatus(e.getStatus());

        problem.setTitle(e.getKey());
        problem.setDetail(e.getDetail());

        return problem;
    }
}
