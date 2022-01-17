package com.example.demo.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class EmployeeDaoDbImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("added in database");
	}

}
