package com.test.smartcart;

import java.util.Map;

import com.test.smartcart.model.Basket;
import com.test.smartcart.model.Category;

public class SmartBasketApplication {
	public static void main(String[] args) {
		Map<String, Category> categoryMap = SmartBasketBuilder.generateCategories();

		// Build basket
		Basket basket = SmartBasketBuilder.buildBasket(categoryMap);

		System.out.println("***************************************************");
		System.out.println("*****************     RESULTS     *****************");
		System.out.println("***************************************************");

		System.out.println("Cordinates: " + basket.getCordinates());
		System.out.println("Total Cost: " + basket.getTotalCost());
		System.out.println("Sum of Ratings: " + basket.getSumOfRatings());
		System.out.println("Total Items: " + basket.getItems().size());
	}
}
