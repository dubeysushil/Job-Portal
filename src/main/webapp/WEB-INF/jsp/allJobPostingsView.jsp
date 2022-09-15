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
<form action="allJobPostingBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<form action="manageJobPosting.htm" method="post">
<table border="1">
            <tr>
				<td><b>Select</b></td>            
                <td><b>Job Title</b></td>
                <td><b>Posting Date</b></td>
                <td><b>Job Type</b></td>
                <td><b>Location</b></td>
                <td><b>Status</b></td>
            </tr>
            <c:forEach var="job" items="${jobs}">
                <tr>
                	<td><input type="checkbox" name="job" value="${job.jobId }"></td>
                	<td>${job.jobTitle}</td>
                    <td>${job.date}</td>
                    <td>${job.jobType}</td>
                    <td>${job.location}</td>
                    <td>${job.status}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" name="action" value="View"/>
        <input type="submit" name="action" value="Add"/>
</form>
</body>
</html>