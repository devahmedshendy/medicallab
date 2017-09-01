$(document).ready(function() {
console.log("Hello from app.js");

// General
//$(".dropdown").hover(function(event) {
//	$('.dropdown-toggle').dropdown("toggle");
//}, function(event) {
//	$('.dropdown-toggle').dropdown("toggle");
//})

$('[data-toggle="tooltip"]').tooltip()

if (window.location.href.indexOf("login") != -1) {
	console.log("/login page");
	
	$("#getMedicalProfileForm").submit(function (event) {
		event.preventDefault();
		
		var $personalIdInput 		= $("input[name=personalId]");
		var patientPersonalId 		= $personalIdInput.val();
		var $findPatientAlert 		= $("#findPatientAlert");
		
		if (patientPersonalId) {
			$personalIdInput.removeClass("is-invalid");

			var getPatientAPI 			= window.location.origin + "/patients/api/" + patientPersonalId;
			var getPatientProfileUri 	= window.location.origin + "/patients/medical-profile/" + patientPersonalId
			
			qwest
			.get(getPatientAPI)
			.then(function(xhr, response) {
				window.location.href = getPatientProfileUri
				
			})
			.catch(function(e, xhr, response) {
				if (xhr.status === 404) {
					$findPatientAlert.removeClass("alert-success");
					$findPatientAlert.removeClass("d-none");
					
					$findPatientAlert.addClass("is-invalid");
					$findPatientAlert.addClass("alert-danger");
					
					$findPatientAlert.text("No such patient");
					
				} else {
					$findPatientAlert.text("Unhandled Error");
					console.error(e);
				}
				
		     });
			
		} else {
			$personalIdInput.addClass("is-invalid");
			
		}
	})
	

} else if (window.location.href.indexOf("users") != -1) {
	console.log("/users page");
	
	$("#usersNavLink").addClass("active");
	
	$(document).on("click", "#deleteUserLink", (event) => {
		event.preventDefault;
		
		var username = $(event.target).parent("#deleteUserLink").data("username");
		var confirmDevare = confirm("Devare user '"+  username +"', Are you sure?");
		
		if(confirmDevare) {
			var $deleteUserStatusForm = $("#deleteUserForm");
			var formActionUri = $deleteUserStatusForm.uri();
			
			var formActionUriSegments = formActionUri.segment();
			formActionUriSegments.push(username);

			formActionUri.segment(formActionUriSegments);
			formActionUri.addQuery("delete", true);
			
			$deleteUserStatusForm.attr("action", formActionUri.toString()).submit();
		}
	});
	
	$(document).on("click", "#changeUserStatusLink", (event) => {
		event.preventDefault;
		
		var username = $(event.target).parent("#changeUserStatusLink").data("username");
		var enableUser = $(event.target).parent("#changeUserStatusLink").data("enable-user");

		var confirmDevare;
		if (enableUser === true) {
			confirmDevare = confirm("Enable user '"+  username +"', Are you sure?");
			
		} else if (enableUser === false) {
			confirmDevare = confirm("Disable user '"+  username +"', Are you sure?");
		}
		
		if(confirmDevare) {
			var $changeUserStatusForm = $("#changeUserStatusForm");
			var formActionUri = $changeUserStatusForm.uri();
			
			var formActionUriSegments = formActionUri.segment();
			formActionUriSegments.push(username);
			
			formActionUri.segment(formActionUriSegments);
			formActionUri.addQuery("enable", enableUser);
			
			$changeUserStatusForm.attr("action", formActionUri.toString()).submit();
		}
	});
	
	
} else if (window.location.href.indexOf("patients") != -1) {
	console.log("/patients page");
	
	$("#patientsNavLink").addClass("active");
	
	$(document).on("click", "#deletePatientLink", (event) => {
		event.preventDefault;
		
		var patientId = $(event.target).parent("#deletePatientLink").data("patient-id") + "";
		var confirmDevare = confirm("Devare patient of id '"+  patientId +"', Are you sure?");
		
		if(confirmDevare) {
			var $deletePatientForm = $("#deletePatientForm");
			var formActionUri = $deletePatientForm.uri();

			var formActionUriSegments = formActionUri.segment();
			formActionUriSegments.push(patientId);

			formActionUri.segment(formActionUriSegments);
			formActionUri.addQuery("delete", true);
			
			$deletePatientForm.attr("action", formActionUri.toString()).submit();
		}
	})
	
} else if (window.location.href.indexOf("tests") != -1) {
	$("#testsNavLink").addClass("active");
	
} else if (window.location.href.indexOf("requests") != -1) {
	$("#requestsNavLink").addClass("active");
	
} else {
	$("#homeNavLink").addClass("active");
}

$("#logoutLink").on("click", function(event) {
	event.preventDefault();
	
	$("#logoutForm").submit();
});

var patientDetailsTable="#patientDetailsTable";
var testDetailsTable = "#testDetailsTable";
var modalLoadingBarTimeout = null;

$("#requestsTable tr").on('click', function(event) {
    var tableRowItem = $(event.target).closest("tr");

    if (! $(event.target).is("input[type=radio]")) {
    $(tableRowItem)
        .find("input[type=radio]")
        .prop("checked", function(index, value) {
        return !value;
        });
    }

    var numberOfCheckedCheckboxes = $("input:checked").length;
    // numberOfCheckedCheckboxes !== 0 ?
    // var tableButtons = $("#deleteFromTableButton");
    toggleTableActionButtons()
    // $("#deleteFromTableButton").prop("disabled", false)
    // :
    // $("#deleteFromTableButton").prop("disabled", true);
});

$(".modal").on('show.bs.modal', function(event) {
    startModalLoadingBar();
});

$(".modal").on('hide.bs.modal', function(event) {
    clearTimeout(modalLoadingBarTimeout);

    stopModalLoadingBar();
    resetPatientDetailsTable();
    resetTestDetailsTable();
    resetRequestStatusBadge();
    resetRequestDetailsTable();
})

function toggleTableActionButtons() {
    var tableActionButtons = $("#tableActionButtons").find("button");

    var numberOfCheckedCheckboxes = $("input:checked").length;

    $(tableActionButtons).each(function(index) {
    numberOfCheckedCheckboxes === 0
    ?
        // $(this).prop("disabled", false);
        disableButton(this)
    :
        enableButton(this);
    })
}

function disableButton(buttonElement) {
    $(buttonElement).prop("disabled", true);
}

function enableButton(buttonElement) {
    $(buttonElement).prop("disabled", false);
}

function stopModalLoadingBar(modalLoadingBarId="#modalLoadingBar") {
    $(modalLoadingBarId).removeClass("modal-loading-bar");
}

function startModalLoadingBar(modalLoadingBarId="#modalLoadingBar") {
    $(modalLoadingBarId).addClass("modal-loading-bar");
}

function refreshModalRequestDetailsTable() {
    var patientDetails = {
        id: "28910081841837",
        fullName: "Ahmed Yasser Al Morshdy",
        gender: "Male",
        age: 21
    }

    var testDetails = {
        type: "cbc",
        testItemNames: ["wcb", "hgb", "mcv", "mch"],
        testItemValues: [1.0, 2.0, 3.0, 4.0]
    }

    var requestDetails = {
        status: "Pending",
        doctor: "Mahmoud Khairy",
        comment: "",
    }

    refreshModalPatientDetails(patientDetails=patientDetails);
    refreshModalTestDetails(testDetails=testDetails);
    setRequestStatusBadge(requestDetails["status"]);
    refreshModalRequestDetails(requestDetails=requestDetails);
}


function refreshModalPatientDetails(patientDetails) {
    $(patientDetailsTable + " #patientDetailsTableBodyId")
        .html(patientDetails["id"]);

    $(patientDetailsTable + " #patientDetailsTableBodyFullName")
        .html(patientDetails["fullName"]);

    $(patientDetailsTable + " #patientDetailsTableBodyGender")
        .html(patientDetails["gender"]);

    $(patientDetailsTable + " #patientDetailsTableBodyAge")
        .html(patientDetails["age"]);
}

function resetPatientDetailsTable() {
    $(patientDetailsTable + " #patientDetailsTableBodyId")
        .html("---");

    $(patientDetailsTable + " #patientDetailsTableBodyFullName")
        .html("---");

    $(patientDetailsTable + " #patientDetailsTableBodyGender")
        .html("---");

    $(patientDetailsTable + " #patientDetailsTableBodyAge")
        .html("---");
}

function refreshModalTestDetails(testDetails) {
    $(testDetailsTable + " #testDetailsTableBodyType")
        .html(testDetails["type"]);

    var testItemNames = testDetails["testItemNames"];
    var testItemValues = testDetails["testItemValues"];

    testItemNames.forEach(function(item, index) {
        $(testDetailsTable + " #testDetailsTableHeaderItem" + index)
            .html(item);

        $(testDetailsTable + " #testDetailsTableBodyItem"  + index)
            .html(testItemValues[index]);
    }, this);
}

function resetTestDetailsTable() {
    $(testDetailsTable + " #testDetailsTableBodyType")
        .html("---");

    for(index = 0; index < 4; index++){
        $(testDetailsTable + " #testDetailsTableHeaderItem" + index)
            .html("---");

        $(testDetailsTable + " #testDetailsTableBodyItem"  + index)
            .html("---");
    };
}

function refreshModalRequestDetails(requestDetails) {
    $("#requestDetailsTableBodyDoctor")
        .html(requestDetails["doctor"]);

    $("#requestDetailsTableBodyComment")
        .html(requestDetails["comment"]);
}

function setRequestStatusBadge(requestStatus) {
    switch(requestStatus) {
        case "Pending":
            $("#requestStatusBadge")
                .addClass("badge badge-warning");
            break;

        case "Approved":
            $("#requestStatusBadge")
                .addClass("badge badge-success");
            break;

        case "Rejected":
            $("#requestStatusBadge")
                .addClass("badge badge-danger");
            break;

        case "Edited":
            $("#requestStatusBadge")
                .addClass("badge badge-info");
            break;
    }

    $("#requestStatusBadge")
        .html(requestStatus);
}

function resetRequestDetailsTable() {
    $("#requestDetailsTableBodyDoctor").html("---");

    $("#requestDetailsTableBodyComment").html("---");
}

function resetRequestStatusBadge(requestStatus) {
    $("#requestStatusBadge").html("").attr("class");
        
}

$("#launchModalButton").on('click', function(event) {
    var target = $(".modal-header")[0];

    modalLoadingBarTimeout = setTimeout(function() {
        stopModalLoadingBar();
        refreshModalRequestDetailsTable();
    }, 2000);

    $("#myModal").modal();
});


});