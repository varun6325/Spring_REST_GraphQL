package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;

@Service
public class MyService {
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmailService emailService;
	
	public void doTask() {
		employeeDao.addEmployee();
		emailService.sendMessage("Employee added");
	}
	
}
