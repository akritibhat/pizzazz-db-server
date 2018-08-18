package com.example.projectpizzazz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	enum RESPONSE
	{
	    LIKE,UNLIKE;
	}
	private RESPONSE isLike;
	
	private String comment;
	
	
	@ManyToOne
	private Salon salon;
	
	@ManyToOne
	@JsonIgnore
	private Customer customer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RESPONSE getIsLike() {
		return isLike;
	}
	public void setIsLike(RESPONSE isLike) {
		this.isLike = isLike;
	}


	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Salon getSalon() {
		return salon;
	}
	public void setSalon(Salon salon) {
		this.salon = salon;
	}
}
