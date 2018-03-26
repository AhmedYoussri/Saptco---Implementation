package xxpos.oracle.apps.pos.request.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.pos.onboard.server.FlexRegAMImpl;
import oracle.apps.pos.request.server.AddressAMImpl;
import oracle.apps.pos.request.server.AddressRequestsVOImpl;
import oracle.apps.pos.request.server.AddressRequestsVORowImpl;
import oracle.apps.pos.request.webui.AddAddrRequestPGCO;

public class xxAddAddrRequestPGCO extends AddAddrRequestPGCO {
    public xxAddAddrRequestPGCO() {
    }

public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
          
        if(oapagecontext.getParameter("Continue") != null|| oapagecontext.getParameter("ContinueBtn") != null|| oapagecontext.getParameter("ApplyAndAddDetail") != null)
        { 
                FlexRegAMImpl vFlexRegAM  = (FlexRegAMImpl) oapagecontext.getApplicationModule(oawebbean); 
                if (vFlexRegAM != null) 
                { 
                    AddressAMImpl vAddressAM = (AddressAMImpl) vFlexRegAM.findApplicationModule("AddressAM") ; 
                    if (vAddressAM != null) 
                    { 
                        AddressRequestsVOImpl vAddressRequestsVO = (AddressRequestsVOImpl) vAddressAM.findViewObject("AddressRequestsVO") ;
                        if (vAddressRequestsVO != null) 
                        {   
                            AddressRequestsVORowImpl vAddressRequestsVORow = (AddressRequestsVORowImpl) vAddressRequestsVO.getCurrentRow() ; 
                            if (vAddressRequestsVORow != null)
                            { 
                                String vRfqFlag = "" ;
                                String vPayFlag = "";
                                String vPurFlag = "" ;
                                if (vAddressRequestsVORow.getRfqFlag() != null )                           
                                { 
                                    vRfqFlag = vAddressRequestsVORow.getRfqFlag();          
                                    if (vRfqFlag.equals("Y") )
                                    {
                                        if (vAddressRequestsVORow.getPayFlag() != null )
                                        {
                                                vPayFlag = vAddressRequestsVORow.getPayFlag();
                                        } 
                                        if (vAddressRequestsVORow.getPurFlag() != null )
                                        {
                                                vPurFlag = vAddressRequestsVORow.getPurFlag();
                                        } 
                                        if (vPayFlag.equals("Y") || (vPurFlag.equals("Y") ) )
                                        {
                                             throw new OAException(oapagecontext.getMessage("XXHCM" , "XX_SCM_CHECK_RFQ" , null),OAException.ERROR);
                                        }      
                                    }               
                                 }
                              }
                        }
                    }
                }
        }
                                    
        super.processFormRequest(oapagecontext, oawebbean);
    }
}
                                            