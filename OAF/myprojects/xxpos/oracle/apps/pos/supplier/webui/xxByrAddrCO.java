package xxpos.oracle.apps.pos.supplier.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.pos.supplier.components.server.AddressDtVOImpl;
import oracle.apps.pos.supplier.components.server.AddressDtVORowImpl;
import oracle.apps.pos.supplier.components.server.ByrAddrAMImpl;
import oracle.apps.pos.supplier.server.ByrSuppAMImpl;
import oracle.apps.pos.supplier.webui.ByrAddrCO;

public class xxByrAddrCO extends ByrAddrCO {
    public xxByrAddrCO() {
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
    
      if(oapagecontext.getParameter("applyBtn") != null|| oapagecontext.getParameter("nextBtn") != null)
        {   
            ByrSuppAMImpl vByrSuppAM  = (ByrSuppAMImpl) oapagecontext.getApplicationModule(oawebbean); 
            if (vByrSuppAM != null) 
                {
                   ByrAddrAMImpl vByrAddrAM = (ByrAddrAMImpl) vByrSuppAM.findApplicationModule("ByrAddrAM") ; 
                   if (vByrAddrAM != null) 
                      {  
                        AddressDtVOImpl vAddressDtVO = (AddressDtVOImpl) vByrAddrAM.findViewObject("AddressDtVO") ;
                        if (vAddressDtVO != null) 
                        {   
                            AddressDtVORowImpl vAddressDtVORow = (AddressDtVORowImpl) vAddressDtVO.getCurrentRow() ; 
                            if (vAddressDtVORow != null) 
                            {           
                               String vRfqFlag = "" ;
                               String vPayFlag = "";
                               String vPurFlag = "" ;
                               if (vAddressDtVORow.getRfqFlag() != null )
                               {
                                    vRfqFlag = vAddressDtVORow.getRfqFlag();  
                                    if (vRfqFlag.equals("Y"))
                                    {
                                        if (vAddressDtVORow.getPayFlag() != null )
                                        {
                                                vPayFlag = vAddressDtVORow.getPayFlag();
                                        }	
                                        if (vAddressDtVORow.getPurFlag() != null )
                                        {
                                                vPurFlag = vAddressDtVORow.getPurFlag();
                                        }	
                                        if (vPayFlag.equals("Y") || (vPurFlag.equals("Y") ) )
                                        {
                                            throw new OAException(oapagecontext.getMessage("XXSCM" , "XX_SCM_CHECK_RFQ" , null),OAException.ERROR);
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

   