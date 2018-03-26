package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.webui.MAAppraisePageCO;

public class xxMAAppraisePageCO extends MAAppraisePageCO {
    public xxMAAppraisePageCO() {
    }

    xxCommon vxxCommon ; 
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        vxxCommon = new xxCommon () ; 
        
        vxxCommon.AppraiseprocessRequest(oapagecontext,oawebbean,"ApprProfLevel","ApprPerfLevel");
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.getParameter("Apply") != null )
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
