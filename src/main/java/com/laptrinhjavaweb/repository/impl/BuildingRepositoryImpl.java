package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;
import com.laptrinhjavaweb.repository.mapper.BuildingMapper;
import com.laptrinhjavaweb.utils.StringUtils;
import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
//import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.IBuildingRepository;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;;

@Repository
public class BuildingRepositoryImpl extends BaseJDBCImpl implements IBuildingRepository {

	@Autowired
	private IBuildingRepository buildingRepository;

	@Autowired
	private BuildingConverter buildingConverter;

	@Override
	public List<BuildingEntity> findByCondition(Map<String, String> requestParam, List<String> listType) {
		return query(this.buildQueryForSearchBuilding(requestParam, listType), new BuildingMapper());
	}

	@Override
	public List<BuildingEntity> findAll() {

		String query = "select * from building";
		return query(query, new BuildingMapper());

	}

	private String buildQueryForSearchBuilding(Map<String, String> requestParam, List<String> listType) {

		try {
			return new StringBuilder(
					"SELECT BD.id, BD.name, BD.street, BD.ward, BD.districtid, BD.floorarea, BD.rentprice, BD.rentpricedescription, BD.servicefee,BD.brokeragefee, BD.createddate, BD.managername, BD.managerphone ")
							.append(" FROM building BD ")
							.append(this.buildJoinSQLForSearchBuilding(requestParam, listType))
							.append(this.buildWhereSQLForSearchBuilding(requestParam, listType))
							.append(" GROUP BY BD.id ").toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String buildJoinSQLForSearchBuilding(Map<String, String> requestParam, List<String> listType) {

		String rentAreaFrom = requestParam.getOrDefault("rentEreaFrom", null);
		String rentAreaTo = requestParam.getOrDefault("rentEreaTo", null);
		String staffiD = requestParam.getOrDefault("staffId", null);

		StringBuilder buildJoinSQL = new StringBuilder(" JOIN district DT on DT.id = BD.districtid ");
		if (!StringUtils.isNull(listType)) {
			buildJoinSQL.append(
					" JOIN buildingrenttype BRT ON BRT.buildingid = BD.id JOIN renttype RT ON RT.id = BRT.renttypeid ");
		}

		if (!StringUtils.isNull(staffiD)) {
			buildJoinSQL.append(" JOIN assignmentbuilding ASB on  ASB.buildingid = BD.id ");
		}
		if (!StringUtils.isNull(rentAreaFrom) || !StringUtils.isNull(rentAreaTo)) {
			buildJoinSQL.append(" JOIN rentarea RE ON RE.buildingid = BD.id ");
		}

		return buildJoinSQL.toString();
	}

	private String buildWhereSQLForSearchBuilding(Map<String, String> requestParam, List<String> listType) {
		StringBuilder whereSQLClause = new StringBuilder(" WHERE 1=1 ");

		String name = requestParam.getOrDefault("name", null);
		String street = requestParam.getOrDefault("street", null);
		String ward = requestParam.getOrDefault("ward", null);
		String districtCode = requestParam.getOrDefault("districtCode", null);
		String floorArea = requestParam.getOrDefault("floorArea", null);
		String numberOfBasement = requestParam.getOrDefault("numberOfBasement", null);
		String direction = requestParam.getOrDefault("direction", null);
		String level = requestParam.getOrDefault("level", null);
		String managerName = requestParam.getOrDefault("managerName", null);
		String managerPhone = requestParam.getOrDefault("managerPhone", null);
		String staffiD = requestParam.getOrDefault("staffId", null);
		String rentAreaFrom = requestParam.getOrDefault("rentEreaFrom", null);
		String rentAreaTo = requestParam.getOrDefault("rentEreaTo", null);
		String rentPriceFrom = requestParam.getOrDefault("rentPriceFrom", null);
		String rentPriceTo = requestParam.getOrDefault("rentPriceTo", null);
		
		
		
		whereSQLClause.append(this.buildConditionBuildingType(listType))
				.append(this.checkExistenceOfCondition("BD.name",SystemConstant.LIKE_OPERATOR,name))
				.append(this.checkExistenceOfCondition("BD.street",SystemConstant.LIKE_OPERATOR, street))
				.append(this.checkExistenceOfCondition("BD.ward",SystemConstant.LIKE_OPERATOR, ward))
				.append(this.checkExistenceOfCondition("DT.code",SystemConstant.EQUAL_OPERATOR, districtCode))
				.append(this.checkExistenceOfCondition("BD.floorarea",SystemConstant.EQUAL_OPERATOR, floorArea))
				.append(this.checkExistenceOfCondition("BD.numberOfBasement",SystemConstant.EQUAL_OPERATOR,numberOfBasement))
				.append(this.checkExistenceOfCondition("BD.direction",SystemConstant.LIKE_OPERATOR,direction))
				.append(this.checkExistenceOfCondition("BD.Level",SystemConstant.LIKE_OPERATOR, level))
				.append(this.checkExistenceOfCondition("BD.managername",SystemConstant.LIKE_OPERATOR,managerName))
				.append(this.checkExistenceOfCondition("BD.managerphone",SystemConstant.LIKE_OPERATOR,managerPhone))
				.append(this.checkExistenceOfCondition("ASB.staffid",SystemConstant.EQUAL_OPERATOR, staffiD))
				.append(this.buildBetweenStatementForBuildingSearch("BD.rentprice", rentPriceFrom,rentPriceTo))
				.append(this.buildBetweenStatementForBuildingSearch("RE.value", rentAreaFrom,rentAreaTo));

		return whereSQLClause.toString();
	}

	private String buildConditionBuildingType(List<String> buildingTypes) {
		if (!StringUtils.isNull(buildingTypes)) {	
			StringBuilder buildCondition = new StringBuilder(SystemConstant.AND_STATEMENT);
			List<String> typesString = new ArrayList<>();

			for (String type : buildingTypes) {
				StringBuilder typeStatement = new StringBuilder();
				typeStatement.append("'").append(type).append("'");
				typesString.add(typeStatement.toString());
			}
			return buildCondition.append(" RT.code IN  (").append(String.join(",", typesString)).append(")").toString();
		}
		return "";
	}

	private String buildBetweenStatementForBuildingSearch(String whereSQLClause, String from, String to) {

		if (!StringUtils.isNull(from) || !StringUtils.isNull(to)) {

			if (!StringUtils.isNull(from) && !StringUtils.isNull(to)) {
				return (" AND " + whereSQLClause + " BETWEEN " + from + " AND " + to + " ");
			} else if (!StringUtils.isNull(from) && StringUtils.isNull(to)) {
				return (" AND " + whereSQLClause + " >= " + from + " ");
			}
			return (" AND " + whereSQLClause + " <= " + to + " ");

		}
		return "";
	}

	private String checkExistenceOfCondition(String paramCondition,String typeOfCondition, String parameter) {
		if (!StringUtils.isNull(parameter) && typeOfCondition.equals("=")) {
			return (" AND " + paramCondition  + " = '" + parameter +"' ");
		}else if(!StringUtils.isNull(parameter) && typeOfCondition.equals("LIKE")){
			return (" AND " + paramCondition  + " LIKE '%" + parameter +"%' ");
		}
		return "";
	}

}
