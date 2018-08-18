package com.example.projectpizzazz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.projectpizzazz.models.Appointment;
import com.example.projectpizzazz.models.Salon;


public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{
	
//	@Query("SELECT a FROM Appointment a WHERE a.id=")
//	Iterable<Appointment> findApptbySalonId(@Param("salonId") int salonId);
}
