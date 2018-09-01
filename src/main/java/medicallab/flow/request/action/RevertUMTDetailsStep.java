package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestUMT;

@Component
public class RevertUMTDetailsStep extends AbstractAction {
	
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestUMT requestUMT = currentScope.get("requestDoctor", RequestUMT.class);
		
		requestUMT.setAbc(oldRequsetDetails.getRequestUMT().getAbc());
		requestUMT.setDef(oldRequsetDetails.getRequestUMT().getDef());
		requestUMT.setGhi(oldRequsetDetails.getRequestUMT().getGhi());
		requestUMT.setJkl(oldRequsetDetails.getRequestUMT().getJkl());
		
		return success();
	}
}
