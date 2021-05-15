package com.bekiruzun.todoapp.config;

import com.bekiruzun.todoapp.common.MicroException;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MicroExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(MicroExceptionHandler.class);

    @ExceptionHandler(value = {MicroException.class})
    public ResponseEntity<Object> handleMicroException(MicroException ex, WebRequest request) {
        if (request instanceof ServletWebRequest) {
            ex.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        }

        log.error("MicroExceptionHandler caught an exception: ", ex);

        return handleExceptionInternal(ex, ex, new HttpHeaders(), ex.getStatus(), request);
    }

}
