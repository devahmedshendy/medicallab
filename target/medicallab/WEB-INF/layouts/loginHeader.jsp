<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<%-- <s:url value='/' var="homeUri" />
<s:url value='/images/logo/medicallab-logo.png' var="logoUri" /> --%>

<c:set value='${uri.get("home") }' var="homeUri"></c:set>
<c:set value='${uri.get("logo") }' var="logoUri" ></c:set>



<div id="loginJumbotron" class="jumbotron jumbotron-fluid">
  <div class="container  text-center">
    <h1 class="display-4">Welcome to <a href="${homeUri }">MedicalLab</a></h1>
    <p class="lead">This is a simple <strong>Java</strong>-Based Web App using <strong>Spring</strong> Framework.</p>
  </div>
</div>

<%-- <div class="container">
  <div class="row justify-content-center">
    <div class="col-md-12 text-center">
      <h1>Welcome to <a href="${homeUri }">MedicalLab</a> WebApp</h1>
    </div>
    
    <div class="col-md-12">
      <br />
      <hr />
      <br />
    </div>
  </div>

  <div class="row justify-content-center">
    <div class="col-6 text-center">
	    <a href="${homeUri }">
	      <img alt="medicallab-logo" src='${logoUri }' />
	    </a>
	  </div>
  </div>
</div> --%>
