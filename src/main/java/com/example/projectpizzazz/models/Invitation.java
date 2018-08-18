package com.example.projectpizzazz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Invitation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Customer salonOwner;
	
	@ManyToOne
	private Customer reviewer;
	
	public Customer getSalonOwner() {
		return salonOwner;
	}
	public void setSalonOwner(Customer salonOwner) {
		this.salonOwner = salonOwner;
	}
	public Customer getReviewer() {
		return reviewer;
	}
	public void setReviewer(Customer reviewer) {
		this.reviewer = reviewer;
	}
	

}
