package com.test.smartcart.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BasketTest {

	@Test
	public void testCordinates() {
		Basket basket = new Basket();
		assertEquals("", basket.getCordinates());

		Category category1 = new Category("category1");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category1);

		basket.addItem(item1);
		assertEquals("category1:item1", basket.getCordinates());

		Category category2 = new Category("category2");
		Item item2 = new Item("item2", 1, 1, 1);
		item2.setCategory(category2);

		basket.addItem(item2);
		assertEquals("category1:item1,category2:item2", basket.getCordinates());

		// Test the clearing and setting of new item list
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		basket.setItems(items);
		assertEquals("category1:item1,category2:item2", basket.getCordinates());
	}

	@Test
	public void testTotalCosts() {
		Basket basket = new Basket();
		assertEquals(0, basket.getTotalCost());

		Category category1 = new Category("category1");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category1);

		basket.addItem(item1);
		assertEquals(2, basket.getTotalCost());

		Category category2 = new Category("category2");

		Item item2 = new Item("item2", 2, 2, 1);
		item2.setCategory(category2);

		basket.addItem(item2);
		assertEquals(6, basket.getTotalCost());

		// Test the clearing and setting of new item list
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		basket.setItems(items);
		assertEquals(6, basket.getTotalCost());
	}

	@Test
	public void testSumOfRatings() {
		Basket basket = new Basket();
		assertEquals(0, basket.getSumOfRatings());

		Category category1 = new Category("category1");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category1);

		basket.addItem(item1);
		assertEquals(1, basket.getSumOfRatings());

		Category category2 = new Category("category2");
		Item item2 = new Item("item2", 2, 2, 3);
		item2.setCategory(category2);

		basket.addItem(item2);
		assertEquals(4, basket.getSumOfRatings());

		// Test the clearing and setting of new item list
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		basket.setItems(items);
		assertEquals(4, basket.getSumOfRatings());
	}
}
