package db;

import bean.Product;

public class SynchronizedProduct extends bean.Product {
	private static final long serialVersionUID = -7943105525092890597L;
	private DBHelper helper = DBHelper.getInstance();
//	private Product prod;
	private String prodId;
	
	public SynchronizedProduct(String prodId) {
		this.prodId = prodId;
		Product prod = helper.getProduct(prodId);
		super.setDescription(prod.getDescription());
		super.setId(prod.getId());
		super.setImgUrl(prod.getImgUrl());
		super.setName(prod.getName());
		super.setPrice(prod.getPrice());
		super.setQuantityOnHand(prod.getQuantityOnHand());
	}
	
	public SynchronizedProduct(Product p) {
		this(p.getId());
	}
	
	@Override
	public String getDescription() {
		refreshProduct();
		return super.getDescription();
	}
	
	@Override
	public void setDescription(String description) {
		super.setDescription(description);
		helper.setDescription(this.id, description);
	}
	
	@Override
	public double getPrice() {
		refreshProduct();
		return super.getPrice();
	}
	
	@Override
	public void setPrice(double price) {
		super.setPrice(price);
		helper.setPrice(this.id, price);
	}
	
	@Override
	public String getName() {
		refreshProduct();
		return super.getName();
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		helper.setProductName(this.id, name);
	}
	
	@Override
	public int getQuantityOnHand() {
		refreshProduct();
		return super.getQuantityOnHand();
	}
	
	@Override
	public void setQuantityOnHand(int quantityOnHand) {
		super.setQuantityOnHand(quantityOnHand);
		helper.updateProductStock(quantityOnHand - helper.getStockQuantity(this.id), this.id);
	}
	
	@Override
	public String getImgUrl() {
		refreshProduct();
		return super.getImgUrl();
	}
	
	@Override
	public void setImgUrl(String imgUrl) {
		helper.setImgUrl(prodId, imgUrl);
		super.setImgUrl(imgUrl);
	}
	
	@Override
	public SynchronizedProduct clone() {
		refreshProduct();
		return this;
	}
	
	private void refreshProduct() {
		Product prod = helper.getProduct(prodId);
		super.setDescription(prod.getDescription());
		super.setId(prod.getId());
		super.setImgUrl(prod.getImgUrl());
		super.setName(prod.getName());
		super.setPrice(prod.getPrice());
		super.setQuantityOnHand(prod.getQuantityOnHand());
	}
}
