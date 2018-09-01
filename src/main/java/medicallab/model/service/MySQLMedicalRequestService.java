package medicallab.model.service;

//import medicallab.model.specification.RequestSpecifications;
import static medicallab.model.specification.MedicalRequestSpecifications.doctorFullnameLike;
import static medicallab.model.specification.MedicalRequestSpecifications.patientFullnameLike;
import static medicallab.model.specification.MedicalRequestSpecifications.patientIdLike;
import static medicallab.model.specification.MedicalRequestSpecifications.requestCodeLike;
import static medicallab.model.specification.MedicalRequestSpecifications.requestStatusIn;
import static medicallab.model.specification.MedicalRequestSpecifications.testTypeIn;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import medicallab.enums.RequestStatusEnum;
import medicallab.flow.request.RequestCBC;
import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestDetails.RequestTestType;
import medicallab.flow.request.RequestDoctor;
import medicallab.flow.request.RequestFMT;
import medicallab.flow.request.RequestPatient;
import medicallab.flow.request.RequestUMT;
import medicallab.misc.Searchable;
import medicallab.model.CBC;
import medicallab.model.DoctorReviewDetails;
import medicallab.model.FMT;
import medicallab.model.MedicalRequestView;
import medicallab.model.Patient;
import medicallab.model.Request;
import medicallab.model.UMT;
import medicallab.model.User;
import medicallab.model.repository.CBCRepository;
import medicallab.model.repository.FMTRepository;
import medicallab.model.repository.MedicalRequestRepository;
import medicallab.model.repository.MedicalRequestsViewRepository;
import medicallab.model.repository.PatientRepository;
import medicallab.model.repository.RequestRepository;
import medicallab.model.repository.UMTRepository;
import medicallab.model.repository.UserRepository;


@Transactional @Primary @Service
public class MySQLMedicalRequestService implements MedicalRequestsService {

	private final RequestRepository requestRepo;
	private final MedicalRequestRepository medicalRequestRepo;
	private final MedicalRequestsViewRepository medicalRequestViewRepo;
	private final PatientRepository patientRepo;
	private final UserRepository userRepo;
	private final CBCRepository cbcRepo;
	private final UMTRepository umtRepo;
	private final FMTRepository fmtRepo;

	public MySQLMedicalRequestService(RequestRepository requestRepo, MedicalRequestRepository medicalRequestRepo,
			MedicalRequestsViewRepository medicalRequestViewRepo, PatientRepository patientRepo,
			UserRepository userRepo, CBCRepository cbcRepo, UMTRepository umtRepo, FMTRepository fmtRepo) {

		this.requestRepo = requestRepo;
		this.medicalRequestRepo = medicalRequestRepo;
		this.medicalRequestViewRepo = medicalRequestViewRepo;
		this.patientRepo = patientRepo;
		this.userRepo = userRepo;
		this.cbcRepo = cbcRepo;
		this.umtRepo = umtRepo;
		this.fmtRepo = fmtRepo;
	}

	@Override
	public Request createRequestFromRequestDetails(RequestDetails requestDetails) {
		Patient requestPatient = patientRepo.findByPatientId(requestDetails.getRequestPatient().getPatientId());
		User requestDoctor = userRepo.findOne(requestDetails.getRequestDoctor().getId());

		Request request = null;
		if ("CBC".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			CBC cbc = new CBC();
			cbc.setWcb(requestDetails.getRequestCBC().getWcb());
			cbc.setHgb(requestDetails.getRequestCBC().getHgb());
			cbc.setMcv(requestDetails.getRequestCBC().getMcv());
			cbc.setMch(requestDetails.getRequestCBC().getMch());

			cbc.setPatient(requestPatient);
			cbc.setDoctor(requestDoctor);
			cbc.setUpdatedAt(new Date());

			cbcRepo.save(cbc);
			request = requestRepo.findByRequestCode(cbc.getRequestCode());

		} else if ("UMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			UMT umt = new UMT();
			umt.setAbc(requestDetails.getRequestUMT().getAbc());
			umt.setDef(requestDetails.getRequestUMT().getDef());
			umt.setGhi(requestDetails.getRequestUMT().getGhi());
			umt.setJkl(requestDetails.getRequestUMT().getJkl());

			umt.setPatient(requestPatient);
			umt.setDoctor(requestDoctor);
			umt.setUpdatedAt(new Date());

			umtRepo.save(umt);
			request = requestRepo.findByRequestCode(umt.getRequestCode());

		} else if ("FMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			FMT fmt = new FMT();
			fmt.setMno(requestDetails.getRequestFMT().getMno());
			fmt.setPqr(requestDetails.getRequestFMT().getPqr());
			fmt.setStu(requestDetails.getRequestFMT().getStu());
			fmt.setVwx(requestDetails.getRequestFMT().getVwx());

			fmt.setPatient(requestPatient);
			fmt.setDoctor(requestDoctor);
			fmt.setUpdatedAt(new Date());

			fmtRepo.save(fmt);
			request = requestRepo.findByRequestCode(fmt.getRequestCode());
		}

		return request;
	}

	@Override
	public void create(Request request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Request entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Request entity) {
		// TODO Auto-generated method stub

	}

	public Page<Request> findPagedList(Map<String, String> queryParams) throws NumberFormatException {
		Page<Request> requestsPagedList = null;

		String searchField = queryParams.get("searchField") == null ? "" : queryParams.get("searchField");
		String searchText = queryParams.get("searchText") == null ? "" : queryParams.get("searchText").trim();

		String sortField = queryParams.get("sortField");
		String sortOrder = queryParams.get("sortOrder");

		Integer page = Integer.parseInt(queryParams.get("page")) - 1;
		Integer maxResult = Integer.parseInt(queryParams.get("maxResult"));

		String testTypeListAsString = queryParams.get("testType");
		String requestStatusListAsString = queryParams.get("requestStatus");

		Sort sort = null;
		if (sortField == null && sortOrder == null) {
			sort = new Sort(Direction.DESC, "updatedAt");

		} else {
			switch (sortField) {
			case "patientFullname":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "patientFullname");
				break;

			case "patientId":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "patientId");
				break;

			case "doctorFullname":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "doctorFullname");
				break;

			case "requestCode":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "requestCode");
				break;

			case "testType":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "testType");
				break;

			case "updatedAt":
				sort = new Sort("ASC".equals(sortOrder) ? Direction.ASC : Direction.DESC, "updatedAt");
				break;
			}
		}

		Specifications<Request> requestSpecs = null;
		if ("ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
			User doctor = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

			requestSpecs = where(doctorFullnameLike(doctor.getFullname()))
					.and(requestStatusIn(requestStatusListAsString)).and(testTypeIn(testTypeListAsString));
		} else {
			requestSpecs = where(testTypeIn(testTypeListAsString)).and(requestStatusIn(requestStatusListAsString));
		}

		Pageable pageable = new PageRequest(page, maxResult, sort);
		switch (searchField) {
		case "patient_fullname":
			requestsPagedList = requestRepo.findAll(requestSpecs.and(patientFullnameLike(searchText)), pageable);
			break;

		case "patient_id":
			requestsPagedList = requestRepo.findAll(requestSpecs.and(patientIdLike(searchText)), pageable);
			break;

		case "doctor_fullname":
			if (!"ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
				requestsPagedList = requestRepo.findAll(requestSpecs.and(doctorFullnameLike(searchText)), pageable);
				break;
			}

		case "request_code":
			requestsPagedList = requestRepo.findAll(requestSpecs.and(requestCodeLike(searchText)), pageable);
			break;

		default:
			if ("ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
				requestsPagedList = requestRepo.findAll(requestSpecs, pageable);

			} else {
				requestsPagedList = requestRepo.findAll(pageable);
			}
			break;
		}

		return requestsPagedList;
	}

	@Override
	public Page<MedicalRequestView> findAll(Pageable pageable) {
		return medicalRequestViewRepo.findAll(pageable);
	}

	@Override
	public Page<MedicalRequestView> filter(Pageable pageable, Searchable searchable) {
		// String testTypeListAsString = queryParams.get("testType");
		// String requestStatusListAsString = queryParams.get("requestStatus");

		// Specifications<Request> requestSpecs = null;
		// if ("ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
		// User doctor =
		// userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		//
		// requestSpecs = where(doctorFullnameLike(doctor.getFullname()))
		// .and(requestStatusIn(requestStatusListAsString))
		// .and(testTypeIn(testTypeListAsString));
		// } else {
		// requestSpecs = where(testTypeIn(testTypeListAsString))
		// .and(requestStatusIn(requestStatusListAsString));
		// }
		//
		// switch(searchField) {
		// case "patient_fullname":
		// requestsPagedList =
		// requestRepo.findAll(requestSpecs.and(patientFullnameLike(searchText)),
		// pageable);
		// break;
		//
		// case "patient_id":
		// requestsPagedList =
		// requestRepo.findAll(requestSpecs.and(patientIdLike(searchText)), pageable);
		// break;
		//
		// case "doctor_fullname":
		// if (!"ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
		// requestsPagedList =
		// requestRepo.findAll(requestSpecs.and(doctorFullnameLike(searchText)),
		// pageable);
		// break;
		// }
		//
		// case "request_code":
		// requestsPagedList =
		// requestRepo.findAll(requestSpecs.and(requestCodeLike(searchText)), pageable);
		// break;
		//
		// default:
		// if ("ROLE_DOCTOR".equals(queryParams.get("userRole"))) {
		// requestsPagedList = requestRepo.findAll(requestSpecs, pageable);
		//
		// } else {
		// requestsPagedList = requestRepo.findAll(pageable);
		// }
		// break;
		// }

		return medicalRequestViewRepo.findAll(pageable);
	}

	@Override
	public Request findByRequestCode(String requestCode) {
		return requestRepo.findByRequestCode(requestCode);
	}

	@Override
	public void populateFormIntoObject(Object obj, Request entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateFormFromObject(Object obj, Request entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> findSearchFieldList() {
		Map<String, String> searchFieldList = new LinkedHashMap<>();
		searchFieldList.put("patient_fullname", "Patient Full Name");
		searchFieldList.put("patient_id", "Patient Personal Id");
		searchFieldList.put("doctor_fullname", "Doctor Full Name");
		searchFieldList.put("request_code", "Request Code");
		return searchFieldList;
	}

	@Override
	public void createRequestDetails(RequestDetails requestDetails) {
		Patient requestPatient = patientRepo.findByPatientId(requestDetails.getRequestPatient().getPatientId());
		User requestDoctor = userRepo.findOne(requestDetails.getRequestDoctor().getId());

		if ("CBC".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			CBC cbc = new CBC();
			cbc.setWcb(requestDetails.getRequestCBC().getWcb());
			cbc.setHgb(requestDetails.getRequestCBC().getHgb());
			cbc.setMcv(requestDetails.getRequestCBC().getMcv());
			cbc.setMch(requestDetails.getRequestCBC().getMch());

			cbc.setPatient(requestPatient);
			cbc.setDoctor(requestDoctor);
			cbc.setUpdatedAt(new Date());

			requestDetails.setRequestCode(cbc.getRequestCode());

			cbcRepo.save(cbc);

		} else if ("UMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			UMT umt = new UMT();
			umt.setAbc(requestDetails.getRequestUMT().getAbc());
			umt.setDef(requestDetails.getRequestUMT().getDef());
			umt.setGhi(requestDetails.getRequestUMT().getGhi());
			umt.setJkl(requestDetails.getRequestUMT().getJkl());

			umt.setPatient(requestPatient);
			umt.setDoctor(requestDoctor);
			umt.setUpdatedAt(new Date());

			requestDetails.setRequestCode(umt.getRequestCode());

			umtRepo.save(umt);

		} else if ("FMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			FMT fmt = new FMT();
			fmt.setMno(requestDetails.getRequestFMT().getMno());
			fmt.setPqr(requestDetails.getRequestFMT().getPqr());
			fmt.setStu(requestDetails.getRequestFMT().getStu());
			fmt.setVwx(requestDetails.getRequestFMT().getVwx());

			fmt.setPatient(requestPatient);
			fmt.setDoctor(requestDoctor);
			fmt.setUpdatedAt(new Date());

			requestDetails.setRequestCode(fmt.getRequestCode());

			fmtRepo.save(fmt);
		}
	}

	@Override
	public RequestDetails findRequestDetails(String requestCode) {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setRequestCode(requestCode);

		Request request = findByRequestCode(requestCode);
		if ("CBC".equals(request.getTestType())) {
			CBC cbc = cbcRepo.findByRequestCode(request.getRequestCode());

			requestDetails.setRequestStatus(cbc.getRequestStatus());

			RequestCBC requestCBC = new RequestCBC();
			requestCBC.setWcb(cbc.getWcb());
			requestCBC.setHgb(cbc.getHgb());
			requestCBC.setMch(cbc.getMch());
			requestCBC.setMcv(cbc.getMcv());

			requestDetails.setRequestTest(requestCBC);

			RequestPatient requestPatient = new RequestPatient();
			requestPatient.setFullname(cbc.getPatient().getFullname());
			requestPatient.setPatientId(cbc.getPatient().getPatientId());
			requestPatient.setPhone(cbc.getPatient().getPhone());
			requestPatient.setAge(cbc.getPatient().getAge());
			requestPatient.setGender(cbc.getPatient().getGender());

			requestDetails.setRequestPatient(requestPatient);

			RequestDoctor requestDoctor = new RequestDoctor();
			requestDoctor.setId(cbc.getDoctor().getId());
			requestDoctor.setFullname(cbc.getDoctor().getFullname());
			requestDoctor.setComment(cbc.getDoctorComment());

			requestDetails.setRequestDoctor(requestDoctor);

		} else if ("UMT".equals(request.getTestType())) {
			UMT umt = umtRepo.findByRequestCode(request.getRequestCode());

			requestDetails.setRequestStatus(umt.getRequestStatus());

			RequestUMT requestUMT = new RequestUMT();
			requestUMT.setAbc(umt.getAbc());
			requestUMT.setDef(umt.getDef());
			requestUMT.setGhi(umt.getGhi());
			requestUMT.setJkl(umt.getJkl());

			requestDetails.setRequestTest(requestUMT);

			RequestPatient requestPatient = new RequestPatient();
			requestPatient.setFullname(umt.getPatient().getFullname());
			requestPatient.setPatientId(umt.getPatient().getPatientId());
			requestPatient.setPhone(umt.getPatient().getPhone());
			requestPatient.setAge(umt.getPatient().getAge());
			requestPatient.setGender(umt.getPatient().getGender());

			requestDetails.setRequestPatient(requestPatient);

			RequestDoctor requestDoctor = new RequestDoctor();
			requestDoctor.setId(umt.getDoctor().getId());
			requestDoctor.setFullname(umt.getDoctor().getFullname());
			requestDoctor.setComment(umt.getDoctorComment());

			requestDetails.setRequestDoctor(requestDoctor);

		} else if ("FMT".equals(request.getTestType())) {
			FMT fmt = fmtRepo.findByRequestCode(request.getRequestCode());

			requestDetails.setRequestStatus(fmt.getRequestStatus());

			RequestFMT requestFMT = new RequestFMT();
			requestFMT.setMno(fmt.getMno());
			requestFMT.setPqr(fmt.getPqr());
			requestFMT.setStu(fmt.getStu());
			requestFMT.setVwx(fmt.getVwx());

			requestDetails.setRequestTest(requestFMT);

			RequestPatient requestPatient = new RequestPatient();
			requestPatient.setFullname(fmt.getPatient().getFullname());
			requestPatient.setPatientId(fmt.getPatient().getPatientId());
			requestPatient.setPhone(fmt.getPatient().getPhone());
			requestPatient.setAge(fmt.getPatient().getAge());
			requestPatient.setGender(fmt.getPatient().getGender());

			requestDetails.setRequestPatient(requestPatient);

			RequestDoctor requestDoctor = new RequestDoctor();
			requestDoctor.setId(fmt.getDoctor().getId());
			requestDoctor.setFullname(fmt.getDoctor().getFullname());
			requestDoctor.setComment(fmt.getDoctorComment());

			requestDetails.setRequestDoctor(requestDoctor);
		}

		RequestTestType requestTestType = new RequestTestType();
		requestTestType.setSelectedTestType(request.getTestType());
		requestDetails.setRequestTestType(requestTestType);

		return requestDetails;
	}

	@Override
	public void deleteRequest(Request request) {
		String requestCode = request.getRequestCode();

		if ("CBC".equals(request.getTestType())) {
			cbcRepo.deleteByRequestCode(requestCode);

		} else if ("UMT".equals(request.getTestType())) {
			umtRepo.deleteByRequestCode(requestCode);

		} else if ("FMT".equals(request.getTestType())) {
			fmtRepo.deleteByRequestCode(requestCode);

		}
	}

	@Override
	public void updateRequestDetails(RequestDetails requestDetails) {
		Patient requestPatient = patientRepo.findByPatientId(requestDetails.getRequestPatient().getPatientId());
		User requestDoctor = userRepo.findOne(requestDetails.getRequestDoctor().getId());

		if ("CBC".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			// CBC cbc = cbcRepo.findByRequestCode(requestDetails.getRequestCode());
			CBC cbc = new CBC();
			cbc.setWcb(requestDetails.getRequestCBC().getWcb());
			cbc.setHgb(requestDetails.getRequestCBC().getHgb());
			cbc.setMcv(requestDetails.getRequestCBC().getMcv());
			cbc.setMch(requestDetails.getRequestCBC().getMch());

			if (RequestStatusEnum.APPROVED.getValue().toUpperCase().equals(cbc.getRequestStatus())) {
				cbc.setRequestStatus(RequestStatusEnum.RETURNED.getValue().toUpperCase());
				cbc.setDoctorComment("");
			}

			cbc.setPatient(requestPatient);
			cbc.setDoctor(requestDoctor);
			cbc.setUpdatedAt(new Date());

			cbcRepo.save(cbc);

		} else if ("UMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			// UMT umt = umtRepo.findByRequestCode(requestDetails.getRequestCode());
			UMT umt = new UMT();
			umt.setAbc(requestDetails.getRequestUMT().getAbc());
			umt.setDef(requestDetails.getRequestUMT().getDef());
			umt.setGhi(requestDetails.getRequestUMT().getGhi());
			umt.setJkl(requestDetails.getRequestUMT().getJkl());

			if (RequestStatusEnum.APPROVED.getValue().toUpperCase().equals(umt.getRequestStatus())) {
				umt.setRequestStatus(RequestStatusEnum.RETURNED.getValue().toUpperCase());
				umt.setDoctorComment("");
			}
			umt.setPatient(requestPatient);
			umt.setDoctor(requestDoctor);
			umt.setUpdatedAt(new Date());

			umtRepo.save(umt);

		} else if ("FMT".equals(requestDetails.getRequestTestType().getSelectedTestType())) {
			// FMT fmt = fmtRepo.findByRequestCode(requestDetails.getRequestCode());
			FMT fmt = new FMT();
			fmt.setMno(requestDetails.getRequestFMT().getMno());
			fmt.setPqr(requestDetails.getRequestFMT().getPqr());
			fmt.setStu(requestDetails.getRequestFMT().getStu());
			fmt.setVwx(requestDetails.getRequestFMT().getVwx());

			fmt.setPatient(requestPatient);
			fmt.setDoctor(requestDoctor);
			fmt.setUpdatedAt(new Date());

			if (RequestStatusEnum.APPROVED.getValue().toUpperCase().equals(fmt.getRequestStatus())) {
				fmt.setRequestStatus(RequestStatusEnum.RETURNED.getValue().toUpperCase());
				fmt.setDoctorComment("");
			}

			fmtRepo.save(fmt);
		}
	}

	@Override
	public DoctorReviewDetails updateDoctorReview(DoctorReviewDetails doctorReviewDetails) {
		if ("CBC".equals(doctorReviewDetails.getTestType())) {
			CBC cbc = cbcRepo.findByRequestCode(doctorReviewDetails.getRequestCode());

			cbc.setRequestStatus(
					RequestStatusEnum.valueOf(doctorReviewDetails.getRequestStatus()).getValue().toUpperCase());
			cbc.setDoctorComment(doctorReviewDetails.getDoctorComment());
			cbc.setUpdatedAt(new Date());

			cbcRepo.save(cbc);

		} else if ("UMT".equals(doctorReviewDetails.getTestType())) {
			UMT umt = umtRepo.findByRequestCode(doctorReviewDetails.getRequestCode());

			umt.setRequestStatus(
					RequestStatusEnum.valueOf(doctorReviewDetails.getRequestStatus()).getValue().toUpperCase());
			umt.setDoctorComment(doctorReviewDetails.getDoctorComment());
			umt.setUpdatedAt(new Date());

			umtRepo.save(umt);

		} else if ("FMT".equals(doctorReviewDetails.getTestType())) {
			FMT fmt = fmtRepo.findByRequestCode(doctorReviewDetails.getRequestCode());

			fmt.setRequestStatus(
					RequestStatusEnum.valueOf(doctorReviewDetails.getRequestStatus()).getValue().toUpperCase());
			fmt.setDoctorComment(doctorReviewDetails.getDoctorComment());
			fmt.setUpdatedAt(new Date());

			fmtRepo.save(fmt);
		}
		return null;
	}

	@Override
	public Page<MedicalRequestView> findAllByDoctorUsername(String doctorUsername, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MedicalRequestView> filterByDoctorUsername(String doctorUsername, Pageable pageable2,
			Searchable searchable) {
		User doctor = userRepo.findByUsername(doctorUsername);

		// requestSpecs = where(doctorFullnameLike(doctor.getFullname()))
		// .and(requestStatusIn(requestStatusListAsString))
		// .and(testTypeIn(testTypeListAsString));

		return null;
	}
}
