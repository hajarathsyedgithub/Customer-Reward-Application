package com.customer_reward.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.customer_reward.app.model.Customers;
import com.customer_reward.app.model.Transactions;

@Service
public class CalculateRewardsService {
	private static final Logger logger = LoggerFactory.getLogger(CalculateRewardsService.class);

	@Value("${over100.reward.points}")
	private int over100_rewardPointsValue;

	@Value("${between50and100.reward.points}")
	private int between50and100_rewardPointsValue;

	/**
	 * Calculates reward points for a list of customers.
	 *
	 * @param customers the list of customers
	 * @return a map with customer names as keys and their monthly reward points as
	 *         values
	 */
	public Map<String, Map<String, Integer>> calculateRewards(List<Customers> customers) {
		Map<String, Map<String, Integer>> rewards = new HashMap<>();

		for (Customers customer : customers) {
			Map<String, Integer> monthlyPoints = new HashMap<>();
			int totalPoints = 0;

			/**
			 * Calculates and combine reward points for each month transactions for all
			 * customers.
			 */
			for (Transactions transaction : customer.getTransactions()) {
				int points = calculatePointsPerMonth(transaction.getPurchaseAmount());
				totalPoints += points;
				monthlyPoints.merge(transaction.getDate().getMonth().toString(), points, Integer::sum);

			}
			monthlyPoints.put("Total Rewards", totalPoints);

			rewards.put(customer.getCustomerName(), monthlyPoints);

			logger.debug("Total points for customer {}: {}", customer.getCustomerName(), totalPoints);
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
		Map<String, Map<String, Integer>> perCustomerRewards = new HashMap<>();
		Map<String, Integer> monthlyPoints = new HashMap<>();
		int totalPoints = 0;

		/**
		 * Calculates and combine reward points for each month transactions per
		 * customer.
		 */
		for (Transactions transaction : customer.getTransactions()) {
			int points = calculatePointsPerMonth(transaction.getPurchaseAmount());
			totalPoints += points;
			String month = transaction.getDate().getMonth().toString();
			monthlyPoints.merge(month, points, Integer::sum);
		}
		monthlyPoints.put("Total Rewards", totalPoints);

		perCustomerRewards.put(customer.getCustomerName(), monthlyPoints);

		logger.debug("Total points for customer {}: {}", customer.getCustomerName(), totalPoints);
		return perCustomerRewards;
	}

	/**
	 * Calculates reward points based on the purchase amount.
	 *
	 * @param amount the purchase amount
	 * @return the calculated reward points
	 */
	private int calculatePointsPerMonth(double amount) {

		int points = 0;

		/**
		 * 2 points for every dollar spent over $100 in each transaction.
		 */
		if (amount > 100) {
			points += (((amount - 100) * over100_rewardPointsValue));
			amount = 100;

		}

		/**
		 * 1 point for every dollar spent between $50 and $100 in each transaction.
		 */
		if (amount > 50) {
			points += ((amount - 50) * between50and100_rewardPointsValue);
		}

		return points;
	}

}
