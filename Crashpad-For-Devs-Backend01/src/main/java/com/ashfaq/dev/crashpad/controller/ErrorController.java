package com.ashfaq.dev.crashpad.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashfaq.dev.crashpad.model.ErrorInfoModel;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ErrorController {

	public List<ErrorInfoModel> errorlist = new ArrayList<ErrorInfoModel>();
	private final Map<String, Long> globalErrorCounts = new HashMap<>();

	@PostMapping("/error")
	public ResponseEntity<Void> handleError(@RequestBody ErrorInfoModel errorInfo) {

		errorlist.add(errorInfo);
//		System.out.println(errorInfo);

//		Map<String, List<ErrorInfoModel>> collect = errorlist.stream()
//				.collect(Collectors.groupingBy(ErrorInfoModel::getErrorMessage));
//		System.out.println(collect);

		Map<String, List<ErrorInfoModel>> collect = errorlist.stream()
				.collect(Collectors.groupingBy(ErrorInfoModel::getExceptionName));

		

		// Group errorList by error message and count occurrences
		Map<String, Long> errorCounts = errorlist.stream()
				.collect(Collectors.groupingBy(ErrorInfoModel::getExceptionName, Collectors.counting()));

		// Print error counts for debugging
//		System.out.println("Error Counts: " + errorCounts);

		// Print global error counts for debugging
//	        System.out.println("Global Error Counts: " + globalErrorCounts);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/error-counts")
	public ResponseEntity<Map<String, Long>> getGlobalErrorCounts() {
		return ResponseEntity.ok(globalErrorCounts);
	}
	
	@GetMapping("/mainError")
	public ResponseEntity<List<ErrorInfoModel>> getMainErrorList() {
		return ResponseEntity.ok(errorlist);
	}
	
	
	
	@GetMapping("/errorCounts")
	public ResponseEntity<Map<String, Long>> getErrorCounts() {
	    Map<String, Long> errorCounts = errorlist.stream()
	            .collect(Collectors.groupingBy(ErrorInfoModel::getExceptionName, Collectors.counting()));
	    return ResponseEntity.ok().body(errorCounts);
	}
	
	
	 @GetMapping("/error/average-time")
	    public ResponseEntity<Map<String, Double>> getErrorAverageTime() {
	        // Group errors by error message and calculate average occurrence time for each error
	        Map<String, Double> errorAverageTimes = errorlist.stream()
	                .collect(Collectors.groupingBy(ErrorInfoModel::getErrorMessage,
	                        Collectors.averagingLong(this::getTimestampInMillis)));

	        return ResponseEntity.ok(errorAverageTimes);
	    }

	    // Helper method to convert timestamp to milliseconds
	 private long getTimestampInMillis(ErrorInfoModel error) {
		    LocalDateTime timestamp = error.getTimestamp();
		    return timestamp.toEpochSecond(ZoneOffset.UTC) * 1000;
		}
	 
	 
	 
	 


}
