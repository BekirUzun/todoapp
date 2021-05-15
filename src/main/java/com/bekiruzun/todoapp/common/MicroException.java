package com.bekiruzun.todoapp.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Data
public class MicroException extends RuntimeException {
    Integer errorCode;
    HttpStatus status;
    Integer statusCode;
    String message;
    Throwable cause;
    String path;


    public MicroException() {
        this(-999, "An exception occurred during processing request.");
    }

    public MicroException(Integer errorCode, String message) {
        this(errorCode, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public MicroException(Integer errorCode, String message, HttpStatus status) {
        this(errorCode, message, status, null);
    }

    public MicroException(Integer errorCode, String message, HttpStatus status, @Nullable Throwable cause) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.cause = cause;
        this.statusCode = status.value();
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @JsonIgnore
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
