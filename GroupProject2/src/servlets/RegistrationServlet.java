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

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String user_id = request.getParameter("user_id");
		String user_name = request.getParameter("user_name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String password = request.getParameter("password");
		DBHelper db = DBHelper.getInstance();
		HttpSession session = request.getSession();
		session.setAttribute("errortext", "");

		try {
			db.addDetails(user_id, user_name, address, email, telephone,
					password);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errortext", "Failed to Register User.");
			response.sendRedirect("Registration.jsp");
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
		System.out.println("RegistrationServlet:doPost():" + " " + user_name
				+ " " + telephone + " " + email);
		System.out.println("RegistrationServlet:doPost():going to success.jsp");

		rd.forward(request, response);
		return;
	}
}
	
