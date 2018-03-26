package xxper.oracle.apps.per.wpm.objectives.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.wpm.objectives.webui.MgrFinObjectivesPageCO;

public class xxMgrFinObjectivesPageCO extends MgrFinObjectivesPageCO {
    public xxMgrFinObjectivesPageCO() {
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.isLoggingEnabled(1))
            oapagecontext.writeDiagnostics(this, "Entering xxMgrFinObjectivesPageCO.processFormRequest..", 1);

        String strEvent = oapagecontext.getParameter("event");

        if(strEvent.equals("Finish"))
        {
            xxCommon vxxCommon = new xxCommon() ;
            vxxCommon.ValidateWeightingPercentsForSupWorkerScorecard(oapagecontext,oawebbean);
            
        }

        super.processFormRequest(oapagecontext, oawebbean);
    }
}
