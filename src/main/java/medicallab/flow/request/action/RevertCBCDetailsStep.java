package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestCBC;
import medicallab.flow.request.RequestDetails;

@Component
public class RevertCBCDetailsStep extends AbstractAction {
	
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestCBC requestCBC = currentScope.get("requestDoctor", RequestCBC.class);
		
		requestCBC.setWcb(oldRequsetDetails.getRequestCBC().getWcb());
		requestCBC.setHgb(oldRequsetDetails.getRequestCBC().getHgb());
		requestCBC.setMcv(oldRequsetDetails.getRequestCBC().getMcv());
		requestCBC.setMch(oldRequsetDetails.getRequestCBC().getMch());
		
		return success();
	}
}
