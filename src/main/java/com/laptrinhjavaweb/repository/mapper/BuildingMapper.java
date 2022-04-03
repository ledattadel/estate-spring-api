package com.laptrinhjavaweb.repository.mapper;

import java.sql.ResultSet;

import com.laptrinhjavaweb.repository.entity.BuildingEntity;

public class BuildingMapper implements IRowMapper<BuildingEntity>{

	@Override
	public BuildingEntity mapRow(ResultSet rs) {
		  try {
	        	BuildingEntity buildingEntity = new BuildingEntity();
	        	buildingEntity.setId(rs.getLong("id"));
	        	buildingEntity.setCreatedDate(rs.getDate("createddate"));
	        	buildingEntity.setName(rs.getString("name"));
	        	buildingEntity.setStreet(rs.getString("street"));
	        	buildingEntity.setWard(rs.getString("ward"));
	        	buildingEntity.setDistrictId(rs.getString("districtid"));
	        	buildingEntity.setFloorArea(Integer.valueOf(rs.getString("floorarea")));
	        	buildingEntity.setRentPriceDescription(rs.getString("rentpricedescription"));
	        	buildingEntity.setRentPrice(Integer.valueOf(rs.getString("rentprice")));
	        	buildingEntity.setServiceFee(rs.getString("servicefee"));
	        	buildingEntity.setBrokerageFee(rs.getString("brokeragefee"));
	        	buildingEntity.setManagername(rs.getString("managername"));
	        	buildingEntity.setManagerphone(rs.getString("managerphone"));
	        
	            
	            return buildingEntity;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return new BuildingEntity();
	        }
	}

}
