package org.lanit.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseJSON {

	@JsonProperty("status")
	private String status;
	@JsonProperty("message")
	private String message;

	public ErrorResponseJSON(String status, String message) {
		this.status=status;
		this.message=message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}