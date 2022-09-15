<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recruiters</title>
</head>
<body>
<form action="allRecruitersBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p>
</c:if>
<c:if test="${sessionScope.flag == 'Success'}">
<p style="color: green;">${sessionScope.message}</p>
</c:if>
<form action="viewRecruiter.htm" method="post">
<table border="1">
            <tr>
				<td><b>Select</b></td>            
                <td><b>Company Name</b></td>
                <td><b>Email ID</b></td>
                <td><b>Contact Number</b></td>
                <td><b>Status</b></td>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                	<td><input type="checkbox" name="user" value="${user.email }"></td>
                	<td>${user.companyName}</td>
                    <td>${user.email}</td>
                    <td>${user.contactNo}</td>
                    <td>${user.status}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" name="action" value="Approve"/>
        <input type="submit" name="action" value="View"/>
        <input type="submit" name="action" value="Add"/>
</form>
</body>
</html>