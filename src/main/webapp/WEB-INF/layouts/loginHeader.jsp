<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<br /><br /><br /><br />

<s:url value='/' var="homeUrl" />
<s:url value='/images/logo/medicallab-logo.png' var="logoUrl" />

<div class="container">
  <div class="row justify-content-center">
    <div class="col-2 text-center">
	    <a href="${homeUrl }">
	      <img alt="medicallab-logo" src='${logoUrl }' />
	      <h4>MedicalLab</h4>
	    </a>
	    
	  </div>
  </div>
</div>
