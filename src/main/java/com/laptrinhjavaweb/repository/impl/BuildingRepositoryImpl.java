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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;
import com.laptrinhjavaweb.utils.StringUtils;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
//import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "1234";
	

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingConverter buildingConverter;
	

	
    @Override
    public String buildQueryForSearchBuilding(Map<String,String> requestParam, List<String> listType) {
    	try {
    		
        	 return new StringBuilder("SELECT BD.id, BD.name, BD.street, BD.ward, BD.districtid, BD.floorarea, BD.rentprice, BD.rentpricedescription, BD.servicefee,BD.brokeragefee, BD.createddate, BD.managername, BD.managerphone ")
	                    .append(" FROM building BD ")
	                    .append(this.buildJoinSQLForSearchBuilding(requestParam,listType))
	                    .append(this.buildWhereSQLForSearchBuilding(requestParam,listType))
	                    .append(" GROUP BY BD.id ").toString();
                    
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
   
    @Override
    public String buildJoinSQLForSearchBuilding(Map<String,String> requestParam, List<String> listType) {
    	
    	StringBuilder buildJoinSQL = new StringBuilder(" JOIN district DT on DT.id = BD.districtid ");
    	if(listType!=null) {
    		buildJoinSQL.append(" JOIN buildingrenttype BRT ON BRT.buildingid = BD.id JOIN renttype RT ON RT.id = BRT.renttypeid ");  
    	}
    	
    	if(requestParam.get("staffId") != null) {
    		buildJoinSQL.append(" JOIN assignmentbuilding ASB on  ASB.buildingid = BD.id ");
    	}
    	if(requestParam.get("rentEreaFrom") != null || requestParam.get("rentEreaTo") != null) {
    		buildJoinSQL.append(" JOIN rentarea RE ON RE.buildingid = BD.id ");
    	}
    
        return buildJoinSQL.toString();
    }
    
    
    
    
    @Override
    public String buildWhereSQLForSearchBuilding(Map<String,String> requestParam, List<String> listType) {
        StringBuilder whereSQLClause = new StringBuilder(" WHERE 1=1 ");
        
        whereSQLClause
                .append(this.buildConditionForBuildingType(listType))
                .append(this.checkExistenceOfCondition(" AND BD.name LIKE '%", "%' ", requestParam.get("name")))
                .append(this.checkExistenceOfCondition(" AND BD.street LIKE '%", "%' ", requestParam.get("street")))
                .append(this.checkExistenceOfCondition(" AND BD.ward LIKE '%", "%' ", requestParam.get("ward")))
                .append(this.checkExistenceOfCondition(" AND DT.code = '", "' ",  requestParam.get("districtCode")))
                .append(this.checkExistenceOfCondition(" AND BD.floorarea = ", " ", requestParam.get("floorArea")))
                .append(this.checkExistenceOfCondition(" AND BD.numberOfBasement = '", "' ", requestParam.get("numberOfBasement")))
                .append(this.checkExistenceOfCondition(" AND BD.direction  LIKE '%", "%' ", requestParam.get("direction")))
                .append(this.checkExistenceOfCondition(" AND BD.Level LIKE '%", "%' ", requestParam.get("level")))
                .append(this.checkExistenceOfCondition(" AND BD.managername LIKE '%", "%' ", requestParam.get("managerName")))
                .append(this.checkExistenceOfCondition(" AND BD.managerphone LIKE '%", "%' ", requestParam.get("managerPhone")))
                .append(this.checkExistenceOfCondition(" AND ASB.staffid = '", "' ", requestParam.get("staffId")))
                .append(this.buildBetweenStatementForBuildingSearch("BD.rentprice", requestParam.get("rentPriceFrom"), requestParam.get("rentPriceTo")))
                .append(this.buildBetweenStatementForBuildingSearch("RE.value", requestParam.get("rentEreaFrom"), requestParam.get("rentEreaTo")));
                

        return whereSQLClause.toString();
    }
    @Override
    public String buildConditionForBuildingType(List<String> buildingType) {
        StringBuilder conditionForBuildingType = new StringBuilder("");
        if (buildingType != null) {
        	conditionForBuildingType.append(" AND");
        	if(buildingType.size() == 1) {
        		conditionForBuildingType.append(" RT.code LIKE '"+buildingType.get(0)+"'");
        	}else if(buildingType.size() > 1) {
        		 for (int i = 0; i < buildingType.size(); i++) {
        			 conditionForBuildingType.append(" RT.code LIKE '"+buildingType.get(i)+"'");
        			 if(i < buildingType.size()-1) {
        				 conditionForBuildingType.append(" OR ");
        			 }
                 }
        	}
        	
        }
        	
           
        return conditionForBuildingType.toString();
    }
    @Override
    public String buildBetweenStatementForBuildingSearch(String whereSQLClause, String from, String to) {
    	
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
	
    
    @Override
    public String checkExistenceOfCondition(String prefix, String suffix, Object parameter) {
        if (parameter!= null) {
            return (prefix + parameter + suffix);
        }
        return "";
    }
    
    @Override
    public String getDistrictName(String districtId) {
    	String districtName = "";
    	String QUERY = "select district.name from district where 1 = 1 and district.id like '%"+districtId+"%'";
    	try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(QUERY);
		      ) {		      
		         while(rs.next()){
		        	 
		        	districtName =  rs.getString("name");
		        	 
		        
		         }
		     	return districtName;
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return "";
		
    }
    
	@Override
	public List<BuildingEntity> findAll(Map<String,String> requestParam, List<String> listType) {
		
		String query = buildQueryForSearchBuilding(requestParam,listType);
		
		
		
		List<BuildingEntity> listBuildingEntity = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(query);
		      ) {		      
		         while(rs.next()){
		        	 
		        	BuildingEntity buildingEntity = new BuildingEntity();
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
		        	
		            listBuildingEntity.add(buildingEntity);	
		         }
		     	return listBuildingEntity;
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return null;
		
		
	
	
	}


	
	
	
}
