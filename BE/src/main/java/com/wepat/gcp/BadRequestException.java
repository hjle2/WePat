package com.wepat.gcp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BadRequestException extends RuntimeException{
    private final String message;
    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }
}