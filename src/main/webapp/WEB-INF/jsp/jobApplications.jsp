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
<form action="jobApplicationsBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p><br/>
</c:if>
<form action="deleteJobApplication.htm" method="post">
<table border="1">
            <tr>
				<td><b>Select</b></td>            
                <td><b>Job Title</b></td>
                <td><b>Company</b></td>
                <td><b>Posting Date</b></td>
                <td><b>Applied Date</b></td>
                <td><b>Job Type</b></td>
                <td><b>Location</b></td>
                <td><b>Status</b></td>
            </tr>
            <c:forEach var="app" items="${applications}">
                <tr>
                	<td><input type="radio" name="applicationId" value="${app.getApplicationId() }"></td>
                	<td>${app.getJobPosting().getJobTitle()}</td>
                	<td>${app.getJobPosting().getCompanyName()}</td>
                	<td>${app.getJobPosting().getDate()}</td>
                    <td>${app.getDate()}</td>
                    <td>${app.getJobPosting().getJobType()}</td>
                    <td>${app.getJobPosting().getLocation()}</td>
                    <td>${app.getStatus()}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="Delete"/>
</form>
</body>
</html>