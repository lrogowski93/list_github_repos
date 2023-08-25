package com.demo.list_github_repos.config;

import com.demo.list_github_repos.controller.response.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFound.class)
    public ErrorResponse notFoundException(NotFound ex) {
        return ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .message(ex.getResponseBodyAs(ErrorResponse.class).message())
                .build();
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> mediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
      return ResponseEntity
              .status(ex.getStatusCode())
              .contentType(MediaType.APPLICATION_JSON)
              .body(ErrorResponse.builder()
                      .status(ex.getStatusCode().value())
                      .message(ex.getMessage())
                      .build());
    }

    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(Forbidden.class)
    public ErrorResponse forbiddenException(Forbidden ex) {
        return ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .message(ex.getResponseBodyAs(ErrorResponse.class).message())
                .build();
    }

}
