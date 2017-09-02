<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>


<c:set value='${uri.get("home") }'  var="homeUri"></c:set>
<c:set value='${uri.get("me") }'    var="meUri"></c:set>


<%-- <s:url var="meUri" value="/me"></s:url>
<s:url var="homeUri" value="/"></s:url> --%>

<br /><br />

<div class="container">
  <div class="row justify-content-center text-center">
    <div class="col-12">
      <h5>
        <sec:authentication property="principal.authorities" var="authorities"/>
        
        <small>YOUR ROLE:</small>
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
        
        <span class="badge badge-light">${roleDescription }</span>
      </h5>
    </div>
    
    <div class="col-12">
      <hr>
    </div>
  </div>
  
  <br />
  
  <div class="row justify-content-center">
    <div class="col-2">
  	    <sf:form action="${meUri }" method="POST" commandName="meSettingsForm">
	      <div class="form-group">
	        <s:bind path="firstname">
	          <sf:input path="firstname" id="firstname" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Firstname"/>
	         </s:bind>
	      </div>
	       
	       
	       <div class="form-group">
	             <s:bind path="lastname">
	          <sf:input path="lastname" id="lastname" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Lastname"/>
		          </s:bind>
	            </div>
	       
	       <div class="form-group">
	        <s:bind path="username">
	          <sf:input path="username" id="username" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Username"/>
	        </s:bind>
	       </div>
	       
	       <input type="submit" name="meChangeSettings" value="Save" class="btn btn-outline-primary btn-block btn-sm" />
	    </sf:form>
    </div>
    
    <div class="col-3">
	    <sf:form action="${meUri }" method="POST" commandName="mePasswordForm">
				<div class="form-group">
				  <s:bind path="currentPassword">
	          <sf:input path="currentPassword" id="currentPassword" type="password" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Current Password" />
				  </s:bind>
	       </div>
				
				<div class="form-group">
				  <s:bind path="newPassword">
	          <sf:input path="newPassword" id="newPassword" type="password" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="New Password" />
				  </s:bind>
	       </div>
				
				<div class="form-group">
				  <s:bind path="newPasswordConfirmation">
	          <sf:input path="newPasswordConfirmation" id="newPasswordConfirmation" type="password" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Confirm New Password"/>
				  </s:bind>
	       </div>
	       
			  <input type="submit" name="meChangePassword" value="Change Password" class="btn btn-outline-primary btn-block btn-sm" />
	    </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <div class="col-5">
      <a href="${homeUri }" class="btn btn-outline-danger btn-block btn-sm">Cancel</a>
    </div>
  </div>
  
  <br />
  
  <div class="row justify-content-center">
    <s:bind path="meSettingsForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
    
    <s:bind path="mePasswordForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
  </div>
</div>