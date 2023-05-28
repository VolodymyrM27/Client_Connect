package com.motrechko.clientconnect.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        log.error("Username not found exception: {}", ex.getMessage());
        ApiError errorDetails = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class, AccountRoleException.class})
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Access denied exception: {}", ex.getMessage());
        ApiError errorDetails = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({EmailExistException.class, UserProfileAlreadyExistsException.class, TemplateCreationException.class,
            TemplateRequirementEmptyException.class, TemplateDoesNotBelongException.class, UnsupportedRequirementException.class})
    public ResponseEntity<ApiError> handleBadRequestExceptions(Exception ex, HttpServletRequest request) {
        log.error("Bad Request Exception: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({UserProfileNotFoundException.class, CategoryNotFoundException.class,
            TemplateNotFound.class, BusinessNotFoundException.class, RequirementNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        log.error("Not found exception: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ApiError(
                        request.getRequestURI(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()
                ), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("Validation exception: {}", errorMessage);
        return new ResponseEntity<>(
                new ApiError(
                        request.getRequestURI(),
                        errorMessage,
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("Http message not readable exception: {}", ex.getMessage());

        // Extract the most relevant error message
        Throwable cause = ex.getCause();
        String message = (cause instanceof InvalidFormatException) ? cause.getLocalizedMessage() : ex.getMessage();

        ApiError errorDetails = new ApiError(
                request.getRequestURI(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception: {}", ex.getMessage());
        ApiError errorDetails = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
