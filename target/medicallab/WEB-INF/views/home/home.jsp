<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<s:url value="https://github.com/devahmedshendy/medicallab" var="sourceCodeUrl"></s:url>

<sec:authentication property="principal.firstname" var="firstname" />

<div class="container">
	<div class="jumbotron">
	  <%-- ${pageContext.request.remoteUser } --%>
	  <h2 class="display-4">Welcome, ${firstname }!</h2>
	  <p class="lead">This is a simple Java Web Application.</p>
	  <hr class="my-4">
	  <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
	  <p class="lead">
	    <a class="btn btn-primary btn-lg" href='${sourceCodeUrl }' role="button" target="_blank" >Source Code</a>
	  </p>
	</div>
</div>