package util;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
import util.ProductOrder;
import bean.Product;

public class Cart {
	public static final int DEFAULT_ORDER_ID = 0;
	protected ArrayList<ProductOrder> orderedItems = new ArrayList<>();
	protected int orderNumber = DEFAULT_ORDER_ID;
	protected String userId;
	private DBHelper helper = DBHelper.getInstance();

	public Cart() {
	}

	public synchronized boolean addToCart(Product p, int qty) {
		if (qty <= 0)
			return false;
		if (p == null || p.getQuantityOnHand() == 0)
			return false;

		if (orderNumber == DEFAULT_ORDER_ID) {
			orderNumber = helper.getNewOrderNumber(userId);
		}
		for (ProductOrder order : orderedItems) {
			if (order.getId().equals(p.getId())) {
				if (order.getQuantityOrdered() == qty)
					return true;
				if (qty > order.getQuantityOnHand())
					return false;
				int q = helper.updateQuantityOrdered(orderNumber, p.getId(), qty);
				order.updateQuantityOrdered(q);
				return true;
			}
		}
		if (helper.addToOrder(p, qty, orderNumber, userId)) {
			this.notifyAll();
			return orderedItems.add(new ProductOrder(p, qty));
		} else {
			this.notifyAll();
			return false;
		}
	}

	public synchronized boolean removeFromCart(String productId) {
		if (orderNumber == DEFAULT_ORDER_ID) {
			return false;
		}
		for (ProductOrder order : orderedItems) {
			if (order.getId().equals(productId)) {
				orderedItems.remove(order);
				this.notifyAll();
				return helper.removeFromOrder(order.getProduct(), orderNumber);
			}
		}
		this.notifyAll();
		return false;
	}

	public List<ProductOrder> getOrderedItems() {
		return orderedItems;
	}

	public double getTotal() {
		double sum = 0;
		for (ProductOrder o : orderedItems) {
			sum += o.getPrice() * o.getQuantityOrdered();
		}
		return sum;
	}

	public void invalidate() {
		helper.deleteOrder(orderNumber);
		orderedItems.clear();
		orderNumber = DEFAULT_ORDER_ID;
	}

	public static Cart getNewCart(String userId) {
		Cart c = new Cart();
		c.userId = userId;
		return c;
	}

	public static Cart getExistingCart(int orderId, String userId) {
		Cart c = DBHelper.getInstance().getExistingCart(orderId, userId);
		return c;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrderedItems(ArrayList<ProductOrder> orderedItems) {
		this.orderedItems = orderedItems;
	}
}
