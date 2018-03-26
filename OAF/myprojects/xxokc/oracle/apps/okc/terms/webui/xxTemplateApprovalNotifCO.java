package xxokc.oracle.apps.okc.terms.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.okc.terms.server.TemplateApprovalExpVOImpl;
import oracle.apps.okc.terms.server.TemplateApprovalNotifAMImpl;
import oracle.apps.okc.terms.webui.TemplateApprovalNotifCO;

import oracle.jbo.Row;

import xxokc.oracle.apps.okc.terms.server.xxOKCTermsTemplateApprovalHistoryVOImpl;

public class xxTemplateApprovalNotifCO extends TemplateApprovalNotifCO {
    public xxTemplateApprovalNotifCO() {
    }
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        TemplateApprovalNotifAMImpl vTemplateApprovalNotifAM  = (TemplateApprovalNotifAMImpl) oapagecontext.getApplicationModule(oawebbean); 
        
        if (vTemplateApprovalNotifAM != null)
        {
            xxOKCTermsTemplateApprovalHistoryVOImpl vxxOKCTermsTemplateApprovalHistoryVO =  (xxOKCTermsTemplateApprovalHistoryVOImpl)vTemplateApprovalNotifAM.findViewObject("xxOKCTermsTemplateApprovalHistoryVO") ;
            if ( vxxOKCTermsTemplateApprovalHistoryVO == null )
                {
                    vxxOKCTermsTemplateApprovalHistoryVO = (xxOKCTermsTemplateApprovalHistoryVOImpl)vTemplateApprovalNotifAM.createViewObject("xxOKCTermsTemplateApprovalHistoryVO", "xxokc.oracle.apps.okc.terms.server.xxOKCTermsTemplateApprovalHistoryVO") ;  
                }
            if ( vxxOKCTermsTemplateApprovalHistoryVO != null ) 
                {
                    TemplateApprovalExpVOImpl vTemplateApprovalExpVO = (TemplateApprovalExpVOImpl) vTemplateApprovalNotifAM.getTemplateApprovalExpVO();
                    if (vTemplateApprovalExpVO != null )
                    {
                        if ( vTemplateApprovalExpVO.getCurrentRow() != null )
                        {
                            Row vTemplateApprovalExpRowVO = vTemplateApprovalExpVO.getCurrentRow() ;
                            if (vTemplateApprovalExpRowVO.getAttribute("TemplateId")!=null )
                            {
                                vxxOKCTermsTemplateApprovalHistoryVO.setWhereClause(" TEMPLATE_ID = ".concat(vTemplateApprovalExpRowVO.getAttribute("TemplateId").toString()) );
                                vxxOKCTermsTemplateApprovalHistoryVO.executeQuery();
                            }
                        }    
                    }    
                }

        }        
    }
    
}
