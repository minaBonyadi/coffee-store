package com.bestseller.coffeestore.exception;

public class PhoneNumberNotFoundException extends RuntimeException{
    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
