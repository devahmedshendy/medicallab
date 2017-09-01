<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<%-- <s:url value="/"          var="horoutes.me()"></s:url>
<s:url value="/users"     var="usersUrl"></s:url>
<s:url value="/patients"  var="patientsUrl"></s:url>
<s:url value="/tests"     var="testsUrl"></s:url>
<s:url value="/requests"  var="requestsUrl"></s:url>
<s:url value="/me"        var="routes.me()"></s:url>
<s:url value="/logout"    var="uri.get("logout")" ></s:url>

<c:set value="/images/logo/medicallab-logo.png"  var="routes.logo"></s:url> --%>

<c:set value='${uri.get("home") }'      var="homeUri"></c:set>
<c:set value='${uri.get("users") }'     var="usersUri"></c:set>
<c:set value='${uri.get("patients") }'  var="patientsUri"></c:set>
<c:set value='${uri.get("tests") }'     var="testsUri"></c:set>
<c:set value='${uri.get("requests") }'  var="requestsUri"></c:set>
<c:set value='${uri.get("me") }'        var="meUri"></c:set>
<c:set value='${uri.get("logout") }'    var="logoutUri" ></c:set>



<nav class="navbar navbar-toggleable-md navbar-expand-lg navbar-light bg-faded" style="background-color: #e3f2fd;">
  <div class="container">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <a class="navbar-brand" href='${homeUri }'>
      <img src='${uri.get("logo") }' width="30" height="30" class="d-inline-block align-top" alt="logo">
      MedicalLab
    </a>

    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav mr-auto">
        <sec:authorize access="hasRole('ADMIN')">
          <a id="homeNavLink" class="nav-item nav-link" href='${homeUri }'>Home</a>
          <a id="usersNavLink" class="nav-item nav-link" href='${usersUri }'>Users</a>
          <a id="patientsNavLink" class="nav-item nav-link" href='${patientsUri }'>Patients</a>
          <a id="testsNavLink" class="nav-item nav-link" href='${testsUri }'>Tests</a>
          <a id="requestsNavLink" class="nav-item nav-link" href='${requestsUri }'>Requests</a>
        </sec:authorize>
        
        <sec:authorize access="hasRole('DOCTOR') or hasRole('OFFICER')">
          <a id="homeNavLink" class="nav-item nav-link" href='${homeUri }'>Home</a>
	        <a id="patientsNavLink" class="nav-item nav-link" href='${patientsUri }'>Patients</a>
	        <a id="testsNavLink" class="nav-item nav-link" href='${testsUri }'>Tests</a>
	        <a id="requestsNavLink" class="nav-item nav-link" href='${requestsUri }'>Requests</a>
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
					<a id="meLink" class="nav-item nav-link mr-2" href='${meUri }' data-toggle="tooltip" data-placement="bottom" title="${roleDescription }">
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


<sf:form id="logoutForm" action='${logoutUri }' method="POST">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
</sf:form>