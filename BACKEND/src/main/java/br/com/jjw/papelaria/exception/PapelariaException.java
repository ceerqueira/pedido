package br.com.jjw.papelaria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PapelariaException extends RuntimeException {

    public PapelariaException(String message) {
        super(message);
    }
}
