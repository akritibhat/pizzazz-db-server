package com.example.projectpizzazz.services;

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
import com.example.projectpizzazz.repositories.CustomerRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600 ,  allowCredentials = "true")
public class CustomerService {
	@Autowired
	CustomerRepository repository;
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
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
			if(user.getImage()==null || user.getImage().equalsIgnoreCase("")) {
				user.setImage("https://static.thenounproject.com/png/1095867-200.png");
			}
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
		return null;
	}

	@GetMapping("/api/username/{userName}")
	public Iterable<Customer> findUserByUsername(@PathVariable("userName") String username) {
		return repository.findCustomerByUsername(username);
	}


}