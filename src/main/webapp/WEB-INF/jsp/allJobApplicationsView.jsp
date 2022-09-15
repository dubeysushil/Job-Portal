<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Job Applications</title>
<style type="text/css">	 
body { 
 text-align:center; 
} 
</style>
</head>
<body>
<form action="allJobApplicationBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p><br/>
</c:if>
<form action="viewApplicationDetails.htm" method="post">
<table border="1">
            <tr>
				<td><b>Select</b></td>
				<td><b>Job Title</b></td>            
                <td><b>Applicant Name</b></td>
                <td><b>Applicant Email</b></td>
                <td><b>Applicant Skills</b></td>
                <td><b>Applied Date</b></td>
                <td><b>Status</b></td>
            </tr>
            <c:forEach var="app" items="${applications}">
                <tr>
                	<td><input type="radio" name="applicationId" value="${app.getApplicationId() }"></td>
                	<td>${app.getJobPosting().getJobTitle()}</td>
                	<td>${app.getJobseekerProfile().getUser().getFirstName()} ${app.getJobseekerProfile().getUser().getLastName()}</td>
                	<td>${app.getJobseekerProfile().getUser().getEmail()}</td>
                	<td>${app.getJobseekerProfile().getSkills()}</td>
                    <td>${app.getDate()}</td>
                    <td>${app.getStatus()}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="View Details"/>
</form>
</body>
</html>