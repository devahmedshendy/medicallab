<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>

<c:set value='${uri.get("users") }'                 var="usersUrl"></c:set>
<c:set value='${uri.get("editUser", username) }'   var="editUserUrl"></c:set>

<%-- <s:url value="/users/edit/${username }" var="editUserUrl"></s:url>
<s:url value="/users"                   var="usersUrl"></s:url> --%>

<br />

<div class="container">
  
  <div class="row justify-content-center">
    <div class="col-2 text-center">
      <h5>Edit User</h5>
    </div>
    
    <div class="col-12"><hr><br /></div>
    
    <div class="col-4">
      <sf:form action="${editUserUrl }" method="POST" commandName="editUserSettingsForm">
        <div class="row">
	        <div class="col-6">
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
	        </div>
	        
	        <div class="col-6">
		        <div class="form-group">
		            <s:bind path="roleName">
		              <sf:select path="roleName" items="${availableRoles }"  cssClass="form-control ${status.error ? 'is-invalid': '' }" multiple="flase"></sf:select>
		            </s:bind>
		          </div>
		          
		          <div class="form-group">
		           <s:bind path="gender">
		             <sf:select path="gender" items="${availableGender }" cssClass="form-control ${status.error ? 'is-invalid': '' }" multiple="false"></sf:select>
		           </s:bind>
		          </div>
	        </div>
        </div>
         
        <input type="submit" name="changeUserSettings" value="Save" class="btn btn-outline-primary btn-block btn-sm" />
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
      </sf:form>
    </div>
    
    <div class="col-3">
      <sf:form action="${editUserUrl }" method="POST" commandName="editUserPasswordForm">
        <div class="form-group">
          <s:bind path="password">
            <sf:input path="password" id="password" type="password" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Password" />
          </s:bind>
         </div>
        
        <div class="form-group">
          <s:bind path="passwordConfirmation">
            <sf:input path="passwordConfirmation" id="passwordConfirmation" type="password" cssClass="form-control ${status.error ? 'is-invalid' : '' }" placeholder="Confirm Password"/>
          </s:bind>
         </div>
        
        <input type="submit" name="changeUserPassword" value="Change Password" class="btn btn-outline-primary btn-block btn-sm" />
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
      </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <div class="col-7">
      <a href="${usersUrl }" class="btn btn-outline-danger btn-block btn-sm">Cancel</a>
    </div>
  </div>
  
  <br />
  
  <div class="row justify-content-center">
    <s:bind path="editUserSettingsForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
    
    <s:bind path="editUserPasswordForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
  </div>
  
</div>