<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>

<c:set value='${uri.get("patients") }'                        var="patientsUri"></c:set>
<c:set value='${uri.get("editPatient", patientId) }'          var="editPatientUri"></c:set>
<c:set value='${uri.get("patientProfileImage", patientId) }'  var="patientProfileImageUri"></c:set>
<c:set value='${uri.get("maleDefaultProfileImage") }'         var="maleDefaultProfileImageUri"></c:set>
<c:set value='${uri.get("femaleDefaultProfileImage") }'       var="femaleDefaultProfileImageUri"></c:set>
<br />

<div class="container">
  
  <div class="row justify-content-center">
    
    <div class="col-2 text-center">
      <h5>Edit Patient</h5>
    </div>
    
    <div class="col-12"><hr><br /></div>
    
    <div class="col-8">
       
       <sf:form action="${editPatientUri }" method="POST" commandName="editPatientSettingsForm" enctype="multipart/form-data" cssClass="row justify-content-center">
        <div class="col-4">
         <div class="form-group">
         <c:choose>
	           <c:when test='${ ( empty editPatientSettingsForm.profileImageName ) and ( editPatientSettingsForm.gender == "Male" )}'>
               <img id="profileImage" src="${maleDefaultProfileImageUri }" alt="Profile Image" class="img-thumbnail" width="200" height="200">
	           </c:when>
	           
             <c:when test='${ ( empty editPatientSettingsForm.profileImageName ) and ( editPatientSettingsForm.gender == "Female" )}'>
               <img id="profileImage" src="${femaleDefaultProfileImageUri }" alt="Profile Image" class="img-thumbnail" width="200" height="200">
             </c:when>
         
		         <c:otherwise>
		           <img id="profileImage" src="${patientProfileImageUri }" alt="Profile Image" class="img-thumbnail" width="200" height="200">
		         </c:otherwise>
          </c:choose>
         </div>
          
          <div class="form-group">
            <sf:input path="profileImage" type="file" cssClass="form-control-file form-control-sm" />
          </div>
        </div>
        
        <div class="col-4">
            <div class="form-group">
              <s:bind path="patientId">
                <sf:input path="patientId" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Patient Id"/>
              </s:bind>
            </div>
            
            <div class="form-group">
              <s:bind path="fullname">
                <sf:input path="fullname" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Full Name"/>
              </s:bind>
            </div>
            
            <div class="form-group">
              <s:bind path="phone">
                <sf:input path="phone" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Phone Number"/>
              </s:bind>
            </div>
            
            <div class="form-group">
              <s:bind path="age">
                <sf:input path="age" cssClass="form-control ${status.error ? 'is-invalid': '' }" placeholder="Age"/>
              </s:bind>
            </div>
            
            <div class="form-group">
              <s:bind path="gender">
                <sf:select path="gender" items="${availableGender }" cssClass="form-control ${status.error ? 'is-invalid': '' }" multiple="false"></sf:select>
              </s:bind>
            </div>       
        </div>
       
        <!-- <div class="col-4">
                    
        </div> -->
        
        <div class="col-10"><hr></div>
            
        <div class="col-4">
          <button name="changePatientSettings" class="btn btn-outline-primary btn-sm btn-block" >Save</button>
        </div>
        
        <div class="col-4">
          <a href="${patientsUri }" class="btn btn-outline-danger btn-block btn-sm">Cancel</a>
        </div>
         
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
       </sf:form>
    </div>
  </div>
  
  <div class="row justify-content-center">
    <s:bind path="editPatientSettingsForm.*">
      <t:insertAttribute name="formAlert"></t:insertAttribute>
    </s:bind>
  </div>
  
</div>