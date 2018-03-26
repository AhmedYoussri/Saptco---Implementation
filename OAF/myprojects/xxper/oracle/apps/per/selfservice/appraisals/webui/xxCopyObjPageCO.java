package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.per.selfservice.appraisals.server.AppraisalsAMImpl;
import oracle.apps.per.selfservice.appraisals.webui.CopyObjPageCO;
import oracle.apps.per.wpm.objectives.server.CopyLibraryObjectivesVOImpl;
import oracle.apps.per.wpm.objectives.server.CopyLibraryObjectivesVORowImpl;
import oracle.apps.per.wpm.objectives.server.ObjectiveEditAMImpl;
import oracle.jdbc.OracleConnection;

public class xxCopyObjPageCO extends CopyObjPageCO {
    public xxCopyObjPageCO() {
    }
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    { 
      if(oapagecontext.getParameter("Apply") != null|| oapagecontext.getParameter("HrSaveForLater") != null||oapagecontext.getParameter("Save") != null)
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
                            CopyLibraryObjectivesVOImpl vCopyPastObjectivesVO = vObjectiveEditAM.getCopyLibraryObjectivesVO();
                            if ( vCopyPastObjectivesVO != null) 
                            { 
                                vCopyPastObjectivesVO.first() ;
                                String vObjectiveId ="-1";
                                String vScoreCardId ="-1";
                                for(CopyLibraryObjectivesVORowImpl vCopyPastObjectivesVORow = (CopyLibraryObjectivesVORowImpl)vCopyPastObjectivesVO.getCurrentRow(); vCopyPastObjectivesVORow != null; vCopyPastObjectivesVORow = (CopyLibraryObjectivesVORowImpl)vCopyPastObjectivesVO.next())                         
                                { 
                                    if ( vCopyPastObjectivesVORow != null) 
                                    { 
                                        if (vCopyPastObjectivesVORow.getSelectFlag()!= null)
                                        {
                                            if (vCopyPastObjectivesVORow.getSelectFlag().equals("Y"))
                                            {
                                                vObjectiveId ="-1";
                                                if ( vCopyPastObjectivesVORow.getObjectiveId() != null )
                                                { 
                                                 
                                                    if (!vCopyPastObjectivesVORow.getObjectiveId().equals (""))
                                                    { 
                                                
                                                            vObjectiveId = vCopyPastObjectivesVORow.getObjectiveId().toString();
                                                    }
                                                }
                                                
                                                vScoreCardId ="-1";
                                                if (oapagecontext.getDecryptedParameter("pScorecardId") != null )
                                                {
                                                    if (!oapagecontext.getDecryptedParameter("pScorecardId").equals (""))
                                                    {
                                                        vScoreCardId = oapagecontext.getDecryptedParameter("pScorecardId").toString();
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
                }
            }
      }
      super.processFormRequest(oapagecontext, oawebbean);         

    }
}
