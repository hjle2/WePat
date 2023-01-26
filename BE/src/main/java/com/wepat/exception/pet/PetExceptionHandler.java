package com.wepat.exception.pet;

import com.wepat.controller.PetController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {PetController.class})
public class PetExceptionHandler {

}
