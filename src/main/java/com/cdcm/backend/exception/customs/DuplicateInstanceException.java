package com.cdcm.backend.exception.customs;

import org.springframework.http.HttpStatus;

public class DuplicateInstanceException extends CustomException {
    public DuplicateInstanceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
