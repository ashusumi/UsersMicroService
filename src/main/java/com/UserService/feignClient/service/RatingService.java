package com.UserService.feignClient.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.UserService.Model.Ratings;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
	
	@GetMapping("/ratings/user/{id}")
	List<Ratings> getRatings(@PathVariable("id") Integer id);
	
	@PostMapping("/ratings/addAllRatings")
	public String addRatings(@RequestBody List<Ratings> ratings);
}
