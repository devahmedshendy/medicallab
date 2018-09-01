package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestPatient;

@Component
public class RevertPatientStep extends AbstractAction {

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestPatient requestPatient = currentScope.get("requestPatient", RequestPatient.class);
		
		requestPatient.setFullname(oldRequsetDetails.getRequestPatient().getFullname());
		requestPatient.setPatientId(oldRequsetDetails.getRequestPatient().getPatientId());
		requestPatient.setPhone(oldRequsetDetails.getRequestPatient().getPhone());
		requestPatient.setGender(oldRequsetDetails.getRequestPatient().getGender());
		requestPatient.setAge(oldRequsetDetails.getRequestPatient().getAge());
		
		return success();
	}
	
}
