package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Cart;
import util.ProductOrder;
import bean.Product;

public class DBHelper {

	private Connection connection = null;

	private DBHelper() {
	}

	// get connection for all following methods
	public Connection getConnection() {

		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager
					.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
							"orderdb", "orderdb");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (Exception e) {
		}
		return connection;

	}

	// JINA: Check user credentials
	public boolean checkCredentials(String username, String password) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Select * from users where user_id = ? and password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// JASMINE: admin functions; add a new product
	public boolean addNewProduct(String name, String prod_id, double price,
			String description, int stock, String img) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Insert into products values(?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, prod_id);
			pstmt.setDouble(3, price);
			pstmt.setString(4, description);
			pstmt.setInt(5, stock);
			pstmt.setString(6, img);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// JASMINE: admin functions; remove a product
	public boolean removeProduct(String product_id) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("delete from products where prod_id = ?");
			pstmt.setString(1, product_id);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// JASMINE: admin functions; updateProduct
	// Accept string value--database will automatically change into number value
	public boolean updateProduct(String column, String value, String product_id) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = null;
			if (column.equals("description")) {
				pstmt = connection
						.prepareStatement("update products set description = ? where prod_id = ?");
				pstmt.setString(1, value);
				pstmt.setString(2, product_id);
			} else if (column.equals("name")) {
				pstmt = connection
						.prepareStatement("update products set name = ? where prod_id = ?");
				pstmt.setString(1, value);
				pstmt.setString(2, product_id);
			} else if (column.equals("price")) {
				pstmt = connection
						.prepareStatement("update products set price = ? where prod_id = ?");
				pstmt.setDouble(1, Double.parseDouble(value));
				pstmt.setString(2, product_id);
			} else if (column.equals("stock")) {
				pstmt = connection
						.prepareStatement("update products set stock = ? where prod_id = ?");
				pstmt.setDouble(1, Double.parseDouble(value));
				pstmt.setString(2, product_id);
			} else if (column.equals("image")) {
				pstmt = connection
						.prepareStatement("update products set image = ? where prod_id = ?");
				pstmt.setString(1, value);
				pstmt.setString(2, product_id);
			}

			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// CART get new order number; gets new order_no from sequence order_no
	public int getNewOrderNumber(String user_id) {
		int order_no = 0;
		try {
			connection = getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("Select order_no.nextval \"val\" from dual");

			if (rs.next()) {
				order_no = rs.getInt("val");
				System.out.println("<<<-------------order_no sucks");
				return order_no;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order_no;
	}

	// add to Order; add a row to the add a row to the order table with the
	// given quantity
	public boolean addToOrder(Product p, int qty, int order_no, String user_id) {
		String prod_id = p.getId();
		// String sysdate = "sysdate"; <<<----No longer used
		String paid = "no";
		try {
			int stockQuantity = getStockQuantity(prod_id);
			if (qty > stockQuantity) {
				return false;
			}
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Insert into orders values (?, ?, sysdate, ?, ?, ?)");
			pstmt.setString(1, prod_id);
			pstmt.setString(2, user_id);
			// pstmt.setString(3, sysdate);
			pstmt.setInt(3, qty);
			pstmt.setInt(4, order_no);
			pstmt.setString(5, paid);

			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// remove item from order; delete a row from the orders table
	public boolean removeFromOrder(Product p, int order_no) {
		String prod_id = p.getId();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Delete from orders where prod_id = ? and order_no = ? and paid = 'no'");
			pstmt.setString(1, prod_id);
			pstmt.setInt(2, order_no);

			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// delete order; used on logout to invalidate the Cart and associated order
	public boolean deleteOrder(int order_no) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Delete from orders where order_no = ? and paid = 'no'");
			pstmt.setInt(1, order_no);

			int rs = pstmt.executeUpdate();
			if (rs >= 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// AARON: get Product; returns all information about a particular product
	public Product getProduct(String prod_id) {
		Product product = new Product();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Select * from products where prod_id = ?");
			pstmt.setString(1, prod_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String prod_name = rs.getString("prod_name");
				double price = rs.getDouble("price");
				String description = rs.getString("description");
				int stock = rs.getInt("stock");
				String image = rs.getString("image");

				product.setName(prod_name);
				product.setId(prod_id);
				product.setPrice(price);
				product.setDescription(description);
				product.setQuantityOnHand(stock);
				product.setImgUrl(image);
				return product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// returns a list of all products offered
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from products");
			while (rs.next()) {
				String prod_id = rs.getString("prod_id");
				Product p = getProduct(prod_id);
				products.add(p);
			}
			return products;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;

	}

	// CURTIS: returns a list of products ordered by price descending
	public ArrayList<Product> getAllProductsByPrice() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("Select * from products order by price desc");
			while (rs.next()) {
				String prod_id = rs.getString("prod_id");
				Product p = getProduct(prod_id);
				products.add(p);
			}
			return products;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;

	}

	// CURTIS: searches the product name and description for entered keyword and
	// returns a list of search results
	public ArrayList<Product> searchProducts(String name) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("select * from products where upper(prod_name) like upper(?) or upper(description) like upper(?) order by price desc");
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + name + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String prod_id = rs.getString("prod_id");
				Product p = getProduct(prod_id);
				products.add(p);
			}
			return products;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;

	}

	// get existing paid cart from a previous order
	public Cart getExistingCart(int order_no, String user_id) {
		Cart c = new Cart();
		ArrayList<ProductOrder> prodOrder = new ArrayList<ProductOrder>();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Select * from orders o, products p where o.prod_id = p.prod_id and o.order_no = ? and o.user_id = ? and o.paid = 'yes'");
			pstmt.setInt(1, order_no);
			pstmt.setString(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					String prod_id = rs.getString("prod_id");
					int qty = rs.getInt("qty");
					Product product = getProduct(prod_id);
					ProductOrder prod = new ProductOrder(product, qty);
					prodOrder.add(prod);
				} while (rs.next());
			} else {
				Logger.getLogger("DBHelper").log(Level.SEVERE, "No results found");
				return null;
			}
			c.setOrderedItems(prodOrder);
			c.setOrderNumber(order_no);
			if(c.getTotal() == 0) {
				return null;
			}
			return c;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// set a product name in the database
	public boolean setProductName(String prod_id, String prod_name) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update products set prod_name = ? where prod_id = ?");
			pstmt.setString(1, prod_name);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// set the product description in the database
	public boolean setDescription(String prod_id, String description) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update products set description = ? where prod_id = ?");
			pstmt.setString(1, description);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// set product price in the database
	public boolean setPrice(String prod_id, double price) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update products set price = ? where prod_id = ?");
			pstmt.setDouble(1, price);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// set image url in the database
	public boolean setImgUrl(String prod_id, String image) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update products set image = ? where prod_id = ?");
			pstmt.setString(1, image);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// get current stock quantity of a specified product
	public int getStockQuantity(String prod_id) {
		int stock = 0;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("Select stock from products where prod_id = ?");
			pstmt.setString(1, prod_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				stock = rs.getInt("stock");
				return stock;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stock;
	}

	// change the quantity of the order;
	// used if user changes quantity they want to purchase
	public int updateQuantityOrdered(int order_no, String prod_id,
			int newQuantity) {
		try {
			int stockQuantity = getStockQuantity(prod_id);
			if (newQuantity > stockQuantity) {
				newQuantity = stockQuantity;
			}
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update orders set qty = ? where prod_id = ? and order_no = ? and paid = 'no'");
			pstmt.setInt(1, newQuantity);
			pstmt.setString(2, prod_id);
			pstmt.setInt(3, order_no);
			int rs = pstmt.executeUpdate();
			if (rs == 1) {
				return newQuantity;
			} else
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	// update paid status in database when user pays for order
	public boolean updateToPaid(int order_no, String prod_id) {
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(
					"update orders set paid = 'yes' where order_no = ? and prod_id = ?");
			pstmt.setInt(1, order_no);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs >= 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// updates the stock in the product database after a purchase is made
	public boolean updateProductStock(int qtySold, String prod_id) {
		try {
			int currentQty = getStockQuantity(prod_id);
			int updatedQty = currentQty - qtySold;
			connection = getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("update products set stock = ? where prod_id = ?");
			pstmt.setInt(1, updatedQty);
			pstmt.setString(2, prod_id);
			int rs = pstmt.executeUpdate();
			if (rs >= 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// update paid status and stock quantity at time of purchase
	public boolean updateOrderToPaid(Cart cart) {
		int order_no = cart.getOrderNumber();
		List<ProductOrder> orderedItems = cart.getOrderedItems();
		try {
			Logger log = Logger.getLogger("DBHelper");
			boolean autoCom = connection.getAutoCommit();
			connection.setAutoCommit(false);
			for (ProductOrder prodOrd : orderedItems) {
				boolean updatedStock = updateProductStock(
						prodOrd.getQuantityOrdered(), prodOrd.getId());
				if (updatedStock) {
					boolean updatedPayStatus = updateToPaid(order_no,
							prodOrd.getId());
					if (updatedPayStatus) {
						continue;
					} else {
						log.log(Level.SEVERE, "Unable to update paid status");
						connection.rollback();
						return false;
					}
				} else {
					log.log(Level.SEVERE, "Unable to update stock");
					connection.rollback();
					return false;
				}
			}
			connection.commit();
			connection.setAutoCommit(autoCom);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// SMITHU: add new user details to the database
	public void addDetails(String user_id, String user_name, String address,
			String email, String telephone, String password)
			throws SQLException, ClassNotFoundException {
		try {
			connection = getConnection();
			PreparedStatement pstmt1 = connection
					.prepareStatement("insert into users values(?, ?, ?, ?, ?, ?)");
			pstmt1.setString(1, user_id);
			pstmt1.setString(2, user_name);
			pstmt1.setString(3, address);
			pstmt1.setString(4, email);
			pstmt1.setString(5, telephone);
			pstmt1.setString(6, password);
			pstmt1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// close connection when user logs out or finishes purchase
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception any) {
			}
		}
	}

	private static class InstanceHolder {
		private static final DBHelper INSTANCE = new DBHelper();
	}

	public static DBHelper getInstance() {
		return InstanceHolder.INSTANCE;
	}
}