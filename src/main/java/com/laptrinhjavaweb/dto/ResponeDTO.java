package com.laptrinhjavaweb.dto;

import java.util.List;

public class ResponeDTO {
	private List<String> error;
	private Object data;
	
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
