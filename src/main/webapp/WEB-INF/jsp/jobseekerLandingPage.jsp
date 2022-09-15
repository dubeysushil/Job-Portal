<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jobseeker</title>
</head>
<body>
<form action="jobseekerSignOut.htm" method="POST">
<input type="submit" value="Sign Out">
</form>
<h3>Welcome ${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</h3>
<c:if test="${sessionScope.flag == 'Error'}">
<p style="color: red;">${sessionScope.message}</p><br/>
</c:if>
<span><img width="150" height="150" src="/project/filesUploads/${jobseeker.photoName}"/></span><br/>
<span>${jobseeker.about }</span><br/>
<span>${jobseeker.skills }</span><br/>
<form action="viewProfile.htm" method="POST">
<input type="hidden" name="jobseekerId" value="${jobseeker.jobseekerId }"/>
<input type="submit" value="Profile" />
</form>
<form action="viewJobPostings.htm" method="POST">
<input type="submit" value="All Jobs" />
</form>
<form action="viewJobApplications.htm" method="POST">
<input type="hidden" name="jobseekerId" value="${jobseeker.jobseekerId }"/>
<input type="submit" value="My Applications" />
</form>
<%-- <form:form modelAttribute="jobseekerProfile" action="jobseekerHome.htm" method="post" enctype="multipart/form-data">

                    <label>Select Photo:</label><br/>
                    <form:input type="file" path="photo" name="photo" />
                    <input type="submit" value="Add" />

        </form:form> --%>
</body>
</html>