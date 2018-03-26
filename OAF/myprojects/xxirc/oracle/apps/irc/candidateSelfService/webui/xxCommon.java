package xxirc.oracle.apps.irc.candidateSelfService.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.candidateSelfService.server.ApplicantAMImpl;

import oracle.apps.irc.common.webui.IrcOAControllerImpl;

import oracle.jbo.domain.Number;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxCommon 
{
    public xxCommon() 
    {
    }
    private String ValidateIfThePersonCanApplyOnVacancy(OracleConnection poracleconnection, String pVacancyId, String pPersonId)
    {
        String Result = "VALID";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_IRC_PKG.VLDT_EMP_VAC_APLY (:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pVacancyId);
                oraclecallablestatement.setString(3, pPersonId);
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


    
    public void IrcOAControllerprocessFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean,IrcOAControllerImpl pIrcOAControllerImpl)
    {
        if (pIrcOAControllerImpl != null)
        {
            String vIrcAction = pIrcOAControllerImpl.getIrcAction(oapagecontext);
            if (vIrcAction != null)
            {
                    if("Apply".equals(vIrcAction))
                    {
                        String vIrcActionValue = pIrcOAControllerImpl.getIrcActionValue(oapagecontext);
                        if (vIrcActionValue != null)
                        {
                            Number vVacancyId = pIrcOAControllerImpl.toNumber(vIrcActionValue);
                            ApplicantAMImpl vApplicantAM = (ApplicantAMImpl)oapagecontext.getApplicationModule(oawebbean);
                            if (vApplicantAM != null)
                            {
                                Number vPersonId = vApplicantAM.getCachedPersonId() ;
                                if (vPersonId != null)
                                {
                                    OADBTransaction voadbtransaction = vApplicantAM.getOADBTransaction();
                                    if (voadbtransaction != null)
                                    {
                                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                        if (voracleconnection != null)
                                        {
                                            String vValidateIfThePersonCanApplyOnVacancy= ValidateIfThePersonCanApplyOnVacancy (voracleconnection,vVacancyId.toString(),vPersonId.toString() ) ; 
                                            if (!vValidateIfThePersonCanApplyOnVacancy.equals("VALID"))
                                            {
                                               throw new OAException(vValidateIfThePersonCanApplyOnVacancy,OAException.ERROR);
                                              
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
