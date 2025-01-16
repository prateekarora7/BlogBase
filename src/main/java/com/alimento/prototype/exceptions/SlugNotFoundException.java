package com.alimento.prototype.exceptions;

public class SlugNotFoundException extends RuntimeException{
    public SlugNotFoundException(String message){
        super(message);
    }
}
