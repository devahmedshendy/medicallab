$(document).ready(function() {
console.log("Hello from app.js");

// General
//$(".dropdown").hover(function(event) {
//	$('.dropdown-toggle').dropdown("toggle");
//}, function(event) {
//	$('.dropdown-toggle').dropdown("toggle");
//})

$('[data-toggle="tooltip"]').tooltip()

if (window.location.href.indexOf("users") != -1) {
	$("#usersNavLink").addClass("active");
	
} else if (window.location.href.indexOf("patients") != -1) {
	$("#patientsNavLink").addClass("active");
	
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
    // console.log(tableButtons.length);
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

    let testItemNames = testDetails["testItemNames"];
    let testItemValues = testDetails["testItemValues"];

    testItemNames.forEach(function(item, index) {
        console.log()
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
            console.log('here')
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