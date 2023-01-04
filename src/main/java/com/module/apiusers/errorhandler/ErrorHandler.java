package com.module.apiusers.errorhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.module.apiusers.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest httpServletRequest;

    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

//    @ExceptionHandler({
//            UnauthorizedException.class
//    })
//    public ResponseEntity<ApiErrorResponse> handle(UnauthorizedException ex) {
//        log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
//        return buildResponseError(HttpStatus.UNAUTHORIZED, ex, ex.getCode());
//    }
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ApiErrorResponse> handle(MethodArgumentNotValidException ex) {
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handle(Throwable ex) {
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex, ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handle(BusinessException ex) {
        return buildResponseError(HttpStatus.CONFLICT, ex, ex.getCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handle(MissingServletRequestParameterException ex) {
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseError(HttpStatus httpStatus, Throwable ex, ErrorCode errorCode) {
        Map<String,String> map = new HashMap<>();
        
        if ( ex instanceof MethodArgumentNotValidException){
            final List<FieldError> errors = ((MethodArgumentNotValidException) ex).getFieldErrors();
            map = errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        } else {
            map.put(ex.getClass().getCanonicalName(), ex.getMessage());
        }
        
        final var apiErrorResponse = ApiErrorResponse
                .builder()
                .name(httpStatus.getReasonPhrase())
                .detail(map)
                .status(httpStatus.value())
                .message(errorCode.getReasonPhrase())
                .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @Builder
    @NonNull
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    private static class ApiErrorResponse {

        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private String name;
        @JsonProperty
        private Integer status;
        @JsonProperty
        private String message;
        @JsonProperty
        private Map<String, String> detail;
    }
}

