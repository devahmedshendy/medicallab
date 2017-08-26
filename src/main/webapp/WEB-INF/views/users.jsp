<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<br /><br />

<s:url value="/users"         var="usersUri"></s:url>
<s:url value="/users/new"     var="newUserUri"></s:url>
<s:url value="/users/search"  var="searchUsersUri"></s:url>

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

<!-- Determine if this page is /users/search -->
<c:if test="${currentUri == searchUsersUri }">
  <c:set value="/users/search"         var="currentPath"></c:set>
</c:if>

<!-- 
     Now currentPath would be /users with/without its query string 
     or /users/search with its queries string 
-->
<!-- User currentPath to create sorting columns href -->
<c:forEach items='${uriListHolder.get("sortFields").split(" ") }' var="field">
  <c:url value="${currentPath }" var="fieldSortByUri">
    <c:param name="sortField" value="${field }"></c:param>
    <c:param name="sortOrder" value='${sortField == field ? sortOrder : "ASC" }'></c:param>
    
    <c:if test="${currentUri == searchUsersUri }">
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
          <a id="addUserLink" class="text-primary" href="${newUserUri }">
            <i class="fa fa-user-plus fa-2x" aria-hidden="true"></i> 
            <span>NEW USER</span>
          </a>
	      </div>
	
	      <div class="col-5">
          <div class="row no-gutters justify-content-end align-items-start">
	          <div class="col-6 text-right">
	            <h5><span class="badge badge-light mr-2">${pageSize } / ${totalUsers } users</span></h5>
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
	          <th width="5%"></th>
	          
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
            
            <!-- Username Column Head -->
	          <th width="15%">
	            <a href='${uriListHolder.get("username") }'>Username</a>
	            
	            <c:if test='${sortField.equals("username") }'>
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
            
            <!-- Gender Column Head -->
	          <th>
	            <a href='${uriListHolder.get("gender") }'>Gender</a>
	            
	            <c:if test='${sortField.equals("gender") }'>
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
	          <th width="15%">
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
	          <th width="15%">
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
	          
	          <!-- Edit/Delete/Enable/Disable User Column Head -->
	          <th width="7%" class="text-center">
	            <a id="meLink" 
	               href='#' data-toggle="tooltip" 
	               data-placement="top" data-html="true" 
	               title='
			                 <table class="text-light">
				                 <tr>
				                   <td><i class="fa fa-pencil-square-o text-light mr-2" aria-hidden="true"></i></td>
				                   <td><small>Edit User</small></td>
				                 </tr>
				                 <tr>
			                     <td><i class="fa fa-pause text-light mr-2" aria-hidden="true"></i></td>
			                     <td><small>Disbale User</small></td>
			                   </tr><tr>
			                     <td><i class="fa fa-play text-light mr-2" aria-hidden="true"></i></td>
			                     <td><small>Enable User</small></td>
			                   </tr>
			                   <tr>
			                     <td><i class="fa fa-trash text-light mr-2" aria-hidden="true"></i></td>
			                     <td><small>Delete User</small></td>
			                   </tr>
			                 </table>
               '>
	              <i class="fa fa-question-circle text-info" aria-hidden="true"></i>
	            </a>
            </th>
	        </tr>
	      </thead>
	
	      <tbody>
		      <c:forEach var="user" items="${users }">
		        <c:set value="${user.getFirstname() } ${user.getLastname() }"  var="fullname"></c:set>
		        <c:set value="${user.getUsername() }"       var="username"></c:set>
		        <c:set value="${user.getGender() }"         var="gender"></c:set>
		        <c:set value="${user.getCreatedAt() }"      var="createdAt"></c:set>
		        <c:set value="${user.getUpdatedAt() }"      var="updatedAt"></c:set>
		        <c:set value="${user.isEnabled() }"         var="enabled"></c:set>
		        <c:set value="${user.role.getRoleName() }"  var="userRole"></c:set>
		        
		        <s:url value="/users/edit/${username }"   var="editUserUrl"></s:url>		
		        
					  <tr class="${! enabled ? 'table-warning' : '' }">
				      <td>
				        <c:choose>
                  <c:when test="${userRole == 'ROLE_ADMIN' }">
                    <i class="fa fa-user-secret mr-2" aria-hidden="true"></i>
                  </c:when>
                                     
                  <c:when test="${userRole == 'ROLE_DOCTOR' }">
                    <i class="fa fa-user-md mr-2" aria-hidden="true"></i>
                  </c:when>
                   
                  <c:when test="${userRole == 'ROLE_OFFICER' }">
                    <i class="fa fa-user mr-2" aria-hidden="true"></i>
                  </c:when>
                </c:choose>
				      </td>
				      
	            <td>${fullname }</td>
	            
	            <td>${username }</td>
	            
	            <td>${gender }</td>
	            
	            <td><fmt:formatDate type="date" value="${createdAt }" /></td>
	            
	            <td><fmt:formatDate type="date" value="${updatedAt }" /></td>
	            
	            <td class="text-center">
	              <a id="editUserLink" data-username="${username }" href='${editUserUrl }'>
                   <i class="fa fa-pencil-square-o text-primary" aria-hidden="true"></i>
                 </a>
                 
		            <c:choose>
		              <c:when test="${enabled }">
    		              <a id="changeUserStatusLink" data-username="${username }" data-enable-user="false" href="#">
                      <i class="fa fa-pause text-danger" aria-hidden="true"></i>		              
    		              </a>
		              </c:when>
		              
		              <c:otherwise>
									 <a id="changeUserStatusLink" data-username="${username }" data-enable-user="true" href="#">
									   <i class="fa fa-play text-primary" aria-hidden="true"></i>
									 </a>
		              </c:otherwise>
		            </c:choose>
                 
							 <a id="deleteUserLink" data-username="${username }" href="#">
							   <i class="fa fa-trash text-danger" aria-hidden="true"></i>
							 </a>
	            </td>
	          </tr>
					</c:forEach>
	      </tbody>
	    </table>
	  </div>
	  
	  <div class="col-md-2">
      <sf:form action="${searchUsersUri }" method="GET" modelAttribute="searchUserForm">
        <button type="submit" class="btn btn-outline-secondary btn-sm btn-block">Refine</button>
      
        <hr />
        <div class="row no-gutters">

	        <c:if test="${currentUri == searchUsersUri }">
	          <div class="col-md-12 text-center">
	            <a href="${usersUri }" class="btn btn-link"><small>RESET</small></a>
	          </div>
	        </c:if>
	        
          <div class="col-md-12">
            <div class="form-group">
              <sf:input path="searchText" cssClass="form-control form-control-sm" placeHolder="Search..."/>
            </div>
          </div>
          <div class="col-md-12">
            <div class="form-group">
              <sf:select path="searchField" items="${searchUserForm.getAvailableSearchFieldList() }" cssClass="custom-select form-control form-control-sm"></sf:select>
            </div>
          </div>
        </div>
      </sf:form>
    </div>
	</div>
</div>

<sf:form id="deleteUserForm" action="${usersUri }" method="POST"></sf:form>
<sf:form id="changeUserStatusForm" action="${usersUri }" method="POST"></sf:form>