package com.wepat.gcp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InvalidFileTypeException extends RuntimeException{
    private final String message;

    public InvalidFileTypeException(String message) {
        super(message);
        this.message = message;
    }
}