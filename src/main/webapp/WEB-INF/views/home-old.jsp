<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home - MedicalLab</title>
</head>
<body>
  <!-- vars -->
  <c:url var="logoutUrl" value="/logout"></c:url>

	<h1>
	 Hello <c:out value="${pageContext.request.remoteUser }"></c:out> 
  </h1>
	<h3>MedicalLab - HomeController</h3>
	
	<%-- <form action="${logoutUrl }" method="post">
	 <input type="submit" value="Log Out" />
	 <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
	</form> --%>
	
	<sf:form action="${logoutUrl }" method="post">
	   <input type="submit" value="Log Out" />
	</sf:form>
</body>
</html>