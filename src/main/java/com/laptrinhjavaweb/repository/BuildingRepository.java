package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;


// data access layer
public interface BuildingRepository {
	List<BuildingEntity> findAll(Map<Object,Object> requestParam);
	String buildQueryForSearchBuilding(BuildingSearchRequest buildingSearchRequest);
	String buildJoinSQLForSearchBuilding(BuildingSearchRequest buildingSearchRequest);
	String buildWhereSQLForSearchBuilding(BuildingSearchRequest buildingSearchRequest);
	String buildConditionForBuildingType(List<String> buildingType);
	String checkExistenceOfCondition(String prefix, String suffix, Object parameter);
	String buildBetweenStatementForBuildingSearch(String whereSQLClause, Integer from, Integer to);
	String getDistrictName(String districtId);
}
