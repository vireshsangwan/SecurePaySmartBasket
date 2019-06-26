package com.test.smartcart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.smartcart.model.Basket;
import com.test.smartcart.model.Category;
import com.test.smartcart.model.Item;

public class SmartBasketBuilder {

	public static Map<String, Category> generateCategories() {
		Map<String, Category> categoryMap = new HashMap<>();

		// Generate categories
		for (int categoryCount = 1; categoryCount <= Constants.MAX_CATEGORIES; categoryCount++) {
			Category category = new Category("Category" + categoryCount);
			List<Item> items = new ArrayList<>();
			for (int itemCount = 1; itemCount <= Constants.MAX_ITEMS_CREATED_PER_CATEGORY; itemCount++) {
				Item item = new Item("Item" + itemCount /* Item name */,
						(int) (Math.random() * (Constants.MAX_ITEM_PRICE - Constants.MIN_ITEM_PRICE + 1)
								+ Constants.MIN_ITEM_PRICE) /* Item price */,
						(int) (Math.random() * (Constants.MAX_ITEM_SHIPPING_COST - Constants.MIN_ITEM_SHIPPING_COST + 1)
								+ Constants.MIN_ITEM_SHIPPING_COST) /* Item shipping cost */,
						(int) (Math.random() * (Constants.MAX_ITEM_RATING - Constants.MIN_ITEM_RATING + 1)
								+ Constants.MIN_ITEM_RATING /* Item rating */));

				item.setCategory(category);
				items.add(item);

				// Printing the generated items as these can be used for validating the output
				System.out.println(item);
			}

			category.setItems(items);
			categoryMap.put(category.getName(), category);
		}

		return categoryMap;
	}

	public static Basket buildBasket(Map<String, Category> categoryMap) {
		Basket basket = new Basket();
		List<Item> eligibleBestItemsFromEachCategory = new ArrayList<>();

		// Iterate over each category
		categoryMap.forEach((key, value) -> {
			// Sort all items in category by best rating and price
			sortItemsByBestRatingAndPrice(value.getItems());

			// Pick the best amongst all the items and store in separate collection
			eligibleBestItemsFromEachCategory.add(value.getItems().get(0));
		});

		// Sort by best rating and price amongst the list of chosen best from each
		// category
		sortItemsByBestRatingAndPrice(eligibleBestItemsFromEachCategory);

		// This list will maintain the categories that could not contribute in basket
		List<String> categoriesNotIncludedInBasket = new ArrayList<>();

		// Iterate over the list of sorted items and start filling basket
		for (Item bestItemFromEachCategory : eligibleBestItemsFromEachCategory) {
			int itemTotalCost = bestItemFromEachCategory.getTotalCost();

			// Add the item only if more items can be added (leaves price gap of > 3)
			// TODO Need to analyze this strategy of reducing total cost
			if ((basket.getTotalCost() + itemTotalCost) <= (Constants.MAX_BASKET_COST - Constants.MIN_ITEM_PRICE
					- Constants.MIN_ITEM_SHIPPING_COST)) {
				basket.addItem(bestItemFromEachCategory);
			} else {
				// Record the category that could not contribute in basket
				categoriesNotIncludedInBasket.add(bestItemFromEachCategory.getCategory().getName());
				System.out.println("Item that could not fit : " + bestItemFromEachCategory);
			}
		}

		// Now find the last item with least price & best rating
		Item bestItemAmongstNonContributingCateogryItems = findBestItemAmongstNonContributingCateogryItems(categoryMap,
				categoriesNotIncludedInBasket, basket.getTotalCost());

		// Now, add the best item found to the basket
		if (bestItemAmongstNonContributingCateogryItems != null) {
			System.out.println("Choosen amongst rest: " + bestItemAmongstNonContributingCateogryItems.getTotalCost()
					+ ", " + bestItemAmongstNonContributingCateogryItems.getRating());

			basket.addItem(bestItemAmongstNonContributingCateogryItems);
		}

		return basket;
	}

	public static List<Item> sortItemsByBestRatingAndPrice(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item item1, Item item2) {
				// If cost is equal, chose the best rating item to be at start of list
				if (item1.getTotalCost() == item2.getTotalCost()) {
					return (item1.getRating() > item2.getRating()) ? -1
							: (item1.getRating() < item2.getRating()) ? 1 : 0;
				} else { // If cost is not equal, check if costlier item has justified rating
					if ((item2.getRating() - item1.getRating()) > (item2.getTotalCost() - item1.getTotalCost())) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		});

		System.out.println("Sorted List by Ratings and Price: " + items);
		return items;
	}

	public static Item findBestItemAmongstNonContributingCateogryItems(Map<String, Category> categoryMap,
			List<String> categoriesNotIncludedInBasket, int currentTotalBasketCost) {
		int maxTotalCostItemPossible = Constants.MAX_BASKET_COST - currentTotalBasketCost;
		System.out.println("maxPriceItemPossible: " + maxTotalCostItemPossible);

		int leastCostAmongstNonContributingCateogryItems = maxTotalCostItemPossible;
		int maxRatingAmongstNonContributingCateogryItems = 0;
		Item bestItemAmongstNonContributingCateogryItems = null;

		for (String categoryName : categoriesNotIncludedInBasket) {
			// Iterate over each item in the list to find best match
			// This list is already sorted by best price+rating combination
			List<Item> itemsListForCategory = categoryMap.get(categoryName).getItems();
			for (Item item : itemsListForCategory) {
				// Pick the item if its cost is less than total allowed and have max ratings
				if (item.getTotalCost() <= maxTotalCostItemPossible
						&& ((item.getRating() > maxRatingAmongstNonContributingCateogryItems)
								|| (item.getRating() == maxRatingAmongstNonContributingCateogryItems
										&& item.getTotalCost() < leastCostAmongstNonContributingCateogryItems))) {
					// Record the best rating found so far
					maxRatingAmongstNonContributingCateogryItems = item.getRating();

					leastCostAmongstNonContributingCateogryItems = item.getTotalCost();
					// Record the best matching item found so far
					bestItemAmongstNonContributingCateogryItems = item;
				}
			}
		}

		return bestItemAmongstNonContributingCateogryItems;
	}

}
