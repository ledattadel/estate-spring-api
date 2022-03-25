package com.laptrinhjavaweb.dto;

import java.util.ArrayList;
import java.util.List;

public class BuildingDTO extends ResponeDTO {

	private String name;
	private Integer numberOfBasement;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private List<String> types = new ArrayList<>();
	
	
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
}
	