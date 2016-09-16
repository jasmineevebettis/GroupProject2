<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header_blank.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New User Registration</title>
<body>
	<h2>Please enter in the following information</h2>
	<form action="RegistrationServlet" method="post">
		User ID: <input type="text" name="user_id" pattern="[0-9]{4}" required><br>
		Name: <input type="text" name="user_name" pattern="^[a-zA-Z]+$" required> <br>
		Address: <input type="text" name="address" size="35" required> <br>
		Email: <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required> <br>
		Telephone: <input type="text" name="telephone" pattern="\d{3}[\-]\d{3}[\-]\d{4}" maxlength="12" required> <br>
		Password: <input type="password" name="password"
			pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
			value="" required title="Password must contain at least 8 or more characters, with at least one capital letter, one lower case letter and one number"> <br>
		<input type="submit" name="submit" value="Submit">
	</form>
	<br>
	<a href="Login.jsp">Click here to go back to login page</a>
	
	<p style="color: red; font-weight: bold;">${errortext}</p>
</body>
</html>