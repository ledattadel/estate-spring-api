package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laptrinhjavaweb.utils.StringUtils;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.repository.IDistrictRepository;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.filter.BuildingFilter;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private IBuildingRepository buildingRepository;

	@Autowired
	private IDistrictRepository districtRepository;

	@Autowired
	private BuildingConverter buildingConverter;

	@Override
	public List<BuildingSearchResponse> findByCondition(Map<String, String> requestParam, List<String> listType) {
		// TODO Auto-generated method stub
		List<BuildingSearchResponse> result = new ArrayList<>();
		if (!requestParam.isEmpty() || listType != null) {
			List<BuildingEntity> buildingEntities = buildingRepository.findByCondition(requestParam, listType);

			for (BuildingEntity entity : buildingEntities) {
				DistrictEntity districtEntity = districtRepository.getDistrict(entity.getDistrictId());
				BuildingSearchResponse buildingSearchResponse = buildingConverter
						.convertToBuildingSearchResponseFromEntity(entity, districtEntity);
				result.add(buildingSearchResponse);
			}

		} else {
			List<BuildingEntity> entities = buildingRepository.findAll();

			for (BuildingEntity entity : entities) {
				
				DistrictEntity districtEntity = districtRepository.getDistrict(entity.getDistrictId());
				BuildingSearchResponse buildingSearchResponse = buildingConverter
						.convertToBuildingSearchResponseFromEntity(entity,districtEntity);
				result.add(buildingSearchResponse);
			}
		}

		return result;
	}

}
