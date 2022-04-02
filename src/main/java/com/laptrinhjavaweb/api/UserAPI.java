package com.laptrinhjavaweb.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.UserDTO;

@RestController
public class UserAPI {
	
	// giai quyet dc 2 bai toan, neu khong truyen building vao thi la get all nhan vien, neu truyen vao thi se Load dc nhan vien nao dang quan ly toa nha
	
	@GetMapping(value="/api/staffs")
	public List<UserDTO>getStaffs(@RequestParam(value="building_id", required = false) Long buildingId){
		List<UserDTO> results = new ArrayList<>();
		UserDTO dto1 = new UserDTO();
		dto1.setFullName("Nguyen van a");
		dto1.setChecked("");
		results.add(dto1);
	
		UserDTO dto2 = new UserDTO();
		dto2.setFullName("Nguyen van B");
		dto2.setChecked("checked");
		results.add(dto2);
		
		return results;
	}
	
	
	
}
