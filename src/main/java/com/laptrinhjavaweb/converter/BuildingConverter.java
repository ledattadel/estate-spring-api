package com.laptrinhjavaweb.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;

@Component
public class BuildingConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IBuildingRepository buildingRepository;
	
//	@Autowired
//	private D
	
	@Autowired
	private BuildingSearchResponse buildingSearchResponse;
	

	public BuildingDTO convertToDTO(BuildingEntity entity) {
		BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
		dto.setAddress(entity.getStreet() +","+ entity.getWard());
		return dto;
	}
	
	public BuildingSearchResponse convertToBuildingSearchResponseFromEntity(BuildingEntity buildingEntity, DistrictEntity districtEntity) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
       
        try {
        	BuildingSearchResponse dto = modelMapper.map(buildingEntity, BuildingSearchResponse.class);
//        	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//        	modelMapper.getConfiguration().setAmbiguityIgnored(true);
    		dto.setAddress(buildingEntity.getStreet() +","+ buildingEntity.getWard()+"," + districtEntity.getName());
    		dto.setRentprice(buildingEntity.getRentPrice());
    		if(buildingEntity.getCreatedDate() != null) {
    			Date date = buildingEntity.getCreatedDate();  
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
              
        		dto.setDate(dateFormat.format(date));
    		}
    		
    		return dto;
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		
		
		return null;
	}
//	public BuildingSearchResponse convertMapToBuildingSearchResponse(Map<String,Object> requestParam) {
//		BuildingSearchResponse dto = modelMapper.map(requestParam, BuildingSearchResponse.class);
//		
//		return dto;
//	}
	public BuildingSearchRequest convertMapToBuildingSearchRequest(Map<Object,Object> requestParam) {
		BuildingSearchRequest dto = modelMapper.map(requestParam, BuildingSearchRequest.class);
		System.out.print(dto.getBuildingTypes());
		
		return dto;
	}
	
	
	public BuildingEntity convertToEntity(BuildingDTO dto) {
		BuildingEntity entity = modelMapper.map(dto, BuildingEntity.class);
		return entity;
	}
	
}
