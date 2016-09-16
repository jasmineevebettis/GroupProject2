<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="util.Cart"%>
<%@page import="servlets.*"%>
<%@page import="db.DBHelper"%>
<%@page import="util.ProductOrder"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style.css" rel="stylesheet" type="text/css" />
<title>View your previous orders</title>
</head>
<body>

	<% Cart c = (Cart) request.getAttribute("cart");%>
	<%! ArrayList<ProductOrder> list; %>
	<h2>
		Order Number: <%= c == null ? "undefined" : c.getOrderNumber() %>
		<%-- <%
		if(c == null) {
			session.setAttribute("errortext", "Invalid order returned");
			response.sendRedirect("ViewAllProductsServlet");
			return;
		}
		c.getOrderNumber();
		%> --%>
	</h2>
	<p>
	<p>
	<form action="ViewPreviousOrderServlet" method="post">

		<table border="1">
			<tr>
				<th>Item Number</th>
				<th>Item Name</th>
				<th>Price</th>
				<th>Quantity</th>
			</tr>

			<%
			if(c==null){ %>
			<tr>
				<td><input type="text" name="order_no" pattern="^[0-9]{1,9}$" /></td>
				<td><input type="text" name="user_id" /></td>
				<td><input type="submit" value="submit" /></td>
			</tr>
			<%
				}else{
			%>
			<% list = (ArrayList<ProductOrder>) c.getOrderedItems();%>
			<%}
						if(list.isEmpty()){
							%>
			<tr>
				<td colspan="3">No results found</td>
			</tr>

			<% 
						}else{
							for (ProductOrder p: list) {		
								
								
			%>
			<tr>
				<td><%=p.getId() %></td>
				<td><%=p.getName() %></td>
				<td><%= String.format("$%0,1.2f", p.getPrice()) %></td>
				<td style="text-align: center;"><%=p.getQuantityOrdered() %>
			</tr>



			<%}} %>

		</table>
	</form>
</body>
</html>