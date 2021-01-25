package com.space.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidData extends RuntimeException{
    public InvalidData(String message) {
        super(message);
    }
}
