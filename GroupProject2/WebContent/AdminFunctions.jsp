<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header_admin.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Functions</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<h2>Administrative Functions</h2>
	<form action="AddProduct.jsp" method="post">
		<input type="submit" value="Add Product"/>
	</form>
	<form action="DropProduct.jsp" method="post">
		<input type="submit" value="Drop Product"/>
	</form>
	<form action="UpdateProduct.jsp" method="post">
		<input type="submit" value="Update Product"/>
	</form><br>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="Logout"/>
	</form>
</body>
</html>