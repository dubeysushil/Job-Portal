<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Job Postings</title>
<style type="text/css">	 
body { 
 text-align:center; 
} 
</style>
</head>
<body>
<form action="availableJobsBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p><br/>
</c:if>
<form action="viewJobDetails.htm" method="post">
<table border="1">
            <tr>
				<td><b>Select</b></td>            
                <td><b>Job Title</b></td>
                <td><b>Posting Date</b></td>
                <td><b>Job Type</b></td>
                <td><b>Location</b></td>
            </tr>
            <c:forEach var="job" items="${jobs}">
                <tr>
                	<td><input type="radio" name="jobId" value="${job.jobId }"></td>
                	<td>${job.jobTitle}</td>
                    <td>${job.date}</td>
                    <td>${job.jobType}</td>
                    <td>${job.location}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="View Details"/>
</form>
</body>
</html>