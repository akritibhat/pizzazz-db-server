package com.example.projectpizzazz.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.projectpizzazz.models.Review;


public interface ReviewRepository extends CrudRepository<Review, Integer>{
	

}
