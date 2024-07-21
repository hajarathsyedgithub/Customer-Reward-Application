package com.customer_reward.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.customer_reward.app.model.Customers;
import com.customer_reward.app.model.Transactions;

@Service
public class CalculateRewardsService {
	private static final Logger logger = LoggerFactory.getLogger(CalculateRewardsService.class);

	public Map<String, Map<String, Integer>> calculateRewards(List<Customers> customers) {
		Map<String, Map<String, Integer>> rewards = new HashMap<>();

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

	private int calculatePointsPerMonth(double amount) {
		int points = 0;

		if (amount > 100) {
			points += (((amount - 100) * 2));
			amount = 100;

		}

		if (amount > 50) {
			points += (amount - 50);
		}

		return points;
	}

}
