package xxirc.oracle.apps.irc.offers.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.common.IrcLog;
import oracle.apps.irc.offers.webui.OffersApplyNavCO;

public class xxOffersApplyNavCO extends OffersApplyNavCO {
    public xxOffersApplyNavCO() {
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        IrcLog.entry(oapagecontext, "xxirc.oracle.apps.irc.offers.webui.xxOffersApplyNavCO", "processFormRequest");

        xxCommon vxxCommon = new xxCommon() ;
        vxxCommon.ValidateTicketsForSAOffers(oapagecontext,oawebbean);

        super.processFormRequest(oapagecontext, oawebbean);
    }    
}
