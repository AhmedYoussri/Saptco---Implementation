package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.webui.ReviewPageCO;

public class xxReviewPageCO extends ReviewPageCO {
    public xxReviewPageCO() {
    }
    xxCommon vxxCommon ; 
    
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        vxxCommon = new xxCommon () ; 
        vxxCommon.FinalRatingprocessRequest(oapagecontext,oawebbean);
        
    }    
}
