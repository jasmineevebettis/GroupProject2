<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header_blank.jsp"></jsp:include>
<head>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<link href="style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h1>Login</h1>

	<form action="LoginServ" method="post">
		User ID: <input type="text" name="USER"><br>
		Password: <input type="password" name="PASS"><br>
		<input type="submit" name="LoginButton" value="Login">
	</form>
	<br>
	<form action="RegistrationRedirect">
		<input type="submit" name="NewButton" value="New User Registration">
	</form>

	<p style="color: red; font-weight: bold;">${errortext}</p>

</body>
</html>