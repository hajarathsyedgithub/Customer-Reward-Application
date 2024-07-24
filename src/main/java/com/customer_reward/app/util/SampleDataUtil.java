package com.customer_reward.app.util;

import com.customer_reward.app.model.Customers;
import com.customer_reward.app.model.Transactions;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for providing sample data for demonstration purposes.
 */
@Component
public class SampleDataUtil {

	private static final Logger logger = LoggerFactory.getLogger(SampleDataUtil.class);

	/**
	 * Provides a list of sample customers with transactions.
	 *
	 * @return a list of sample customers with transactions
	 */
	public List<Customers> getSampleData() {
		try {
			// Creating sample customer data
			Customers customer1 = createCustomer(10, "James Brown",
					Arrays.asList(new Transactions(100, 120.0, LocalDate.of(2024, 1, 4)),
							new Transactions(101, 100.0, LocalDate.of(2024, 1, 5))));

			Customers customer2 = createCustomer(11, "Michael Phelps",
					Arrays.asList(new Transactions(102, 50.0, LocalDate.of(2024, 1, 4)),
							new Transactions(103, 150.0, LocalDate.of(2024, 1, 8))));

			Customers customer3 = createCustomer(12, "Robert Williams",
					Arrays.asList(new Transactions(104, 250.0, LocalDate.of(2024, 2, 8)),
							new Transactions(105, 60.0, LocalDate.of(2024, 3, 8))));

			Customers customer4 = createCustomer(13, "Linda Hamilton",
					Arrays.asList(new Transactions(106, 200.0, LocalDate.of(2024, 1, 8)),
							new Transactions(107, 300.0, LocalDate.of(2024, 2, 8)),
							new Transactions(108, 400.0, LocalDate.of(2024, 3, 8))));

			return Arrays.asList(customer1, customer2, customer3, customer4);

		} catch (Exception e) {
			// Logging the exception and returning an empty list if any exception occurs
			// System.err.println("An error occurred while creating sample data: " +
			// e.getMessage());
			logger.error("An error occurred while creating sample data: \": {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Creates a customer with the given ID, name, and transactions.
	 *
	 * @param customerId   the ID of the customer
	 * @param customerName the name of the customer
	 * @param transactions the list of transactions for the customer
	 * @return a new customer object
	 */
	private Customers createCustomer(Integer customerId, String customerName, List<Transactions> transactions) {
		if (customerId == null || customerName == null || customerName.isEmpty() || transactions == null) {
			throw new IllegalArgumentException("Customer ID, name, and transactions cannot be null or empty");
		}

		Customers customer = new Customers();
		customer.setCustomerId(customerId);
		customer.setCustomerName(customerName);
		customer.setTransactions(transactions);
		return customer;
	}
}
