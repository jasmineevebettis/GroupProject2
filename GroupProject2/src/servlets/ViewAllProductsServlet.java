package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Product;
import db.DBHelper;

/**
 * Servlet implementation class ViewAllProductsServlet
 */
@WebServlet("/ViewAllProductsServlet")
public class ViewAllProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewAllProductsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(); // Establish new session or grab from LoginServlet if one was created
		session.setAttribute("errortext", "");
		DBHelper dbh = DBHelper.getInstance(); // default DBHelper construtor to gain access to DB methods
		ArrayList<Product> searchResults = new ArrayList<Product>(); // initialize variables
		String custChoice = req.getParameter("custChoice");
		if (custChoice == null || custChoice.equals("")) {
			searchResults = dbh.getAllProducts(); // execute SQL Query to place all DB records in Product ArrayList
		}
		if (custChoice != null) {
			searchResults = dbh.searchProducts(custChoice);
		}
		session.setAttribute("searchResults", searchResults);
		res.sendRedirect("ViewAllProducts.jsp");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
