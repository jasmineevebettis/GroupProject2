package bean;

import java.io.Serializable;

public class Product implements Serializable, Cloneable {
	private static final long serialVersionUID = -4973525984807336748L;
	protected String name, id, description;
	protected double price;
	protected int quantityOnHand;
	protected String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTableRow() {
		// Name, desc, price, view/add
		StringBuffer sb = new StringBuffer();

		sb.append("<tr>");
		sb.append("<td>").append(getName()).append("</td>");
		sb.append("<td style='max-width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>").append(getDescription()).append("</td>");
		sb.append("<td>").append(String.format("$%0,1.2f", getPrice())).append("</td>");
		sb.append("<td>");
		sb.append("<form action='ViewProduct.jsp' method='post' style='display:inline;'>");
		sb.append("<input type='submit' value='?' style='color:blue;font-weight:bold;' />");
		sb.append("<input type='hidden' name='id' value='").append(getId()).append("' /></form>");
		sb.append("<form action='AddToCartServlet' method='post' style='display:inline;'>");
		if (quantityOnHand == 0) {
			sb.append("<input type='submit' value='+' style='color:green;font-weight:bold;' disabled />");
			sb.append("<input type='number' name='Qty' style='margin-left:0;' maxlength='3' required pattern='^[0-9]+$' title='Must be a number' value='1' disabled />");
			sb.append("<input type='hidden' name='ProductID' value='").append(getId()).append("' /></form>");
		} else {
			sb.append("<input type='submit' value='+' style='color:green;font-weight:bold;' />");
			sb.append("<input type='text' name='Qty' style='margin-left:0;' maxlength='3' required pattern='^[0-9]+$' title='Must be a number' value='1' />");
			sb.append("<input type='hidden' name='ProductID' value='").append(getId()).append("' /></form>");
		}
		sb.append("</td></tr>");

		return sb.toString();
	}

	public int getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(int quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public Product clone() {
		Product p = new Product();
		p.setDescription(this.getDescription());
		p.setId(this.getId());
		p.setName(this.getName());
		p.setPrice(this.getPrice());
		p.setQuantityOnHand(this.getQuantityOnHand());
		return p;
	}
}
