$(document).ready(function() {
	console.log("Hello from app.js");
	console.log("Hello Production");
	console.log("Hello Production");

	/*
	 * General Configuration
	 */
	
    var originUrl 	= window.location.origin;
    var currentUrl 	= window.location.href;
	qwest.base = originUrl;

	$('[data-toggle="tooltip"]').tooltip()
	
	/*
	 * Login Configuration
	 */
	if (currentUrl.substr(originUrl.length).startsWith("/login")) {
		console.log("/login page");
		
		// Handle "Getting Patient's Medical Form" from login page
		$("#getMedicalProfileForm").submit(function (event) {
			event.preventDefault();
			
			var $personalIdInput 		= $("input[name=personalId]");
			var patientPersonalId 		= $personalIdInput.val();
			var $findPatientAlert 		= $("#findPatientAlert");
			
			if (patientPersonalId) {
				var getPatientAPI 			= window.location.origin;
				var getPatientProfileUri 	= window.location.origin + "/patients/medical-profile/" + patientPersonalId

				$personalIdInput.removeClass("is-invalid");
				
				qwest
				.get("/patients/api/" + patientPersonalId)
				.then(function(xhr, response) {
					currentUrl = getPatientProfileUri
					
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
		});
		
	
	} else if (currentUrl.indexOf("users") != -1) {
		console.log("/users page");
		
		$("#usersNavLink").addClass("active");
		
		$(document).on("click", "#deleteUserLink", (event) => {
			var $deleteUserLink = $(event.target).parent("#deleteUserLink");
			var username = $deleteUserLink.data("username");
			
			var deleteUserConfirmation = confirm("Deleting user '" +  username + "', Are you sure?");
			if (deleteUserConfirmation) {
				$deleteUserLink.parent("#deleteUserForm").submit();
			}
		});
		
		$(document).on("click", "#disableUserLink", (event) => {
			var $disableUserLink = $(event.target).parent("#disableUserLink");
			var username = $disableUserLink.data("username");
			
			var disableUserConfirmation = confirm("Disabling user '" +  username + "', Are you sure?");
			if (disableUserConfirmation) {
				$disableUserLink.parent("#disableUserForm").submit();
			}
		});
		
		$(document).on("click", "#enableUserLink", (event) => {
			var $enableUserLink = $(event.target).parent("#enableUserLink");
			var username = $enableUserLink.data("username");
	
			var enableUserConfirmation = confirm("Enabling user '" +  username + "', Are you sure?");
			if (enableUserConfirmation) {
				$enableUserLink.parent("#enableUserForm").submit();
			}
		});
		
		
	} else if (currentUrl.indexOf("patients") != -1) {
		console.log("/patients page");
		
		$("#patientsNavLink").addClass("active");
		
		$(document).on("click", "#deletePatientLink", (event) => {
			var $deleteUserLink = $(event.target).parent("#deletePatientLink");
			var patientId = $deleteUserLink.data("patient-id");
			
			var deleteUserConfirmation = confirm("Deleting user '" +  patientId + "', Are you sure?");
			if (deleteUserConfirmation) {
				$deleteUserLink.parent("#deletePatientForm").submit();
			}
		});
		
	
	} else if (currentUrl.substr(originUrl.length).startsWith("/requests/new")) {
	//} else if (currentUrl.indexOf("requests") != -1 && currentUrl.indexOf("new") != -1) {
		console.log("/requests/new page");
		$("#requestsNavLink").addClass("active");
		
	//	clearPatientDetailsForm();
		
		$(document).on("click", "#findPatientButton", function(event) {
			event.preventDefault();
			
			var patientAsJsonUri;
			var patientId = $("#patientIdInput").val();
	
			if (! patientId || patientId.length !== 14) {
				$("#patientIdInput").addClass("is-invalid");
				return;
				
			} else {
				$("#patientIdInput").removeClass("is-invalid");
			}
			
			clearPatientDetailsForm();
			getPatientFromBackend(patientId);
		});
		
		/* -------------------------------------------------------------
		 * Select Doctor Step
		 */
		if ($("#doctorListForm").length > 0) {
			console.log("doctorListForm");
			
			var doctorListFormContainer = document.querySelector('#doctorListFormContainer');
			$(doctorListFormContainer).removeClass("invisible");
	
			$(doctorListFormContainer).perfectScrollbar({
				wheelSpeed: 2,
				wheelPropagation: true,
				minScrollbarLength: 20
			});
			
	//		getUri("/uri?arg1=apiDoctorList", getDoctorListFromBackend)
		}
	
		$(document).keyup("#searchInput", function(event) {
			var searchText = $("#searchInput").val();
			
			if (searchText.length === 0) {
				$(".request-flow-doctor-radio").removeClass("d-none");
			
			} else {
				$(".request-flow-doctor-radio").removeClass("d-none");
				
				$(".request-flow-doctor-radio").each(function(index, element) {
					var doctorFullname = $(element).find(".doctor-fullname").text().toLowerCase();
					
					if (! doctorFullname.startsWith(searchText.toLowerCase())) {
						$(element).addClass("d-none");
					}
				})
			}
		});
		
		$("#doctorListRadios").change(function(event) {
			console.log("Change");
		});
		
		function getUri(url, func) {
			console.log(url)
			qwest
			.get(url)
			.then(function(xhr, uri) {
				func(uri);
			})
			.catch(function(e, xhr, response) {
				console.error(e);
			})
		}
		
		function getDoctorListFromBackend(uri) {
			disableSearchInput();
			showSpinnerLoader();
			qwest
			.get(uri)
			.then(function(xhr, doctorList) {
				setTimeout(function() {
					hideSpinnerLoader();
					renderDoctorList(doctorList);
					enableSearchInput();
				}, 1000)
			})
			.catch(function(e, xhr, response) {
				if (xhr.status === 404) {
					setTimeout(function() {
						alert("No doctors in the system");
					}, 1000);
					
				} else {
					console.log(e);
				}
			});
		}
		
		function renderDoctorList(doctorList) {
			for (index in doctorList) {
				var radioElementCloned = $("#doctorFullnameRadioExample").clone(true);
				radioElementCloned.addClass("doctor-fullname-radio");
				radioElementCloned.prop("id", "doctorRadio" + doctorList[index].id);
				$(radioElementCloned).find("input").prop("id", doctorList[index].id);
				$(radioElementCloned).find("input").prop("value", doctorList[index].id);
				$(radioElementCloned).find(".custom-control-description").text(doctorList[index].fullname);
				$(radioElementCloned).removeClass("d-none");
				$(radioElementCloned).appendTo("#doctorListRadios");
			}
		}
		
		function disableSearchInput(inputId) {
			$("#searchInput").prop("disabled", true);
		}
		
		function enableSearchInput() {
			$("#searchInput").prop("disabled", false);
			$("#searchInput").focus();
		}
		
		function getPatientFromBackend(patientId) {
			showSpinnerLoader();
			
			qwest
			.get("/uri?arg1=patientAsJson&arg2=" + patientId)
			.then(function(xhr, uri) {
				patientAsJsonUri = uri;
				
				qwest
				.get(patientAsJsonUri)
				.then(function(xhr, patient) {
					setTimeout(function() {
						hideSpinnerLoader();
						fillPatientDetailsForm(patient.fullname, patient.patientId, patient.phone, patient.age, patient.gender);
					}, 1000);
					
				})
				.catch(function(e, xhr, patient) {
					if (xhr.status === 404) {
						setTimeout(function() {
							hideSpinnerLoader();
							alert("No such patient");
						}, 1000);
						
					} else {
						console.log("HERERERER");
						hideSpinnerLoader();
						console.error(e);
					}
			     });
				
			})
			.catch(function(e, xhr, uri) {
				hideSpinnerLoader();
		    		console.error(e);
		    });
		}
		
		function showSpinnerLoader() {
			$("#loadingSpinner").removeClass("invisible");
		}
		
		function hideSpinnerLoader() {
			$("#loadingSpinner").addClass("invisible");
		}
		
		function enableButton(button) {
			$(button).attr("disabled", false);
			
		}
		
		function disableButton(button) {
			$(button).attr("disabled", true);
		}
	
		
		function fillPatientDetailsForm(patientFullname, patientId, patientPhone, patientAge, patientGender) {
			var formId = "#patientDetailsForm";
			var formInputs = $(formId).find("input[type=text]");
			
			$(formId).find("#patientFullname").val(patientFullname);
			$(formId).find("#patientId").prop("value", patientId);
			$(formId).find("#patientPhone").val(patientPhone);
			$(formId).find("#patientAge").val(patientAge);
			$(formId).find("#patientGender").val(patientGender);
		}
		
		function clearPatientDetailsForm() {
			formId = "#patientDetailsForm";
			$(formId).find("input[type=text]").val("");
	//		document.getElementById("patientDetailsForm").reset();
		}
		
	} else if (currentUrl.substr(originUrl.length).startsWith("/requests/edit")) {
		console.log("/requests/edit page");
		$("#requestsNavLink").addClass("active");
		
	//		clearPatientDetailsForm();
		
		$(document).on("click", "#findPatientButton", function(event) {
			event.preventDefault();
			
			var patientAsJsonUri;
			var patientId = $("#patientIdInput").val();
	
			if (! patientId || patientId.length !== 14) {
				$("#patientIdInput").addClass("is-invalid");
				return;
				
			} else {
				$("#patientIdInput").removeClass("is-invalid");
			}
			
			clearPatientDetailsForm();
			getPatientFromBackend(patientId);
		});
		
		/* -------------------------------------------------------------
		 * Select Doctor Step
		 */
		if ($("#doctorListForm").length > 0) {
			console.log("doctorListForm");
			
			var doctorListFormContainer = document.querySelector('#doctorListFormContainer');
			$(doctorListFormContainer).removeClass("invisible");
	
			$(doctorListFormContainer).perfectScrollbar({
				wheelSpeed: 2,
				wheelPropagation: true,
				minScrollbarLength: 20
			});
			
	//			getUri("/uri?arg1=apiDoctorList", getDoctorListFromBackend)
		}
	
		$(document).keyup("#searchInput", function(event) {
			var searchText = $("#searchInput").val();
			
			if (searchText.length === 0) {
				$(".request-flow-doctor-radio").removeClass("d-none");
			
			} else {
				$(".request-flow-doctor-radio").removeClass("d-none");
				
				$(".request-flow-doctor-radio").each(function(index, element) {
					var doctorFullname = $(element).find(".doctor-fullname").text().toLowerCase();
					
					if (! doctorFullname.startsWith(searchText.toLowerCase())) {
						$(element).addClass("d-none");
					}
				})
			}
		});
		
		$("#doctorListRadios").change(function(event) {
			console.log("Change");
		});
		
		function getUri(url, func) {
			console.log(url)
			qwest
			.get(url)
			.then(function(xhr, uri) {
				func(uri);
			})
			.catch(function(e, xhr, response) {
				console.error(e);
			})
		}
		
		function getDoctorListFromBackend(uri) {
			disableSearchInput();
			showSpinnerLoader();
			qwest
			.get(uri)
			.then(function(xhr, doctorList) {
				setTimeout(function() {
					hideSpinnerLoader();
					renderDoctorList(doctorList);
					enableSearchInput();
				}, 1000)
			})
			.catch(function(e, xhr, response) {
				if (xhr.status === 404) {
					setTimeout(function() {
						alert("No doctors in the system");
					}, 1000);
					
				} else {
					console.log(e);
				}
			});
		}
		
		function renderDoctorList(doctorList) {
			for (index in doctorList) {
				var radioElementCloned = $("#doctorFullnameRadioExample").clone(true);
				radioElementCloned.addClass("doctor-fullname-radio");
				radioElementCloned.prop("id", "doctorRadio" + doctorList[index].id);
				$(radioElementCloned).find("input").prop("id", doctorList[index].id);
				$(radioElementCloned).find("input").prop("value", doctorList[index].id);
				$(radioElementCloned).find(".custom-control-description").text(doctorList[index].fullname);
				$(radioElementCloned).removeClass("d-none");
				$(radioElementCloned).appendTo("#doctorListRadios");
			}
		}
		
		function disableSearchInput(inputId) {
			$("#searchInput").prop("disabled", true);
		}
		
		function enableSearchInput() {
			$("#searchInput").prop("disabled", false);
			$("#searchInput").focus();
		}
		
		function getPatientFromBackend(patientId) {
			showSpinnerLoader();
			
			qwest
			.get("/uri?arg1=patientAsJson&arg2=" + patientId)
			.then(function(xhr, uri) {
				patientAsJsonUri = uri;
				
				qwest
				.get(patientAsJsonUri)
				.then(function(xhr, patient) {
					setTimeout(function() {
						hideSpinnerLoader();
						fillPatientDetailsForm(patient.fullname, patient.patientId, patient.phone, patient.age, patient.gender);
					}, 1000);
					
				})
				.catch(function(e, xhr, patient) {
					if (xhr.status === 404) {
						setTimeout(function() {
							hideSpinnerLoader();
							alert("No such patient");
						}, 1000);
						
					} else {
						console.log("HERERERER");
						hideSpinnerLoader();
						console.error(e);
					}
			     });
				
			})
			.catch(function(e, xhr, uri) {
				hideSpinnerLoader();
		    		console.error(e);
		    });
		}
		
		function showSpinnerLoader() {
			$("#loadingSpinner").removeClass("invisible");
		}
		
		function hideSpinnerLoader() {
			$("#loadingSpinner").addClass("invisible");
		}
		
		function enableButton(button) {
			$(button).attr("disabled", false);
			
		}
		
		function disableButton(button) {
			$(button).attr("disabled", true);
		}
	
		
		function fillPatientDetailsForm(patientFullname, patientId, patientPhone, patientAge, patientGender) {
			var formId = "#patientDetailsForm";
			var formInputs = $(formId).find("input[type=text]");
			
			$(formId).find("#patientFullname").val(patientFullname);
			$(formId).find("#patientId").prop("value", patientId);
			$(formId).find("#patientPhone").val(patientPhone);
			$(formId).find("#patientAge").val(patientAge);
			$(formId).find("#patientGender").val(patientGender);
		}
		
		function clearPatientDetailsForm() {
			formId = "#patientDetailsForm";
			$(formId).find("input[type=text]").val("");
	//			document.getElementById("patientDetailsForm").reset();
		}
	
	
	// Load Javascript related to /requests page
	} else if (currentUrl.substr(originUrl.length).startsWith("/requests")) {
		console.log("/requests page");
		
		$("#requestsNavLink").addClass("active");
		
		
		
		/*--- "/requests" Event Handling ---*/
		
		// Perform Delete/Edit/Show for a request
		// --------------------------------------

		// Officer attempts to delete a request
		$(document).on("click", ".deleteRequestLink > i", function(event) {
			var requestCode = $(event.target).parent().data("request-code");
			var $deleteRequestForm = $("#deleteRequestForm")
			var deleteRequestFormActionUri = $deleteRequestForm.data("form-action").split("requestCode").join(requestCode);
			
			$deleteRequestForm.attr("action", deleteRequestFormActionUri);
			$("#confirmCancelAddRequestLabel").find("span").text(requestCode);
		});
		
		// User attempts to show request details
		$(document).on("click", ".showRequestLink > i", function(event) {
			var requestCode = $(event.target).parent().data("request-code");
			var requestDetailsUri = $("#requestDetailsModal").data("request-details-api").split("requestCode").join(requestCode);
			
			setRequestDetailsModalContentToDefaultContent();
			showRequestDetailsModal();
			
			qwest
			.get(requestDetailsUri)
			.then(function(xhr, requestDetails) {
				setTimeout(function() {
					console.log(requestDetails);
					updateRequestDetailsActualModalContent(requestDetails);
					setRequestDetailsModalContentToActualContent();

				}, 1000);
			})
			.catch(function(e, xhr, response) {
				if (xhr.status === 404) {
					alert("No such request");
					
				} else {
					console.error(e);
				}
			});
		});
		
		// Officer attempts to edit the request
		$(document).on("click", "#modalActionButtonsForOfficer button#editRequest", function(event) {
			var requestCode = $("#actualModalTitle span#requestCode").text();
			$("a.editRequestLink[data-request-code=" + requestCode + "]")[0].click();
		});
		
		
		
		// Handling Doctor Actions for Approving/Rejecting A Request
		// ---------------------------------------------------------
		
		// Doctor attempts to edit DoctorReviewTable
		$(document).on("click", "#modalActionButtonsForDoctor #editReview", function(event) {
			switchToEditDoctorReviewState();
		});

		// Doctor attempts to revert a review edit
		$(document).on("click", "#modalActionButtonsForDoctor #revertReviewEdit", function(event) {
			revertEditDoctorReviewState();
		});
		
		// Doctor attempts to confirm approval
		$(document).on("click", "#modalActionButtonsForDoctor #approveRequest", function(event) {
			switchToConfirmApprovalState();
		});
		
		// Doctor attempts to cancel confirming approval
		$(document).on("click", "#modalActionButtonsForDoctor #cancelApproval", function(event) {
			switchToEditDoctorReviewState();
		});
		
		// Doctor attempts to confirm rejection
		$(document).on("click", "#modalActionButtonsForDoctor #rejectRequest", function(event) {
			switchToConfirmRejectionState();
		});
		
		// Doctor attempts to cancel confirming rejection
		$(document).on("click", "#modalActionButtonsForDoctor #cancelRejection", function(event) {
			switchToEditDoctorReviewState();
		});
		
		// RequestDetailsModal is closed
		$(document).on("hidden.bs.modal", "#requestDetailsModal", function(event) {
			hideAllTestDetailsTablesOfActualModalBody();
			hideAllModalActionButtonsOfActualModalFooter();
			resetDoctorReviewTable();
		});
		
		// Doctor confirmed request approval
		$(document).on("click", "#modalActionButtonsForDoctor button#confirmApproval", function(event) {
			var doctorReviewDetails = getRequestDoctorReviewDetails();
			doctorReviewDetails["requestStatus"] = "APPROVED";
			
			postDoctorReviewDetails(doctorReviewDetails);
		});
		
		// Doctor confirmed request approval
		$(document).on("click", "#modalActionButtonsForDoctor button#confirmRejection", function(event) {
			var doctorReviewDetails = getRequestDoctorReviewDetails();
			doctorReviewDetails["requestStatus"] = "RETURNED";
			
			postDoctorReviewDetails(doctorReviewDetails);
		});
		
		
		
		/*--- "/requests/" Common Functions ---*/
		
		function switchToEditDoctorReviewState() {
			$("#doctorReviewTable #doctorComment > textarea").prop("readonly", false);
			
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#approveOrRejectActionBlock").removeClass("d-none");
		}
		
		function switchToConfirmApprovalState() {
			$("#doctorReviewTable #doctorComment > textarea").prop("readonly", true);
			
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#confirmApprovalActionBlock").removeClass("d-none");
		}
		
		function switchToConfirmRejectionState() {
			$("#doctorReviewTable #doctorComment > textarea").prop("readonly", true);
			
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#confirmRejectionActionBlock").removeClass("d-none");
		}
		
		function switchToDoctorReviewDefaultState() {
			var $doctorCommentTextarea = $("#doctorReviewTable #doctorComment > textarea");
			$doctorCommentTextarea.prop("readonly", true);
			
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#editReviewActionBlock").removeClass("d-none");
		}
		
		function revertEditDoctorReviewState() {
			var $doctorCommentTextarea = $("#doctorReviewTable #doctorComment > textarea");
			$doctorCommentTextarea.prop("readonly", true);
			$doctorCommentTextarea.val($doctorCommentTextarea.data("original-comment"));
			
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#editReviewActionBlock").removeClass("d-none");
		}
		
		function switchToApproveActionBlock() {
			var $modalActionButtons = $(".modalActionButtons");
			$modalActionButtons.find(".actionBlock").addClass("d-none");
			$modalActionButtons.find("#confirmApprovalActionBlock").removeClass("d-none");
		}
		
		function resetDoctorReviewTable() {
			$doctorReviewTable = $("#doctorReviewTable");
			$doctorReviewTable.find("#doctorFullname").text("");
			$doctorReviewTable.find("#doctorComment > textarea").text("");
			$doctorReviewTable.find("#doctorComment > textarea").prop("readonly", true);
		}
		
		function getRequestDoctorReviewDetails() {
			var requestCode = $("#actualModalTitle #requestCode").text();
			var requestStatus = $("#medicalRequestStatusIcon i").not(".d-none"); // $("#medicalRequestStatusIcon i").not(".d-none");
			var testType = $("#actualModalBody .testDetailsTable").not(".d-none").find("thead>tr>th").first().text();
			var doctorFullname = $("#actualModalBody #doctorReviewTable #doctorFullname").text();
			var doctorComment = $("#actualModalBody #doctorReviewTable #doctorComment>textarea").val();
			
			return {
				"requestCode": requestCode,
				"requestStatus": requestStatus,
				"testType": testType,
				"doctorFullname": doctorFullname,
				"doctorComment": doctorComment
			};
		}
		
		function postDoctorReviewDetails(doctorReviewDetails) {
			$confirmApprovalButton = $(event.target);
			
			$confirmApprovalButton.closest("#confirmApprovalActionBlock").find("button").prop("disabled", true);
			$confirmApprovalButton.addClass("button-spinner");
			
			
			
			qwest.setDefaultDataType('json');
			
			qwest.get("/uri?arg1=apiPostDoctorReview&arg2=")
			.then(function(xhr, postApprovalUri) {
				qwest
				.post(postApprovalUri, doctorReviewDetails, {
					headers: {
						'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
					}
				})
				.then(function(xhr, uri) {
						qwest
						.get("/uri?arg1=requests")
						.then(function(xhr, uri) {
							setTimeout(function() {
								$confirmApprovalButton.closest("#confirmApprovalActionBlock").find("button").prop("disabled", false);
								$confirmApprovalButton.removeClass("button-spinner");
								
								switchToDoctorReviewDefaultState();
								
								if (doctorReviewDetails["requestStatus"] === "APPROVED") {
									window.location.href = uri + "?success=Request '" + doctorReviewDetails["requestCode"] + "' has been approved successfully";
									
								} else if (doctorReviewDetails["requestStatus"] === "RETURNED") {
									window.location.href = uri + "?success=Request '" + doctorReviewDetails["requestCode"] + "' has been rejected successfully";
								}
								
							}, 1000);
						})
						.catch(function(e, xhr) {
							console.log(e);
						})
				})
				.catch(function(e, xhr, response) {
					// YOU HAVE TO CHECK AGAIN
					setTimeout(function() {
						$confirmApprovalButton.closest("#confirmApprovalActionBlock").find("button").prop("disabled", false);
						$confirmApprovalButton.removeClass("button-spinner");
					}, 1000);
					
					if (xhr.status === 404) {
						alert("No such patient");
					} else {
						console.log("HERERERER");
						console.error(e);
					}
			     });
				
			})
			.catch(function(e, xhr, response) {
		    		console.error(e);
		    });
		}
		
		function updateRequestDetailsActualModalContent(requestDetails) {
			var requestCode 			= requestDetails.requestCode;
			var requestStatus		= requestDetails.requestStatus;
			var requestPatient 		= requestDetails.requestPatient;
			var selectedTestType 	= requestDetails.requestTestType.selectedTestType;
			var requestTest 			= requestDetails.requestTest;
			var requestDoctor 		= requestDetails.requestDoctor;
			
			
			updateActualModalTitleWithRequestCode(requestCode, requestStatus);
			updateActualModalPatientDetailsTable(requestPatient);
			updateActualModalDoctorReviewTable(requestDoctor, requestStatus);
			updateActualModalActionButtons();
			
			switch (selectedTestType) {
				case "CBC":
					updateActualModalCBCDetailsTable(requestTest);
					break;
					
				case "UMT":
					updateActualModalUMTDetailsTable(requestTest);
					break;
					
				case "FMT":
					updateActualModalFMTDetailsTable(requestTest);
					break;
			}
		}
		
		function updateActualModalPatientDetailsTable(requestPatient) {
			var $patientDetailsTable = $("#actualModalBody").find("#patientDetailsTable");
			
			$patientDetailsTable.find("#patientFullname").text(requestPatient.fullname);
			$patientDetailsTable.find("#patientId").text(requestPatient.patientId);
			$patientDetailsTable.find("#patientPhone").text(requestPatient.phone);
			$patientDetailsTable.find("#patientAge").text(requestPatient.age + " Yrs Old");
			$patientDetailsTable.find("#patientGender").text(requestPatient.gender);
		}
		
		function updateActualModalDoctorReviewTable(requestDoctor, requestStatus) {
			var $actualDoctorReviewTable = $("#doctorReviewTable");
			
			console.log(requestDoctor);
			$actualDoctorReviewTable.find("#doctorFullname").text(requestDoctor.fullname);
			$actualDoctorReviewTable.find("#doctorComment > textarea").text(requestDoctor.comment == null ? "" : requestDoctor.comment);
			$actualDoctorReviewTable.find("#doctorComment > textarea").attr("data-original-comment", requestDoctor.comment == null ? "" : requestDoctor.comment);
			
			$("#medicalRequestStatusIcon").find("#" + requestStatus.toLowerCase() + "Icon").removeClass("d-none");
		}
		
		function updateActualModalActionButtons() {
			var $modalActionButton = $(".modalActionButtons");
			
			if ($modalActionButton.attr("id") === "modalActionButtonsForDoctor") {
				$modalActionButton.find("#editReviewActionBlock").removeClass("d-none");
			}
		}
		
		function updateActualModalCBCDetailsTable(requestTest) {
			var $cbcDetailsTable = $("#actualModalBody").find("#cbcDetailsTable");
			
			$cbcDetailsTable.removeClass("d-none");
			
			$cbcDetailsTable.find("thead > tr > th")
							.first().text("CBC");
			
			$cbcDetailsTable.find("#wcbValue").text(requestTest.wcb);
			$cbcDetailsTable.find("#hgbValue").text(requestTest.hgb);
			$cbcDetailsTable.find("#mcvValue").text(requestTest.mcv);
			$cbcDetailsTable.find("#mchValue").text(requestTest.mch);
		}
		
		function updateActualModalUMTDetailsTable(requestTest) {
			var $umtDetailsTable = $("#actualModalBody").find("#umtDetailsTable");

			$umtDetailsTable.removeClass("d-none");

			$umtDetailsTable.find("thead > tr > th")
							.first().text("UMT");
			
			$umtDetailsTable.find("#abcValue").text(requestTest.abc);
			$umtDetailsTable.find("#defValue").text(requestTest.def);
			$umtDetailsTable.find("#ghiValue").text(requestTest.ghi);
			$umtDetailsTable.find("#jklValue").text(requestTest.jkl);
		}
		
		function updateActualModalFMTDetailsTable(requestTest) {
			var $fmtDetailsTable = $("#actualModalBody").find("#fmtDetailsTable");

			$fmtDetailsTable.removeClass("d-none");

			$fmtDetailsTable.find("thead > tr > th")
							.first().text("FMT");
			
			$fmtDetailsTable.find("#mnoValue").text(requestTest.mno);
			$fmtDetailsTable.find("#pqrValue").text(requestTest.pqr);
			$fmtDetailsTable.find("#stuValue").text(requestTest.stu);
			$fmtDetailsTable.find("#vwxValue").text(requestTest.vwx);
		}
		
		
		function updateActualModalTitleWithRequestCode(requestCode, requestStatus) {
			$("#actualModalTitle").find("#requestCode").text(requestCode);
			
			$("#actualModalTitle").find("i").addClass("d-none");
		}
		
		function showRequestDetailsModal() {
			$("#requestDetailsModal").modal('show');
		}
		
		function hideAllTestDetailsTablesOfActualModalBody() {
			$("#actualModalBody").find(".testDetailsTable").addClass("d-none");
		}
		
		function hideAllModalActionButtonsOfActualModalFooter() {
			$(".modalActionButtons").find(".actionBlock").addClass("d-none");
		}
		
		function setRequestDetailsModalContentToDefaultContent() {
			$("#defaultModalContent").removeClass("d-none");
			$("#actualModalContent").addClass("d-none");
		}
		
		function setRequestDetailsModalContentToActualContent() {
			$("#defaultModalContent").addClass("d-none");
			$("#actualModalContent").removeClass("d-none");
		}
		
		function toggleEmptyModalBodyAppearence() {
			$("#emptyModalBody").toggleClass("d-none");
		}
		
		function toggleActualModalBodyAppearence() {
			$("#actualModalBody").toggleClass("d-none");
		}
		
		function toggleEmptyModalTitle() {
			$("#emptyModalTitle").toggleClass("d-none");
		}
		
		function toggleActualModalTitle() {
			$("#actualModalTitle").toggleClass("d-none");
		}
	

	// Load Javascript related to / page
	} else if (currentUrl.substr(originUrl.length).startsWith("/")) {
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
	
	function createModal(modal_id, modal_header, modal_body, modal_footer) {
		let modal =  $(`
		  <div id="${modal_id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="${modal_id}_title" aria-hidden="true">
		    <div class="modal-dialog" role="document">
		      <div class="modal-content">
		        <div class="modal-header">
		          ${modal_header}
		        </div>
		
		        <div class="modal-body">
		          ${modal_body}
		        </div>
		
		        <div class="modal-footer">
		          ${modal_footer}
		        </div>
		      </div>
		    </div>
		  </div>
		`);
		
		return modal
	}

});