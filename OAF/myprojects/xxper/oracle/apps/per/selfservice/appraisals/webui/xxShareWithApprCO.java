package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVOImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVORowImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalsAMImpl;
import oracle.apps.per.selfservice.appraisals.webui.ShareWithApprCO;

import oracle.jbo.domain.Number;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxShareWithApprCO extends ShareWithApprCO 
{
    public xxShareWithApprCO() 
    {
    }
    public void SetAppraisalIsSharedWithEmployee(OracleConnection poracleconnection, String pAppraisalId)
    {
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin XX_HR_PM_PKG.SET_APPRSL_SHRD_EMP (:1); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.setString(1, pAppraisalId);
                oraclecallablestatement.execute();
                oraclecallablestatement.close();
            }
        }
        catch(Exception sqlexception)
        {
            throw OAException.wrapperException(sqlexception);
        }
    }

    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if(oapagecontext.getParameter("CommitTransfer") != null)
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
                                    SetAppraisalIsSharedWithEmployee  ( voracleconnection , vAppraisalId.toString()  );
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
