package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.filter.BuildingFilter;

@Service
public class BuildingServiceImpl implements BuildingService{

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingConverter buildingConverter;
	
//	@Override
//	public List<BuildingSearchResponse> findAll() {
//		// TODO Auto-generated method stub
//		
//		List<BuildingSearchResponse> BuildingDTOs = new ArrayList<>();
//		List<BuildingEntity> buildingEntity = buildingRepository.findAll();
//		
//		for(BuildingEntity item :buildingEntity ) {
//			
//			BuildingSearchResponse buildingFilter = buildingConverter.convertToBuildingSearchResponseFromEntity(item);
//		
//			BuildingDTOs.add(buildingFilter);
//			
//			
//			
//		}
//		
//		return BuildingDTOs;
//	}

	@Override
	public List<BuildingSearchResponse> findAll(Map<Object,Object> requestParam) {
		// TODO Auto-generated method stub
		List<BuildingEntity> entities = buildingRepository.findAll(requestParam);
		List<BuildingSearchResponse> result = new ArrayList<>();
	        
		for (BuildingEntity entity : entities) {
	        	BuildingSearchResponse buildingSearchResponse = buildingConverter.convertToBuildingSearchResponseFromEntity(entity);
	            result.add(buildingSearchResponse);
	        }
		
		
	    return result;
	}
	
	
	
}
