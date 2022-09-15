<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
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
<form action="loginBack.htm" method="POST">
<input type="submit" value="Back">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<c:if test="${sessionScope.flag == 'Success'}">
<p style="color: green;">${sessionScope.message}</p>
</c:if>
<h1>LOGIN</h1>
	<form action="login.htm" method="POST">
		<fieldset>
		<label>EMAIL ID:</label>
		<input type="text" name="email"class="inputbox" required/><br/>
		<label>PASSWORD:</label>
 		<input type="password" name="password" class="inputbox" required/><br/>
 		</fieldset>
		<input type="submit" value="Login" class="submitButton">
	</form>
</body>
</html>