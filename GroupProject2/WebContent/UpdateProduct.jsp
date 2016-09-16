<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header_admin.jsp"></jsp:include>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<script type="text/javascript">
			function validateForm() {
		    var x = document.forms["myForm"]["id"].value;
		    	if (x == null || x == "") {
		        	alert("ID must be filled out");
		        	return false;
		    	}
		    var w = document.forms["myForm"]["productValue"].value;
		    	if (w == null || w == "") {
		        	alert("Product Value must be filled out");
		        	return false;
		    	}
			}
	</script>
	<h3>Update a Product</h3>
	<form action="AdminServlet" method="post" onsubmit="return validateForm()" name="myForm">
		<table>
			<tr>
				<td>Select Column:</td>
				<td><select name="column">
						<option value="name">Name</option>
						<option value="price">Price</option>
						<option value="description">Description</option>
						<option value="stock">Quantity on hand</option>
						<option value="image">Image</option>
				</select></td>
			</tr>
			<tr>
				<td>ID:</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>Value:</td>
				<td><input type="text" name="productValue" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="adminChoice" value="Update Product"/></td>
		</table>
	</form>
	<form action="AdminFunctions.jsp">
		<input type="submit" value="Return"/>
	</form>
</body>
</html>