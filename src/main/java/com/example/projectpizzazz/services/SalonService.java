package com.example.projectpizzazz.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Customer;
import com.example.projectpizzazz.models.Salon;
import com.example.projectpizzazz.repositories.CustomerRepository;
import com.example.projectpizzazz.repositories.SalonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class SalonService {
	

	@Autowired
	SalonRepository repository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@DeleteMapping("/api/{salonId}/salon")
	public void deleteSalon(@PathVariable("salonId") int id) {
		repository.deleteById(id);
	}

	@PostMapping("/api/salon")
	public Salon createSalon(@RequestBody Salon salon , HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		salon.setSalonOwner(currentUser.getId());
		Salon salonNew = repository.save(salon);
		return salonNew;
	}
	
	@PostMapping("/api/{owner}/salonFromAdmin")
	public Salon createFromScreen(@PathVariable ("owner") int owner,@RequestBody Salon salon , HttpSession session) {
		Optional<Customer> cu = customerRepository.findById(owner);
		if(cu.isPresent()) {
			salon.setSalonOwner(owner);
			salon.setYelpId(salon.getWebsite());
			repository.save(salon);
		}
		return new Salon();
	}
	
	@GetMapping("/api/salonsFromAdmin")
	public List<Salon> findAllSalonsFromAdmin() {
		List<Salon> temp = (List<Salon>) repository.findAll();
		List<Salon> res = new ArrayList<Salon>();
		
		for(Salon ss : temp) {
			if(ss.getYelpId() !=null && ss.getWebsite()!=null
					&& ss.getYelpId().equalsIgnoreCase(ss.getWebsite())) {
				res.add(ss);
			}
		}
		
		return res;
	}
	
	@PostMapping("/api/salonforApi/{salonId}/{name}")
	public Salon createApiSalon(@PathVariable ("salonId") String salonId, @PathVariable("name") String name) {
		Salon newSalon = new Salon();
		newSalon.setYelpId(salonId);
		newSalon.setName(name);
		return repository.save(newSalon);
	}


	@GetMapping("/api/salon/{salonId}")
	public Salon findSalonById(@PathVariable("salonId") int salonId) {
		Optional<Salon> data = repository.findById(salonId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/salonApi/{salonId}")
	public Salon findSalonByAnyId(@PathVariable("salonId") String salonId, HttpServletResponse response) {
		Optional<Salon> data = repository.findSalonByYelpId(salonId);
		if (data.isPresent()) {
			response.setStatus(HttpServletResponse.SC_OK);
			return data.get();
		}
		return new Salon();
	}
	
	@GetMapping("/api/{owner}/salonOwner")
	public Salon findSalonByOwner(@PathVariable("owner") int owner) {
		Optional<Salon> data = repository.findSalonByOwner(owner);
		if (data.isPresent()) {
			return data.get();
		}
		return new Salon();
	}
	
	@GetMapping("/api/checkSalon")
	public Salon checkSalon(HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		return findSalonByOwner(currentUser.getId());
	}
	
	@GetMapping("/api/salon")
	public List<Salon> findAllSalons() {
		return (List<Salon>) repository.findAll();
	}
	
	
	@PutMapping("/api/salon/{salonId}")
	public Salon updateSalon(@PathVariable("salonId") int salonId, @RequestBody Salon newSalon) {
		Optional<Salon> data = repository.findById(salonId);
		if (data.isPresent()) {
			Salon salon = data.get();
			salon.setName(newSalon.getName());
			salon.setAddress(newSalon.getAddress());
			salon.setCity(newSalon.getCity());
			salon.setWebsite(newSalon.getCity());
			repository.save(salon);
			return salon;
		}
		return null;
	}


	@GetMapping("/api/{keyWord}/salon")
	public List<Salon> findSalonByKeyWord(@PathVariable ("keyWord") String key) {
		List<Salon> data = (List<Salon>) repository.findAll();
		List<Salon> result = new ArrayList<Salon>();
		for(Salon ss : data) {
			if(ss.getName().toLowerCase().contains(key.toLowerCase())
					|| ss.getCity().toLowerCase().contains(key.toLowerCase())
					|| ss.getAddress().toLowerCase().contains(key.toLowerCase())){
				result.add(ss);
			}
		}
		return result;
	}

}
