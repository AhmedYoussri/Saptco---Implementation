package xxicx.oracle.apps.icx.por.req.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.icx.por.req.server.PoRequisitionLinesVOImpl;
import oracle.apps.icx.por.req.server.PoRequisitionLinesVORowImpl;
import oracle.apps.icx.por.req.webui.ShoppingCartPopupCO;
import oracle.apps.icx.por.req.server.RequisitionAMImpl;

import oracle.jbo.domain.Number;
import oracle.cabo.ui.UIConstants;
import oracle.cabo.ui.action.FirePartialAction;
import oracle.cabo.ui.collection.Parameter;

public class xxShoppingCartPopupCO extends ShoppingCartPopupCO {
    public xxShoppingCartPopupCO() {
    }
    xxCommon vxxCommon = new xxCommon(); 
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        vxxCommon.AddRequisitionLineAttributes(oapagecontext,oawebbean);

    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        String vEvent = oapagecontext.getParameter(UIConstants.EVENT_PARAM);
        if (vEvent!= null)
        {
            if("ShoppingPopupClose".equals(vEvent) || "ShoppingPopupReview".equals(vEvent) ||"ShoppingPopupSave".equals(vEvent) ||"ShoppingPopupSubmit".equals(vEvent) ||"xxPoRequisitionLinesVO_Attribute6_PartialAction".equals(vEvent) ||"xxPoRequisitionLinesVO_Attribute7_PartialAction".equals(vEvent)||"xxPoRequisitionLinesVO_Attribute8_PartialAction".equals(vEvent))
            {
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
                                    String vMessage = "" ;
                                    
                                    vPoRequisitionLinesVO.first();
                                    for(PoRequisitionLinesVORowImpl vPoRequisitionLinesVORow = (PoRequisitionLinesVORowImpl)vPoRequisitionLinesVO.getCurrentRow(); vPoRequisitionLinesVORow != null; vPoRequisitionLinesVORow = (PoRequisitionLinesVORowImpl)vPoRequisitionLinesVO.next()) 
                                    {
                                        if("ShoppingPopupClose".equals(vEvent) || "ShoppingPopupReview".equals(vEvent) ||"ShoppingPopupSave".equals(vEvent) ||"ShoppingPopupSubmit".equals(vEvent)  )
                                        {
                                            if (vPoRequisitionLinesVORow.getAttribute6() == null || vPoRequisitionLinesVORow.getAttribute6().equals("") || vPoRequisitionLinesVORow.getAttribute7() == null || vPoRequisitionLinesVORow.getAttribute7().equals("") || vPoRequisitionLinesVORow.getAttribute8() == null || vPoRequisitionLinesVORow.getAttribute8().equals("") )
                                            {
                                                vMessage = oapagecontext.getMessage("XXSCM" , "XX_POS_SRVS_ITMS_ATTRS_R"  , null);
                                            }
                                            else
                                            {
                                                vxxCommon.CalcRequisitionLineAmount(vPoRequisitionLinesVORow);
                                            } 
                                            if (!vMessage.equals(""))
                                            {
                                                if (vPoRequisitionLinesVORow.getLineNum() != null )
                                                {
                                                    vMessage = vMessage.concat(" ( ").concat(vPoRequisitionLinesVORow.getLineNum().toString()).concat(" ) ") ; 
                                                }
                                                throw new OAException(vMessage ,OAException.ERROR);
                                            }
                                        }
                                        else 
                                        {
                                            if (vEvent.equals("xxPoRequisitionLinesVO_Attribute6_PartialAction")  ||"xxPoRequisitionLinesVO_Attribute7_PartialAction".equals(vEvent)||"xxPoRequisitionLinesVO_Attribute8_PartialAction".equals(vEvent) )
                                            {
                                                if (oapagecontext.getParameter ("xxRequisitionLineId") != null   && vPoRequisitionLinesVORow.getRequisitionLineId() != null )
                                                {
                                                    Number xxRequisitionLineId = new Number(Integer.parseInt(oapagecontext.getParameter ("xxRequisitionLineId").toString()));
                                                    if (xxRequisitionLineId.equals(vPoRequisitionLinesVORow.getRequisitionLineId()) )
                                                    {
                                                        vxxCommon.CalcRequisitionLineAmount(vPoRequisitionLinesVORow);
                                                        break ;  
                                                    }                                            
                                                }                                            
                                            }
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
