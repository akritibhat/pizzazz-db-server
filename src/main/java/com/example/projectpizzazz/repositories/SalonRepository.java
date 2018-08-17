package com.example.projectpizzazz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.projectpizzazz.models.Salon;

public interface SalonRepository extends CrudRepository<Salon, Integer>{
	
	@Query("SELECT s FROM Salon s WHERE s.salonOwner=:owner")
	Optional<Salon> findSalonByOwner(
		@Param("owner") int owner);
}
