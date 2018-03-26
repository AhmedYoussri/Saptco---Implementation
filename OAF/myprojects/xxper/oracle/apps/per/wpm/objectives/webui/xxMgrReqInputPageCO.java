package xxper.oracle.apps.per.wpm.objectives.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.wpm.objectives.webui.MgrReqInputPageCO;

public class xxMgrReqInputPageCO extends MgrReqInputPageCO {
    public xxMgrReqInputPageCO() {
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.isLoggingEnabled(1))
            oapagecontext.writeDiagnostics(this, "Entering xxMgrReqInputPageCO.processFormRequest..", 1);
            
        String strEvent = oapagecontext.getParameter("event");

        if(strEvent.equals("Transfer"))
        {
            xxCommon vxxCommon = new xxCommon() ;
            vxxCommon.ValidateWeightingPercentsForSupWorkerScorecard(oapagecontext,oawebbean);
            
        }
        
        super.processFormRequest(oapagecontext, oawebbean);
    }    
}
