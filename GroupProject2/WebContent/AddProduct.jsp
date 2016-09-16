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
			var v = document.forms["myForm"]["qty"].value;
			if (v == null || v == "") {
				alert("Quantity must be filled out");
				return false;
			}
			var x = document.forms["myForm"]["id"].value;
			if (x == null || x == "") {
				alert("ID must be filled out");
				return false;
			}
			var w = document.forms["myForm"]["name"].value;
			if (w == null || w == "") {
				alert("Name must be filled out");
				return false;
			}
			var y = document.forms["myForm"]["description"].value;
			if (y == null || y == "") {
				alert("Description must be filled out");
				return false;
			}
			var z = document.forms["myForm"]["price"].value;
			if (z == null || z == "") {
				alert("Price must be filled out");
				return false;
			}
		}
	</script>
	<h3>Add a Product</h3>
	<form action="AdminServlet" method="post" name="myForm" onsubmit="return validateForm()">
		<table>
			<tr>
				<td>ID:</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Price:</td>
				<td><input type="text" name="price" pattern="^[0-9]+(\.[0-9]{2})?$"/></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="description" /></td>
			</tr>
			<tr>
				<td>Quantity on hand:</td>
				<td><input type="number" name="qty" /></td>
			</tr>
			<tr>
				<td>Image:</td>
				<td><input type="text" name="image"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="adminChoice" value="Add Product"/></td>
			</tr>
		</table>
	</form>
	<form action="AdminFunctions.jsp">
		<input type="submit" value="Return"/>
	</form>
</body>
</html>