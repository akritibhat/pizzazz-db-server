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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Appointment;
import com.example.projectpizzazz.models.Customer;
import com.example.projectpizzazz.models.Review;
import com.example.projectpizzazz.models.Salon;
import com.example.projectpizzazz.repositories.SalonRepository;
import com.example.projectpizzazz.repositories.CustomerRepository;
import com.example.projectpizzazz.repositories.ReviewRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class ReviewService {
	
	@Autowired
	ReviewRepository ReviewRepository;
	
	@Autowired
	SalonRepository salonRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping("/api/review")
	public Review createReview(@RequestBody Review review , HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		review.setReviewerId(currentUser.getId());
		
		if(review.getSalon()!=null)
		review.setSalonReviewedId(review.getSalon().getId());
		review.setSalon(null);
		Review newReview = ReviewRepository.save(review);
		return newReview;
	}
	
	@GetMapping("/api/reviews")
	public List<Review> getUserReviews(HttpSession session){
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		return currentUser.getReviews();
	}
	
	@GetMapping("/api/salon/{salonId}/reviews")
	public Iterable<Review> getSalonReviews(@PathVariable ("salonId") int salonId ){
		Optional<Salon> salon = salonRepository.findById(salonId);
		List<Review> res = new ArrayList<Review>();
		if (salon.isPresent()) {
			Iterable<Review> a = ReviewRepository.findAll();
			Iterator<Review> i = a.iterator();
			
			while(i.hasNext()) {
				Review temp = i.next();
				if(temp.getSalon()!= null && temp.getSalon().getId() == salonId) {
					res.add(temp);
				}
			}
			return res;
		}
		return null;
	}
	
	@PutMapping("/api/reviews")
	public List<Review> updateReviews(@RequestBody Iterable<Review> appts) {
		List<Review> addedApts = new ArrayList<>();
		for(Review app : appts)
			{
			if(app.getSalon()!=null)
				app.setSalonReviewedId(app.getSalon().getId());
			app.setSalon(null);
			if(app.getCustomer()!=null) {
				Customer cu = app.getCustomer();
				app.setReviewerId(cu.getId());
				app.setCustomer(null);
				Review newReview = ReviewRepository.save(app);
				List<Review> rs = cu.getReviews();
				rs.add(newReview);
				cu.setReviews(rs);
				customerRepository.save(cu);
				addedApts.add(newReview);
			}else {
				addedApts.add(ReviewRepository.save(app));
			}
			}
				return addedApts;
	}

}
