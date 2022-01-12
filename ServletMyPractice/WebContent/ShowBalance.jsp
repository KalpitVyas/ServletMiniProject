
<%@page import="DatabaseManager.Manager"%>
<%@page import="UserPojo.UserPojo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Balance</title>
</head>
<body>
<% UserPojo user = (UserPojo) session.getAttribute("userData");
int userId = (int)user.getId();
System.out.println("Show Balance : "+userId);
Manager manager = new Manager();
if(manager.showBalance(user)!=0){
%> 
	<h4>Balance <%=user.getFirstName()%>:  <%=user.getBalanace()%></h4>	
<%}else{%>
	<p>Error Occured</p>
<%} %>
</body>
</html>