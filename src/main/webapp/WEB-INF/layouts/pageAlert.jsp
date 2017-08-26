<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="container">
	<div class="row justify-content-center">

    <div class="col-6">
      <c:choose>
        
        <c:when test="${not empty info}"> <!-- INFO Alerts -->
          <div class="alert alert-info alert-dismissible fade show text-center" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            ${info }
          </div>
        </c:when>
        
        
        <c:when test="${not empty warning}"> <!-- WARNING Alerts -->
          <div class="alert alert-warning text-center" role="alert">
            ${warning }
          </div>
        </c:when>

        
        <c:when test="${not empty success}"> <!-- SUCCESS Alerts -->
          <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            ${success }
          </div>
        </c:when>
		    
		    <c:when test="${param.logout != null}">
		      <div class="alert alert-success text-center" role="alert">
		        You logged out successfully
		      </div>
		    </c:when>

        
        <c:when test="${SPRING_SECURITY_LAST_EXCEPTION != null }"> <!-- ERROR Alerts -->
          <div class="alert alert-danger text-center" role="alert">
		        You provided wrong credentials
	        </div>
        </c:when>
        
        <c:when test="${not empty error}">
	        <div class="alert alert-danger text-center" role="alert">
	          ${error }
	        </div>
        </c:when>
	    
		    <c:when test="${param.error != null}">
		      <div class="alert alert-danger text-center" role="alert">
		      </div>
		    </c:when>

        
		    <c:when test="${param.expired != null }"> <!-- WARNING Alerts -->
		       <div class="alert alert-warning text-center" role="alert">
              Your login session has been terminated !
            </div>
		    </c:when>
		     
		    <c:when test="${param.invalidSession != null }">
            <div class="alert alert-warning text-center" role="alert">
              Your login session has been terminated !
            </div>
        </c:when>
		  </c:choose>
    </div>
	</div>
</div>