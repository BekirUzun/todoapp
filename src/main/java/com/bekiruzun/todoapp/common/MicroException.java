package com.bekiruzun.todoapp.common;

//import lombok.Data;

//@Data
public class MicroException extends RuntimeException  {
    Long code;

    public MicroException() {
        super();
    }

    public MicroException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public MicroException(Long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
