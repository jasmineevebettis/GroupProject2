package servlets;

import util.Cart;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RemoveFromCart")
public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RemoveFromCart() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		ses.setAttribute("errortext", "You Fail");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		ses.setAttribute("errortext", "");
		String id = request.getParameter("id");
		Cart cart = new Cart();
		boolean removed;
		
		//get cart
		try{
		cart = (Cart)ses.getAttribute("cart");
		}catch(NullPointerException npe){
			System.out.println("Mistakes were made...session cart object null serv");
			ses.setAttribute("errortext", "There were complications with editing your cart...Error# VC_NPE");
			RequestDispatcher rd = request.getRequestDispatcher("ViewCart.jsp");
			rd.forward(request,response);
		}catch(ClassCastException cce){
			System.out.println("Mistakes were made...could not cast session object to Cart object serv");
			ses.setAttribute("errortext", "There were complications with editing your cart...Error# VC_CCE");
			RequestDispatcher rd = request.getRequestDispatcher("ViewCart.jsp");
			rd.forward(request,response);
		}
		//remove item from cart
		removed = cart.removeFromCart(id);
		//check if 
		if(removed == false){
			ses.setAttribute("errortext", "There were complications with editing your cart...Error# VC_RIE");
			System.out.println("Mistakes were made...false returned from DB serv");
		}
		ses.setAttribute("cart", cart);
		RequestDispatcher rd = request.getRequestDispatcher("ViewCart.jsp");
		rd.forward(request,response);
	}

}
