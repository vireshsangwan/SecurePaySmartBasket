# SecurePaySmartBasket
Coding exercise from SecurePay

# Problem Statement
Imagine you have a basket. Your objective is to fill this basket with as many items as you can. However, there are 20 categories of items and you may only pick 1 item from each category. Each item has a price, a shipping cost and a rating.

Write a program that adheres to the above constraints and does the following each time you run it. 
* Generates;
	* 20 item categories - (Category1, Category2, …. , Category20) 
	* 10 items of each category – (Item 1, Item 2, …., Item 10).
* Each item should be randomly assigned;
	* A price of between $1 to $20
	* A shipping cost of between $2 to $5
	* A rating of between 1 to 5 (a bigger value indicates a better rating)
* Picks as many items as you can for the basket, while keeping the total cost (price + shipping cost) of all picked items below $50, and ensuring that the sum of ratings of all items picked is optimized.

At the end, your program should;
* Print the coordinates of the selected items. e.g. – CategoryA:ItemX,CategoryB:ItemY, ….
* Total cost, and
* Sum of ratings of all the items that were picked

## Assumptions
The requirement "ensuring that the sum of ratings of all items picked is optimized" was not very clear as what is the definition of "optimized" in the project's context?

Some assumptions, as listed below, were taken to prepare the solution:
* All the costs etc are small and non fractional, hence data type 'int' has used. In commercial applications BigDecimal must be used.
* It is assumed that definition of "sum of ratings of all items picked is optimized" is to achieve highest rating with lowest cost
* If rating is significantly higher for the costlier item, the item with higher cost has been considered to be added to basket. Eg: If difference in ratings is higher than difference in costs, the costly item is considered. Ex: Item1: Cost=3,Rating=1 and Item2: Cost=5&Rating=5. Here Item2 is considered to be more value efficient
* It is assumed to be a simple, coding exercise hence plain java is used (no framework)
