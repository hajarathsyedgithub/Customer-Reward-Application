package com.customer_reward.app.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
import com.customer_reward.app.model.Transactions;
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
	public Map<String, Map<String, Integer>> getRewards() {
		logger.info("Fetching rewards data");
		List<Customers> customers = getSampleData();
		return calculateRewardsService.calculateRewards(customers);
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
		// Reading data sets
		List<Customers> customers = getSampleData();

		// Retrieving customer id
		Customers customer = customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst()
				.orElseThrow(() -> new CustomerNotFoundException(
						"Requested customer id is  not found. Customer ID: " + customerId));
		// Calling reward calculation method
		Map<String, Map<String, Integer>> customerRewards = calculateRewardsService
				.calculateRewardsForCustomer(customer);
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);

	}

	/**
	 * Creates sample data for demonstration purposes.
	 * 
	 * @return a list of sample customers with transactions.
	 */
	private List<Customers> getSampleData() {

		// Data sets for demonstration
		Customers customer1 = new Customers();
		customer1.setCustomerId(10);
		customer1.setCustomerName("James Brown");
		customer1.setTransactions(Arrays.asList(new Transactions(100, 120.0, LocalDate.of(2024, 1, 4)),
				new Transactions(101, 100.0, LocalDate.of(2024, 1, 5))));

		Customers customer2 = new Customers();
		customer2.setCustomerId(11);
		customer2.setCustomerName("Michael Phelps");
		customer2.setTransactions(Arrays.asList(new Transactions(102, 50.0, LocalDate.of(2024, 1, 4)),
				new Transactions(103, 150.0, LocalDate.of(2024, 1, 8))));

		Customers customer3 = new Customers();
		customer3.setCustomerId(12);
		customer3.setCustomerName("Robert Williams");
		customer3.setTransactions(Arrays.asList(new Transactions(104, 250.0, LocalDate.of(2024, 2, 8)),
				new Transactions(105, 60.0, LocalDate.of(2024, 3, 8))));

		Customers customer4 = new Customers();
		customer4.setCustomerId(13);
		customer4.setCustomerName("Linda Hamilton");
		customer4.setTransactions(Arrays.asList(new Transactions(106, 200.0, LocalDate.of(2024, 1, 8)),
				new Transactions(107, 300.0, LocalDate.of(2024, 2, 8)),
				new Transactions(108, 400.0, LocalDate.of(2024, 3, 8))));
		return Arrays.asList(customer1, customer2, customer3, customer4);
	}
}
