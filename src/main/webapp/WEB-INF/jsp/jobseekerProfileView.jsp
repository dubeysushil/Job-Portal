<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jobseeker Profile</title>
</head>
<body>
<form action="jobseekerProfileBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<h3>User Profile</h3>
<form:form modelAttribute="jobseekerProfile" action="updateJobseekerProfile.htm" method="POST" enctype="multipart/form-data">
		<fieldset>
		<label>First Name:</label>
		<input type="text" name="firstName" class="inputbox" value="${sessionScope.user.getFirstName() }" required/><br/>
		<label>Last Name:</label>
 		<input type="text" name="lastName" class="inputbox" value="${sessionScope.user.getLastName() }" required/><br/>
 		<label>Contact Number:</label>
 		<input type="text" name="contactNo" class="inputbox" value="${sessionScope.user.getContactNo() }" required/><br/>
 		<label>About:</label>
 		<input type="text" name="about" class="inputbox" value="${jobseeker.getAbout() }" required/><br/>
 		<label>Skills:</label>
 		<input type="text" name="skills" class="inputbox" value="${jobseeker.getSkills() }" required/><br/>
 		<label>Email ID:</label>
 		<input type="text" name="email" class="inputbox" value="${sessionScope.user.getEmail() }" disabled="disabled"/><br/>		
        <label>Profile Photo:</label>
        <form:input type="file" path="photo" name="photo" /><br/>
        <label>Resume:</label>
        <form:input type="file" path="resume" name="resume" />
        </fieldset>
        <input type="submit" value="Update" />
</form:form> 
</body>
</html>