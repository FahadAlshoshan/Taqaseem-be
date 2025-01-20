package com.app.taqaseem.otp;

//TODO: replace this retarded logic later
public enum StatusEnum {
    SUCCESS(200),
    FAILURE(203),
    FIRST_TIME(204);

    public final int value;

    StatusEnum(int value) {
        this.value = value;
    }

}
