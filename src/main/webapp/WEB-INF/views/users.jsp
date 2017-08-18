<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<br /><br />

<div class="container">

	<div class="row">
	  <div class="col-md-2">
	    <small><strong>Search / Sort</strong></small>
	    <hr>
	
	    <form>
	      <small><strong>Search By</strong></small>
	      <div class="row no-gutters">
	        <div class="col-md-12">
	          <div class="form-group">
	            <select class="custom-select form-control form-control-sm">
	              <option value="1" selected>Patient Id</option>
	              <option value="1">Patient Name</option>
	              <option value="2">Request Id</option>
	            </select>
	          </div>
	        </div>
	        <div class="col-md-12">
	          <div class="form-group">
	            <input id='requestSearchBox' type="text" class="form-control form-control-sm" placeholder="Type here">
	          </div>
	        </div>
	      </div>
	
	      <small><strong>Sort By</strong></small>
	      <div class="row no-gutters"> 
	        <div class="col-12">
	          <div class="form-group">
	            <select id="requestStatusSelector" class="custom-select form-control form-control-sm">
	              <option value="1">Patient Name</option>
	              <option value="1" selected>Creation At</option>
	              <option value="1">Request Status</option>
	            </select>
	          </div>
	        </div>
	
	        <div class="col-12">
	          <div class="form-group">
	            <select id="requestStatusSelector" class="custom-select form-control form-control-sm">
	              <option value="1" selected>Asec</option>
	              <option value="1">Desc</option>
	            </select>
	          </div>
	        </div>
	
	      </div>
	    </form>
	
	    <button type="submit" name="button" class="btn btn-primary btn-sm btn-block">Apply</button>
	  </div>
	
	  <div class="col-md-10">
	    <div class="row justify-content-between">
	      <div class="col-3">
	          <button id="addPatientButton" type="button" role="button" class="btn btn-primary btn-sm btn-block">Add Patient</button>
	      </div>
	
	      <div class="col-4">
	        <nav aria-label="Page navigation example">
	          <ul class="pagination justify-content-end pagination-sm">
	            <li class="page-item disabled">
	              <a class="page-link" href="#" tabindex="-1">Previous</a>
	            </li>
	
	            <li class="page-item"><a class="page-link" href="#">2</a></li>
	
	            <li class="page-item">
	              <a class="page-link" href="#">Next</a>
	            </li>
	          </ul>
	        </nav>
	      </div>
	    </div>
	    <table class="table table-sm table-hover">
	      <thead class="blue-grey lighten-4">
	        <tr>
		        <th></th>
	          <th>Id</th>
	          <th>Patient Name</th>
	          <th>Age</th>
	          <th>Created At</th>
	        </tr>
	      </thead>
	
	      <tbody>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>28411111950032</td>
	          <td>Yomna Maher El Sayed</td>
	          <td>29</td>
	          <td>2017-07-04</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>28910081841837</td>
	          <td>Ahmed Yasser Al Morshdy</td>
	          <td>21</td>
	          <td>2017-07-04</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>28810264201410</td>
	          <td>Yasmin Mostafa Ahmed</td>
	          <td>34</td>
	          <td>2017-07-03</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>27812341201432</td>
	          <td>Mohamed Ayman El Sayed</td>
	          <td>57</td>
	          <td>2017-07-03</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>28910081841837</td>
	          <td>Ahmed Yasser Al Morshdy</td>
	          <td>33</td>
	          <td>2017-07-04</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>27812341201432</td>
	          <td>Mohamed Ayman El Sayed</td>
	          <td>43</td>
	          <td>2017-07-03</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>27812341201432</td>
	          <td>Mohamed Ayman El Sayed</td>
	          <td>25</td>
	          <td>2017-07-03</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>28910081841837</td>
	          <td>Ahmed Yasser Al Morshdy</td>
	          <td>12</td>
	          <td>2017-07-04</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>27812341201432</td>
	          <td>Mohamed Ayman El Sayed</td>
	          <td>11</td>
	          <td>2017-07-03</td>
	        </tr>
	        <tr>
	          <td>
	            <a href="#" class="text-primary"><i class="fa fa-trash" aria-hidden="true"></i></a>
	            <a href="#" class="text-primary"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
	          </td>
	          <td>27812341201432</td>
	          <td>Mohamed Ayman El Sayed</td>
	          <td>76</td>
	          <td>2017-07-03</td>
	        </tr>
	      </tbody>
	    </table>
	  </div>
	</div>

</div>