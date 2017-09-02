<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags"          prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form"     prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<c:set value="${patient.getPatientId() }"   var="patientId"></c:set>
<c:set value="${patient.getFullname() }"    var="fullname"></c:set>
<c:set value="${patient.getGender() }"      var="gender"></c:set>
<c:set value="${patient.getAge() }"         var="age"></c:set>
<c:set value="${patient.getPhone() }"       var="phone"></c:set>

<c:set value='${uri.get("editPatient", patientId) }'          var="editPatientUri"></c:set>
<c:set value='${uri.get("patientProfileImage", patientId) }'   var="patientProfileImageUri"></c:set>

<%-- <s:url value="/patients/edit/${patientId }" var="editPatientUri"></s:url>
<s:url value="/patients/${patientId }/profile-image.png"       var="patientProfileImageUri"></s:url> --%>


<div class="container">
  
  <div class="row justify-content-center">
    <div class="col-md-12">
	    <div class="card bg-light mb-3">
	      <div class="card-header">
	        <div class="row justify-content-center">
		        <div class="col-md-8">
		          <h4 class="d-inline mr-1">${fullname }</h4>
		          
		          <sec:authorize access="hasRole('OFFICER')">
		            <a id="editPatientLink" data-patientId="${patientId }" href='${editPatientUri }'>
		              <i class="fa fa-pencil-square-o text-primary" aria-hidden="true"></i>
		            </a>
		          </sec:authorize>
		        </div>
	        </div>
	      </div>
	
			  <div class="card-body">
		      <div class="row justify-content-center">
			      <div class="col-md-8">
				      <div class="row justify-content-left">
					      <div class="col-4"><img alt="Profile Image" src="${patientProfileImageUri }" class="rounded img-thumbnail"></div>
	              
	              <div class="col-8 card-text">
	                <table class="table">
	                  <tbody>
	                    <tr>
	                      <td><strong>Personal Id</strong></td>
	                      <td>${patientId }</td>
	                    </tr>
	                    <tr>
	                      <td><strong>Age</strong></td>
	                      <td>${age } Years Old</td>
	                    </tr>
	                    <tr>
	                      <td><strong>Phone</strong></td>
	                      <td>${phone }</td>
	                    </tr>
	                    <tr>
	                      <td><strong>Gender</strong></td>
	                      <td>${gender }</td>
	                    </tr>
	                  </tbody>
	                </table>
	              </div>
				      </div>
			      </div>
		      </div>
			  </div>
			</div>
    </div>
  </div>  
  
  <div class="row">
    <div class="col-md-12 text-center">
      <br /><br />
      <h4><span class="lead badge badge-light">LATEST</span></h4>
    </div>
    
    <div class="col-md-12">
      <div class="card">
        <div class="card-body">
        
          <div class="row justify-content-center">
	          <div class="col-md-8">
		          <div class="row">
		            <div class="col-6 text-left">
		              <h4 class="card-title mr-2">2017-07-12 <small class="text-muted">#3423</small></h4>
		            </div>
		            
		            <div class="col-6 text-right">
                  <h4 class="card-title mr-2"><small><span class="badge badge-pill badge-info">Pending</span> CBC</small></h4>
                </div>
		          </div>
	            
              <hr>
              
              <!-- <div class="row justify-content-center">
                <div class="col-md-8"> -->
                  <div class="row justify-content-center">
                    <div class="col-md-5 text-center">
                      <span><u>Test Result</u></span>
                      <table class="table table-hover">
                        <thead>
                          <tr>
                            <th></th>
                            <th>Value</th>
                            <th>Normal</th>
                          </tr>
                        </thead>
                        
                        <tbody>
                          <tr>
                            <th scope="row">ABC</th>
                            <td>?</td>
                            <td>2.0 : 3</td>
                          </tr>
                          <tr>
                            <th scope="row">DEF</th>
                            <td>?</td>
                            <td>2.0 : 4.0</td>
                          </tr>
                          <tr>
                            <th scope="row">GEH</th>
                            <td>?</td>
                            <td>4 : 10</td>
                          </tr>
                          <tr>
                            <th scope="row">IJK</th>
                            <td>?</td>
                            <td>3 : 5</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    
                    <div class="col-md-7 text-center">
                      <span><u>Test Approval</u></span>
                      <table class="table table-hover">
                        <thead>
                          <tr>
                            <th width="40%">Doctor</th>
                            <th width="60%">Comment</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td>Mahmoud Yassin Abdel Ghany</td>
                            <td>No problem...</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                <!-- </div>
              </div> -->
              
              
              
		          <div class="row">
	              <div class="col-md-12">
                  <a href="#" class="card-link btn btn-outline-primary btn-sm btn-block">
		                PAY <span class="badge badge-light">200 EGP</span> [ Result With Approval ] 
		               </a>
                </div>
		          </div>
							
	          </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-12 text-center">
      <br /><br />
      <h4><span class="lead badge badge-light">OLD</span></h4>
    </div>
    <div class="col-md-12">
	    <div id="accordion" role="tablist">
	      <div class="card">
          <div class="card-header" role="tab" id="headingOne">
            <div class="row justify-content-center">
              <div class="col-md-8">
                <div class="row">
	                <div class="col-6 text-left">
	                  <h5 class="mb-2">
		                  <a class="collapsed" data-toggle="collapse" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
		                    2017-02-07
		                  </a>
		                  <small class="text-muted">#375</small>
		                </h5>
	                </div>
	                
	                <div class="col-6 text-right">
	                  <h4 class="card-title mr-2"><small><span class="badge badge-pill badge-light">Approved</span> CBC</small></h4>
	                </div>
	              </div>
              
                
                
                <%-- <h6 class="card-subtitle mb-2 text-muted"><span class="badge badge-light">Approved</span></h6> --%>
              </div>
            </div>
          </div>
          <div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne" data-parent="#accordion">
            <div class="card-body">
              Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
            </div>
          </div>
        </div>
        
			  <div class="card">
			    <div class="card-header" role="tab" id="headingTwo">
	          <div class="row justify-content-center">
	            <div class="col-md-8">
	              <div class="row">
                  <div class="col-6 text-left">
                    <h5 class="mb-2">
                      <a class="collapsed" data-toggle="collapse" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        2017-02-07
                      </a>
                      <small class="text-muted">#375</small>
                    </h5>
                  </div>
                  
                  <div class="col-6 text-right">
                    <h4 class="card-title mr-2"><small><span class="badge badge-pill badge-light">Approved</span> ABC</small></h4>
                  </div>
                </div>
	            </div>
	          </div>
	        </div>
			    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo" data-parent="#accordion">
			      <div class="card-body">
			        <div class="row justify-content-center">
			          <div class="col-md-8">
				          <div class="row justify-content-center">
		                <div class="col-md-5 text-center">
		                  <span><u>Test Result</u></span>
		                  <table class="table table-hover">
		                    <thead>
		                      <tr>
		                        <th></th>
		                        <th>Value</th>
		                        <th>Normal</th>
		                      </tr>
		                    </thead>
		                    
		                    <tbody>
		                      <tr>
		                        <th scope="row">ABC</th>
		                        <td>1</td>
		                        <td>2.0 : 3</td>
		                      </tr>
		                      <tr>
		                        <th scope="row">DEF</th>
		                        <td>3.0</td>
		                        <td>2.0 : 4.0</td>
		                      </tr>
		                      <tr>
		                        <th scope="row">GEH</th>
		                        <td>2</td>
		                        <td>4 : 10</td>
		                      </tr>
		                      <tr>
		                        <th scope="row">IJK</th>
		                        <td>2</td>
		                        <td>3 : 5</td>
		                      </tr>
		                    </tbody>
		                  </table>
		                </div>
		                
		                <div class="col-md-7 text-center">
		                  <span><u>Doctor Approval</u></span>
			                <table class="table table-hover">
			                  <thead>
			                    <tr>
			                      <th width="40%">Doctor</th>
			                      <th width="60%">Comment</th>
		                      </tr>
			                  </thead>
                        <tbody>
                          <tr>
                            <td>????</td>
                            <td>???...</td>
                          </tr>
                        </tbody>
                      </table>
		                </div>
		              </div>
		              
		              <div class="row">
		                <div class="col-md-12">
		                   <a href="#" class="card-link btn btn-outline-success btn-sm btn-block disabled" role="button" aria-disabled="true">
		                     <span class="badge badge-light">200 EGP</span> Paid For [ Result With Approval ]
                       </a>
		                </div>
		              </div>
			          </div>
			        </div>
			      </div>
			    </div>
			  </div>
			</div>
    </div>
    
    <br /><br /><br />
  </div>
</div>