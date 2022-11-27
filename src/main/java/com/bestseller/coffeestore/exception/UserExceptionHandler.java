package com.bestseller.coffeestore.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = {UserNotFoundException.class})
//    protected ResponseEntity<RestResponse> handleNotFoundException(UserNotFoundException ex, WebRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
//    }
//
//    @ExceptionHandler(value = {UserLogicalException.class})
//    protected ResponseEntity<RestResponse> handleUserLogicalException(UserLogicalException ex, WebRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
//    }
//
//    @ExceptionHandler(value = {EmailNotFoundException.class})
//    protected ResponseEntity<RestResponse> handleEmailNotFoundException(EmailNotFoundException ex, WebRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
//    }
//
//    @ExceptionHandler(value = {PhoneNumberNotFoundException.class})
//    protected ResponseEntity<RestResponse> handlePhoneNumberNotFoundException(PhoneNumberNotFoundException ex, WebRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
//    }
}
