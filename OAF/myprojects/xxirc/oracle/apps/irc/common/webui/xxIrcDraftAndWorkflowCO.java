package xxirc.oracle.apps.irc.common.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.common.webui.IrcDraftAndWorkflowCO;

import oracle.apps.irc.vacancy.server.IrcEditVacancyVOImpl;
import oracle.apps.irc.vacancy.server.IrcEditVacancyVORowImpl;
import oracle.apps.irc.vacancy.server.VacancyAMImpl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxIrcDraftAndWorkflowCO extends IrcDraftAndWorkflowCO {
    public xxIrcDraftAndWorkflowCO() {
    }
    private String CheckPositionHitsForNewVacancy(OracleConnection poracleconnection, String pPositionId, String pBudgetMeasurementValue )
    {
        String Result = "Y";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_IRC_PKG.CHK_POS_FOR_NEW_VAC (:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pPositionId);
                oraclecallablestatement.setString(3, pBudgetMeasurementValue);
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


    public void processFormData(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
      
        String strEvent= oapagecontext.getParameter(EVENT_PARAM) ;
        if(strEvent.equals("goto") || strEvent.equals("update") ) //Next and Review
        {
            VacancyAMImpl vVacancyAM  = (VacancyAMImpl) oapagecontext.getApplicationModule(oawebbean); 
            
            if (vVacancyAM != null)
            {
                OADBTransaction voadbtransaction = vVacancyAM.getOADBTransaction();
                if (voadbtransaction != null)
                {
                    OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                    if (voracleconnection != null)
                    {
                        IrcEditVacancyVOImpl vIrcEditVacancyVO = vVacancyAM.getIrcEditVacancyVO() ; 
                        if ( vIrcEditVacancyVO != null)
                        {
                            IrcEditVacancyVORowImpl vIrcEditVacancyVORow = (IrcEditVacancyVORowImpl) vIrcEditVacancyVO.getCurrentRow() ;
                            if (vIrcEditVacancyVORow != null )
                            {
                                String vBudgetMeasurementValue ="";
                                vBudgetMeasurementValue = vIrcEditVacancyVORow.getBudgetMeasurementValue().toString();
                                if (!vBudgetMeasurementValue.equals (""))
                                {
                                    String vPositionId = "" ;
                                    vPositionId = vIrcEditVacancyVORow.getPositionId().toString();
                                    if (!vPositionId.equals(""))
                                    {
                                        String vCheckPositionHitsForNewVacancy= CheckPositionHitsForNewVacancy (voracleconnection,vPositionId,vBudgetMeasurementValue ) ; 
                                        if (!vCheckPositionHitsForNewVacancy.equals("Y"))
                                        {
                                           throw new OAException(oapagecontext.getMessage("XXHCM" , "XX_HR_POS_FTE" , null),OAException.ERROR);
                                          
                                        } 
                                    }
                                }
                            }
                        }    
                    }                    
                }
                    
            }
        }   
        super.processFormData(oapagecontext, oawebbean);
    }   
}
