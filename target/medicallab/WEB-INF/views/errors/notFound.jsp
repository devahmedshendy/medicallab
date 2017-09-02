<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags"          prefix="s" %>

<br /><br /><br /><br />

<c:set value='${uri.get("home") }'  var="homeUri"></c:set>
<c:set value='${uri.get("logo") }'  var="logoUri"></c:set>

<%-- <s:url value="/"  var="homeUri"></s:url>
<s:url value="/images/logo/medicallab-logo.png"  var="logoUri"></s:url> --%>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-2 text-center">
      <a href='${homeUri }' class="btn btn-link">
        <img alt="medicallab-logo" src='${logoUri }' />
        <h4>MedicalLab</h4>
      </a>
    </div>
  </div>
    
   <div class="row justify-content-center">
    <div class="col-6">
	      <div class="alert alert-danger text-center" role="alert">
	        <h5>NOT FOUND [ 404 ]</h5>
	        <hr />
	        
          <c:choose>
		        <c:when test="${not empty errorMessage }">
		          <p>${errorMessage }</p>
		        </c:when>
		        
		        <c:otherwise>
	            <p>It seems you're trying to access an invalid URL !!</p>
	          </c:otherwise>
	        </c:choose>
	      </div>
      </div>
    </div>
	</div>
</div>