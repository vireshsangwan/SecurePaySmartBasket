package com.test.smartcart.model;

public class Item {
	private String name;
	private int price;
	private int shippingCost;
	private int rating;
	private Category category;

	public Item(String name, int price, int shippingCost, int rating) {
		this.name = name;
		this.price = price;
		this.shippingCost = shippingCost;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(int shippingCost) {
		this.shippingCost = shippingCost;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getTotalCost() {
		return shippingCost + price;
	}
	
	@Override
	public String toString() {
		return "name: " + this.name
				+ ", price: " + this.price
				+ ", shippingCost: " + this.shippingCost
				+ ", totalCost: " + this.getTotalCost()
				+ ", rating: " + this.rating
				+ ", category: " + this.category.getName();
	}
}