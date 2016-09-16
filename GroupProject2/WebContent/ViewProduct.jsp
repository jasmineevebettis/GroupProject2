<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="bean.Product,db.DBHelper"%>
<jsp:include page="errorcatcher.jsp"></jsp:include>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Page</title>

<style>
p {
	font-size: 1.5em;
}

.item-background {
	position: absolute;
	width: 100%;
	height: 100%;
	overflow: auto;
	top: 0px;
	left: 0px;
}

.item-frame {
	position: absolute;
	width: 750px;
	height: 600px;
	top: 50%;
	left: 50%;
	margin: -300px 0 0 -375px;
	border: 2px black solid;
	box-sizing: border-box;
	padding: 16px;
}

body {
	color: #333;
	background-color: #eee;
}
</style>


</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<%
		String ProductID = (String) request.getParameter("id");

		if (ProductID == null) {
			System.out.println("<<<---------------------------------ProductID is null");
			response.sendRedirect("ViewAllProducts.jsp");
			return;
		}
		Product product = DBHelper.getInstance().getProduct(ProductID);
		String Name = product.getName();
		String ID = product.getId();
		String Description = product.getDescription();
		double Price = product.getPrice();
		int Qty = product.getQuantityOnHand();
		String ImgUrl = product.getImgUrl();
	%>

	<div class="item-background">
		<div class="item-frame">
			<h2>Product View</h2>
			<table>
				<tr>
					<td><img src="<%=ImgUrl%>" alt="name" height="300px" width="300px" /></td>
					<td valign="top" style="padding-left: 64px">
						<p>
							<strong>Name: </strong><%=Name%>
						</p>
						<p>
							<strong>Price: </strong><%=String.format("$%0,1.2f", Price)%>
						</p>
						<p>
							<strong>Quantity: </strong><%=Qty%>
						</p>
						<p>
						<form action="AddToCartServlet" method="post">
							<input type="submit" value="Add to Cart" /> 
							<input type="hidden" value="<%=ID%>" name="ProductID"/>
						</form>
					</td>
				</tr>
			</table>

			<div style="display: block; width: 100%">
				<h2>Product Description:</h2>
				<h3><%=Description%></h3>
			</div>
		</div>
	</div>



</body>
</html>