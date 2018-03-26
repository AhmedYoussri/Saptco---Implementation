package xxper.oracle.apps.per.wpm.objectives.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.wpm.objectives.server.ObjectiveEditAMImpl;
import oracle.apps.per.wpm.objectives.server.ObjectivesAMImpl;
import oracle.apps.per.wpm.objectives.server.ScorecardObjectivesVOImpl;
import oracle.apps.per.wpm.objectives.server.ScorecardObjectivesVORowImpl;
import oracle.apps.per.wpm.objectives.server.SupWorkerScorecardsVOImpl;
import oracle.apps.per.wpm.objectives.server.SupWorkerScorecardsVORowImpl;
import oracle.jbo.domain.Number;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxCommon {
    public xxCommon() {
    }
    public void ValidateWeightingPercents(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        ValidateWeightingPercents(oapagecontext, oawebbean, "" , "" ) ;
    }
    

    private String GetScorecardSumWeightingPercentsFromTransaction(OracleConnection poracleconnection, String pScorecardId)
    {
        String Result = "0";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PM_PKG.GET_SCRCRD_SUM_WGHTPCT_TRX (:2); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pScorecardId);
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

    public void ValidateWeightingPercentsForSupWorkerScorecard(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        ObjectivesAMImpl vObjectivesAM  = (ObjectivesAMImpl) oapagecontext.getApplicationModule(oawebbean);
        if (vObjectivesAM != null)
        {
            SupWorkerScorecardsVOImpl vSupWorkerScorecardsVO = vObjectivesAM.getSupWorkerScorecardsVO() ;
            if (vSupWorkerScorecardsVO!= null )
            {
                if (vSupWorkerScorecardsVO.getRowCount() > 0)
                {
                    vSupWorkerScorecardsVO.first();
                    for(SupWorkerScorecardsVORowImpl vSupWorkerScorecardsVORow = (SupWorkerScorecardsVORowImpl)vSupWorkerScorecardsVO.getCurrentRow(); vSupWorkerScorecardsVORow != null; vSupWorkerScorecardsVORow = (SupWorkerScorecardsVORowImpl)vSupWorkerScorecardsVO.next()) 
                    {
                        if (vSupWorkerScorecardsVORow.getSelectFlag() != null )
                        {
                            if (vSupWorkerScorecardsVORow.getSelectFlag().equals("Y") )
                            {
                                if (vSupWorkerScorecardsVORow.getScorecardId() != null )
                                {
                                    ValidateWeightingPercents(oapagecontext,oawebbean,vSupWorkerScorecardsVORow.getScorecardId().toString(),vSupWorkerScorecardsVORow.getFullName());  
                                }
                            }    
                        }
                    }
                }
            }   
            
        }    
    
    }
    public void ValidateWeightingPercents(OAPageContext oapagecontext, OAWebBean oawebbean, String vScorecardId, String vFullName)
    {
            Number vWeightingPercent = new Number (Integer.parseInt("0")); 
            ObjectivesAMImpl vObjectivesAM  = (ObjectivesAMImpl) oapagecontext.getApplicationModule(oawebbean);

            if (vObjectivesAM != null)
            {
                ObjectiveEditAMImpl vObjectiveEditAM = null ;
                vObjectiveEditAM = (ObjectiveEditAMImpl) vObjectivesAM.findApplicationModule("ObjectiveEditAM") ;
                if (vObjectiveEditAM == null)
                {
                    vObjectiveEditAM = (ObjectiveEditAMImpl) vObjectivesAM.createApplicationModule("ObjectiveEditAM" , "oracle.apps.per.wpm.objectives.server.ObjectiveEditAM") ;
                }    
                if (vObjectiveEditAM != null)
                {
                    OADBTransaction voadbtransaction = vObjectiveEditAM.getOADBTransaction();
                    if (voadbtransaction != null)
                    {
                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                        if (voracleconnection != null)
                        {
                            vWeightingPercent = new Number (Integer.parseInt(GetScorecardSumWeightingPercentsFromTransaction(voracleconnection,vScorecardId ))) ;
                            ScorecardObjectivesVOImpl vScorecardObjectivesVO = vObjectiveEditAM.getScorecardObjectivesVO(); 
                            if (vScorecardObjectivesVO != null)
                            {
                                if (!vScorecardObjectivesVO.isExecuted() && !vScorecardId.equals(""))
                                {
                                    vObjectiveEditAM.initScorecardObjectivesVO(vScorecardId) ;
                                }
                                
                                if (vScorecardObjectivesVO.getRowCount() > 0)
                                {
                                    vScorecardObjectivesVO.first();
                                    for(ScorecardObjectivesVORowImpl vScorecardObjectivesVORow = (ScorecardObjectivesVORowImpl)vScorecardObjectivesVO.getCurrentRow(); vScorecardObjectivesVORow != null; vScorecardObjectivesVORow = (ScorecardObjectivesVORowImpl)vScorecardObjectivesVO.next()) 
                                    {
                                        if (vScorecardObjectivesVORow.getWeightingPercent() != null )
                                        {
                                            vWeightingPercent = vWeightingPercent.add(vScorecardObjectivesVORow.getWeightingPercent()) ;
                                        }
                                    }
                                }
                            }   
                        }
                     }
                }    
            }            
            if ( !vWeightingPercent.equals(new Number (Integer.parseInt("100"))) )
            {
                String vMessage = oapagecontext.getMessage("XXHCM" , "XX_HR_PM_W_PCT"  , null).concat(" ( ").concat(vWeightingPercent.toString()).concat(" ) ") ; 
                if (!vFullName.equals(""))
                {
                    vMessage = vMessage.concat(" - ").concat( vFullName);
                }

                throw new OAException(vMessage,OAException.ERROR);
            }
        
    }
    
}
