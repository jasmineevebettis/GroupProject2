package util;

import db.SynchronizedProduct;
import bean.Product;

public class ProductOrder {
	protected Product product;
	protected int quantityOrdered;

	// Transforms into a row in a table
	public String getTableRow() {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr>");
		sb.append("<td>").append(product.getName()).append("</td>");
		sb.append("<td>").append(product.getDescription()).append("</td>");
		sb.append("<td>").append(quantityOrdered).append("</td>");
		sb.append("<td>").append(String.format("$%0,1.2f", product.getPrice())).append("</td>");
		sb.append("<td>");
		sb.append("<form action='RemoveFromCart' method='post' style='display:inline;'>");
		sb.append("<input type='submit' value='X' style='color:red;font-weight:bold;' />");
		sb.append("<input type='hidden' name='id' value='").append(product.getId()).append("' /></form>");
		sb.append("</td></tr>\n");

		return sb.toString();
	}

	public ProductOrder(Product p, int qty) {
		product = new SynchronizedProduct(p);
		quantityOrdered = qty;
	}

	public int getQuantityOnHand() {
		return product.getQuantityOnHand();
	}

	public String getName() {
		return product.getName();
	}

	public void setName(String name) {
		product.setName(name);
	}

	public String getId() {
		return product.getId();
	}

	public String getDescription() {
		return product.getDescription();
	}

	public void setDescription(String description) {
		product.setDescription(description);
	}

	public double getPrice() {
		return product.getPrice();
	}

	public void setPrice(double price) {
		product.setPrice(price);
	}

	public Product getProduct() {
		return product.clone();
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void updateQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public String getImgUrl() {
		return product.getImgUrl();
	}

	public void setImgUrl(String imgUrl) {
		product.setImgUrl(imgUrl);
	}
}
