<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header_admin.jsp"></jsp:include>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Product</title>
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
		}
	</script>
	<h3>Remove a Product (Please enter product's ID)</h3>
	<form action="AdminServlet" method="post"
		onsubmit="return validateForm()" name="myForm">
		<input type="text" name="id" />
		<input type="submit" name="adminChoice" value="Drop Product"/>
	</form>
	<form action="AdminFunctions.jsp">
		<input type="submit" value="Return"/>
	</form>
</body>
</html>