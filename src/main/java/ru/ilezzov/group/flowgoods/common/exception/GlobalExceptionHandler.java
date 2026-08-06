package ru.ilezzov.group.flowgoods.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.text.MessageFormat;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ExceptionProperties exceptionProperties;

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleAppException(BusinessException ex, HttpServletRequest request) {
        final ExceptionProperties.ErrorConfig config = exceptionProperties.errors().get(ex.getErrorCode());

        if (config == null) {
            return ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unknown error code: " + ex.getErrorCode()
            );
        }

        final String formattedMessage = MessageFormat.format(config.message(), ex.getArgs());

        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.valueOf(config.status()),
                formattedMessage
        );

        problem.setType(URI.create(config.type()));
        problem.setTitle(config.title());
        problem.setInstance(URI.create(request.getRequestURI()));

        problem.setProperty("error_code", ex.getErrorCode());

        return problem;
    }
}
