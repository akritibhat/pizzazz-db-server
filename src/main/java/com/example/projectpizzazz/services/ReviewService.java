package com.example.projectpizzazz.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600 ,  allowCredentials = "true")
public class ReviewService {
	

}
