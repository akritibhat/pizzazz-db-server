package com.example.projectpizzazz.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.projectpizzazz.models.Customer;;

public interface CustomerRepository 
	extends CrudRepository<Customer, Integer>{
	
	@Query("SELECT u FROM Customer u WHERE u.username=:username AND u.password=:password")
	Iterable<Customer> findCustomerByCredentials(
		@Param("username") String username, 
		@Param("password") String password);

	
	@Query("SELECT u FROM Customer u WHERE u.username=:username AND u.password=:password")
	Customer findCustomer(
		@Param("username") String username, 
		@Param("password") String password);
	
	@Query("SELECT u FROM Customer u WHERE u.username=:username")
	Customer findCustomerByUsername(
		@Param("username") String username);
	
	@Query("SELECT u FROM Customer u WHERE u.role='reviewer'")
	Iterable<Customer> findAllReviewers();
	
	@Query("SELECT u FROM Customer u WHERE u.role='owner'")
	Iterable<Customer> findAllOwners();
}
