<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String errText = (String)session.getAttribute("errortext");
if(errText != null && !errText.equals("")) {
	out.print("<script>alert('" + errText + "');</script>");
}
%>