package com.wepat.gcp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FileWriteException extends RuntimeException{

    private final String message;

    public FileWriteException(String message) {
        super(message);
        this.message = message;
    }
}