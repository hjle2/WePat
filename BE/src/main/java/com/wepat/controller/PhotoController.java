package com.wepat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PhotoController {
}
