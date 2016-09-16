package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBHelper;
import util.Cart;
import bean.Product;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String ProductID = null;
		int Qty = 1;
		ProductID = request.getParameter("ProductID");
		HttpSession session = request.getSession();
		session.setAttribute("errortext", "");

		try {
			Qty = Integer.parseInt(request.getParameter("Qty"));
		} catch (Exception e) {
			Qty = 1;
			System.out.println("<<<------------------Qty entered on jsp was not a valid #");
		}

		Cart cart = (Cart) session.getAttribute("cart");
		RequestDispatcher rd;
		Product product = DBHelper.getInstance().getProduct(ProductID);

		if (product == null) {
			System.out.println("<<<-------------------Product object is null");
			session.setAttribute("errortext", "That item was not found in stock.");
			rd = request.getRequestDispatcher("ViewAllProductsServlet");
			rd.forward(request, response);
			return;
		}

		if (!cart.addToCart(product, Qty)) {
			System.out.println("<<<-------------------Failed to add product and qty to cart object");
			session.setAttribute("errortext", "Failed to add product and qty to your cart.  Please try again.");
			rd = request.getRequestDispatcher("ViewAllProductsServlet");
			rd.forward(request, response);
			return;
		}

		session.setAttribute("infotext", "Item was successfully added to the cart");
		rd = request.getRequestDispatcher("ViewAllProductsServlet");//
		rd.forward(request, response);
		return;

	}
}
