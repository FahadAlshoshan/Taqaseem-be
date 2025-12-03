package com.app.taqaseem.constant;

public class ValidationConstants {
    
    // Username Validation
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
    
    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
