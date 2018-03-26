package xxicx.oracle.apps.icx.por.req.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.icx.por.req.server.PoRequisitionLinesVOImpl;
import oracle.apps.icx.por.req.server.PoRequisitionLinesVORowImpl;
import oracle.apps.icx.por.req.server.RequisitionAMImpl;
import oracle.apps.icx.por.req.webui.EditSubmitCO;

public class xxEditSubmitCO extends EditSubmitCO 
{
    public xxEditSubmitCO() 
    {
    }
    
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        String vDisplayServicesItemsAttributes = oapagecontext.getProfile("XX_POS_SRVS_ITMS_ATTRS"); // Display Services Items Attributes
        if ( vDisplayServicesItemsAttributes != null)
        { 
            if ( vDisplayServicesItemsAttributes.equals("Y"))
            { 
                RequisitionAMImpl vRequisitionAM = (RequisitionAMImpl) oapagecontext.getApplicationModule(oawebbean);
                if (vRequisitionAM!=null)
                {
                    PoRequisitionLinesVOImpl vPoRequisitionLinesVO = vRequisitionAM.getPoRequisitionLinesVO() ;
                    if (vPoRequisitionLinesVO!=null)
                    {
                        if (vPoRequisitionLinesVO.getRowCount() > 0)
                        {
                            xxCommon vxxCommon = new xxCommon(); 
                            vPoRequisitionLinesVO.first();
                            for(PoRequisitionLinesVORowImpl vPoRequisitionLinesVORow = (PoRequisitionLinesVORowImpl)vPoRequisitionLinesVO.getCurrentRow(); vPoRequisitionLinesVORow != null; vPoRequisitionLinesVORow = (PoRequisitionLinesVORowImpl)vPoRequisitionLinesVO.next()) 
                            {
                                vxxCommon.CalcRequisitionLineAmount(vPoRequisitionLinesVORow);
                            } 
                        }
                    }
                }
            }
        }
        
    }    
    
}
