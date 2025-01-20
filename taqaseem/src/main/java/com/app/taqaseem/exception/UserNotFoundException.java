package com.app.taqaseem.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    public String messageAr;
    public String messageEn;
    public UserNotFoundException(String messageAr, String messageEn) {
        this.messageAr = messageAr;
        this.messageEn = messageEn;
    }
}
