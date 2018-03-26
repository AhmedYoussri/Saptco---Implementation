package xxper.oracle.apps.per.wpm.objectives.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.wpm.objectives.webui.SetObjectivesPageCO;


public class xxSetObjectivesPageCO extends SetObjectivesPageCO {
    public xxSetObjectivesPageCO() {
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.isLoggingEnabled(1))
            oapagecontext.writeDiagnostics(this, "Entering xxSetObjectivesPageCO.processFormRequest..", 1);

        if(oapagecontext.getParameter("EmpFinish") != null || oapagecontext.getParameter("MgrTransfer") != null || oapagecontext.getParameter("MgrFinish") != null)
        {
            xxCommon vxxCommon = new xxCommon() ;
            vxxCommon.ValidateWeightingPercents(oapagecontext,oawebbean);
        }
        
        super.processFormRequest(oapagecontext, oawebbean);
    }
    
}
