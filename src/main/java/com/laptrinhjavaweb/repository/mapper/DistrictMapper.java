package com.laptrinhjavaweb.repository.mapper;

import java.sql.ResultSet;

import com.laptrinhjavaweb.repository.entity.DistrictEntity;

public class DistrictMapper implements IRowMapper<DistrictEntity>{

	@Override
	public DistrictEntity mapRow(ResultSet resultSet) {
		// TODO Auto-generated method stub
      try {
      DistrictEntity districtEntity = new DistrictEntity();
      districtEntity.setName(resultSet.getString("name"));
      districtEntity.setCode(resultSet.getString("code"));

      return districtEntity;
  } catch (Exception ex) {
      ex.printStackTrace();
      return new DistrictEntity();
  }
		
	}

	
	
}
