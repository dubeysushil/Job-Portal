<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Job</title>

<style type="text/css">	 
body { 
 text-align:center; 
} 
</style>
</head>
<body>
<form action="addJobPostingBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<h1>Add</h1>
	<form action="addJobPosting.htm" method="POST">
		<fieldset>
		<label>Job Title:</label>
		<input type="text" name="jobTitle" class="inputbox" required/><br/>
		<label>Job Description:</label>
 		<input type="text" name="jobDescription" class="inputbox" required/><br/>
 		<label>Number Of Openings:</label>
 		<input type="text" name="numberOfOpenings" class="inputbox" required/><br/>
 		<label>Job Location:</label>
 		<input type="text" name="location" class="inputbox" required/><br/>
 		<label>Monthly Salary:</label>
 		<input type="text" name="salary" class="inputbox" required/><br/>
 		<label>Job Type:</label>
 		<input type="text" name="jobType" class="inputbox" required/><br/>
 		<label>Status:</label>
 		<input type="radio" name="status" value="Active" required/>Active
 		<input type="radio" name="status" value="Inactive"/ required>Inactive
 		</fieldset>
		<input type="submit" value="Add" class="submitButton">
	</form>
</body>
</html>