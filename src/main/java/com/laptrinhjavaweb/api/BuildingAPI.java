package com.laptrinhjavaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.ResponeDTO;
import com.laptrinhjavaweb.dto.request.BuildingAssignmentRequest;
import com.laptrinhjavaweb.dto.response.BuildingSearchResponse;
import com.laptrinhjavaweb.myexception.FieldRequiredException;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.filter.BuildingFilter;

import ch.qos.logback.core.net.SyslogOutputStream;

@RestController 
public class BuildingAPI {
	
	@Autowired
	private BuildingService buildingService;
	
	
	@GetMapping("/api/building")
	public List<BuildingSearchResponse> getBuildingList(@RequestParam Map<String,String> requestParam,
			 											@RequestParam(value = "listType", required = false) List<String> listType) {
		
		
		
			List<BuildingSearchResponse> results = buildingService.findAll(requestParam, listType);
			
			return results;
	}
	


	@GetMapping("/api/building/{id}")
	public List<BuildingDTO> getBuildingDetail(@PathVariable("id") long id) {

		return null;
	}

	@PostMapping("/api/building")
	public ResponeDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
		ResponeDTO result = new ResponeDTO();
		try {
				
			validateDataBuilding(buildingDTO);
			return buildingDTO;
		} catch (FieldRequiredException e) {
			
			throw e; 
			
		}
		
	}

	private void validateDataBuilding(BuildingDTO buildingDTO) throws FieldRequiredException{
		if (buildingDTO.getName() == null) {

			// throw an object of user defined exception
			throw new FieldRequiredException("Name is required");
		} 
	}

	@PutMapping("/api/building")
	public List<BuildingDTO> updateBuilding() {

		return null;
	}

	@DeleteMapping("/api/building")
	public List<BuildingDTO> deleteBuilding(@RequestBody String[] ids) {
		System.out.println("Success");
		return null;
	}
	
	@PostMapping("/api/building/assignment")
	public void assignBuildingToStaff(@RequestBody BuildingAssignmentRequest buildingAssignmentRequest) {
		
	}

	
	
}
