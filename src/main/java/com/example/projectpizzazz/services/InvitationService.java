package com.example.projectpizzazz.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Invitation;
import com.example.projectpizzazz.repositories.InvitationRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class InvitationService {

	@Autowired
	InvitationRepository repository;
	
	
	@PostMapping("/api/invite")
	public Invitation createInvite(@RequestBody Invitation invite, HttpSession session) {
		Invitation cu = repository.save(invite);
		return cu;
	}

}
