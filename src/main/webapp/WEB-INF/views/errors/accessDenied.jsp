<%@ taglib uri="http://java.sun.com/jsp/jstl/core"    prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="s" %>

<br /><br /><br /><br />

<c:set value='${uri.get("home") }'  var="homeUri"></c:set>
<c:set value='${uri.get("logo") }'  var="logoUri"></c:set>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-2 text-center">
      <a href='${homeUri }' class="btn btn-link">
	      <img alt="medicallab-logo" src='${logoUri }'>
	      <h4>MedicalLab</h4>
      </a>
    </div>
  </div>
    
  <div class="row justify-content-center">
   <div class="col-6">
      <div class="alert alert-danger text-center" role="alert">
        <h5>ACCESS DENIED [ 403 ]</h5>
        <hr >

        <p>You don't have enough privileges to access this url !!</p>
      </div>
    </div>
  </div>
</div>