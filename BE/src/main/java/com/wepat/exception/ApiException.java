package com.wepat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApiException {

    private String errorMessage;
    private Integer statusCode;
    private ZonedDateTime zonedDateTime;
}