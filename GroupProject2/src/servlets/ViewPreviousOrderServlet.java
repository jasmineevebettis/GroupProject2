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

/**
 * Servlet implementation class ViewPreviousOrder
 */
@WebServlet("/ViewPreviousOrderServlet")
public class ViewPreviousOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewPreviousOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("errortext" , "");
		int order_no = Integer.parseInt(request.getParameter("order_no"));
		String user_id = (String)session.getAttribute("user");
		DBHelper instance = DBHelper.getInstance();
		Cart c = instance.getExistingCart(order_no, user_id);
		if(c == null) {
			session.setAttribute("errortext", "Order number not found!");
			response.sendRedirect("ViewAllProducts.jsp");
			return;
		}
		request.setAttribute("cart" , c); 
		
		RequestDispatcher rqd = request.getRequestDispatcher("ViewPreviousOrder.jsp");
		rqd.forward(request, response);
	}

}
