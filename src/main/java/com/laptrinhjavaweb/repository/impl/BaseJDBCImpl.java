package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.laptrinhjavaweb.repository.IBaseJDBC;
import com.laptrinhjavaweb.repository.mapper.IRowMapper;

public class BaseJDBCImpl implements IBaseJDBC {

	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "1234";

	@Override
	public <T> List<T> query(String sql, IRowMapper<T> objectMapper, Object... parameters) {
		List<T> results = new ArrayList<>();

		try (

				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				results.add(objectMapper.mapRow(rs));
			}

			return results;

		} catch (SQLException e) {
			e.printStackTrace();

			return new ArrayList<>();
		}

	}

}
