package com.customer_reward.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customers {

	private Integer customerId;
	private String customerName;

	private List<Transactions> transactions;

}
