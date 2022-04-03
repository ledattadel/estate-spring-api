package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.repository.IDistrictRepository;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;
import com.laptrinhjavaweb.repository.mapper.BuildingMapper;
import com.laptrinhjavaweb.repository.mapper.DistrictMapper;;

@Repository
public class DistrictRepositoryImpl extends BaseJDBCImpl implements IDistrictRepository {

	@Override
	public DistrictEntity getDistrict(String districtId) {
		String query = "select * from district where 1 = 1 and district.id like '%" + districtId + "%'";

		List<DistrictEntity> entityList = query(query, new DistrictMapper());

		return entityList.size() > 0 ? entityList.get(0) : new DistrictEntity();
	}

}
