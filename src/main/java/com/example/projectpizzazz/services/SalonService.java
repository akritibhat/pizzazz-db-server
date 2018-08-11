package com.example.projectpizzazz.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Salon;
import com.example.projectpizzazz.repositories.SalonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SalonService {
	

	@Autowired
	SalonRepository repository;
	
	@DeleteMapping("/api/{salonId}/salon")
	public void deleteUser(@PathVariable("salonId") int id) {
		repository.deleteById(id);
	}

	@PostMapping("/api/salon")
	public Salon createSalon(@RequestBody Salon salon) {
		Salon salonNew = repository.save(salon);
		return salonNew;
	}


	@GetMapping("/api/{salonId}/salon")
	public Salon findSalonById(@PathVariable("salonId") int salonId) {
		Optional<Salon> data = repository.findById(salonId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/salon")
	public List<Salon> findAllSalons() {
		return (List<Salon>) repository.findAll();
	}
	
	
	@PutMapping("/api/{salonId}/salon")
	public Salon updateSalon(@PathVariable("salonId") int salonId, @RequestBody Salon newSalon) {
		Optional<Salon> data = repository.findById(salonId);
		if (data.isPresent()) {
			Salon user = data.get();
			repository.save(user);
			return user;
		}
		return null;
	}


	@GetMapping("/api/{keyWord}/salon")
	public List<Salon> findSalonByKeyWord(@PathVariable ("keyWord") String key) {
		List<Salon> data = (List<Salon>) repository.findAll();
		List<Salon> result = new ArrayList<Salon>();
		for(Salon ss : data) {
			if(ss.getName().contains(key)
					|| ss.getCity().contains(key)
					|| ss.getAddress().contains(key)) {
				result.add(ss);
			}
		}
		return result;
	}

}
