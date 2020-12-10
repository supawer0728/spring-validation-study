package org.parfait.study.springvalidation.error;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.parfait.study.springvalidation.error.ErrorResponse.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdviceImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handleUnknownException(Exception e) {
        log.error("unknown exception", e);
        return new ResponseEntity<>(ErrorResponse.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest webRequest) {
        Map<String, String> errors = ex.getFieldErrors()
                                       .stream()
                                       .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
        HttpServletRequest request = ((ServletWebRequest) webRequest).getNativeRequest(HttpServletRequest.class);
        log.warn("MethodArgumentNotValidException - uri:{} {}, target:{}, errors:{}", request.getMethod(), request.getRequestURI(), ex.getTarget(), errors);
        return new ResponseEntity<>(new ErrorResponse<>(ErrorCode.INVALID_PARAMETERS, errors), HttpStatus.BAD_REQUEST);
    }
}
