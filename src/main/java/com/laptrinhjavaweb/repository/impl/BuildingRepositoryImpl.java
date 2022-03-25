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
	
//	@Autowired
//	private BuildingSearchResponse buildingSearchResponse;
//	
	@Autowired
	private BuildingSearchRequest buildingSearchRequest;
	
	
    @Override
    public String buildQueryForSearchBuilding(BuildingSearchRequest buildingSearchRequest) {
//        , BD.managername, BD.managerphone chua co trong db
    	try {
    		
        	 return new StringBuilder("SELECT BD.id, BD.name, BD.street, BD.ward, BD.districtid, BD.floorarea, BD.rentprice, BD.rentpricedescription, BD.servicefee,BD.brokeragefee, BD.createddate, BD.managername, BD.managerphone ")
	                    .append(" FROM building BD ")
	                    .append(this.buildJoinSQLForSearchBuilding(buildingSearchRequest))
	                    .append(this.buildWhereSQLForSearchBuilding(buildingSearchRequest))
	                    .append(" GROUP BY BD.id ").toString();
                    
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
    @Override
    public String buildJoinSQLForSearchBuilding(BuildingSearchRequest buildingSearchRequest) {
    	String sql = "";
    	
    	String assignmentbuilding = " JOIN assignmentbuilding ASB on  ASB.buildingid = BD.id ";
        String rentarea = " JOIN rentarea RE ON RE.buildingid = BD.id ";
        String buildingrenttype = " JOIN buildingrenttype BRT ON BRT.buildingid = BD.id JOIN renttype RT ON RT.id = BRT.renttypeid ";
    	String district = " JOIN district DT on DT.id = BD.districtid ";
        
    	if(buildingSearchRequest.getBuildingTypes()!=null) {
    		sql+= buildingrenttype;
    	}
    	
    	if(buildingSearchRequest.getStaffId()!= null) {
    		sql += assignmentbuilding;
    	}
    	if(buildingSearchRequest.getRentEreaFrom() != null || buildingSearchRequest.getRentEreaTo() != null) {
    		sql += rentarea;
    	}
    	if(buildingSearchRequest.getDistrictCode()!= null) {
    		sql += district;
    	}
    	
    	
       

        return sql;
    }
    
    
    
    
    @Override
    public String buildWhereSQLForSearchBuilding(BuildingSearchRequest buildingSearchRequest) {
        StringBuilder whereSQLClause = new StringBuilder(" WHERE 1=1 ");

        whereSQLClause
                .append(this.buildConditionForBuildingType(buildingSearchRequest.getBuildingTypes()))
                .append(this.checkExistenceOfCondition(" AND BD.name LIKE '%", "%' ", buildingSearchRequest.getName()))
                .append(this.checkExistenceOfCondition(" AND BD.street LIKE '%", "%' ", buildingSearchRequest.getStreet()))
                .append(this.checkExistenceOfCondition(" AND BD.ward LIKE '%", "%' ", buildingSearchRequest.getWard()))
                .append(this.checkExistenceOfCondition(" AND DT.code = '", "' ",  buildingSearchRequest.getDistrictCode()))
                .append(this.checkExistenceOfCondition(" AND BD.floorarea = ", " ", buildingSearchRequest.getFloorArea()))
                .append(this.checkExistenceOfCondition(" AND BD.numberOfBasement = '", "' ", buildingSearchRequest.getNumberOfBasement()))
                .append(this.checkExistenceOfCondition(" AND BD.direction  LIKE '%", "%' ", buildingSearchRequest.getDirection()))
                .append(this.checkExistenceOfCondition(" AND BD.Level LIKE '%", "%' ", buildingSearchRequest.getLevel()))
                .append(this.checkExistenceOfCondition(" AND BD.managername LIKE '%", "%' ", buildingSearchRequest.getManagerName()))
                .append(this.checkExistenceOfCondition(" AND BD.managerphone LIKE '%", "%' ", buildingSearchRequest.getManagerPhone()))
                .append(this.checkExistenceOfCondition(" AND ASB.staffid = '", "' ", buildingSearchRequest.getStaffId()))
                .append(this.buildBetweenStatementForBuildingSearch("BD.rentprice", buildingSearchRequest.getRentPriceFrom(), buildingSearchRequest.getRentPriceTo()))
                .append(this.buildBetweenStatementForBuildingSearch("RE.value", buildingSearchRequest.getRentEreaFrom(), buildingSearchRequest.getRentEreaTo()));
                

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
    public String buildBetweenStatementForBuildingSearch(String whereSQLClause, Integer from, Integer to) {
    	
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
	public List<BuildingEntity> findAll(Map<Object,Object> requestParam) {
		
		buildingSearchRequest = buildingConverter.convertMapToBuildingSearchRequest(requestParam);
		String QUERY = buildQueryForSearchBuilding(buildingSearchRequest);
		
		
		
		List<BuildingEntity> listBuildingEntity = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(QUERY);
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
