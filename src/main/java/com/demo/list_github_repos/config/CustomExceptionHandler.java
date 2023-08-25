package com.demo.list_github_repos.config;

import com.demo.list_github_repos.controller.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFound.class)
    public ErrorResponse notFoundException(NotFound ex) {
        return ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .message(ex.getMessage())
                .build();
    }
}
