package com.ashfaq.dev.crashpad.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ashfaq.dev.crashpad.model.ErrorInfoModel;

@RestController
@RequestMapping("/api/test")
public class TestingController {
	@GetMapping("/arithmetic")
	public ResponseEntity<String> arithmeticException() {
		try {
			int result = 10 / 0; // This will cause an ArithmeticException
		} catch (Exception e) {
			sendErrorInfo(e);
		}
		return ResponseEntity.ok("Handled Arithmetic Exception");
	}

	@GetMapping("/nullpointer")
	public ResponseEntity<String> nullPointerException() {
		try {
			String data = null;
			data.length(); // This will cause a NullPointerException
		} catch (Exception e) {
			sendErrorInfo(e);
		}
		return ResponseEntity.ok("Handled Null Pointer Exception");
	}

	private String generateGoogleSearchUrl(String errorMessage) {
		try {
			String encodedErrorMessage = URLEncoder.encode(errorMessage, "UTF-8");
			return "https://www.google.com/search?q=" + encodedErrorMessage;
		} catch (Exception e) {
			// Handle encoding exception
			return "";
		}
	}

	private String getStackTraceSnippet(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		String[] lines = stackTrace.split(System.lineSeparator());
		// Limit the stack trace to 5 lines
		int maxLength = Math.min(lines.length, 5);
		StringBuilder snippetBuilder = new StringBuilder();
		for (int i = 0; i < maxLength; i++) {
			snippetBuilder.append(lines[i]).append(System.lineSeparator());
		}
		return snippetBuilder.toString();
	}

	@GetMapping("/indexoutofbounds")
	public ResponseEntity<String> indexOutOfBoundsException() {
		try {
			int[] arr = new int[5];
			int number = arr[10]; // This will cause an IndexOutOfBoundsException
		} catch (Exception e) {
			sendErrorInfo(e);
		}
		return ResponseEntity.ok("Handled Index Out of Bounds Exception");
	}

	@GetMapping("/custom")
	public ResponseEntity<String> customException() {
		try {
			throw new CustomException("Custom exception occurred");
		} catch (Exception e) {
			sendErrorInfo(e);
		}
		return ResponseEntity.ok("Handled Custom Exception");
	}

	private void sendErrorInfo(Exception e) {
		ErrorInfoModel errorInfo = new ErrorInfoModel();
		errorInfo.setErrorMessage(e.getMessage());
		errorInfo.setStackTrace(getStackTraceSnippet(e));
		errorInfo.setGoogleSearchUrl(generateGoogleSearchUrl(e.getMessage()));
		errorInfo.setTimestamp(LocalDateTime.now()); // Set the timestamp
		errorInfo.setExceptionName(e.getClass().getSimpleName()); // Set the exception name
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://localhost:8080/error";
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl, errorInfo, String.class);
		System.out.println(postForEntity.toString());
	}

	// You can define the CustomException here or in a separate class file
	static class CustomException extends RuntimeException {
		public CustomException(String message) {
			super(message);
		}
	}
}
