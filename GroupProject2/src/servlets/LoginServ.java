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

@WebServlet("/LoginServ")
public class LoginServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServ() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("USER");
		String password = request.getParameter("PASS");
		HttpSession session = request.getSession();
		session.setAttribute("errortext", "");
		DBHelper db = DBHelper.getInstance();
		

		if (username == null || username.equals("") || password == null || password.equals("")) {
			session.setAttribute("errortext", "Please enter your user ID and password and try again.");
			
			response.sendRedirect("Login.jsp");
			return;
		} else if (username.equals("admin")) {
			boolean admin = db.checkCredentials(username, password);
			if (admin) {
				session.setAttribute("logcheck", true);
				session.setAttribute("user", "admin");
				RequestDispatcher rd = request
						.getRequestDispatcher("AdminFunctions.jsp");
				rd.forward(request, response);
				return;
			} else {
				session.setAttribute("errortext", "Please enter your user ID and password and try again.");
				response.sendRedirect("Login.jsp");
				return;
			}
		} else {
			boolean user = db.checkCredentials(username, password);
			if (user) {
				session.setAttribute("logcheck", true);
				session.setAttribute("user", username);
				session.setAttribute("cart", util.Cart.getNewCart(username));
				RequestDispatcher rd = request.getRequestDispatcher("ViewAllProductsServlet");
				rd.forward(request, response);
				return;
			} else {
				session.setAttribute("errortext", "Please enter your user ID and password and try again.");
				response.sendRedirect("Login.jsp");
				return;
			}

		}
		
		
		
		
		
	}
}