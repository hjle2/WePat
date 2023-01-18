package com.wepat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PhotoController {
    private final Logger logger = LoggerFactory.getLogger(PhotoController.class);
}
