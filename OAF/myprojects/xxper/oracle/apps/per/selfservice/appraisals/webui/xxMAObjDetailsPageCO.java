package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.server.AppraisalsAMImpl;
import oracle.apps.per.selfservice.appraisals.webui.MAObjDetailsPageCO;
import oracle.apps.per.wpm.objectives.server.ObjectiveEditAMImpl;
import oracle.apps.per.wpm.objectives.server.ObjectivesVOImpl;
import oracle.apps.per.wpm.objectives.server.ObjectivesVORowImpl;
import oracle.jdbc.OracleConnection;

public class xxMAObjDetailsPageCO extends MAObjDetailsPageCO {
    public xxMAObjDetailsPageCO() {
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {

        if(oapagecontext.getParameter("Apply") != null|| oapagecontext.getParameter("AddAnother") != null||oapagecontext.getParameter("Save") != null)
          { 
          
            AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean); 
            if (vAppraisalsAM != null) 
            { 
        
                ObjectiveEditAMImpl vObjectiveEditAM  = (ObjectiveEditAMImpl) vAppraisalsAM.findApplicationModule("ObjectiveEditAM") ; 
                if (vObjectiveEditAM != null) 
                {
            
                    OADBTransaction voadbtransaction = vObjectiveEditAM.getOADBTransaction();
                    if (voadbtransaction != null)
                    {
                   
                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                        if (voracleconnection != null)
                        {
                            ObjectivesVOImpl vObjectivesVO = vObjectiveEditAM.getObjectivesVO() ;
                            if ( vObjectivesVO != null)
                            { 
                          
                                ObjectivesVORowImpl vObjectivesVORow = (ObjectivesVORowImpl) vObjectivesVO.getCurrentRow() ;
                                if (vObjectivesVORow != null )
                                {
                              
                                    String vObjectiveId ="-1";
                                    if ( vObjectivesVORow.getObjectiveId() != null )
                                    { 
                                     
                                        if (!vObjectivesVORow.getObjectiveId().equals (""))
                                        { 
                                    
                                                vObjectiveId = vObjectivesVORow.getObjectiveId().toString();
                                        }
                                    }
                                    
                                    String vScoreCardId ="-1";
                                    if (vObjectivesVORow.getScorecardId() != null )
                                    {
                                        if (!vObjectivesVORow.getScorecardId().equals (""))
                                        {
                                            vScoreCardId = vObjectivesVORow.getScorecardId().toString();
                                        }
                                    }
                                    xxCommon vxxCommon = new xxCommon();
                                    String VCheckAddObjective = vxxCommon.CheckAddObjective(voracleconnection,vObjectiveId,vScoreCardId ) ; 
                                    if (VCheckAddObjective.equals("Y"))
                                     { 
                                        throw new OAException(oapagecontext.getMessage("XXHCM" , "XX_HR_OBJECTIVE_MAME" , null),OAException.ERROR);
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