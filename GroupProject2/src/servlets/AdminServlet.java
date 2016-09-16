package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static db.DBHelper.getInstance;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("errortext", "");
		if (session.getAttribute("user") == null | !session.getAttribute("user").equals("admin")) {
			session.setAttribute("errortext", "Insufficient access privileges");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			return;
		}
		String choice = request.getParameter("adminChoice");
		
		if (choice.equals("Add Product")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			String description = request.getParameter("description");
			int quantityOnHand = Integer.parseInt(request.getParameter("qty"));
			String image = request.getParameter("image");
			//call dbhelper
			if (getInstance().addNewProduct(name, id, price, description, quantityOnHand, image))
				request.getRequestDispatcher("AdminSuccess.jsp").forward(request, response);
			else {
				session.setAttribute("errortext", "Failed to add Product");
				request.getRequestDispatcher("AddProduct.jsp").forward(request, response);
			}
			
		} else if (choice.equals("Drop Product")) {
			String id = request.getParameter("id");
			//call dbhelper
			if (getInstance().removeProduct(id))
				request.getRequestDispatcher("AdminSuccess.jsp").forward(request, response);
			else {
				session.setAttribute("errortext", "Failed to Remove Product");
				request.getRequestDispatcher("DropProduct.jsp").forward(request, response);
			}
			
		} else if (choice.equals("Update Product")) {
			String id = request.getParameter("id");
			String productValue = request.getParameter("productValue");
			String column = request.getParameter("column");
			//call dbhelper
			if (getInstance().updateProduct(column, productValue, id))
				request.getRequestDispatcher("AdminSuccess.jsp").forward(request, response);
			else {
				session.setAttribute("errortext", "Failed to Update Product");
				request.getRequestDispatcher("UpdateProduct.jsp").forward(request, response);
			}
		} else if (choice.equals("logout")) {
			session.invalidate();
			request.getRequestDispatcher("LogOutServlet.jsp").forward(request, response);
		}
		return;
	}

}
