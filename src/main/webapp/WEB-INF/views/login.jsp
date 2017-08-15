<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<br />

<div class="container">
  <div class="row justify-content-center">
    <div class="col-4">
	    <c:url var="loginUrl" value="/login"></c:url>
	    
	    <sf:form name="loginForm" cssClass="form" action="${loginUrl }" method="POST">
	      <div class="form-group">
	        <input type="text" name="username" value="" placeholder="Enter your username" class="form-control">
	      </div>
	
	      <div class="form-group">
	        <input type="password" name="password" value="" placeholder="Enter your password" class="form-control">
	      </div>
	
	      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
	
	      <button type="submit" name="login" class="btn btn-primary btn-block">Login</button>
	    </sf:form>
	
	  </div>
	</div>
</div>