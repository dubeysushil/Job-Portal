<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Recruiter</title>
<style type="text/css">	 
body { 
 text-align:center; 
} 
</style>
</head>
<body>
<form action="addRecruiterBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<c:if test="${sessionScope.flag == 'Success'}">
<p style="color: green;">${sessionScope.message}</p>
</c:if>
<h1>Add</h1>
	<form action="addRecruiter.htm" method="POST">
		<fieldset>
		<label>Company Name:</label>
		<input type="text" name="companyName" required/><br/>
 		<label>Contact Number:</label>
 		<input type="text" name="contactNo" required/><br/>
 		<label>Email ID:</label>
 		<input type="text" name="email" required/><br/>
 		<label>Password:</label>
 		<input type="password" name="password" required/>
 		</fieldset>
		<input type="submit" value="Add">
	</form>
</body>
</html>