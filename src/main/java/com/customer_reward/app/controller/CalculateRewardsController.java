package com.customer_reward.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer_reward.app.exception.CustomerNotFoundException;
import com.customer_reward.app.model.Customers;
import com.customer_reward.app.service.CalculateRewardsService;

@RestController
@RequestMapping("/customers")
public class CalculateRewardsController {
	private static final Logger logger = LoggerFactory.getLogger(CalculateRewardsController.class);

	@Autowired
	private CalculateRewardsService calculateRewardsService;

	/**
	 * Endpoint to fetch rewards data for all customers.
	 * 
	 * @return a map with customer names as keys and their monthly reward points as
	 *         values.
	 */
	@GetMapping("/rewards")
	public ResponseEntity<Map<String, Map<String, Integer>>> getRewardsForAllCustomers() {
		logger.info("Fetching rewards for all customsers");

		Map<String, Map<String, Integer>> rewards = calculateRewardsService.getRewards();
		return new ResponseEntity<>(rewards, HttpStatus.OK);
	}

	/**
	 * Endpoint to fetch rewards data for a specific customer by their ID.
	 *
	 * @param customerId the ID of the customer
	 * @return a ResponseEntity with the customer's reward points or a not found
	 *         status
	 */
	@GetMapping("/rewards/{customerId}")
	public ResponseEntity<Map<String, Map<String, Integer>>> getRewardsByCustomerId(@PathVariable Integer customerId) {
		logger.info("Fetching rewards data for customer ID: {}", customerId);
		if (customerId == null) {
			throw new CustomerNotFoundException("Requested customer id is null");
		}
		// Retrieving customer by id
		Customers customer = calculateRewardsService.getCustomerById(customerId);
		// Calling reward calculation method
		Map<String, Map<String, Integer>> customerRewards = calculateRewardsService
				.calculateRewardsForCustomer(customer);
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

}
