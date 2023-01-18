package com.wepat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);
}
