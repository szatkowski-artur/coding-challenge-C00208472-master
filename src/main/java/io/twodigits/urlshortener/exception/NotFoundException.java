package io.twodigits.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends NoSuchElementException {

    public NotFoundException(String message) {
        super(message);
    }


}
