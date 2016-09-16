<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="util.*,java.util.List,bean.*"%>
<%
Cart cart = (Cart)session.getAttribute("cart");
List<ProductOrder> orderedItems = cart.getOrderedItems();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="errorcatcher.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your order summary</title>
<style>
.frame-holder {
	position: fixed;
	height: 100vh;
	width: 100vw;
	top: 0px;
	left: 0px;
	overflow: auto;
}

.content-frame {
	position: absolute;
	top: 50%;
	left: 50%;
	height: 500px;
	width: 750px;
	margin: -250px 0 0 -375px;
	padding: 16px;
	box-sizing: border-box;
	border: 1px #000 solid;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="frame-holder">
		<div class="content-frame">
				<h3>Enter your card information to continue</h3>
				<form action="PurchaseServlet" method="post" style="float: left; margin-left: 32px">
				<table>
					<tbody>
						<tr>
							<td><label for="name">Name</label></td>
							<td><input type="text" name="name" required
								pattern="^([A-Za-z]{3,} ([A-Z] )?[A-Za-z]{3,})( (Jr|Sr|III))?$"
								title="Must be first and last name without special characters, middle initial and suffixes are allowed" /></td>
						</tr>
						<tr>
							<td><label for="cardNum">Card Number</label></td>
							<td><input type="text" name="cardNum" required
								pattern="^[0-9]{16}$" title="Must be a sixteen digit number" /></td>
						</tr>
						<tr>
							<td><label for="zip">Zip code</label></td>
							<td><input type="text" name="zip" required
								pattern="^[0-9]{5}$" title="Must be 5 digit zip code" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="Purchase" />
								<button onclick="event.preventDefault();window.location = 'ViewAllProducts.jsp';">Continue
									Shopping</button></td>
						</tr>
					</tbody>
				</table>
			</form>
			<div style="float: right; margin: 32px 32px;">
				<h1>
					Your total:
					<%=String.format("$%0,1.2f", cart.getTotal())%></h1>
				<h3>
					<u>Shipping is free on this order!</u>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>