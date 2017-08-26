<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<s:url value="/"          var="homeUrl"></s:url>
<s:url value="/users"     var="usersUrl"></s:url>
<s:url value="/patients"  var="patientsUrl"></s:url>
<s:url value="/tests"     var="testsUrl"></s:url>
<s:url value="/requests"  var="requestsUrl"></s:url>
<s:url value="/me"        var="meUrl"></s:url>
<s:url value="/logout"    var="logoutUrl" ></s:url>

<s:url value="/images/logo/medicallab-logo.png"  var="logoUrl"></s:url>


<nav class="navbar navbar-toggleable-md navbar-expand-lg navbar-light bg-faded" style="background-color: #e3f2fd;">
  <div class="container">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href='${homeUrl }'>
      <img src='${logoUrl }' width="30" height="30" class="d-inline-block align-top" alt="logo">
      MedicalLab
    </a>

    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav mr-auto">
        <a id="homeNavLink" class="nav-item nav-link" href='${homeUrl }'>Home</a>
        
        <sec:authorize access="hasRole('ADMIN')">
          <a id="usersNavLink" class="nav-item nav-link" href='${usersUrl }'>Users</a>
        </sec:authorize>
        
        <sec:authorize access="hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('OFFICER')">
	        <a id="patientsNavLink" class="nav-item nav-link" href='${patientsUrl }'>Patients</a>
	        <a id="testsNavLink" class="nav-item nav-link" href='${testsUrl }'>Tests</a>
	        <a id="requestsNavLink" class="nav-item nav-link" href='${requestsUrl }'>Requests</a>
        </sec:authorize>
      </div>
    
      
      <sec:authorize url="/">
         <sec:authentication property="principal.firstname"   var="firstname" />
         <sec:authentication property="principal.lastname"    var="lastname" />
         <sec:authentication property="principal.authorities" var="authorities" />
         
         <c:set value="${firstname } ${lastname }"            var="fullname"></c:set>
         <c:choose>
           <c:when test="${authorities.get(0) == 'ROLE_ROOT' }">
             <c:set value="Root" var="roleDescription"></c:set>
           </c:when>
           
           <c:when test="${authorities.get(0) == 'ROLE_ADMIN' }">
             <c:set value="Administrator" var="roleDescription"></c:set>
           </c:when>
           
           <c:when test="${authorities.get(0) == 'ROLE_DOCTOR' }">
             <c:set value="Investigation Doctor" var="roleDescription"></c:set>
           </c:when>
           
           <c:when test="${authorities.get(0) == 'ROLE_OFFICER' }">
             <c:set value="Registration Officer" var="roleDescription"></c:set>
           </c:when>
         </c:choose>

				<div class="navbar-nav">
					<a id="meLink" class="nav-item nav-link mr-2" href='${meUrl }' data-toggle="tooltip" data-placement="bottom" title="${roleDescription }">
					  <u>${fullname}</u>
					  
					  <sec:authorize access="hasRole('ROOT')">
              <i class="fa fa-android" aria-hidden="true"></i>
            </sec:authorize>
					  
					  <sec:authorize access="hasRole('ADMIN')">
						  <i class="fa fa-user-secret" aria-hidden="true"></i>
					  </sec:authorize>
					  
            <sec:authorize access="hasRole('DOCTOR')">
              <i class="fa fa-user-md" aria-hidden="true"></i>
            </sec:authorize>

					  <sec:authorize access="hasRole('OFFICER')">
              <i class="fa fa-user" aria-hidden="true"></i>
            </sec:authorize>
				  </a>
				  
					<a id="logoutLink" class="btn btn-outline-primary" href="#">Logout</a>
				</div>
      </sec:authorize>
    </div>
  </div>
</nav>


<sf:form id="logoutForm" action="${logoutUrl }" method="post"></sf:form>