package xxokc.oracle.apps.okc.terms.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.okc.terms.server.TemplateAMImpl;
import oracle.apps.okc.terms.webui.TemplateCO;
import oracle.apps.okc.util.webui.TermsUtil;

import xxokc.oracle.apps.okc.terms.server.xxOKCTermsTemplateApprovalHistoryVOImpl;

public class xxTemplateCO extends TemplateCO 
{
    public xxTemplateCO() 
    {
    }
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        
        TemplateAMImpl vTemplateAM  = (TemplateAMImpl) oapagecontext.getApplicationModule(oawebbean); 
        if (vTemplateAM != null)
        {
            xxOKCTermsTemplateApprovalHistoryVOImpl vxxOKCTermsTemplateApprovalHistoryVO =  (xxOKCTermsTemplateApprovalHistoryVOImpl)vTemplateAM.findViewObject("xxOKCTermsTemplateApprovalHistoryVO") ;
            if ( vxxOKCTermsTemplateApprovalHistoryVO == null )
                {
                    vxxOKCTermsTemplateApprovalHistoryVO = (xxOKCTermsTemplateApprovalHistoryVOImpl)vTemplateAM.createViewObject("xxOKCTermsTemplateApprovalHistoryVO", "xxokc.oracle.apps.okc.terms.server.xxOKCTermsTemplateApprovalHistoryVO") ;  
                }
            if ( vxxOKCTermsTemplateApprovalHistoryVO != null ) 
                {
                    if (TermsUtil.getTermsParam(oapagecontext, "OKC_DOCUMENT_ID")!= null)
                    {
                        String vTemplateId = TermsUtil.getTermsParam(oapagecontext, "OKC_DOCUMENT_ID").toString();
                        vxxOKCTermsTemplateApprovalHistoryVO.setWhereClause(" TEMPLATE_ID = ".concat(vTemplateId) );
                        vxxOKCTermsTemplateApprovalHistoryVO.executeQuery();
                    }
                }

        }             
    }    
}
