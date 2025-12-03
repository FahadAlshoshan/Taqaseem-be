package com.app.taqaseem.constant;

public class Messages {
    // OTP Authentication Messages
    public static final String SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN = "Successfully authenticated";
    public static final String SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR = "تم توثيق رمز التحقق بنجاح";

    public static final String UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN = "Authentication failed";
    public static final String UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR = "فشل توثيق رمز التحقق";

    // User Name Change Messages
    public static final String SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN = "Successfully changed user's name";
    public static final String SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR = "تم تغيير اسم المستخدم بنجاح";

    public static final String UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN = "Changing user's name failed";
    public static final String UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR = "فشل تغيير اسم المستخدم";

    // Username Availability Messages
    public static final String USERNAME_AVAILABLE_MESSAGE = "Username is available";
    public static final String USERNAME_TAKEN_MESSAGE = "Username is already taken";

    // Username Validation Messages
    public static final String USERNAME_SIZE_VALIDATION_MESSAGE = "Username must be between 3 and 20 characters";
    public static final String USERNAME_PATTERN_VALIDATION_MESSAGE = "Username can only contain letters, numbers, and underscores";
    
    private Messages() {
        throw new IllegalStateException("Utility class");
    }
}
