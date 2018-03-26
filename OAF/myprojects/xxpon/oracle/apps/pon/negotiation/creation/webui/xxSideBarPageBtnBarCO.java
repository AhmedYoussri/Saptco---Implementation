package xxpon.oracle.apps.pon.negotiation.creation.webui;

import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.pon.negotiation.creation.server.NegotiationCreationAMImpl;
import oracle.apps.pon.negotiation.creation.webui.SideBarPageBtnBarCO;

import oracle.jdbc.OracleConnection;

public class xxSideBarPageBtnBarCO extends SideBarPageBtnBarCO {
    public xxSideBarPageBtnBarCO() {
    }
    private void DisablePublishButton(OAPageContext oapagecontext, OAWebBean oawebbean ) 
    { 
          OASubmitButtonBean SubmitBtn = (OASubmitButtonBean)oawebbean.findChildRecursive("SubmitBtn");
          if (SubmitBtn != null) 
          {
              
              NegotiationCreationAMImpl  vNegotiationCreationAM  = (NegotiationCreationAMImpl) oapagecontext.getApplicationModule(oawebbean);
               if (vNegotiationCreationAM != null)
               { 
                   OADBTransaction voadbtransaction = vNegotiationCreationAM.getOADBTransaction();
                 if (voadbtransaction != null)
                 {          
                     String vDisplyButton = voadbtransaction.getProfile("XX_PON_DISPLAY_PUBLISH_BTN");
                     if ( vDisplyButton != null)
                     { 
                        if (vDisplyButton.equals("N"))
                        {
                            SubmitBtn.setRendered(false);
                        }
                     }
                     else
                     {
                         SubmitBtn.setRendered(false);
                     }
                 }
               }                
          }
           
        
    }

    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean )   
    { 
        super.processRequest(oapagecontext, oawebbean);
        DisablePublishButton (oapagecontext, oawebbean);
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean )   
    { 
        super.processFormRequest(oapagecontext, oawebbean);
        DisablePublishButton (oapagecontext, oawebbean);
    }
    }





