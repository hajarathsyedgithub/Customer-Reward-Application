package com.customer_reward.app.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions {

	private Integer transactionId;
	private Double purchaseAmount;
	private LocalDate date;

}
