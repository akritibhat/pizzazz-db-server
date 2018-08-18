package com.example.projectpizzazz.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Appointment;
import com.example.projectpizzazz.models.Customer;
import com.example.projectpizzazz.repositories.AppointmentRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class AppointmentService {
	
	@Autowired
	AppointmentRepository repository;
	
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
	
}
