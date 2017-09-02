<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<c:set value='${uri.get("login") }' var="loginUrl"></c:set>
<c:set value='${uri.get("personalIdExample") }' var="personalIdExampleUri"></c:set>

<%-- <s:url value="/login" var="loginUrl"></s:url> --%>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-4">
      <div class="card bg-light mb-3" style="max-width: 100%;">
			  <div class="card-header text-center"><h5>A PATIENT ?</h5></div>
			  
			  <div class="card-body">
			    <form id="getMedicalProfileForm" class="form">
	          <div class="form-group">
		          <div id="findPatientAlert" class="alert alert-danger text-center d-none" role="alert">
	            </div>
	            
	            <input type="text" name="personalId" class="form-control" placeholder="Enter Personal Id" aria-describedby="personalIdHelpBlock">
							<small id="personalIdHelpBlock" class="form-text text-muted">
							  Personal id is 14 number long, <a href="${personalIdExampleUri }" target="_blank">example?</a>
							</small>
	          </div>
	          
            <button type="submit" name="Show Medical Profile" class="btn btn-outline-primary btn-block">SHOW [ Medical Profile ]</button>
          </form>
			  </div>
			</div>
    </div>
  
    <div class="col-1"></div>
    <div class="col-4">
      <div class="card bg-light mb-3" style="max-width: 100%;">
        <div class="card-header text-center"><h5>A USER ?</h5></div>

        <div class="card-body">
			    <sf:form name="loginForm" cssClass="form" action="${loginUrl }" method="POST">
			      <div class="form-group">
			        <input type="text" name="username" value="" placeholder="Enter your username" class="form-control">
			      </div>
			
			      <div class="form-group">
			        <input type="password" name="password" value="" placeholder="Enter your password" class="form-control">
			      </div>
			
			      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
			
			      <button type="submit" name="login" class="btn btn-outline-primary btn-block">LOG IN</button>
			    </sf:form>
			    
			    <c:if test="${param.error != null }">
	          <div class="alert alert-danger text-center" role="alert">
	            Wrong username/password
	          </div>
	        </c:if>
        </div>
      </div>
    
	
	  </div>
	</div>
</div>