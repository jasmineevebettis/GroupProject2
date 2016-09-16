<%@ page language="java" contentType="text/html; 

charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 

Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; 

charset=ISO-8859-1">
<title>View All Products</title>
<%@ page import="java.util.ArrayList"%>
<%@ page import="bean.Product"%>
<jsp:include page="errorcatcher.jsp"></jsp:include>

<%
	String errText = (String) session.getAttribute("infotext");
	if (errText != null && !errText.equals("")) {
		out.print("<script>alert('" + errText + "');</script>");
		session.setAttribute("infotext", "");
	}
%>

<%
	ArrayList<Product> searchResults = (ArrayList<Product>)session.getAttribute("searchResults");
%>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<h1>Product Catalog</h1>
	<form action="ViewAllProductsServlet" method="post">
		Enter product and click Submit to search: <input type="text"
			name="custChoice"> <input type="submit">
	</form>
	<table>
		<tr>
			<th>Product Name</th>
			<th>Description</th>
			<th>Price</th>
			<th>Action</th>
			<!-- <th>Image</th> -->
		</tr>
		<tr>
			<%
				for (Product p1:searchResults){
								out.println(p1.getTableRow());
							}
			%>
		</tr>
	</table>
</body>
</html>