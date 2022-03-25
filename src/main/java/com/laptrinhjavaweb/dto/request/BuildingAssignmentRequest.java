package com.laptrinhjavaweb.dto.request;

import java.util.ArrayList;
import java.util.List;

public class BuildingAssignmentRequest {
	private List<String> staffIds = new ArrayList<>();
	private Long buildingId;
	
	public List<String> getStaffIds() {
		return staffIds;
	}
	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	
	
}
