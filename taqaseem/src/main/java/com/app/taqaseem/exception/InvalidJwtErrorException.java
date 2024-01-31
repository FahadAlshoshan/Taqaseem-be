package com.app.taqaseem.exception;


import io.jsonwebtoken.MalformedJwtException;
import lombok.experimental.StandardException;

@StandardException
public class InvalidJwtErrorException extends RuntimeException {}
