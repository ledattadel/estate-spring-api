package com.laptrinhjavaweb.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.ResponeDTO;

@Component
public class BuildingSearchResponse extends ResponeDTO {

	private String date;

	private String name;


	
	private String address;

	private Integer floorarea;

	private Integer rentprice;
	
	private String rentpricedescription;
	
	private String servicefee;

	private String brokeragefee;
	
	private String managername;
	
	private String managerphone;
	
	

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getManagerphone() {
		return managerphone;
	}

	public void setManagerphone(String managerphone) {
		this.managerphone = managerphone;
	}

	public String getRentpricedescription() {
		return rentpricedescription;
	}

	public void setRentpricedescription(String rentpricedescription) {
		this.rentpricedescription = rentpricedescription;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getFloorarea() {
		return floorarea;
	}

	public void setFloorarea(Integer floorarea) {
		this.floorarea = floorarea;
	}

	public Integer getRentprice() {
		return rentprice;
	}

	public void setRentprice(Integer rentprice) {
		this.rentprice = rentprice;
	}

	public String getServicefee() {
		return servicefee;
	}

	public void setServicefee(String servicefee) {
		this.servicefee = servicefee;
	}

	public String getBrokeragefee() {
		return brokeragefee;
	}

	public void setBrokeragefee(String brokeragefee) {
		this.brokeragefee = brokeragefee;
	}

	
}
