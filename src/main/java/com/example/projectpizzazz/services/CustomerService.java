package com.example.projectpizzazz.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectpizzazz.models.Customer;
import com.example.projectpizzazz.models.Review;
import com.example.projectpizzazz.models.Salon;
import com.example.projectpizzazz.repositories.CustomerRepository;
import com.example.projectpizzazz.repositories.ReviewRepository;
import com.example.projectpizzazz.repositories.SalonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class CustomerService {
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	SalonRepository salonRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@DeleteMapping("/api/user/{userId}")
	public Customer deleteUser(@PathVariable("userId") int id) {
		Optional<Salon> ss = salonRepository.findSalonByOwner(id);
		if(ss.isPresent()) {
			salonRepository.delete(	ss.get());
		}
		Iterable<Review> rr = reviewRepository.findReviewByReviewerId(id);
		Iterator<Review> iter = rr.iterator();

		while(iter.hasNext()) {
			reviewRepository.delete(iter.next());
		}
		repository.deleteById(id);
		return null;	
	}

	@PostMapping("/api/user")
	public Customer createUser(@RequestBody Customer user, HttpSession session) {
		user.setImage("https://static.thenounproject.com/png/1095867-200.png");
		user.setStatus("Hi, I am new to PiZZazz!!!");
		if(user!=null && user.getRole()!=null && user.getRole().equalsIgnoreCase("true")) {
			user.setRole("owner");
		}
		if(user!=null && user.getRole()!=null && user.getRole().equalsIgnoreCase("reviewer")) {
			user.setStatus("Verified Pizzazz Reviewer");
		}
		Customer cu = repository.save(user);
		session.setAttribute("currentCustomer", cu);
		return cu;
	}

	@GetMapping("/api/checkLogin")
	public Customer checkLogin(HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		if(currentUser == null )
			return new Customer();
		return findUserById(currentUser.getId());
	}

	@PostMapping("/api/login")
	public List<Customer> login(@RequestBody Customer user, HttpSession session) {
		System.out.println(checkLogin(session).getUsername());
		return (List<Customer>) repository.findCustomerByCredentials(user.getUsername(), user.getPassword());
	}

	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@PostMapping("/api/username")
	public Customer findUserByUsernamePassword(@RequestBody Customer user, HttpSession session) {
		Customer cu = repository.findCustomer(user.getUsername(), user.getPassword());
		if(cu !=null) {
		session.setAttribute("currentCustomer", cu);
		return cu;
		}
		else {
			return new Customer();
		}

	}

	@GetMapping("/api/user")
	public List<Customer> findAllUsers() {
		return (List<Customer>) repository.findAll();
	}

	@GetMapping("/api/reviewers")
	public List<Customer> findAllReviewers() {
		return (List<Customer>) repository.findAllReviewers();
	}
	
	@GetMapping("/api/owners")
	public List<Customer> findAllOwners() {
		return (List<Customer>) repository.findAllOwners();
	}
	
	@GetMapping("/api/customers")
	public List<Customer> findAllCustomers() {
		List<Customer> res =  (List<Customer>) repository.findAll();
		List<Customer> ans = new ArrayList<Customer>();
		for(Customer result : res) {
			if(result.getRole()==null || (result.getRole()!=null && result.getRole().trim().equalsIgnoreCase("")))
				ans.add(result);
		}
		return ans;
	}
	
	@PutMapping("/api/user/{userId}")
	public Customer updateUser(@PathVariable("userId") int userId, @RequestBody Customer newUser) {
		Optional<Customer> data = repository.findById(userId);
		if (data.isPresent()) {
			Customer user = data.get();
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setEmail(newUser.getEmail());
			user.setPhone(newUser.getPhone());
			user.setStatus(newUser.getStatus());
			user.setPhone(newUser.getPhone());
			user.setImage(newUser.getImage());
			if(user.getImage()==null || user.getImage().equalsIgnoreCase(""))
				user.setImage("https://static.thenounproject.com/png/1095867-200.png");
			repository.save(user);
			return user;
		}
		return null;
	}
	
	@PutMapping("/api/userFromAdmin/{userId}")
	public Customer updateUserFromAdmin(@PathVariable("userId") int userId, @RequestBody Customer newUser) {
		Optional<Customer> data = repository.findById(userId);
		if (data.isPresent()) {
			Customer user = data.get();
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setEmail(newUser.getEmail());
			repository.save(user);
			return user;
		}
		return null;
	}

	@PutMapping("/api/profile")
	public Customer updateProfile(@RequestBody Customer newUser, HttpSession session) {
		Customer currentUser = (Customer) session.getAttribute("currentCustomer");
		Customer user = findUserById(currentUser.getId());
		if (user != null) {
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setEmail(newUser.getEmail());
			user.setDateOfBirth(newUser.getDateOfBirth());
			user.setPhone(newUser.getPhone());
			user.setRole(newUser.getRole());
			repository.save(user);
			return user;
		}
		return null;
	}

	@PutMapping("/api/user/admin/{userId}")
	public Customer updateUserProfile(@PathVariable("userId") int userId, @RequestBody Customer newUser) {
		Optional<Customer> data = repository.findById(userId);
		if (data.isPresent()) {
			Customer user = data.get();
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			user.setEmail(newUser.getEmail());
			user.setPhone(newUser.getPhone());
			user.setDateOfBirth(newUser.getDateOfBirth());
			repository.save(user);
			return user;
		}
		return null;
	}

	@GetMapping("/api/user/{userId}")
	public Customer findUserById(@PathVariable("userId") int userId) {
		Optional<Customer> data = repository.findById(userId);
		if (data.isPresent()) {
			return data.get();
		}
		return new Customer();
	}

	@GetMapping("/api/user/{userName}/username")
	public Iterable<Customer> findUserByUsername(@PathVariable("userName") String username) {
		return repository.findCustomerByUsername(username);
	}


}