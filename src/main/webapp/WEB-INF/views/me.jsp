<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<br /><br />

<div class="container">
  <div class="row justify-content-center text-center">
    <div class="col-12">
      <h5>
        <sec:authentication property="principal.authorities" var="authorities"/>
        
        <small>ASSIGNED ROLES:</small>
        <c:choose>
          <c:when test="${authorities.size() > 0 }">
	          <c:forEach var="authority" items="${authorities }">
	            <span class="badge badge-light">${authority }</span>
	          </c:forEach>
          </c:when>
          
          <c:otherwise>
            <span class="badge badge-warning">NONE</span>
          </c:otherwise>
        </c:choose>
      </h5>
    </div>
    
    <div class="col-12">
      <hr>
    </div>
  </div>
  
  <br />
            
  <s:url var="meUrl" value="/me"></s:url>
  
  <div class="row justify-content-center">
    <div class="col-2">
  	    <sf:form action="${meUrl }" method="POST" commandName="meSettingsForm">
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
	       
	       <input type="submit" name="me-change-settings" value="Save" class="btn btn-outline-primary btn-block btn-sm" />
	    </sf:form>
    </div>
    
    <div class="col-3">
	    <sf:form action="${meUrl }" method="POST" commandName="mePasswordForm">
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
	       
			  <input type="submit" name="me-change-password" value="Change Password" class="btn btn-outline-primary btn-block btn-sm" />
	    </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <div class="col-5">
      <sf:form action="${meUrl }" method="GET">
        <input type="submit" name="me-cancel" value="Cancel" class="btn btn-outline-danger btn-block btn-sm" />      
      </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <s:bind path="meSettingsForm.*">
      <c:if test="${status.errors.errorCount > 0 }">
        <div class="col-6">
          <div class="alert alert-danger">              
            <strong>Please fix the following errors</strong>:
	          <ul>           
	             <c:forEach var="error" items="${status.errors.allErrors }">
	               <li><s:message message="${error}"></s:message><br /></li>
	             </c:forEach>
	           </ul>
          </div>
        </div>
      </c:if>
    </s:bind>
    
    <s:bind path="mePasswordForm.*">
      <c:if test="${status.errors.errorCount > 0 }">
        <div class="col-6">
          <div class="alert alert-danger">  
            <strong>Please fix the following errors</strong>: 
	          <ul>           
	             <c:forEach var="error" items="${status.errors.allErrors }">
	               <li><s:message message="${error}"></s:message><br /></li>
	             </c:forEach>
	           </ul>
          </div>
        </div>
      </c:if>
    </s:bind>
  </div>
</div>