package com.example.projectpizzazz.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Salon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", updatable = true, insertable = true)
	private int id;
	
	private String yelpId;
	
	public String getYelpId() {
		return yelpId;
	}
	public void setYelpId(String yelpId) {
		this.yelpId = yelpId;
	}
	private String name;
	private String address;

	private String city;
	private float rating;
	
	private int salonOwner;
	
	private String website;
	private int phone;
	
	@OneToMany
	private List<Appointment> appointments;
	
	@OneToMany
	private List<Review> reviews;
	
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	@Lob
    @Column(name="SALON_PIC")
    private byte[] profilePic;
	/*private Map<Service,Integer> services; 
	*/
	
	public byte[] getProfilePic() {
		return profilePic;
	}
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getSalonOwner() {
		return salonOwner;
	}
	public void setSalonOwner(int salonOwner) {
		this.salonOwner = salonOwner;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}

}
