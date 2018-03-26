package xxpos.oracle.apps.pos.supplier.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.pos.supplier.server.ByrSuppAMImpl;
import oracle.apps.pos.supplier.webui.ByrTaxRptStCO;
import oracle.apps.zx.taxcontent.registration.server.MaintainPartyTaxProfilesAMImpl;
import oracle.apps.zx.taxcontent.registration.server.MaintainSupAcctSitesVOImpl;
import oracle.apps.zx.taxcontent.registration.server.MaintainSupAcctSitesVORowImpl;



import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;


public class xxByrTaxRptStCO extends ByrTaxRptStCO {
    public xxByrTaxRptStCO() {
    }
    
private String CheckVatRegistrationNumber(OracleConnection poracleconnection, String pVatRegistrationNumber)
   {
       String Result = "";
       OracleCallableStatement oraclecallablestatement = null;
       try
       {
           oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_POS_PKG.CHK_VAT_REGISTRATION_NUM (:2); end;");
           if(oraclecallablestatement != null)
           {
               oraclecallablestatement.registerOutParameter(1, 12);
               oraclecallablestatement.setString(2, pVatRegistrationNumber);
               oraclecallablestatement.execute();
               Result = oraclecallablestatement.getString(1);
               oraclecallablestatement.close();
           }
       }
       catch(Exception sqlexception)
       {
           throw OAException.wrapperException(sqlexception);
       }
       return Result;
   }

    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {          
       if (oapagecontext.getParameter("saveBtn") != null)
        {
           ByrSuppAMImpl vByrSuppAM  = (ByrSuppAMImpl) oapagecontext.getApplicationModule(oawebbean); 
             if (vByrSuppAM != null) 
             {
                MaintainPartyTaxProfilesAMImpl vMaintainPartyTaxProfilesAM = (MaintainPartyTaxProfilesAMImpl) vByrSuppAM.findApplicationModule("MaintainPartyTaxProfilesAM") ; 
                if (vMaintainPartyTaxProfilesAM != null)
                     {
                        MaintainSupAcctSitesVOImpl vMaintainSupAcctSitesVO = vMaintainPartyTaxProfilesAM.getMaintainSupAcctSitesVO() ; 
                        if ( vMaintainSupAcctSitesVO != null) 
                        {
                              MaintainSupAcctSitesVORowImpl vMaintainSupAcctSitesVORow = (MaintainSupAcctSitesVORowImpl) vMaintainSupAcctSitesVO.getCurrentRow() ;      
                              if (vMaintainSupAcctSitesVORow != null) 
                                {
                                     String vVatRegistrationNum = "" ;
                                     if (vMaintainSupAcctSitesVORow.getVatRegistrationNum() != null)
                                      {
                                        vVatRegistrationNum = vMaintainSupAcctSitesVORow.getVatRegistrationNum().toString();
                                        OADBTransaction voadbtransaction = vByrSuppAM.getOADBTransaction();
                                        if (voadbtransaction != null)
                                           {
                                             OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                             if (voracleconnection != null)
                                             {
                                               String vCheckVatRegistrationNumber = CheckVatRegistrationNumber(voracleconnection,vVatRegistrationNum);
                                                if ( !vCheckVatRegistrationNumber.equals("VALID"))
                                                {
                                                    throw new OAException(oapagecontext.getMessage("XXSCM" , "XX_SCM_VAT_NUM" , null),OAException.ERROR);
                                                }
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