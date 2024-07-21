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


    public Transactions(Integer transactionId, Double purchaseAmount, LocalDate date) {
        this.transactionId = transactionId;
        this.purchaseAmount = purchaseAmount;
        this.date = date;
    }


    public Integer getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
    public Double getPurchaseAmount() {
        return purchaseAmount;
    }
    public void setPurchaseAmount(Double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    

    
}


