package com.customer_reward.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handles all exceptions that are not specifically handled by other exception
	 * handlers.
	 * 
	 * @param ex      the exception that was thrown
	 * @param request the current web request
	 * @return a ResponseEntity containing an error message and the HTTP status code
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
		logger.error("An error occurred: {}", ex.getMessage(), ex);

		// Create a detailed error response
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "An unexpected error occurred: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles IllegalArgumentException and returns a bad request response.
	 * 
	 * @param ex      the exception that was thrown
	 * @param request the current web request
	 * @return a ResponseEntity containing an error message and the HTTP status code
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest request) {
		logger.error("A bad request error occurred: {}", ex.getMessage(), ex);

		// Create a detailed error response
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Invalid request: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles CustomerNotFoundException and returns a not found response.
	 * 
	 * @param ex      the exception that was thrown
	 * @param request the current web request
	 * @return a ResponseEntity containing an error message and the HTTP status code
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCustomerNotFoundException(CustomerNotFoundException ex,
			WebRequest request) {
		logger.error("Customer not found: {}", ex.getMessage(), ex);

		// Create a detailed error response
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
