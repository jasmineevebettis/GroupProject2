<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successful Login</title>
</head>
<body>
	<h1>You have registered successfully</h1>
	<%
		System.out.println(" Login success "
				+ request.getParameter("user_name"));
	%>

	<a href="Login.jsp">Click here to return to login page</a>
</body>
</html>