package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVOImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVORowImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalsAMImpl;
import oracle.apps.per.selfservice.appraisals.server.AssessmentsAMImpl;
import oracle.apps.per.selfservice.appraisals.webui.MAFinalRatingsPageCO;

import oracle.jbo.domain.Number;

import oracle.jdbc.OracleConnection;

public class xxMAFinalRatingsPageCO extends MAFinalRatingsPageCO {
    public xxMAFinalRatingsPageCO() 
    {
    }
    xxCommon vxxCommon = new xxCommon () ; 
    public void processRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processRequest(oapagecontext, oawebbean);
        vxxCommon.FinalRatingprocessRequest(oapagecontext,oawebbean);

        AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean);
        if (vAppraisalsAM != null)
        {
            AssessmentsAMImpl vAssessmentsAM = (AssessmentsAMImpl)vAppraisalsAM.findApplicationModule("AssessmentsAM");
            if (vAssessmentsAM != null)
            {
                vxxCommon.LoadFinalCompListVO(vAssessmentsAM) ; 
            }
        }
    } 
    
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.getParameter("Continue") != null)
        {
            AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean);
            if (vAppraisalsAM != null)
            {
                OADBTransaction voadbtransaction = vAppraisalsAM.getOADBTransaction();
                if (voadbtransaction != null)
                {
                    OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                    if (voracleconnection != null)
                    {
                        AppraisalVOImpl vAppraisalVO = vAppraisalsAM.getAppraisalVO() ;
                        if (vAppraisalVO != null)
                        {
                            AppraisalVORowImpl vAppraisalRowVO = (AppraisalVORowImpl) vAppraisalVO.getCurrentRow() ;
                            if (vAppraisalRowVO!= null)
                            {
                                Number vAppraisalId       = vAppraisalRowVO.getAppraisalId() ;
                                if ( vAppraisalId != null )
                                {
                                    String Result = vxxCommon.ValidateAppraisalIsSharedWithEmployee ( voracleconnection , vAppraisalId.toString()  );
                                    if (!Result.equals("VALID"))
                                    {
                                        throw new OAException(Result ,OAException.ERROR);
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
