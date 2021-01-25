package com.space.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handle(Exception e, WebRequest rq){
        if (e instanceof NotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        if (e instanceof InvalidData) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        else return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(e);
    }
}
