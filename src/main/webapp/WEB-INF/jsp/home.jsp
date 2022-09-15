<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Home</title>
<style type="text/css">	 
body { 
 text-align:center; 
 color:blue;
 border:2px solid black;
 background-image: url("bg.jpg");
} 
input{
border:2px solid blue;
border-radius:4px;
}
h1{
margin-top: 15%;
font-family:cursive;
}
</style>
</head>
<body>
<h1>Career Path</h1>
	<form action="register.htm" method="POST">
		<input type="submit" name="page" value="Login">
		<input type="submit" name="page" value="Register">
	</form>
</body>
</html>
