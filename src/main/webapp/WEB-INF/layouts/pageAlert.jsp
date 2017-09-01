<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<c:choose>
  
  <c:when test="${not empty info}"> <!-- INFO Alerts -->
    <div class="container">
			<div class="row justify-content-center">
		     <div class="col-7">
		      <div class="alert alert-info alert-dismissible fade show text-center" role="alert">
		        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		        ${info }
		      </div>
		     </div>
			</div>
    </div>
  </c:when>
  
  
  <c:when test="${not empty warning}"> <!-- WARNING Alerts -->
    <div class="container">
      <div class="row justify-content-center">
	      <div class="col-7">
		       <div class="alert alert-warning text-center" role="alert">
		         ${warning }
		       </div>
	      </div>
      </div>
    </div>
  </c:when>

  
  <c:when test="${not empty success}"> <!-- SUCCESS Alerts -->
    <div class="container">
      <div class="row justify-content-center">
	      <div class="col-7">
		       <div class="alert alert-success alert-dismissible fade show" role="alert">
		         <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		           <span aria-hidden="true">&times;</span>
		         </button>
		         ${success }
		       </div>
	      </div>
      </div>
    </div>
  </c:when>

	<c:when test="${param.logout != null}">
	  <div class="container">
	      <div class="row justify-content-center">
					<div class="col-7">
						<div class="alert alert-success alert-dismissible fade show text-center" role="alert">
			         <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			           <span aria-hidden="true">&times;</span>
			         </button>
						  You logged out successfully
						</div>
					</div>
	      </div>
	    </div>
	</c:when>

  
  <%-- <c:when test="${SPRING_SECURITY_LAST_EXCEPTION != null }"> <!-- ERROR Alerts -->
    <div class="container">
      <div class="row justify-content-center">
	      <div class="col-7">
		       <div class="alert alert-danger text-center" role="alert">
		       You provided wrong credentials
		      </div>
	      </div>
      </div>
    </div>
  </c:when> --%>
  
  <c:when test="${not empty error}">
    <div class="container">
      <div class="row justify-content-center">
	      <div class="col-7">
		      <div class="alert alert-danger text-center" role="alert">
		        ${error }
		      </div>
	      </div>
      </div>
    </div>
  </c:when>

	<%-- <c:when test="${param.error != null}">
	   <div class="container">
	     <div class="row justify-content-center">
				<div class="col-7">
					<div class="alert alert-danger text-center" role="alert">
					</div>
				</div>
	     </div>
	   </div>
	</c:when> --%>

  
  <c:when test="${param.expired != null }"> <!-- WARNING Alerts -->
    <div class="container">
			<div class="row justify-content-center">
				<div class="col-7">
					<div class="alert alert-warning text-center" role="alert">
					  Your login session has been terminated !
					</div>
				</div>
			</div>
    </div>
  </c:when>
 
  <c:when test="${param.invalidSession != null }">
    <div class="container">
			<div class="row justify-content-center">
				<div class="col-7">
				  <div class="alert alert-warning text-center" role="alert">
				    Your login session has been terminated !
				  </div>
				</div>
			</div>
    </div>
  </c:when>
</c:choose>
