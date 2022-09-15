<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Application Details</title>
</head>
<body>
<form action="applicationDetailsBackSignOut.htm" method="POST">
<input type="submit" name="action" value="Back">
<input type="submit" name="action" value="Sign Out">
</form>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p><br/>
</c:if>
<span><img width="150" height="150" src="/project/filesUploads/${application.getJobseekerProfile().getPhotoName()}"/></span><br/>
<a href="/project/filesUploads/${application.getJobseekerProfile().getResumeName()}" download>Download Resume</a><br/>
<span>About Applicant: ${application.getJobseekerProfile().getAbout() }</span><br/>
<span>Applicant's Skills: ${application.getJobseekerProfile().getSkills() }</span><br/>
<span>Applicant Name: ${application.getJobseekerProfile().getUser().getFirstName()} ${application.getJobseekerProfile().getUser().getLastName()}</span>><br/>
<span>Applicant Email: ${application.getJobseekerProfile().getUser().getEmail()}</span><br/>
<span>Application Contact: ${application.getJobseekerProfile().getUser().getContactNo()}</span><br/>
<span>Applied Date: ${application.getDate()}</span><br/>
<span>Status: ${application.getStatus()}</span><br/>
<span>Job Title: ${application.getJobPosting().getJobTitle()}</span><br/>
<span>Job Description: ${application.getJobPosting().getJobDescription()}</span><br/>
<form action="sendEmail.htm" method="POST">
<input type="hidden" name="applicationId" value="${application.getApplicationId() }"/>
<input type="text" name="emailBody" required/><br/>
<input type="submit" value="Send Interview Email" />
</form>
<form action="rejectJobApplication.htm" method="POST">
<input type="hidden" name="applicationId" value="${application.getApplicationId() }"/>
<input type="submit" value="Reject Application" />
</form>
</body>
</html>