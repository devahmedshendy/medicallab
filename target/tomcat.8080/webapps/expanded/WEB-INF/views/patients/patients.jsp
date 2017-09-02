<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"             prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags"          prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form"     prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>

<br /><br />

<%-- <s:url value="/patients"         var="patientsUri"></s:url>
<s:url value="/patients/new"     var="newPatientUri"></s:url>
<s:url value="/patients/search"  var="searchPatientsUri"></s:url>
 --%>
 
<c:set value='${uri.get("patients") }'        var="patientsUri"></c:set>
<c:set value='${uri.get("newPatient") }'      var="newPatientUri"></c:set>
<c:set value='${uri.get("searchPatients") }'  var="searchPatientsUri"></c:set>

<c:set value="${requestScope['javax.servlet.forward.request_uri']}" var="currentUri"></c:set>
<c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="currentPath"></c:set>


<!-- Prepare with previous query string if exists the URI of next page -->
<c:url value="${currentPath }" var="nextPageUri">
  <c:forEach items="${requestParams }" var="parameter">
    <c:param name="${parameter.key }" value="${parameter.value }"></c:param>
  </c:forEach>

  <c:param name="page" value="${nextPage }"></c:param>
</c:url>

<!-- Prepare with previous query string if exists the URI of previous page -->
<c:url value="${currentPath }" var="prevPageUri">
  <c:forEach items="${requestParams }" var="parameter">
    <c:param name="${parameter.key }" value="${parameter.value }"></c:param>
  </c:forEach>

  <c:param name="page" value="${prevPage }"></c:param>
</c:url>

<!-- Determine if this page is /patients/search -->
<c:if test="${currentUri == searchPatientsUri }">
  <c:set value="/patients/search"         var="currentPath"></c:set>
</c:if>

<!-- 
     Now currentPath would be /patients with/without its query string 
     or /patients/search with its queries string 
-->
<!-- Patient currentPath to create sorting columns href -->
<c:forEach items='${uriListHolder.get("sortFields").split(" ") }' var="field">
  <c:url value="${currentPath }" var="fieldSortByUri">
    <c:param name="sortField" value="${field }"></c:param>
    <c:param name="sortOrder" value='${sortField == field ? sortOrder : "ASC" }'></c:param>
    
    <c:if test="${currentUri == searchPatientsUri }">
      <c:param name="searchField" value='${requestParams.get("searchField") }'></c:param>
      <c:param name="searchText"  value='${requestParams.get("searchText") }'></c:param>
      </c:if>
  </c:url>
  
  ${uriListHolder.put(field, fieldSortByUri) }
  
</c:forEach>


<div class="container">
  <div class="row">
    <div class="col-md-10">
      <div class="row justify-content-between">
        <div class="col-3">
          <sec:authorize access="hasRole('OFFICER')">
	          <a id="addPatientLink" class="text-primary" href="${newPatientUri }">
	            <i class="fa fa-user-plus fa-2x" aria-hidden="true"></i> 
	            <span>NEW PATIENT</span>
	          </a>
          </sec:authorize>
        </div>
  
        <div class="col-5">
          <div class="row no-gutters justify-content-end align-items-start">
            <div class="col-6 text-right">
              <h5><span class="badge badge-light mr-2">${pageSize } / ${totalPatients } patients</span></h5>
            </div>  
            
            <div class="col-5">
              <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-end pagination-sm">
                  <c:choose>
                    <c:when test="${isFirstPage == true}">
                      <li class="page-item disabled">
                        <a class="page-link" tabindex="-1">Previous</a>
                      </li>
                    </c:when>
                    
                    <c:when test="${isFirstPage == false}">
                      <li class="page-item">
                        <a class="page-link" href="${prevPageUri }" tabindex="-1">Previous</a>
                      </li>
                    </c:when>
                  </c:choose>
                  
      
                  <li class="page-item"><a class="page-link" href="#"><c:out value="${currentPage }"></c:out></a></li>
      
                  <c:choose>
                    <c:when test="${isLastPage == true}">
                      <li class="page-item disabled">
                        <a class="page-link" tabindex="-1">Next</a>
                      </li>
                    </c:when>
                    
                    <c:when test="${isLastPage == false}">
                      <li class="page-item">
                        <a class="page-link" href="${nextPageUri }" tabindex="-1">Next</a>
                      </li>
                    </c:when>
                  </c:choose>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
      
      <table class="table table-sm table-hover">
        <thead class="blue-grey lighten-4">
          <tr>
            <!-- Role Icon Column Head -->
            <th width="3%"></th>
            
            
            
            <!-- Fullname Column Head -->
            <th width="25%">
              <a href='${uriListHolder.get("fullname") }'>Full Name</a>
              
              <c:if test='${sortField.equals("fullname") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th>
            
            <!-- Patient Id Column Head -->
            <th width="15%">
              <a href='${uriListHolder.get("patientId") }'>Patient Id</a>
              
              <c:if test='${sortField.equals("patientId") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th>
            
            <%-- <!-- Phone Column Head -->
            <th width="12%">
              <a href='${uriListHolder.get("phone") }'>Phone</a>
              
              <c:if test='${sortField.equals("phone") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th> --%>
            
            <!-- Gender Column Head -->
            <th width="5%">
              <a href='${uriListHolder.get("age") }'>Age</a>
              
              <c:if test='${sortField.equals("age") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th>
            
            <!-- Created At Column Head -->
            <th width="10%">
              <a href='${uriListHolder.get("createdAt") }'>Created At</a>
              
              <c:if test='${sortField.equals("createdAt") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th>
            
            <!-- Updated At Column Head -->
            <th width="10%">
              <a href='${uriListHolder.get("updatedAt") }'>Updated At</a>
              
              <c:if test='${sortField.equals("updatedAt") }'>
                <c:choose>
                  <c:when test='${sortOrder.equals("DESC") }'>
                    <i class="fa fa-arrow-down text-secondary" aria-hidden="true"></i>
                  </c:when>
                  
                  <c:when test='${sortOrder.equals("ASC") }'>
                    <i class="fa fa-arrow-up text-secondary" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </c:if>
            </th>
            
            <!-- Edit/Delete Patient Column Head -->
            <th width="7%" class="text-center">
              <a id="meLink" 
                 href='#' data-toggle="tooltip" 
                 data-placement="top" data-html="true" 
                 title='
                       <table class="text-light">
                         <tr>
                           <td><i class="fa fa-address-card-o text-light mr-2" aria-hidden="true"></i></td>
                           <td><small>Medical Profile</small></td>
                         </tr>
                         <sec:authorize access="hasRole('OFFICER')">
	                         <tr>
	                           <td><i class="fa fa-pencil-square-o text-light mr-2" aria-hidden="true"></i></td>
	                           <td><small>Edit Patient</small></td>
	                         </tr>
	                         <tr>
	                           <td><i class="fa fa-trash text-light mr-2" aria-hidden="true"></i></td>
	                           <td><small>Delete Patient</small></td>
	                         </tr>
                         </sec:authorize>
                       </table>
               '>
                <i class="fa fa-question-circle text-info" aria-hidden="true"></i>
              </a>
            </th>
          </tr>
        </thead>
  
        <tbody>
          <c:forEach var="patient" items="${patients }">
            <c:set value="${patient.getPatientId() }"     var="patientId"></c:set>
            <c:set value="${patient.getFullname() }"      var="fullname"></c:set>
            <%-- <c:set value="${patient.getPhone() }"         var="phone"></c:set> --%>
            <c:set value="${patient.getAge() }"           var="age"></c:set>
            <c:set value="${patient.getGender() }"        var="gender"></c:set>
            <c:set value="${patient.getCreatedAt() }"     var="createdAt"></c:set>
            <c:set value="${patient.getUpdatedAt() }"     var="updatedAt"></c:set>
            
            <c:set value='${uri.get("editPatient", patientId) }'    var="editPatientUri"></c:set>
            <c:set value='${uri.get("medicalProfile", patientId) }'    var="medicalProfileUri"></c:set>
            
            <%-- <s:url value="/patients/edit/${patientId }"    var="editPatientUri"></s:url>
            <s:url value="/patients/medical-profile/${patientId }"    var="medicalProfileUri"></s:url> --%>    
            
            <tr>
              <td>
                <c:choose>
                  <c:when test="${patient.getGender() == 'Male' }">
                    <i class="fa fa-male mr-2" aria-hidden="true"></i>
                  </c:when>
                                     
                  <c:when test="${patient.getGender() == 'Female' }">
                    <i class="fa fa-female mr-2" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
              </td>
              
              <td>${fullname }</td>
              
              <td>${patientId }</td>
              
              <td>${age }</td>
              
              <td><fmt:formatDate type="date" value="${createdAt }" /></td>
              
              <td><fmt:formatDate type="date" value="${updatedAt }" /></td>
              
              <td class="text-center">
                <a id="medicalProfileLink" data-patientId="${patientId }" href='${medicalProfileUri }'>
                  <i class="fa fa-address-card-o text-primary" aria-hidden="true"></i>
                </a>

                <sec:authorize access="hasRole('OFFICER')">
	                <a id="editPatientLink" data-patientId="${patientId }" href='${editPatientUri }'>
	                  <i class="fa fa-pencil-square-o text-primary" aria-hidden="true"></i>
	                </a>
	                
	                <a id="deletePatientLink" data-patient-id="${patientId }" href="#">
	                  <i class="fa fa-trash text-danger" aria-hidden="true"></i>
	                </a>
                </sec:authorize>
                
                
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    
    <div class="col-md-2">
      <sf:form action="${searchPatientsUri }" method="GET" modelAttribute="searchPatientForm">
        <button type="submit" class="btn btn-outline-secondary btn-sm btn-block">Refine</button>
      
        <hr />
        <div class="row no-gutters">

          <c:if test="${currentUri == searchPatientsUri }">
            <div class="col-md-12 text-center">
              <a href="${patientsUri }" class="btn btn-link"><small>RESET</small></a>
            </div>
          </c:if>
          
          <div class="col-md-12">
            <div class="form-group">
              <sf:input path="searchText" cssClass="form-control form-control-sm" placeHolder="Search..."/>
            </div>
          </div>
          <div class="col-md-12">
            <div class="form-group">
              <sf:select path="searchField" items="${patientSearchFieldList }" cssClass="custom-select form-control form-control-sm"></sf:select>
            </div>
          </div>
          
          <div class="col-md-12">
            <div class="form-group">
              <sf:select path="searchGender" cssClass="custom-select form-control form-control-sm">
                <sf:option path="searchGender"  value="" label="Any Gender"/>
                <sf:options path="searchGender" items="${genderList }" />
              </sf:select>
            </div>
          </div>
        </div>
      </sf:form>
    </div>
  </div>
</div>

<sf:form id="deletePatientForm" action="${patientsUri }" method="POST"></sf:form>
