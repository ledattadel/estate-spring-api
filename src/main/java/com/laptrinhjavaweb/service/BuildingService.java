package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.service.filter.BuildingFilter;

// you have to tranfer from DAO : Entity to Service: Filter 
//from service: Filter to dto 

//business logic layer
public interface BuildingService {
	List<BuildingSearchResponse> findAll(Map<Object,Object> requestParam);
	
}
