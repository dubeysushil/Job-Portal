<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
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
<form action="registerBack.htm" method="POST">
<input type="submit" value="Back">
</form>
<h1>REGISTRATION</h1>
	<form action="register.htm" method="POST">
		<input type="radio" name="radio1" id="jobseeker" value="Jobseeker" required/>Jobseeker
 		<input type="radio" name="radio1" id="recruiter" value="Recruiter" required/>Recruiter
		<br>
		<input type="submit" value="Submit">
		<input type="hidden" name="page" value="type"/>
	</form>
</body>
</html>
