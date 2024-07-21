package com.customer_reward.app.exception;

public class CustomerNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3444117705985287858L;

	public CustomerNotFoundException(String message) {
        super(message);
    }
}

