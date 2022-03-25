package com.laptrinhjavaweb.service.filter;
// doi tuong xu ly du lieu cho tang Business, sua nay se gop no voi DTO -> model, con entity thao tac truc tiep voi db nen giu nguyen k gop

public class BuildingFilter {
	private String name;
	private Integer numberOfBasement;
	private String address;
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
