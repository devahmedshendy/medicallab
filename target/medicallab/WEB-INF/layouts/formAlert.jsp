<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

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