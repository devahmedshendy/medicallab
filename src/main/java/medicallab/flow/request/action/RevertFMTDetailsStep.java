package medicallab.flow.request.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import medicallab.flow.request.RequestDetails;
import medicallab.flow.request.RequestFMT;

@Component
public class RevertFMTDetailsStep extends AbstractAction {
	
	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		MutableAttributeMap<Object> currentScope = context.getFlowExecutionContext().getActiveSession().getScope();
		
		RequestDetails oldRequsetDetails = currentScope.get("oldRequestDetails", RequestDetails.class);
		RequestFMT requestFMT = currentScope.get("requestDoctor", RequestFMT.class);
		
		requestFMT.setMno(oldRequsetDetails.getRequestFMT().getMno());
		requestFMT.setPqr(oldRequsetDetails.getRequestFMT().getPqr());
		requestFMT.setStu(oldRequsetDetails.getRequestFMT().getStu());
		requestFMT.setVwx(oldRequsetDetails.getRequestFMT().getVwx());
		
		return success();
	}
}
