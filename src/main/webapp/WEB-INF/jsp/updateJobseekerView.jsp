<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Jobseeker</title>
</head>
<body>
<form action="updateJobseekerBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<c:if test="${sessionScope.flag == 'Success'}">
<p style="color: green;">${sessionScope.message}</p>
</c:if>
<form action="updateJobseeker.htm" method="POST">
<c:set var="user" value="${user}"/> 
		<fieldset>
		<label>First Name:</label>
		<input type="text" name="firstName" value="${user.getFirstName() }" required/><br/>
		<label>Last Name:</label>
 		<input type="text" name="lastName" value="${user.getLastName() }" required/><br/>
 		<label>Contact Number:</label>
 		<input type="text" name="contactNo" value="${user.getContactNo() }" required/><br/>
 		<label>Email ID:</label>
 		<input type="text" name="email" value="${user.getEmail() }" disabled="disabled"/><br/>
 		<input type="hidden" name="id" value="${user.getId() }"/><br/>
 		</fieldset>
		<input type="submit" name="action" value="Update">
		<input type="submit" name="action" value="Delete">
	</form>
</body>
</html>