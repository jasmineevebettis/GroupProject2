<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,util.ProductOrder, util.Cart"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Cart</title>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<% 
	//check for login
	boolean logcheck = false;
	if(session.getAttribute("logcheck") != null){
		logcheck = (Boolean)session.getAttribute("logcheck");
	}
	if (logcheck != true){
		session.setAttribute("errortext", "You are not logged in, you have been redirected to Login page...Error# VC_NVL");
		response.sendRedirect("LogoutServlet.java");
		return;
	}
	Cart cart = new Cart();
	List<ProductOrder> items = new ArrayList<ProductOrder>();
	//attempt to pull cart from session
	try{
	cart= (Cart)session.getAttribute("cart");
	items = cart.getOrderedItems();
	}catch(NullPointerException npe){
		session.setAttribute("errortext", "Your session has timed out...Error# VC_NPE_CN");
		response.sendRedirect("LogOutServlet.java");
		return;
	}	
%>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<h1>Your Cart</h1>
	<table class="viewCart">
		<tr>
			<th>Name</th>
			<th>Desc</th>
			<th>Qty</th>
			<th>Price</th>
			<th></th>
		</tr>
		<%
			if(!items.isEmpty()){
					for(ProductOrder p : items){
						out.print(p.getTableRow());
				}
		%>
		<tr>
			<td colspan="2">
			<td>Total:</td>
			<td colspan="2"><%=String.format("$%0,1.2f",cart.getTotal())%></td>
		</tr>
		<%
			} else{
		%>
		<tr>
			<td colspan="5">Your cart is empty</td>
		</tr>
		<tr>
			<td colspan="2">
			<td>Total:</td>
			<td colspan="2"><%=String.format("$%0,1.2f",0.00)%></td>

		</tr>
		<%
			}
		%>
	</table>
	<br>
	<form action="Checkout.jsp" method="post">
		<input type="submit" value="Checkout" />
	</form>
	
</body>
</html>