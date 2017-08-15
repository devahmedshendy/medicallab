<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<br /><br /><br /><br />

<div class="container">
  <div class="row justify-content-center">
    <div class="col-2 text-center">
      <a href='<s:url value="/"></s:url>' class="btn btn-link">
	      <img alt="medicallab-logo" src='<s:url value="/resources/images/medicallab-logo.png"></s:url>' />
	      <h4>MedicalLab</h4>
      </a>
    </div>
  </div>
    
   <div class="row justify-content-center">
    <div class="col-6">
        <div class="alert alert-danger text-center" role="alert">
          <h5>403 - Access Denied</h5>

          It seems you don't have sufficient privileges to access this page !!
        </div>
      </div>
    </div>
  </div>
</div>