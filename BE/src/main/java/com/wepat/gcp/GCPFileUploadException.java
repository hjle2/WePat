package com.wepat.gcp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GCPFileUploadException extends RuntimeException{

    private final String message;

    public GCPFileUploadException(String message) {
        super(message);
        this.message = message;
    }
}