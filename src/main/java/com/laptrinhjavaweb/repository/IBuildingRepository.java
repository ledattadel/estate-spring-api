package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;


// data access layer
public interface BuildingRepository {
	
	List<BuildingEntity> findAll(Map<String,String> requestParam,List<String> listType);
	String buildQueryForSearchBuilding(Map<String,String> requestParam, List<String> listType);
	String buildJoinSQLForSearchBuilding(Map<String,String> requestParam, List<String> listType);
	String buildWhereSQLForSearchBuilding(Map<String,String> requestParam, List<String> listType);
	String buildConditionForBuildingType(List<String> buildingType);
	String checkExistenceOfCondition(String prefix, String suffix, Object parameter);
	String buildBetweenStatementForBuildingSearch(String whereSQLClause, String from, String to);
	String getDistrictName(String districtId);
}
