package biz.sobie.web.beans;

import java.util.List;

public class ShoppingCart {

	private List<Product> shoppingCartProducts = null;
	private double grandTotal;
	private int totalItems;

	public List<Product> getShoppingCartProducts() {
		return shoppingCartProducts;
	}

	public void setShoppingCartProducts(List<Product> shoppingCartProducts) {
		this.shoppingCartProducts = shoppingCartProducts;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
}
