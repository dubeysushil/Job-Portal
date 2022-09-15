<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<style type="text/css">	 
body { 
 text-align:center; 
} 
</style>
</head>
<body>
<form action="adminSignOut.htm" method="POST">
<input type="submit" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<c:if test="${sessionScope.flag == 'Success'}">
<p style="color: green;">${sessionScope.message}</p>
</c:if>
<h3>Welcome ${sessionScope.user.getFirstName()}</h3>
<form action="adminHome.htm" method="post">
<input type="submit" name="action" value="View Jobseekers"/> <br/>
<input type="submit" name="action" value="View Recruiters"/>
</form>
</body>
</html>