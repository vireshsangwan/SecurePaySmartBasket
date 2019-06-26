package com.test.smartcart.model;

import java.util.List;

public class Category {
	private String name;
	private List<Item> items;
	
	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
