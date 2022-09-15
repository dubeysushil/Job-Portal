<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Job Details</title>
</head>
<body>
<form action="jobDetailsBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:set var="job" value="${job}"/> 
	<form action="applyForJob.htm" method="POST">
		<fieldset>
		<label>Job Title:</label>
		<input type="text" name="jobTitle"  value="${job.getJobTitle() }" disabled="disabled"/><br/>
		<label>Company:</label>
		<input type="text" name="companyName"  value="${job.getCompanyName() }" disabled="disabled"/><br/>
		<label>Job Description:</label>
 		<input type="text" name="jobDescription" value="${job.getJobDescription() }" disabled="disabled"/><br/>
 		<label>Number Of Openings:</label>
 		<input type="text" name="numberOfOpenings" value="${job.getNumberOfOpenings() }" disabled="disabled"/><br/>
 		<label>Job Location:</label>
 		<input type="text" name="location" value="${job.getLocation() }" disabled="disabled"/><br/>
 		<label>Salary:</label>
 		<input type="text" name="salary" value="${job.getSalary() } $/Month" disabled="disabled"/><br/>
 		<label>Job Type:</label>
 		<input type="text" name="jobType" value="${job.getJobType() }" disabled="disabled"/><br/>
 		<label>Posted Date:</label>
 		<input type="text" name="date" value="${job.getDate() }" disabled="disabled"/><br/>
		<input type="hidden" name="id" value="${job.getJobId() }"/><br/>
 		</fieldset>
		<input type="submit" value="Apply">
	</form>
</body>
</html>