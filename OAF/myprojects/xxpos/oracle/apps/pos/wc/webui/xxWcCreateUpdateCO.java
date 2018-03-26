package xxpos.oracle.apps.pos.wc.webui;


import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.pos.wc.server.WcAMImpl;
import oracle.apps.pos.wc.server.WcHeaderInterfaceVOImpl;
import oracle.apps.pos.wc.server.WcHeaderInterfaceVORowImpl;
import oracle.apps.pos.wc.webui.WcCreateUpdateCO;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxWcCreateUpdateCO extends WcCreateUpdateCO {
    public xxWcCreateUpdateCO() {
    }
    private String Genworkconfirmationnum(OracleConnection poracleconnection, String pPoHeaderId)
        {
            String Result = "";
            OracleCallableStatement oraclecallablestatement = null;
            try
            {
                oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_POS_PKG.GEN_WRK_CNFRMN_NUM (:2); end;");
                if(oraclecallablestatement != null)
                {
                    oraclecallablestatement.registerOutParameter(1, 12);
                    oraclecallablestatement.setString(2, pPoHeaderId);
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

    private void SetAttribute1Value(OracleConnection poracleconnection, String pHeaderInterfaceId,String pAttribute1,String pShipmentNum)
        {
            OracleCallableStatement oraclecallablestatement = null;
            try
            {
                oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin XX_HEADERS_INTERFACE_PKG.SET_HEADER_ATTRIBUTE1 (:1,:2,:3); end;");
                if(oraclecallablestatement != null)
                {
                    oraclecallablestatement.setString(1, pHeaderInterfaceId);
                    oraclecallablestatement.setString(2, pAttribute1);
                    oraclecallablestatement.setString(3, pShipmentNum);
                    oraclecallablestatement.execute();
                    oraclecallablestatement.close();
                }
            }
            catch(Exception sqlexception)
            {
                throw OAException.wrapperException(sqlexception);
            }
        }

    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean ) 
     {
        super.processRequest(oapagecontext, oawebbean);
   
    	WcAMImpl vWcAM  = (WcAMImpl) oapagecontext.getApplicationModule(oawebbean); 
        if (vWcAM != null)
            {
                OADBTransaction voadbtransaction = vWcAM.getOADBTransaction();   
                if (voadbtransaction != null)
                    {    
                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                        WcHeaderInterfaceVOImpl  vWcHeaderInterfaceVO =  vWcAM.getWcHeaderInterfaceVO() ;
                        if (vWcHeaderInterfaceVO != null)
                            { 
                                WcHeaderInterfaceVORowImpl vWcHeaderInterfaceVORow = (WcHeaderInterfaceVORowImpl) vWcHeaderInterfaceVO.getCurrentRow() ;
                                if (vWcHeaderInterfaceVORow != null)
                                { 
                                    String vPoHeaderId = "" ;
                                    if  (oapagecontext.getParameter("PoHeaderId")!= null)
                                    {
                                        vPoHeaderId = oapagecontext.getParameter("PoHeaderId").toString();
                                        if (!vPoHeaderId.equals(""))
                                        {
                                            boolean Vreturndata = false ;
                                            if (vWcHeaderInterfaceVORow.getShipmentNum() == null )
                                            {
                                               Vreturndata = true ;
                                            }
                                            else
                                            {
                                                if (vWcHeaderInterfaceVORow.getShipmentNum().equals (""))
                                              {       
                                                     Vreturndata = true ; 
                                              }
                                            }    
                                            if (Vreturndata)
                                            {
                                               String vshipmentnumber= Genworkconfirmationnum (voracleconnection,vPoHeaderId ) ; 
                                               vWcHeaderInterfaceVORow.setShipmentNum((vshipmentnumber) );
                                            }
                                        }  
                                    }    
                                }
                                if (voadbtransaction.getValue("xxWcHeaderInterfaceRegion") != null && voadbtransaction.getValue("xxWcHeaderInterfaceRegionDesc") != null)
                                {
                                    OAMessageStyledTextBean vxxWcHeaderInterfaceRegionRN = (OAMessageStyledTextBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegion") ; 
                                    if (vxxWcHeaderInterfaceRegionRN != null)
                                    {
                                        vxxWcHeaderInterfaceRegionRN.setValue(oapagecontext, voadbtransaction.getValue("xxWcHeaderInterfaceRegion").toString().concat(":").concat(voadbtransaction.getValue("xxWcHeaderInterfaceRegionDesc").toString())  ) ;
                                    }                                
                                }                                
                                
                                
                            }
                    }    
                                    
                }
        }

                   
        public  void  processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
       {          
          if(oapagecontext.getParameter("Save") != null|| oapagecontext.getParameter("Submit") != null|| oapagecontext.getParameter("Preview") != null)
           {    
               WcAMImpl vWcAM  = (WcAMImpl) oapagecontext.getApplicationModule(oawebbean);  
               if (vWcAM != null)
               {         
                    WcHeaderInterfaceVOImpl  vWcHeaderInterfaceVO =  vWcAM.getWcHeaderInterfaceVO() ;
                    if (vWcHeaderInterfaceVO != null)
                    { 
                       WcHeaderInterfaceVORowImpl vWcHeaderInterfaceVORow = (WcHeaderInterfaceVORowImpl) vWcHeaderInterfaceVO.getCurrentRow() ;
                       if (vWcHeaderInterfaceVORow != null)
                          {           
                               boolean Vcheckdatefromrequired = false ;
                               boolean Vcheckdatetorequired = false ;
                               
                               if (vWcHeaderInterfaceVORow.getPerformancePeriodFrom() == null )
                               {
                                  Vcheckdatefromrequired = true ;
                               }
                                  else
                               {
                                 if (vWcHeaderInterfaceVORow.getPerformancePeriodFrom().equals (""))
                                    {       
                                        Vcheckdatefromrequired = true ; 
                                    }
                               }    
 
                               if (vWcHeaderInterfaceVORow.getPerformancePeriodTo() == null )
                               {
                                  Vcheckdatetorequired = true ;
                               }
                                  else
                               {
                                 if (vWcHeaderInterfaceVORow.getPerformancePeriodTo().equals (""))
                                    {       
                                      Vcheckdatetorequired = true ; 
                                    }
                               }    
                               if (Vcheckdatetorequired||Vcheckdatefromrequired)
                               {
                                    throw new OAException(oapagecontext.getMessage("XXSCM" , "XX_POS_DATE_FROM_REQUIRED" , null),OAException.ERROR);
                               }  
                               
                                if  (vWcHeaderInterfaceVORow.getHeaderInterfaceId() != null && vWcHeaderInterfaceVORow.getShipmentNum() != null )
                                {
                                    OADBTransaction voadbtransaction = vWcAM.getOADBTransaction();
                                    if (voadbtransaction!= null)
                                    {
                                        if (voadbtransaction.getValue("xxWcHeaderInterfaceRegion") != null)
                                        {
                                            OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                            if (voracleconnection != null)
                                            {
                                                SetAttribute1Value(voracleconnection, vWcHeaderInterfaceVORow.getHeaderInterfaceId().toString() , voadbtransaction.getValue("xxWcHeaderInterfaceRegion").toString() , vWcHeaderInterfaceVORow.getShipmentNum() );  
                                            } 
                                       }    
                                    }
                                }                                     
                            }
                     }
                }
        }
       WcAMImpl vWcAM  = (WcAMImpl) oapagecontext.getApplicationModule(oawebbean);  
       if (vWcAM != null)
       {         
           OADBTransaction voadbtransaction = vWcAM.getOADBTransaction();
           if (voadbtransaction!= null)
           {
               if (voadbtransaction.getValue("xxWcHeaderInterfaceRegion") != null && voadbtransaction.getValue("xxWcHeaderInterfaceRegionDesc") != null)
               {
                   OAMessageStyledTextBean vxxWcHeaderInterfaceRegionRN = (OAMessageStyledTextBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegion") ; 
                   if (vxxWcHeaderInterfaceRegionRN != null)
                   {
                       vxxWcHeaderInterfaceRegionRN.setValue(oapagecontext, voadbtransaction.getValue("xxWcHeaderInterfaceRegion").toString().concat(":").concat(voadbtransaction.getValue("xxWcHeaderInterfaceRegionDesc").toString())  ) ;
                   }                                
               }                                
           }
       }
       
       super.processFormRequest(oapagecontext, oawebbean);
       
   } 
}