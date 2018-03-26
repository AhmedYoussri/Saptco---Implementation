package xxpos.oracle.apps.pos.wc.webui;

import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.pos.wc.server.WcAMImpl;
import oracle.apps.pos.wc.server.WcPayItemsVOImpl;
import oracle.apps.pos.wc.webui.WcPayItemsSelectCO;

import oracle.jdbc.OracleConnection;

import xxfnd.oracle.apps.fnd.common.xxCommon;

public class xxWcPayItemsSelectCO extends WcPayItemsSelectCO {
    public xxWcPayItemsSelectCO() {
    }

    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        WcAMImpl vWcAM  = (WcAMImpl) oapagecontext.getApplicationModule(oawebbean); 
        if (vWcAM != null)
        {
            OAViewObject xxWcHeaderInterfaceRegionsLOVVO =  (OAViewObject)vWcAM.findViewObject("xxWcHeaderInterfaceRegionsLOVVO") ;
            if ( xxWcHeaderInterfaceRegionsLOVVO == null )
                {
                    xxWcHeaderInterfaceRegionsLOVVO = (OAViewObject)vWcAM.createViewObject("xxWcHeaderInterfaceRegionsLOVVO", "xxpos.oracle.apps.pos.wc.server.xxWcHeaderInterfaceRegionsLOVVO") ;  
                }
            if ( xxWcHeaderInterfaceRegionsLOVVO != null ) 
                {
                    xxWcHeaderInterfaceRegionsLOVVO.executeQuery();
                }

            WcPayItemsVOImpl  vWcPayItemsVO =  vWcAM.getWcPayItemsVO() ;
            if (vWcPayItemsVO != null)
            { 
                OAMessageLovInputBean vxxWcHeaderInterfaceRegionsLOVRN = (OAMessageLovInputBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegionsLOVRN") ; 
                if (vxxWcHeaderInterfaceRegionsLOVRN != null)
                {
                    String vDefaultLocation = oapagecontext.getProfile("XX_POS_WC_DEFAULT_LOCATION"); // Work Confirmation Default Location
                    if ( vDefaultLocation != null)
                    { 
                        vxxWcHeaderInterfaceRegionsLOVRN.setValue(oapagecontext, vDefaultLocation) ;
                    } 
                }
                if (vxxWcHeaderInterfaceRegionsLOVRN != null)
                {
                   if (vxxWcHeaderInterfaceRegionsLOVRN.getValue(oapagecontext)!= null)
                   {
                       String vxxWcHeaderInterfaceRegion = vxxWcHeaderInterfaceRegionsLOVRN.getValue(oapagecontext).toString() ; 
                       vWcPayItemsVO.setWhereClause(vWcPayItemsVO.getWhereClause().concat(" AND XX_HEADERS_INTERFACE_PKG.VALIDATE_SHIP_TO_LOC_ATTRIBUT1 (SHIP_TO_LOCATION_ID , '".concat(vxxWcHeaderInterfaceRegion).concat("' ) = 'Y' ") ) );
                       vWcPayItemsVO.executeQuery(); 
                       vWcAM.getOADBTransaction().putValue("xxWcHeaderInterfaceRegion",vxxWcHeaderInterfaceRegion) ;

                       OADBTransaction voadbtransaction = vWcAM.getOADBTransaction();
                       if (voadbtransaction!= null)
                       {
                           OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                           if (voracleconnection != null)
                           {
                               OAMessageStyledTextBean vxxWcHeaderInterfaceRegionRN = (OAMessageStyledTextBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegion") ; 
                               if (vxxWcHeaderInterfaceRegionRN != null)
                               {
                                   xxCommon vxxCommon = new xxCommon() ;
                                   String vxxWcHeaderInterfaceRegionDesc = vxxCommon.GetHRLocationCode(voracleconnection , vxxWcHeaderInterfaceRegion ) ;
                                   vxxWcHeaderInterfaceRegionRN.setValue(oapagecontext, vxxWcHeaderInterfaceRegionDesc ) ;
                                   vWcAM.getOADBTransaction().putValue("xxWcHeaderInterfaceRegionDesc",vxxWcHeaderInterfaceRegionDesc) ;
                               }    
                           }
                       }
                   }
                }

            }
        }
    }
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.getParameter("Next") != null)
         {
             WcAMImpl vWcAM  = (WcAMImpl) oapagecontext.getApplicationModule(oawebbean); 
             if (vWcAM != null)
             {
                 OAMessageLovInputBean vxxWcHeaderInterfaceRegionsLOVRN = (OAMessageLovInputBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegionsLOVRN") ; 
                 if (vxxWcHeaderInterfaceRegionsLOVRN != null)
                 {
                    if (vxxWcHeaderInterfaceRegionsLOVRN.getValue(oapagecontext)!= null)
                    {
                        vWcAM.getOADBTransaction().putValue("xxWcHeaderInterfaceRegion",vxxWcHeaderInterfaceRegionsLOVRN.getValue(oapagecontext).toString()) ;
                    }
                 }
                 OAMessageStyledTextBean vxxWcHeaderInterfaceRegionRN = (OAMessageStyledTextBean) oawebbean.findChildRecursive("xxWcHeaderInterfaceRegion") ; 
                 if (vxxWcHeaderInterfaceRegionRN != null)
                 {
                     if (vxxWcHeaderInterfaceRegionRN.getValue(oapagecontext)!= null)
                     {
                         vWcAM.getOADBTransaction().putValue("xxWcHeaderInterfaceRegionDesc",vxxWcHeaderInterfaceRegionRN.getValue(oapagecontext).toString()) ;
                     }
                 }    
             }         
         }
        super.processFormRequest(oapagecontext, oawebbean);
    }    
    
}
