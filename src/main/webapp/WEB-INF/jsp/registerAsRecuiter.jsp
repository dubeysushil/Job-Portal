<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<style type="text/css">	 
body { 
 text-align:center; 
 font-family:cursive;
 border:2px solid black;
 background-image: url("bg.jpg");
} 
input{
margin-top: 1%;
margin-bottom: 1%;
}

fieldset{
margin-left: 28%;
margin-right: 29%;
}
input{
border:2px solid blue;
border-radius:4px;
}
</style>
</head>
<body>
<form action="registerRecruiterBack.htm" method="POST">
<input type="submit" value="Back">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<h1>REGISTRATION</h1>
	<form action="register.htm" method="POST">
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
		<input type="submit" value="Submit">
		<input type="hidden" name="page" value="recruiter"/>
	</form>
</body>
</html>