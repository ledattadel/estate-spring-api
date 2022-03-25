package com.laptrinhjavaweb.repository.entity;

public class RentAreaEntity extends BaseEntity{
	String value;
	String buildingId;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	
}
