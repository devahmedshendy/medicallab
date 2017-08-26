<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>


<s:url value="/users/new" var="newUserUrl"></s:url>
<s:url value="/users"     var="usersUrl"></s:url>

<br /><br />

<div class="container">
  
  <div class="row justify-content-center">
    
    <div class="col-2 text-center">
      <h5>Add New User</h5>
    </div>
    
    <div class="col-12"><hr><br /></div>
    
    <div class="col-8">
       
       <sf:form action="${newUserUrl }" method="POST" commandName="addUserForm" cssClass="row justify-content-center">
        <div class="col-4">
					  <div class="form-group">
							<s:bind path="firstname">
							  <sf:input path="firstname" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Firstname"/>
							</s:bind>
					  </div>
					  
					  <div class="form-group">
				      <s:bind path="lastname">
                <sf:input path="lastname" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Lastname"/>
              </s:bind>
					  </div>
					  
						<div class="form-group">
              <s:bind path="username">
                <sf:input path="username" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Username"/>
              </s:bind>
						</div>
						
             <div class="form-group">
              <s:bind path="password">
                <sf:password path="password" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Password"/>
              </s:bind>
             </div>
             
             <div class="form-group">
              <s:bind path="passwordConfirmation">
                <sf:password path="passwordConfirmation" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Confirm Password"/>
              </s:bind>
             </div>            
        </div>
       
        <div class="col-4">
	        <div class="form-group">
            <s:bind path="roleName">
			        <sf:select path="roleName"  cssClass="form-control ${status.error ? 'is-invalid': '' }">
			          <sf:option value="" label="Select A Role"></sf:option>
			          <sf:options items="${availableRoles }"/>
   			      </sf:select>
            </s:bind>
				  </div>
				  
				  <div class="form-group">
            <s:bind path="gender">
              <sf:select path="gender" items="${availableGender }" cssClass="form-control ${status.error ? 'is-invalid': '' }" multiple="false"></sf:select>
            </s:bind>
           </div>
        </div>
        
        <div class="col-10"><hr></div>
        
         <div class="col-4">
          <button name="addUser" class="btn btn-outline-primary btn-sm btn-block" >Add</button>
         </div>
         
         <div class="col-4">
           <a href="${usersUrl }" class="btn btn-outline-danger btn-block btn-sm">Cancel</a>
         </div>
         
       </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <s:bind path="addUserForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
  </div>
  
</div>