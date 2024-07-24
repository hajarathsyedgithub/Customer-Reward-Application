package com.customer_reward.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.customer_reward.app.exception.CustomerDataException;
import com.customer_reward.app.exception.CustomerNotFoundException;
import com.customer_reward.app.model.Customers;
import com.customer_reward.app.model.Transactions;
import com.customer_reward.app.util.SampleDataUtil;

@Service
public class CalculateRewardsService {
	private static final Logger logger = LoggerFactory.getLogger(CalculateRewardsService.class);

	@Value("${over100.reward.points}")
	private int over100_rewardPointsValue;

	@Value("${between50and100.reward.points}")
	private int between50and100_rewardPointsValue;

	@Autowired
	private SampleDataUtil sampleDataUtil;

	/**
	 * Retrieves rewards data for all customers.
	 *
	 * @return a map with customer names as keys and their monthly reward points as
	 *         values
	 */
	public Map<String, Map<String, Integer>> getRewards() {
		try {
			List<Customers> customers = sampleDataUtil.getSampleData();
			if (customers == null || customers.isEmpty()) {
				throw new CustomerNotFoundException("No customers found");
			}
			return calculateRewards(customers);
		} catch (Exception e) {
			logger.error("Error while fetching rewards: {}", e.getMessage(), e);
			throw new CustomerDataException("Currently unable to retrieve customer details, please try again later.");
		}
	}

	/**
	 * Calculates reward points for a list of customers.
	 *
	 * @param customers the list of customers
	 * @return a map with customer names as keys and their monthly reward points as
	 *         values
	 */
	public Map<String, Map<String, Integer>> calculateRewards(List<Customers> customers) {
		if (customers == null || customers.isEmpty()) {
			throw new IllegalArgumentException("Customer list cannot be null or empty");
		}

		Map<String, Map<String, Integer>> rewards = new HashMap<>();
		try {
			for (Customers customer : customers) {

				Map<String, Integer> monthlyPoints = new HashMap<>();
				int totalPoints = 0;

				for (Transactions transaction : customer.getTransactions()) {

					int points = calculatePointsPerMonth(transaction.getPurchaseAmount());
					totalPoints += points;
					monthlyPoints.merge(transaction.getDate().getMonth().toString(), points, Integer::sum);
				}

				monthlyPoints.put("Total Rewards", totalPoints);
				rewards.put(customer.getCustomerName(), monthlyPoints);
				logger.debug("Total points for customer {}: {}", customer.getCustomerName(), totalPoints);
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve customer rewards details: {}", e.getMessage(), e);
			throw new CustomerDataException("Currently unable to retrieve customer details, please try again later.");
		}
		logger.info("Reward points calculation completed");
		return rewards;
	}

	/**
	 * Calculates reward points for a single customer.
	 *
	 * @param customer the customer
	 * @return a map with month names as keys and their reward points as values
	 */
	public Map<String, Map<String, Integer>> calculateRewardsForCustomer(Customers customer) {
		if (customer == null || customer.getTransactions() == null) {
			throw new IllegalArgumentException("Customer or transactions cannot be null");
		}

		Map<String, Map<String, Integer>> perCustomerRewards = new HashMap<>();
		Map<String, Integer> monthlyPoints = new HashMap<>();
		int totalPoints = 0;

		try {
			for (Transactions transaction : customer.getTransactions()) {

				int points = calculatePointsPerMonth(transaction.getPurchaseAmount());
				totalPoints += points;
				String month = transaction.getDate().getMonth().toString();
				monthlyPoints.merge(month, points, Integer::sum);
			}

			monthlyPoints.put("Total Rewards", totalPoints);
			perCustomerRewards.put(customer.getCustomerName(), monthlyPoints);
			logger.debug("Total points for customer {}: {}", customer.getCustomerName(), totalPoints);
		} catch (Exception e) {
			logger.error("Unable to retrieve customer details, customer name is: {}. Error: {}",
					customer.getCustomerName(), e.getMessage(), e);
			throw new CustomerDataException("Currently unable to retrieve customer details, please try again later.");
		}
		return perCustomerRewards;
	}

	/**
	 * Retrieves a customer by ID.
	 *
	 * @param customerId the customer ID
	 * @return the customer
	 * @throws CustomerNotFoundException if the customer is not found
	 */
	public Customers getCustomerById(Integer customerId) {
		if (customerId == null) {
			throw new IllegalArgumentException("Customer ID cannot be null");
		}

		try {
			List<Customers> customers = sampleDataUtil.getSampleData();
			if (customers == null || customers.isEmpty()) {
				throw new CustomerNotFoundException("No customers found");
			}

			return customers.stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst()
					.orElseThrow(() -> new CustomerNotFoundException(
							"Requested customer id is not found. Customer ID: " + customerId));
		} catch (Exception e) {
			logger.error("Error while retrieving customer by ID: {}", customerId, e.getMessage(), e);
			throw new RuntimeException("Error while retrieving customer by ID", e);
		}
	}

	/**
	 * Calculates reward points based on the purchase amount.
	 *
	 * @param amount the purchase amount
	 * @return the calculated reward points
	 */
	private int calculatePointsPerMonth(double amount) {
		int points = 0;

		try {
			if (amount > 100) {
				points += (amount - 100) * over100_rewardPointsValue;
				amount = 100;
			}

			if (amount > 50) {
				points += (amount - 50) * between50and100_rewardPointsValue;
			}
		} catch (Exception e) {
			logger.error("Error while calculating points for amount {}: {}", amount, e.getMessage(), e);
			throw new RuntimeException("Error while calculating points", e);
		}

		return points;
	}
}
