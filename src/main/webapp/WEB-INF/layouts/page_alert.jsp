<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="container">
	<div class="row justify-content-center">

    <div class="col-6">
      <c:choose>
        <c:when test="${not empty info}">
          <div class="alert alert-info alert-dismissible fade show" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            ${info }
          </div>
        </c:when>
      </c:choose>
        
    
      <c:choose>
        <c:when test="${not empty success}">
          <div class="alert alert-success alert-dismissible fade show" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            ${info }
          </div>
        </c:when>
		    
		    <c:when test="${param.logout != null}">
		      <div class="alert alert-success alert-dismissible fade show" role="alert">
		        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		        
		        You logged out successfully
		      </div>
		    </c:when>
	    </c:choose>
	    
	    
	    <c:choose>
        <c:when test="${SPRING_SECURITY_LAST_EXCEPTION != null }">
          <div class="alert alert-danger" role="alert">
		        You provided wrong credentials
	        </div>
        </c:when>
        
        <c:when test="${not empty error}">
	        <div class="alert alert-danger" role="alert">
	          ${error }
	        </div>
        </c:when>
	    
		    <c:when test="${param.error != null}">
		      <div class="alert alert-danger" role="alert">
		      </div>
		    </c:when>
		    
		   </c:choose>
    </div>
	</div>
</div>