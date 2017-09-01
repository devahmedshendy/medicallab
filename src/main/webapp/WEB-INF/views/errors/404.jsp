<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<br /><br /><br /><br />

<s:url value="/"  var="homeUrl"></s:url>

<s:url value="/images/logo/medicallab-logo.png"  var="logoUrl"></s:url>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-2 text-center">
      <a href='${homeUrl }' class="btn btn-link">
        <img alt="medicallab-logo" src='${logoUrl }' />
        <h4>MedicalLab</h4>
      </a>
    </div>
  </div>
    
   <div class="row justify-content-center">
    <div class="col-6">
	      <div class="alert alert-danger text-center" role="alert">
	        <h5>404 - Not Found</h5>

	        It seems you're trying to access an invalid URL !!
	      </div>
      </div>
    </div>
	</div>
</div>