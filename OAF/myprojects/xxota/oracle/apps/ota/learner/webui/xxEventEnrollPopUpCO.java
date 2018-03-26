package xxota.oracle.apps.ota.learner.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.ota.learner.webui.EventEnrollPopUpCO;

public class xxEventEnrollPopUpCO extends EventEnrollPopUpCO {
    public xxEventEnrollPopUpCO() {
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    { 
        if(oapagecontext.getParameter("SubmitEnrollment") != null )
        { 
            throw new OAException(oapagecontext.getMessage("OTA" , "OTA_443277_CLASHING_EVENT" , null),OAException.ERROR);
        }
        super.processFormRequest(oapagecontext, oawebbean);
    }
}
