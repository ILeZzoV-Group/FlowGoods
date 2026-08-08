package ru.ilezzov.group.flowgoods.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

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


    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        final List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "reason", error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value"
                ))
                .toList();

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Your request parameters didn't validate."
        );

        problemDetail.setType(URI.create("https://api.ilezzov.ru/errors/validation-error"));
        problemDetail.setTitle("Validation Failed");
        problemDetail.setProperty("invalid_params", invalidParams);

        problemDetail.setProperty("error_code", "validation-failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
