package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestDoctor;

@Component
public class RevertDoctorStep extends AbstractAction {

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestDoctor requestDoctor = currentScope.get("requestDoctor", RequestDoctor.class);
		
		requestDoctor.setId(oldRequsetDetails.getRequestDoctor().getId());
		requestDoctor.setFullname(oldRequsetDetails.getRequestDoctor().getFullname());
		
		return success();
	}

}
