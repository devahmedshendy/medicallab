package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestDetails.RequestTestType;

@Component
public class RevertTestTypeStep extends AbstractAction {
	
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestTestType requestTestType = currentScope.get("requestDoctor", RequestTestType.class);
		
		requestTestType.setSelectedTestType(oldRequsetDetails.getRequestTestType().getSelectedTestType());
		
		return success();
	}
}
