package com.example.demo.service;

public class EmailService {
	private String address;
	private int port;
	public EmailService(String address, int port) {
		super();
		this.address = address;
		this.port = port;
	}
	
	public void sendMessage(String msg) {
		System.out.println("Email sent to " + address + "[ " + msg + " ] ");
	}
}
