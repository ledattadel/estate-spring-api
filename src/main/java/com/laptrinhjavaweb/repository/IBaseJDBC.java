package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.repository.mapper.IRowMapper;

public interface IBaseJDBC {
	
		<T> List<T> query(String sql, IRowMapper<T> objectMapper, Object... parameters);


}
