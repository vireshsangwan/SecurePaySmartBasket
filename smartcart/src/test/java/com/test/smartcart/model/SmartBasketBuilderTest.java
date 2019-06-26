package com.test.smartcart.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.test.smartcart.Constants;
import com.test.smartcart.SmartBasketBuilder;

public class SmartBasketBuilderTest {

	@Test
	public void testGenerateCategories() {
		Map<String, Category> categoryMap = SmartBasketBuilder.generateCategories();

		assertNotNull(categoryMap);
		assertEquals(Constants.MAX_CATEGORIES, categoryMap.size());

		categoryMap.forEach((key, value) -> {
			assertNotNull(value.getName());

			List<Item> items = value.getItems();

			assertNotNull(items);
			assertEquals(Constants.MAX_ITEMS_CREATED_PER_CATEGORY, items.size());

			items.forEach((item) -> {
				assertNotNull(item);
				assertNotNull(item.getCategory());
				assertNotNull(item.getName());

				assertTrue(item.getPrice() >= Constants.MIN_ITEM_PRICE && item.getPrice() <= Constants.MAX_ITEM_PRICE);
				assertTrue(
						item.getRating() >= Constants.MIN_ITEM_RATING && item.getRating() <= Constants.MAX_ITEM_RATING);
				assertTrue(item.getShippingCost() >= Constants.MIN_ITEM_SHIPPING_COST
						&& item.getShippingCost() <= Constants.MAX_ITEM_SHIPPING_COST);

				assertEquals(item.getTotalCost(), item.getPrice() + item.getShippingCost());
			});

		});
	}
	
	@Test
	public void testSortItemsByBestRatingAndPrice_sameTotalCostWithDifferentRatings() {
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 1, 1, 2);
		item2.setCategory(category);
		items.add(item2);

		Item item3 = new Item("item3", 1, 1, 3);
		item3.setCategory(category);
		items.add(item3);

		Item item4 = new Item("item4", 1, 1, 4);
		item4.setCategory(category);
		items.add(item4);

		SmartBasketBuilder.sortItemsByBestRatingAndPrice(items);
		
		assertTrue(items.get(0).getName().equals("item4"));
		assertTrue(items.get(1).getName().equals("item3"));
		assertTrue(items.get(2).getName().equals("item2"));
		assertTrue(items.get(3).getName().equals("item1"));
	}
	
	@Test
	public void testSortItemsByBestRatingAndPrice_differentTotalCostWithSameRatings() {
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 1, 1, 5);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 1, 2, 5);
		item2.setCategory(category);
		items.add(item2);

		Item item3 = new Item("item3", 1, 3, 5);
		item3.setCategory(category);
		items.add(item3);

		Item item4 = new Item("item4", 1, 4, 5);
		item4.setCategory(category);
		items.add(item4);

		SmartBasketBuilder.sortItemsByBestRatingAndPrice(items);
		
		assertTrue(items.get(0).getName().equals("item1"));
		assertTrue(items.get(1).getName().equals("item2"));
		assertTrue(items.get(2).getName().equals("item3"));
		assertTrue(items.get(3).getName().equals("item4"));
	}

	
	@Test
	public void testSortItemsByBestRatingAndPrice_differentTotalCostWithHigherCostRatingsMarginallyHigher() {
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 1, 3, 2);
		item2.setCategory(category);
		items.add(item2);

		SmartBasketBuilder.sortItemsByBestRatingAndPrice(items);
		assertTrue(items.get(0).getName().equals("item1"));
		assertTrue(items.get(1).getName().equals("item2"));
	}

	
	@Test
	public void testSortItemsByBestRatingAndPrice_differentTotalCostWithHigherCostRatingsSignificantlyHigher() {
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 1, 1, 1);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 1, 3, 5);
		item2.setCategory(category);
		items.add(item2);

		SmartBasketBuilder.sortItemsByBestRatingAndPrice(items);
		assertTrue(items.get(0).getName().equals("item2"));
		assertTrue(items.get(1).getName().equals("item1"));
	}
	
	@Test
	public void testFindBestItemAmongstNonContributingCateogryItems_SameCostDifferentRating() {
		
		int totalBasketCost = 40;
		
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 2, 2, 1);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 2, 2, 2);
		item2.setCategory(category);
		items.add(item2);

		Item item3 = new Item("item3", 2, 2, 3);
		item3.setCategory(category);
		items.add(item3);

		Item item4 = new Item("item4", 2, 2, 4);
		item4.setCategory(category);
		items.add(item4);
		
		category.setItems(items);
		
		Map<String, Category> categoryMap = new HashMap<>();
		categoryMap.put("category", category);

		List<String> categoryList = new ArrayList<>();
		categoryList.add("category");
		
		Item selectedItem = SmartBasketBuilder.findBestItemAmongstNonContributingCateogryItems(categoryMap, categoryList, totalBasketCost);
		
		assertTrue(selectedItem.getName().equals("item4"));
	}

	@Test
	public void testFindBestItemAmongstNonContributingCateogryItems_DifferentCostSameRating() {
		
		int totalBasketCost = 40;
		
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 1, 2, 5);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 1, 3, 5);
		item2.setCategory(category);
		items.add(item2);

		Item item3 = new Item("item3", 1, 4, 5);
		item3.setCategory(category);
		items.add(item3);

		Item item4 = new Item("item4", 1, 5, 5);
		item4.setCategory(category);
		items.add(item4);
		
		category.setItems(items);
		
		Map<String, Category> categoryMap = new HashMap<>();
		categoryMap.put("category", category);

		List<String> categoryList = new ArrayList<>();
		categoryList.add("category");
		
		Item selectedItem = SmartBasketBuilder.findBestItemAmongstNonContributingCateogryItems(categoryMap, categoryList, totalBasketCost);
		
		assertTrue(selectedItem.getName().equals("item1"));
	}

	@Test
	public void testFindBestItemAmongstNonContributingCateogryItems_NoItemWithLessTotalCost() {
		
		int totalBasketCost = 47;
		
		List<Item> items = new ArrayList<>();
		Category category = new Category("category");
		Item item1 = new Item("item1", 2, 2, 1);
		item1.setCategory(category);
		items.add(item1);

		Item item2 = new Item("item2", 2, 3, 2);
		item2.setCategory(category);
		items.add(item2);

		Item item3 = new Item("item3", 2, 4, 3);
		item3.setCategory(category);
		items.add(item3);

		Item item4 = new Item("item4", 2, 5, 4);
		item4.setCategory(category);
		items.add(item4);
		
		category.setItems(items);
		
		Map<String, Category> categoryMap = new HashMap<>();
		categoryMap.put("category", category);

		List<String> categoryList = new ArrayList<>();
		categoryList.add("category");
		
		Item selectedItem = SmartBasketBuilder.findBestItemAmongstNonContributingCateogryItems(categoryMap, categoryList, totalBasketCost);
		
		assertNull(selectedItem);
	}

	@Test
	public void testBuildBasket_Automatic() {
		
		Map<String, Category> categoryMap = SmartBasketBuilder.generateCategories();
		
		Basket basket = SmartBasketBuilder.buildBasket(categoryMap);
		
		assertNotNull(basket);
		assertNotNull(basket.getItems());
		
		assertTrue(basket.getTotalCost() <= Constants.MAX_BASKET_COST);
		assertTrue(basket.getCordinates().length() > 0);
	}
	
	
}
