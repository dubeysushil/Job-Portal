<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>update Job</title>
</head>
<body>
<form action="updateJobPostingBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:set var="job" value="${job}"/> 
	<form action="updateJobPosting.htm" method="POST">
		<fieldset>
		<label>Job Title:</label>
		<input type="text" name="jobTitle"  value="${job.getJobTitle() }" required/><br/>
		<label>Job Description:</label>
 		<input type="text" name="jobDescription" value="${job.getJobDescription() }" required/><br/>
 		<label>Number Of Openings:</label>
 		<input type="text" name="numberOfOpenings" value="${job.getNumberOfOpenings() }" required/><br/>
 		<label>Job Location:</label>
 		<input type="text" name="location" value="${job.getLocation() }" required/><br/>
 		<label>Monthly Salary:</label>
 		<input type="text" name="salary" value="${job.getSalary() }" required/><br/>
 		<label>Job Type:</label>
 		<input type="text" name="jobType" value="${job.getJobType() }" required/><br/>
 		<label>Status:</label>
 		<c:choose>
		<c:when test="${job.getStatus() == 'Active'}">
		<input type="radio" name="status" value="Active" checked="checked"/>Active
		<input type="radio" name="status" value="Inactive"/>Inactive
		</c:when>
		<c:otherwise>
		<input type="radio" name="status" value="Active" required/>Active
		<input type="radio" name="status" value="Inactive" checked="checked" required/>Inactive
		</c:otherwise>
		</c:choose>
		<input type="hidden" name="id" value="${job.getJobId() }"/><br/>
 		</fieldset>
		<input type="submit" name="action" value="Update">
		<!-- <input type="submit" name="action "value="Delete"> -->
	</form>
</body>
</html>