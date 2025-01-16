package com.alimento.prototype.exceptions;

public class BlogIdNotFoundException extends RuntimeException{
    public BlogIdNotFoundException(String message){
        super(message);
    }
}
