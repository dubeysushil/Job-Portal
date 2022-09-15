<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recruiter</title>
</head>
<body>
<form action="recruiterSignOut.htm" method="POST">
<input type="submit" value="Sign Out">
</form>
<h3>Welcome ${sessionScope.user.getCompanyName()}</h3>
<form action="recruiterHome.htm" method="post">
<input type="submit" name="action" value="Manage Job Postings"/> <br/>
<input type="submit" name="action" value="Check Applications"/>
</form>
</body>
</html>