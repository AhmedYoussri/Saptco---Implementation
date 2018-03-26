package xxicx.oracle.apps.icx.por.req.webui;

import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.icx.por.req.server.PoRequisitionLinesVORowImpl;

import oracle.cabo.ui.action.FirePartialAction;
import oracle.cabo.ui.collection.Parameter;

import oracle.jbo.domain.Number;

public class xxCommon {
    public xxCommon() 
    {
    }
    
    public void AddRequisitionLineAttributes (OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        String vDisplayServicesItemsAttributes = oapagecontext.getProfile("XX_POS_SRVS_ITMS_ATTRS"); // Display Services Items Attributes
        if ( vDisplayServicesItemsAttributes != null)
        { 
            if ( vDisplayServicesItemsAttributes.equals("Y"))
            { 
                OAMessageTextInputBean vxxPoRequisitionLinesVO_Attribute6 = (OAMessageTextInputBean) oawebbean.findChildRecursive("xxPoRequisitionLinesVO_Attribute6"); 
                if (vxxPoRequisitionLinesVO_Attribute6 != null)
                {
                    vxxPoRequisitionLinesVO_Attribute6.setRendered(true);
                    vxxPoRequisitionLinesVO_Attribute6.setRequired(OAWebBeanConstants.REQUIRED_YES);
                    
                    Parameter vxxRequisitionLineId_Parameter = new Parameter();
                    vxxRequisitionLineId_Parameter.setKey("xxRequisitionLineId");
                    vxxRequisitionLineId_Parameter.setValueBinding(new OADataBoundValueViewObject(vxxPoRequisitionLinesVO_Attribute6 , "RequisitionLineId"));
                    Parameter[] vxxPoRequisitionLinesVO_Attribute6_Parameters = {vxxRequisitionLineId_Parameter};
                    FirePartialAction vxxPoRequisitionLinesVO_Attribute6_PartialAction = new FirePartialAction();
                    vxxPoRequisitionLinesVO_Attribute6_PartialAction.setEvent("xxPoRequisitionLinesVO_Attribute6_PartialAction");
                    vxxPoRequisitionLinesVO_Attribute6_PartialAction.setUnvalidated(true);
                    vxxPoRequisitionLinesVO_Attribute6_PartialAction.setSource("xxPoRequisitionLinesVO_Attribute6"); 
                    vxxPoRequisitionLinesVO_Attribute6_PartialAction.setParameters(vxxPoRequisitionLinesVO_Attribute6_Parameters);
                    vxxPoRequisitionLinesVO_Attribute6.setPrimaryClientAction(vxxPoRequisitionLinesVO_Attribute6_PartialAction);
                    
                }
                OAMessageTextInputBean vxxPoRequisitionLinesVO_Attribute7 = (OAMessageTextInputBean) oawebbean.findChildRecursive("xxPoRequisitionLinesVO_Attribute7"); 
                if (vxxPoRequisitionLinesVO_Attribute7 != null)
                {
                    vxxPoRequisitionLinesVO_Attribute7.setRendered(true);
                    vxxPoRequisitionLinesVO_Attribute7.setRequired(OAWebBeanConstants.REQUIRED_YES);

                    Parameter vxxRequisitionLineId_Parameter = new Parameter();
                    vxxRequisitionLineId_Parameter.setKey("xxRequisitionLineId");
                    vxxRequisitionLineId_Parameter.setValueBinding(new OADataBoundValueViewObject(vxxPoRequisitionLinesVO_Attribute7 , "RequisitionLineId"));
                    Parameter[] vxxPoRequisitionLinesVO_Attribute7_Parameters = {vxxRequisitionLineId_Parameter};
                    FirePartialAction vxxPoRequisitionLinesVO_Attribute7_PartialAction = new FirePartialAction();
                    vxxPoRequisitionLinesVO_Attribute7_PartialAction.setEvent("xxPoRequisitionLinesVO_Attribute7_PartialAction");
                    vxxPoRequisitionLinesVO_Attribute7_PartialAction.setUnvalidated(true);
                    vxxPoRequisitionLinesVO_Attribute7_PartialAction.setSource("xxPoRequisitionLinesVO_Attribute7"); 
                    vxxPoRequisitionLinesVO_Attribute7_PartialAction.setParameters(vxxPoRequisitionLinesVO_Attribute7_Parameters);
                    vxxPoRequisitionLinesVO_Attribute7.setPrimaryClientAction(vxxPoRequisitionLinesVO_Attribute7_PartialAction);
                }
                OAMessageTextInputBean vxxPoRequisitionLinesVO_Attribute8 = (OAMessageTextInputBean) oawebbean.findChildRecursive("xxPoRequisitionLinesVO_Attribute8"); 
                if (vxxPoRequisitionLinesVO_Attribute8 != null)
                {
                    vxxPoRequisitionLinesVO_Attribute8.setRendered(true);
                    vxxPoRequisitionLinesVO_Attribute8.setRequired(OAWebBeanConstants.REQUIRED_YES);

                    Parameter vxxRequisitionLineId_Parameter = new Parameter();
                    vxxRequisitionLineId_Parameter.setKey("xxRequisitionLineId");
                    vxxRequisitionLineId_Parameter.setValueBinding(new OADataBoundValueViewObject(vxxPoRequisitionLinesVO_Attribute8 , "RequisitionLineId"));
                    Parameter[] vxxPoRequisitionLinesVO_Attribute8_Parameters = {vxxRequisitionLineId_Parameter};
                    FirePartialAction vxxPoRequisitionLinesVO_Attribute8_PartialAction = new FirePartialAction();
                    vxxPoRequisitionLinesVO_Attribute8_PartialAction.setEvent("xxPoRequisitionLinesVO_Attribute8_PartialAction");
                    vxxPoRequisitionLinesVO_Attribute8_PartialAction.setUnvalidated(true);
                    vxxPoRequisitionLinesVO_Attribute8_PartialAction.setSource("xxPoRequisitionLinesVO_Attribute8"); 
                    vxxPoRequisitionLinesVO_Attribute8_PartialAction.setParameters(vxxPoRequisitionLinesVO_Attribute8_Parameters);
                    vxxPoRequisitionLinesVO_Attribute8.setPrimaryClientAction(vxxPoRequisitionLinesVO_Attribute8_PartialAction);
                }
                OAMessageTextInputBean vTxnAmount = (OAMessageTextInputBean) oawebbean.findChildRecursive("TxnAmount"); 
                if (vTxnAmount != null)
                {
                    if (vTxnAmount.isRendered())
                    {
                        vTxnAmount.setReadOnly(true);
                    }    
                }                
                OAMessageTextInputBean vTxnAsFuncAmount = (OAMessageTextInputBean) oawebbean.findChildRecursive("TxnAsFuncAmount"); 
                if (vTxnAsFuncAmount != null)
                {
                    vTxnAsFuncAmount.setReadOnly(true);
                    if (vTxnAsFuncAmount.isRendered())
                    {
                        vTxnAsFuncAmount.setReadOnly(true);
                    }    
                }    
                OAColumnBean vxxPoRequisitionLinesVO_Attribute6_Col = (OAColumn) oawebbean.findChildRecursive("xxPoRequisitionLinesVO_Attribute6_Col"); 
            }

        } 
    
    }
    
    public void CalcRequisitionLineAmount(PoRequisitionLinesVORowImpl pPoRequisitionLinesVORow )
    {
        String vAttribute6Str = pPoRequisitionLinesVORow.getAttribute6() ;
        Number vAttribute6Num = new Number(Integer.parseInt(  "1" )) ;
        if (vAttribute6Str != null && !vAttribute6Str.equals(""))
        {
            vAttribute6Num = new Number(Integer.parseInt(vAttribute6Str)) ;
        }
        
        String vAttribute7Str = pPoRequisitionLinesVORow.getAttribute7() ;
        Number vAttribute7Num = new Number(Integer.parseInt(  "1" )) ;
        if (vAttribute7Str != null && !vAttribute7Str.equals(""))
        {
            vAttribute7Num = new Number(Integer.parseInt(vAttribute7Str)) ;
        }
        
        String vAttribute8Str = pPoRequisitionLinesVORow.getAttribute8() ;
        Number vAttribute8Num = new Number(Integer.parseInt(  "1" )) ;
        if (vAttribute8Str != null && !vAttribute8Str.equals(""))
        {
            vAttribute8Num = new Number(Integer.parseInt(vAttribute8Str)) ;
        }
        
        Number vNewAmount = vAttribute6Num.multiply(vAttribute7Num).multiply(vAttribute8Num) ;
    
        Number vAmount = pPoRequisitionLinesVORow.getAmount() ;
        if (vAmount != null )
        {
            if (!vAmount.equals(vNewAmount)) 
            {
                pPoRequisitionLinesVORow.setAmount(vNewAmount);
            }
        }

        Number vCurrencyAmount = pPoRequisitionLinesVORow.getCurrencyAmount() ;
        if (vCurrencyAmount != null )
        {
            if (!vCurrencyAmount.equals(vNewAmount)) 
            {
                pPoRequisitionLinesVORow.setCurrencyAmount(vNewAmount);
            }
        }

        Number vDisplayFuncAmount = pPoRequisitionLinesVORow.getDisplayFuncAmount() ;
        if (vDisplayFuncAmount != null )
        {
            if (!vDisplayFuncAmount.equals(vNewAmount)) 
            {
                pPoRequisitionLinesVORow.setDisplayFuncAmount(vNewAmount);
            }
        }

        Number vDisplayTxnAmount = pPoRequisitionLinesVORow.getDisplayTxnAmount() ;
        if (vDisplayTxnAmount != null )
        {
            if (!vDisplayTxnAmount.equals(vNewAmount)) 
            {
                pPoRequisitionLinesVORow.setDisplayTxnAmount(vNewAmount);
            }
        }

    }
    
}
