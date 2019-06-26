package com.test.smartcart.model;

import java.util.ArrayList;
import java.util.List;

import com.test.smartcart.Constants;

public class Basket {
	private List<Item> items = new ArrayList<>();
	private int totalCost;
	private int sumOfRatings;
	private StringBuilder cordinateBuilder = new StringBuilder();

	public String getCordinates() {
		if (this.cordinateBuilder.length() == 0) {
			return "";
		}

		return this.cordinateBuilder.substring(0, this.cordinateBuilder.length() - 1);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		clearBasket();
		items.forEach(this :: addItem);
	}

	public void addItem(Item item) {
		this.items.add(item);
		this.totalCost += item.getTotalCost();
		this.sumOfRatings += item.getRating();
		this.cordinateBuilder.append(item.getCategory().getName()).append(Constants.COLON).append(item.getName())
				.append(Constants.COMMA);
	}

	public void addItems(List<Item> items) {
		items.forEach(this :: addItem);
	}
	
	private void clearBasket() {
		this.items = new ArrayList<>();
		this.totalCost = 0;
		this.sumOfRatings = 0;
		this.cordinateBuilder = new StringBuilder();
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public long getSumOfRatings() {
		return sumOfRatings;
	}

	public void setSumOfRatings(int sumOfRatings) {
		this.sumOfRatings = sumOfRatings;
	}

}
