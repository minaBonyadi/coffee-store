package com.bestseller.coffeestore.exception;

import com.bestseller.coffeestore.restresponse.RestResponse;
import com.bestseller.coffeestore.restresponse.RestResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DrinkNotFoundException.class})
    protected ResponseEntity<RestResponse> handleNotFoundException(DrinkNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, "drink not found"));
    }

    @ExceptionHandler(value = {ToppingNotFoundException.class})
    protected ResponseEntity<RestResponse> handleUserLogicalException(ToppingNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, "topping not found"));
    }

}
