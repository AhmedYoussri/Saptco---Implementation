package xxirc.oracle.apps.irc.candidateSelfService.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.candidateSelfService.webui.EmpAplJobBasketCO;


public class xxEmpAplJobBasketCO extends EmpAplJobBasketCO {
    public xxEmpAplJobBasketCO() {
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        xxCommon vxxCommon = new xxCommon (); 
        vxxCommon.IrcOAControllerprocessFormRequest(oapagecontext,oawebbean,this);

        super.processFormRequest(oapagecontext, oawebbean);
    }    
}
