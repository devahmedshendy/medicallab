<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<%-- <s:url value="/images/defaultProfileImages/maleDefaultProfileImage.png"     var="maleDefaultProfileImageUri"></s:url> --%>

<c:set value='${uri.get("maleDefaultProfileImage") }'     var="maleDefaultProfileImageUri"></c:set>

<br /><br /><br />

<div class="container">
  
	<div class="row justify-content-center">
	    
	    <div class="col-md-8">
		    <ul class="nav nav-tabs">
	        <li class="nav-item">
	          <a class="nav-link active" href="#">Identity</a>
	        </li>
	        <li class="nav-item">
	          <span class="nav-link disabled">Register</span>
	        </li>
	        <li class="nav-item">
	          <span class="nav-link disabled">Credit Card</span>
	        </li>
	        <li class="nav-item">
            <span class="nav-link disabled">Confirm</span>
          </li>
	        <li class="nav-item">
	          <span class="nav-link disabled">Result</span>
	        </li>
	      </ul>
	      
	      <div id="dialog" class="row no-gutters justify-content-center">
          <div class="col-md-8">
            <br />
            <div class="row no-gutters">
              <div class="col-md-4 text-nowrap">
                <h5><span class="lead align-middle text-muted">Identify Yourself</span> </h5>
              </div>
              
              <div class="col-md-8">
                <hr>
              </div>
            </div>
          
            <br />
            <form class="form-inline">
              <span class="lead mr-1 align-middle">You're "Mohamed Abbas" ?</span>
              <div class="form-group">
                <label for="inputPassword2" class="sr-only">Patient Secret Key</label>
                <input type="text" class="form-control" id="patientSecretKey" placeholder="Your Personal Id">
              <button type="submit" class="btn btn-primary">YES</button>
              </div>
            </form>
              
            <form class="form-inline">
              <span class="lead mr-2 align-middle">You're someone else ?</span>
              <button type="submit" class="btn btn-primary">YES</button>
            </form>
          </div>
        </div>
	    </div>
	</div>
	
	
	<br /><br /><br />
	
	<div class="row justify-content-center">
      
      <div class="col-md-8">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <span class="nav-link disabled">Identity <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="#">Register</a>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Credit Card</span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Confirm</span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Result</span>
          </li>
        </ul>
        
        <div id="dialog" class="row no-gutters justify-content-center">
          <div class="col-md-8">
            <br />
            <div class="row no-gutters">
              <div class="col-md-5 text-nowrap">
                <h5><span class="lead align-middle text-muted">Register Your Details</span> </h5>
              </div>
              
              <div class="col-md-7">
                <hr>
              </div>
            </div>
          
            <br />
            <form action="${editPatientUri }" method="POST" commandName="editPatientSettingsForm" enctype="multipart/form-data" class="row justify-content-center">
			        <div class="col-6">
			         <div class="form-group">
			            <img id="profileImage" src="${maleDefaultProfileImageUri }" alt="Profile Image" class="img-thumbnail" width="200" height="200">
			         </div>
			          
			          <div class="form-group">
			            <input path="profileImage" type="file" class="form-control-file form-control-sm" />
			          </div>
			        </div>
			        
			        <div class="col-6">
			            <div class="form-group">
			              <%-- <s:bind path="patientId"> --%>
			                <input path="patientId" class="form-control ${status.error ? 'is-invalid': '' }" placeholder="Personal Id"/>
			              <%-- </s:bind> --%>
			            </div>
			            
			            <div class="form-group">
			              <%-- <s:bind path="fullname"> --%>
			                <input path="fullname" class="form-control ${status.error ? 'is-invalid': '' }" placeholder="Full Name"/>
			              <%-- </s:bind> --%>
			            </div>
			            
			            <div class="form-group">
			              <%-- <s:bind path="phone"> --%>
			                <input path="phone" class="form-control ${status.error ? 'is-invalid': '' }" placeholder="Phone Number"/>
			              <%-- </s:bind> --%>
			            </div>
			            
			            <div class="form-group">
			              <%-- <s:bind path="age"> --%>
			                <input path="age" class="form-control ${status.error ? 'is-invalid': '' }" placeholder="Age"/>
			              <%-- </s:bind> --%>
			            </div>
			            
			            <div class="form-group">
			              <%-- <s:bind path="gender"> --%>
			                <select path="gender" items="${availableGender }" class="form-control ${status.error ? 'is-invalid': '' }" multiple="false"></select>
			              <%-- </s:bind> --%>
			            </div>       
			        </div>
			       
			        <!-- <div class="col-4">
			                    
			        </div> -->
			        
			        <div class="col-10"><hr></div>
			            
			         
			       </form>
			        <div class="form-row">
		              <div class="col-md-5">
		                <button type="submit" class="btn btn-outline-primary btn-sm btn-block">Back</button>
		              </div>
		              
		              <div class="col-md-2">
		                <button type="submit" class="btn btn-outline-danger btn-sm btn-block">Cancel</button>
		              </div>
		              
		              <div class="col-md-5">
		                  <button type="submit" class="btn btn-outline-primary btn-sm btn-block">Register</button>
		                </div>
		            </div>
          </div>
        </div>
      </div>
  </div>
  
  <br /><br /><br />
  
  <div class="row justify-content-center">
      
      <div class="col-md-8">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <span class="nav-link disabled">Identity <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Register <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="#">Credit Card</a>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Confirm</span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Result</span>
          </li>
        </ul>
        
        <div id="dialog" class="row no-gutters justify-content-center">
          <div class="col-md-8">
            <br />
            <div class="row no-gutters">
              <div class="col-md-6 text-nowrap">
                <h5><span class="lead align-middle text-muted">Your Payment Information</span> </h5>
              </div>
              
              <div class="col-md-6">
                <hr>
              </div>
            </div>
            
            <br />
            
            <span class="align-middle">We Accept</span>
            <img alt="Accepted Credit Card" src="/medicallab/images/misc/accepted-credit-card.png" width="50%" height="6%">
            
            <br /><br /><br />
            
            <span class="lead mr-4 align-middle">Card Details</span>
            
            <hr />
            
            <form class="">
					  <div class="form-row">
						  <div class="col-7">
                 <label for="creditCardNumber">Card Number</label>
                 <input type="text" class="form-control" id="creditCardNumber" placeholder="5663 1474 0731 8713" readonly>
                </div>
                
                <div class="col-5">
                   <label for="ccv">CCV</label>
                   <input type="text" class="form-control" id="ccv" placeholder="123" readonly>
                 </div>
					  </div>
					  
					  <br />
					  
					  <div class="form-row">
                <div class="col-md-4">
                  <label>Expiration Date?</label>
                </div>
                
						  <div class="col-md-4">
                 <input type="text" class="form-control" id="creditCardExpirationDate" placeholder="12" readonly>
                </div>
                
                <div class="col-md-4">
                  <input type="text" class="form-control" id="creditCardExpirationDate" placeholder="2018" readonly>
                </div>
                
					  </div>
					  
					  <br /><br /><br /><br />
					  
					  <div class="form-row">
					    <div class="col-md-5">
					      <button type="submit" class="btn btn-outline-primary btn-sm btn-block">Back</button>
					    </div>
					    
					    <div class="col-md-2">
					      <button type="submit" class="btn btn-outline-danger btn-sm btn-block">Cancel</button>
					    </div>
					    
					    <div class="col-md-5">
                  <button type="submit" class="btn btn-outline-primary btn-sm btn-block">Next</button>
                </div>
					  </div>
					</form>
              
          </div>
        </div>
      </div>
  </div>
  
  <br /><br /><br />
  
  <div class="row justify-content-center">
      
      <div class="col-md-8">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <span class="nav-link disabled">Identity <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Register <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Credit Card <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <a class="nav-link active">Confirm</a>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Result</span>
          </li>
        </ul>
        
        <div id="dialog" class="row no-gutters justify-content-center">
          <div class="col-md-8">
            <br />
            <div class="row no-gutters">
              <div class="col-md-6 text-nowrap">
                <span class="lead align-middle text-muted">Your Personal Information</span> 
              </div>
            </div>
            
            
            <div class="row justify-content-center">
              <div class="col-md-12">
                <table class="table">
                  <thead>
                    <tr>
                      <th>Fullname</th>
                      <th>Personal Id</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Mohamed Abbas</td>
                      <td>24502181902012</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            
            <div class="row no-gutters">
              <div class="col-md-6 text-nowrap">
                <span class="lead align-middle text-muted">Your Payment Information</span> 
              </div>
            </div>
            
            <div class="row justify-content-center">
              <div class="col-md-12">
                <table class="table">
                  <thead>
                    <tr>
                      <th>Card Number / CCV</th>
                      <th>Expiration Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>5663 1474 0731 8713 / 123</td>
                      <td>12/2018</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            
            <!-- <form class="">
	            <div class="form-row">
	              <div class="col-7">
	                 <label for="creditCardNumber">Card Number</label>
	                 <input type="text" class="form-control" id="creditCardNumber" placeholder="5663 1474 0731 8713" readonly>
	                </div>
	                
	                <div class="col-5">
	                   <label for="ccv">CCV</label>
	                   <input type="text" class="form-control" id="ccv" placeholder="123" readonly>
	                 </div>
	            </div>
	            
	            <br />
	            
	            <div class="form-row">
	                <div class="col-md-4">
	                  <label>Expiration Date</label>
	                </div>
	                
	              <div class="col-md-4">
	                 <input type="text" class="form-control" id="creditCardExpirationDate" placeholder="12" readonly>
	                </div>
	                
	                <div class="col-md-4">
	                  <input type="text" class="form-control" id="creditCardExpirationDate" placeholder="2018" readonly>
	                </div>
	                
	            </div>
            </form> -->
            
            <div class="row no-gutters">
              <div class="col-md-3 text-nowrap">
                <span class="lead align-middle text-muted">Cost Details</span> 
              </div>
            </div>
            
            <div class="row justify-content-center">
              <div class="col-md-12">
                <table class="table">
                  <thead>
                    <tr>
                      <th>Fullname</th>
                      <th>Personal Id</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Mohamed Abbas</td>
                      <td>24502181902012</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            
            <br />
            
            <div class="form-row">
              <div class="col-md-5">
                <button type="submit" class="btn btn-outline-primary btn-sm btn-block">Back</button>
              </div>
              
              <div class="col-md-2">
                <button type="submit" class="btn btn-outline-danger btn-sm btn-block">Cancel</button>
              </div>
              
              <div class="col-md-5">
                  <button type="submit" class="btn btn-primary btn-sm btn-block">Confirm</button>
                </div>
            </div>
            
              
          </div>
        </div>
      </div>
  </div>
  
  <br /><br /><br />
  
  <div class="row justify-content-center">
      
      <div class="col-md-8">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <span class="nav-link disabled">Identity <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Register <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <span class="nav-link disabled">Credit Card <i class="fa fa-check-circle text-success" aria-hidden="true"></i></span>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled">Confirm <i class="fa fa-check-circle text-success" aria-hidden="true"></i></a>
          </li>
          <li class="nav-item">
            <span class="nav-link active">Result</span>
          </li>
        </ul>
        
        <div id="dialog" class="row no-gutters justify-content-center">
          <div class="col-md-8 text-center">
            <div class="alert alert-success" role="alert">
               <br /><br />
						  <h4 class="alert-heading"><i class="fa fa-check" aria-hidden="true"></i> Payment Done</h4>
						  <p>You have paid <span class="badge badge-light">200 EGP</span> for CBC test successfully.</p>
						  <hr>
               <a href="#" >Get me back to the medical profile</a>
						</div>
            
            <br />
            
          </div>
        </div>
      </div>
  </div>
</div>
