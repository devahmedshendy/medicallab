package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestCBC;
import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestDoctor;
import medicallab.flow.request.RequestFMT;
import medicallab.flow.request.RequestPatient;
import medicallab.flow.request.RequestUMT;
import medicallab.flow.request.RequestDetails.RequestTestType;

@Component
public class RevertStep extends AbstractAction {

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		String stateId = context.getFlowExecutionContext().getActiveSession().getState().getId();

		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		
		if ("patientStep".equals(stateId)) {
			RequestPatient requestPatient = currentScope.get("requestPatient", RequestPatient.class);
			revertPatientStep(oldRequsetDetails, requestPatient);

		} else if ("doctorStep".equals(stateId)) {
			RequestDoctor requestDoctor = currentScope.get("requestDoctor", RequestDoctor.class);
			revertDoctorStep(oldRequsetDetails, requestDoctor);
			
		} else if ("testTypeStep".equals(stateId)) {
			RequestTestType requestTestType = currentScope.get("requestTestType", RequestTestType.class);
			revertTestTypeStep(oldRequsetDetails, requestTestType);
			
		} else if ("cbcDetailsStep".equals(stateId)) {
			RequestCBC requestCBC = currentScope.get("requestCBC", RequestCBC.class);
			revertCBCDetailsStep(oldRequsetDetails, requestCBC);
			
		} else if ("umtDetailsStep".equals(stateId)) {
			RequestUMT requestUMT = currentScope.get("requestUMT", RequestUMT.class);
			revertUMTDetailsStep(oldRequsetDetails, requestUMT);
			
		} else if ("fmtDetailsStep".equals(stateId)) {
			RequestFMT requestFMT = currentScope.get("requestFMT", RequestFMT.class);
			revertFMTDetailsStep(oldRequsetDetails, requestFMT);
		}
		
		return success();
	}
	
	private void revertPatientStep(RequestDetails oldRequsetDetails, RequestPatient requestPatient) {
		requestPatient.setFullname(oldRequsetDetails.getRequestPatient().getFullname());
		requestPatient.setPatientId(oldRequsetDetails.getRequestPatient().getPatientId());
		requestPatient.setPhone(oldRequsetDetails.getRequestPatient().getPhone());
		requestPatient.setGender(oldRequsetDetails.getRequestPatient().getGender());
		requestPatient.setAge(oldRequsetDetails.getRequestPatient().getAge());
	}
	
	private void revertDoctorStep(RequestDetails oldRequsetDetails, RequestDoctor requestDoctor) {
		requestDoctor.setId(oldRequsetDetails.getRequestDoctor().getId());
		requestDoctor.setFullname(oldRequsetDetails.getRequestDoctor().getFullname());
	}
	
	private void revertTestTypeStep(RequestDetails oldRequsetDetails, RequestTestType requestTestType) {
		requestTestType.setSelectedTestType(oldRequsetDetails.getRequestTestType().getSelectedTestType());
	}
	
	private void revertCBCDetailsStep(RequestDetails oldRequsetDetails, RequestCBC requestCBC) {
		requestCBC.setWcb(oldRequsetDetails.getRequestCBC().getWcb());
		requestCBC.setHgb(oldRequsetDetails.getRequestCBC().getHgb());
		requestCBC.setMcv(oldRequsetDetails.getRequestCBC().getMcv());
		requestCBC.setMch(oldRequsetDetails.getRequestCBC().getMch());
	}
	
	private void revertUMTDetailsStep(RequestDetails oldRequsetDetails, RequestUMT requestUMT) {
		requestUMT.setAbc(oldRequsetDetails.getRequestUMT().getAbc());
		requestUMT.setDef(oldRequsetDetails.getRequestUMT().getDef());
		requestUMT.setGhi(oldRequsetDetails.getRequestUMT().getGhi());
		requestUMT.setJkl(oldRequsetDetails.getRequestUMT().getJkl());
	}
	
	private void revertFMTDetailsStep(RequestDetails oldRequsetDetails, RequestFMT requestFMT) {
		requestFMT.setMno(oldRequsetDetails.getRequestFMT().getMno());
		requestFMT.setPqr(oldRequsetDetails.getRequestFMT().getPqr());
		requestFMT.setStu(oldRequsetDetails.getRequestFMT().getStu());
		requestFMT.setVwx(oldRequsetDetails.getRequestFMT().getVwx());
	}

}
