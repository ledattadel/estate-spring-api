package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;

// data access layer
public interface IBuildingRepository {
// GET DATA
	List<BuildingEntity> findByCondition(Map<String, String> requestParam, List<String> listType);

	List<BuildingEntity> findAll();

}
