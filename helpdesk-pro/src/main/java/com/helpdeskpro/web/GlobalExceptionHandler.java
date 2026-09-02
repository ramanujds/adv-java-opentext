package com.helpdeskpro.web;

import com.helpdeskpro.exception.TicketNotFoundException;
import com.helpdeskpro.web.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(TicketNotFoundException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var body= new ErrorResponse(
                LocalDateTime.now().toString(),
                status.value(),
                status.toString(),
                ex.toString(),
                request.getRequestURI()

        );
        return ResponseEntity.status(status).body(body);
    }

}
