package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBHelper;
import util.Cart;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("user");
		Object logck = session.getAttribute("logcheck");
		Cart cart = (Cart)session.getAttribute("cart");
		int orderNum = cart.getOrderNumber();
		DBHelper helper = DBHelper.getInstance();
		session.setAttribute("errortext", "");
		
		if(cart == null) {
			Logger.getLogger("PurchaseServlet").log(Level.SEVERE, "No cart object found in the session");
			response.sendRedirect("LogOutServlet");
			return;
		} else if(userId == null || userId.equals("") || logck == null) {
			Logger.getLogger("PurchaseServlet").log(Level.WARNING, "Invalid app state");
			response.sendRedirect("LogOutServlet");
			return;
		}
		
		session.setAttribute("errortext", "Thank you for your purchase! Your order number is: " + orderNum);
		boolean success = helper.updateOrderToPaid(cart);
		if(success) {
			session.setAttribute("cart", Cart.getNewCart(userId));
		} else {
			session.setAttribute("errortext", "Unable to submit order");
		}
		response.sendRedirect("ViewAllProducts.jsp");
	}

}
