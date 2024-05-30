package com.service.unischeduleservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = getBadRequestResponseError(webRequest);
        errorResponse.setMessage(Arrays.toString(ex.getDetailMessageArguments()));
        return errorResponse;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = getBadRequestResponseError(webRequest);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = getBadRequestResponseError(webRequest);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodValidationException(HandlerMethodValidationException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = getBadRequestResponseError(webRequest);
        errorResponse.setMessage(Arrays.toString(ex.getDetailMessageArguments()));
        return errorResponse;
    }

    private ErrorResponse getBadRequestResponseError(WebRequest webRequest) {
        return ErrorResponse.builder()
                .timeStamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(getPathWebRequest(webRequest))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMethodResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        return ErrorResponse.builder()
                .timeStamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .path(getPathWebRequest(webRequest))
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    private String getPathWebRequest(WebRequest webRequest) {
        return webRequest.getDescription(false).replace("uri=", "");
    }
}
