package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.webui.OverviewPageCO;

public class xxOverviewPageCO extends OverviewPageCO {
    public xxOverviewPageCO() {
    }
    
    xxCommon vxxCommon ; 
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);

        vxxCommon = new xxCommon () ; 
        
        vxxCommon.AppraiseprocessRequest(oapagecontext,oawebbean,"ProficiencyLevel","PerformanceLevel");
        
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.getParameter("Save") != null || oapagecontext.getParameter("HrSaveForLater") != null||oapagecontext.getParameter("Transfer") != null||oapagecontext.getParameter("TransferToMA") != null||oapagecontext.getParameter("Submit") != null||oapagecontext.getParameter("SelfComplete") != null )
        {
            vxxCommon.ValidateAppraiserProfAndPerfLevels (oapagecontext,oawebbean);
        }
        else 
        {
            vxxCommon.AppraiseprocessFormRequest(oapagecontext,oawebbean);
        }
        super.processFormRequest(oapagecontext, oawebbean);
    }
    
}
