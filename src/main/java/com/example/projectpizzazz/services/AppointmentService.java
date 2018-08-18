package com.example.projectpizzazz.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Appointment;
import com.example.projectpizzazz.models.Customer;
import com.example.projectpizzazz.models.Salon;
import com.example.projectpizzazz.repositories.AppointmentRepository;
import com.example.projectpizzazz.repositories.SalonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class AppointmentService {
	
	@Autowired
	AppointmentRepository repository;
	
	@Autowired
	SalonRepository salonRepository;
	
	@PostMapping("/api/appointment")
	public Appointment createAppointment(@RequestBody Appointment appointment , HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		appointment.setCustomer(currentUser);
		Appointment newAppointment = repository.save(appointment);
		return newAppointment;
	}

	@GetMapping("/api/appointments")
	public List<Appointment> getUserAppointments(HttpSession session){
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		return currentUser.getAppointments();
	}
	/*
	@GetMapping("/api/salon/{salonId}/appointments")
	public Iterable<Appointment> getSalonAppointments(@PathVariable ("salonId") int salonId ){
		Optional<Salon> salon = salonRepository.findById(salonId);
		if (salon.isPresent()) {
//			return repository.findApptbySalonId(salonId);
		}
		return null;
	}*/
	
	@GetMapping("/api/salon/{salonId}/appointments")
	public Iterable<Appointment> getSalonAppointment(@PathVariable ("salonId") int salonId ){
		Optional<Salon> salon = salonRepository.findById(salonId);
		List<Appointment>res = new ArrayList<Appointment>();
		if (salon.isPresent()) {
			Iterable<Appointment> a = repository.findAll();
			Iterator<Appointment> i = a.iterator();
			
			while(i.hasNext()) {
				Appointment temp = i.next();
				if(temp.getSalon().getId() == salonId) {
					res.add(temp);
				}
			}
			return res;
//			return repository.findApptbySalonId(salonId);
		}
		return null;
	}
}
