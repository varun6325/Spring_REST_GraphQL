package com.adobe.demo.entity;

public class Tv extends Product {
	private String screenType;

	public Tv() {
	}

	public Tv(int id, String name, double price, String screenType) {
		super(id, name, price);
		this.screenType = screenType;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
 
 
}
