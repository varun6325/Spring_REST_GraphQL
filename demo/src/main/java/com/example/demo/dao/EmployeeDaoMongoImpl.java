package com.example.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "dao", havingValue = "mongo")
public class EmployeeDaoMongoImpl implements EmployeeDao {

	@Override
	public void addEmployee() {
		System.out.println("mongo Store!!!");
	}

}
