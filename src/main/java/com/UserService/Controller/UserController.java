package com.UserService.Controller;

import java.util.List;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserService.Model.Users;
import com.UserService.ServiceImpl.UsersServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UsersServiceImpl impl;

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		try {
			return new ResponseEntity<List<Users>>(impl.getAllUsers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasAuthority('SCOPE_email')")
	@GetMapping("/user/{id}")
//	@CircuitBreaker(name = "RatingHotelByUserIdBraker", fallbackMethod = "fallbackHotelRatingByUidDown")
//	@Retry(name = "RatingHotelRetry",fallbackMethod = "fallbackHotelRatingByUidDown")
//	@RateLimiter(name = "UserRateLimiter",fallbackMethod = "fallbackHotelRatingByUidDown")
	public ResponseEntity<Users> getUserById(@PathVariable Integer id) {

		return ResponseEntity.status(HttpStatus.OK).body(impl.getUserById(id));
	}

	public ResponseEntity<?> fallbackHotelRatingByUidDown(Integer userId, Throwable throwable) {
		Users users = new Users();
		users.setId(0);
		users.setName("Test");
		users.setMiddelName("TestM");
		users.setSirName("TestS");
		users.setAddress("TestAddress");
		users.setMobile(939393939);
		logger.warn("Falling back due to exception in getUserById() rating or hotel service is down");
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@PostMapping("/addUser")
	public ResponseEntity<?> addUsers(@RequestBody Users entity) {
		try {

			return new ResponseEntity<String>(impl.addUsers(entity), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}

	}

	@PutMapping("update/{id}")
	public ResponseEntity<String> updateUsers(@PathVariable Integer id, @RequestBody Users users) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(impl.updateUsers(id, users));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteUsers(@PathVariable Integer id) {
		try {
			return new ResponseEntity<String>(impl.deleteUserById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
