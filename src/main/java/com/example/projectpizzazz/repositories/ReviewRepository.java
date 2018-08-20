package com.example.projectpizzazz.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.projectpizzazz.models.Review;


public interface ReviewRepository extends CrudRepository<Review, Integer>{
	
	@Query("SELECT r FROM Review r WHERE r.reviewerId=:owner")
	Iterable<Review> findReviewByReviewerId(
		@Param("owner") int owner);

}
