package com.example.test.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    //Http Status Code
    //2xx - > OK
    //4xx - > Client 문제
    //5xx - > Server측 문제
    public UserNotFoundException(String message) {
        super(message);
    }
}
