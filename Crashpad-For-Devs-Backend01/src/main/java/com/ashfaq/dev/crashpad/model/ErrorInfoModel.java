package com.ashfaq.dev.crashpad.model;

import java.time.LocalDateTime;

public class ErrorInfoModel {
	private String errorMessage;
	private String stackTrace;
	private String googleSearchUrl;
	private String exceptionName; // New field for the name of the exception
	private LocalDateTime timestamp; // New field for the timestamp when the exception occurred

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public String getGoogleSearchUrl() {
		return googleSearchUrl;
	}

	public void setGoogleSearchUrl(String googleSearchUrl) {
		this.googleSearchUrl = googleSearchUrl;
	}

	public void setStackTrace(String string) {
		this.stackTrace = string;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorInfoModel [errorMessage=" + errorMessage + ", stackTrace=" + stackTrace + ", googleSearchUrl="
				+ googleSearchUrl + ", exceptionName=" + exceptionName + "]";
	}

}
