package xxper.oracle.apps.per.selfservice.appraisals.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVOImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalVORowImpl;
import oracle.apps.per.selfservice.appraisals.server.AppraisalsAMImpl;
import oracle.apps.per.selfservice.appraisals.server.AssessmentVOImpl;
import oracle.apps.per.selfservice.appraisals.server.AssessmentVORowImpl;
import oracle.apps.per.selfservice.appraisals.server.AssessmentsAMImpl;
import oracle.apps.per.selfservice.appraisals.server.CompetenceElementsVOImpl;
import oracle.apps.per.selfservice.appraisals.server.CompetenceElementsVORowImpl;

import oracle.apps.per.selfservice.appraisals.server.FinalCompListVOImpl;
import oracle.apps.per.selfservice.appraisals.server.FinalCompListVORowImpl;
import oracle.apps.per.selfservice.objectives.server.ObjectivesAMImpl;

import oracle.cabo.ui.action.FirePartialAction;
import oracle.cabo.ui.collection.Parameter;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

import oracle.cabo.ui.UIConstants ;

import oracle.jbo.domain.Number;

public class xxCommon {
    public xxCommon() {
    }
    
    public String vApprProfAndPerfLevelsCSSOrg = "" ; 
    public String ValidateAppraiserProfAndPerfLevels(OracleConnection poracleconnection, String pCompetenceElementId, String pProficiencyLevelId, String pRatingLevelId)
    {
        String Result = "VALID";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PM_PKG.VLDT_APPR_PROF_AND_PERF_LVLS (:2,:3,:4); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pCompetenceElementId);
                oraclecallablestatement.setString(3, pProficiencyLevelId);
                oraclecallablestatement.setString(4, pRatingLevelId);
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

    public void ValidateAppraiserProfAndPerfLevels(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        ValidateAppraiserProfAndPerfLevels(oapagecontext, oawebbean,"-1") ;
    }
    
    public void ValidateAppraiserProfAndPerfLevels(OAPageContext oapagecontext, OAWebBean oawebbean,String pCompetenceElementId)
    {
        String vCompetenceElementId = "" ; 
        AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean);
        if (vAppraisalsAM != null)
        {
            AssessmentsAMImpl vAssessmentsAM = (AssessmentsAMImpl)vAppraisalsAM.findApplicationModule("AssessmentsAM");
            if (vAssessmentsAM != null)
            {
                CompetenceElementsVOImpl vCompetenceElementsVO = vAssessmentsAM.getCompetenceElementsVO() ;
                if (vCompetenceElementsVO!= null )
                {
                    if (vCompetenceElementsVO.getRowCount() > 0)
                    {
                        CompetenceElementsVORowImpl vCompetenceElementsVOCurrentRow = null ;
                        if (vCompetenceElementsVO.getCurrentRow() != null)
                        {
                            vCompetenceElementsVOCurrentRow = (CompetenceElementsVORowImpl) vCompetenceElementsVO.getCurrentRow() ;        
                        }
                        else
                        {
                            vCompetenceElementsVOCurrentRow = (CompetenceElementsVORowImpl) vCompetenceElementsVO.first() ;
                        }
                        
                        vCompetenceElementsVO.first();
                        boolean vRaiseError = false ;
                        String vAllResults = "" ;
                        for(CompetenceElementsVORowImpl vCompetenceElementsVORow = (CompetenceElementsVORowImpl)vCompetenceElementsVO.getCurrentRow(); vCompetenceElementsVORow != null; vCompetenceElementsVORow = (CompetenceElementsVORowImpl)vCompetenceElementsVO.next()) 
                        {
                            if (vCompetenceElementsVORow.getCompetenceElementId()!= null )
                            {
                                if (pCompetenceElementId.equals("-1"))
                                {
                                    vCompetenceElementId = vCompetenceElementsVORow.getCompetenceElementId().toString() ;
                                }
                                else
                                {   
                                    vCompetenceElementId = pCompetenceElementId ;
                                }
                                
                                if (vCompetenceElementsVORow.getCompetenceElementId().toString().equals(vCompetenceElementId) )
                                {
                                    if (vCompetenceElementsVORow.getProficiencyLevelId()!= null && vCompetenceElementsVORow.getRatingLevelId()!= null )
                                    {
                                        String vProficiencyLevelId = vCompetenceElementsVORow.getProficiencyLevelId().toString() ;
                                        String vRatingLevelId = vCompetenceElementsVORow.getRatingLevelId().toString() ;
                                        OADBTransaction voadbtransaction = vAssessmentsAM.getOADBTransaction();
                                        if (voadbtransaction != null)
                                        {
                                            OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                            if (voracleconnection != null)
                                            {
                                                String Result = ValidateAppraiserProfAndPerfLevels ( voracleconnection , vCompetenceElementId.toString() ,vProficiencyLevelId,vRatingLevelId );
                                                if (!Result.equals("VALID"))
                                                {
                                                    vCompetenceElementsVORow.setAttribute("xxApprProfAndPerfLevelsCSS" , "OraErrorNameText") ;
                                                    vRaiseError = true ;
                                                    vAllResults = /*vAllResults.concat(" - ").concat(*/Result/*)*/;
                                                }
                                                else
                                                {
                                                    vCompetenceElementsVORow.setAttribute("xxApprProfAndPerfLevelsCSS" ,vApprProfAndPerfLevelsCSSOrg) ;
                                                }
                                            }
                                        }
                                        
                                    }    
                                }
                            }
                        }
                        vCompetenceElementsVO.setCurrentRow(vCompetenceElementsVOCurrentRow) ;
                        if (vRaiseError)
                        {
                            throw new OAException(vAllResults ,OAException.ERROR);
                        }
                    }
                }
            }
        }
    }
    
    public void AppraiseprocessRequest(OAPageContext oapagecontext, OAWebBean oawebbean , String pApprProfLevelBeanName , String pApprPerfLevelBeanName)
    {
        OAMessageChoiceBean vApprProfLevel = (OAMessageChoiceBean) oawebbean.findChildRecursive(pApprProfLevelBeanName); 
        OAMessageChoiceBean vApprPerfLevel = (OAMessageChoiceBean) oawebbean.findChildRecursive(pApprPerfLevelBeanName); 

        
        if (vApprProfLevel != null || vApprPerfLevel!= null )
        {
            if (vApprProfLevel!= null)
            {
                vApprProfAndPerfLevelsCSSOrg = vApprProfLevel.getCSSClass() ; 
            }
            else if (vApprPerfLevel!= null)
            {
                vApprProfAndPerfLevelsCSSOrg = vApprPerfLevel.getCSSClass() ; 
            }

            AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean);
            if (vAppraisalsAM != null)
            {
                AssessmentsAMImpl vAssessmentsAM = (AssessmentsAMImpl)vAppraisalsAM.findApplicationModule("AssessmentsAM");
                if (vAssessmentsAM != null)
                {
                    CompetenceElementsVOImpl vCompetenceElementsVO = vAssessmentsAM.getCompetenceElementsVO() ;
                    if (vCompetenceElementsVO!= null )
                    {
                        String l_att2;
                        try
                        {
                            l_att2 = vCompetenceElementsVO.findAttributeDef("xxApprProfAndPerfLevelsCSS").toString();
                        }
                        catch(Exception exception)
                        {
                            vCompetenceElementsVO.addDynamicAttribute("xxApprProfAndPerfLevelsCSS");
                        }
                    }
                }
            }
            //////////////////////////////////////////////////////////////
            Parameter vCompetenceElementId = new Parameter();
            vCompetenceElementId.setKey("xxCompetenceElementId");
            if (vApprProfLevel!= null)
            {
                vApprProfLevel.setAttributeValue(UIConstants.STYLE_CLASS_ATTR ,new OADataBoundValueViewObject(vApprProfLevel , "xxApprProfAndPerfLevelsCSS") ) ;
                vCompetenceElementId.setValueBinding(new OADataBoundValueViewObject(vApprProfLevel , "CompetenceElementId"));
            }
            if (vApprPerfLevel!= null)
            {
                vApprPerfLevel.setAttributeValue(UIConstants.STYLE_CLASS_ATTR ,new OADataBoundValueViewObject(vApprPerfLevel , "xxApprProfAndPerfLevelsCSS") ) ;
                vCompetenceElementId.setValueBinding(new OADataBoundValueViewObject(vApprPerfLevel , "CompetenceElementId"));
            }
            Parameter[] xxParameters = {vCompetenceElementId};
            //////////////////////////////////////////////////////////////
            if (vApprProfLevel!= null)
            {
                FirePartialAction vApprProfLevelPartialAction = new FirePartialAction();
                vApprProfLevelPartialAction.setEvent("xxApprProfLevelPartialAction");
                vApprProfLevelPartialAction.setUnvalidated(true);
                vApprProfLevelPartialAction.setSource(pApprProfLevelBeanName); 
                vApprProfLevelPartialAction.setParameters(xxParameters);

                vApprProfLevel.setPrimaryClientAction(vApprProfLevelPartialAction);
            }

            if (vApprPerfLevel!= null)
            {
                FirePartialAction vApprPerfLevelPartialAction = new FirePartialAction();
                vApprPerfLevelPartialAction.setEvent("xxApprPerfLevelPartialAction");
                vApprPerfLevelPartialAction.setUnvalidated(true);
                vApprPerfLevelPartialAction.setSource(pApprPerfLevelBeanName); 
                vApprPerfLevelPartialAction.setParameters(xxParameters);

                vApprPerfLevel.setPrimaryClientAction(vApprPerfLevelPartialAction);
            }
            //////////////////////////////////////////////////////////////
             OAMessageChoiceBean vOverallRating = (OAMessageChoiceBean)  oawebbean.findChildRecursive("OverallRating"); 
             if (vOverallRating!=null)
             {
                 vOverallRating.setRendered(false);
             }       
        }   
        
    
    }
    
    public void AppraiseprocessFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        String vEvent = oapagecontext.getParameter(UIConstants.EVENT_PARAM);
        if (vEvent!= null)
        {
            if (vEvent.equals("xxApprProfLevelPartialAction") || vEvent.equals("xxApprPerfLevelPartialAction")  )
            {
                if (oapagecontext.getParameter ("xxCompetenceElementId") != null   )
                {
                    String vCompetenceElementId = oapagecontext.getParameter ("xxCompetenceElementId").toString() ;
                    ValidateAppraiserProfAndPerfLevels (oapagecontext,oawebbean,vCompetenceElementId) ;
                }
                oapagecontext.putParameter(UIConstants.EVENT_PARAM ,"") ;
            }
        }    
    }
    
    public String CalcOverAllRating(OracleConnection poracleconnection, String pAppraisalId , String pCompetenciesTotalScore, String pObjectivesTotalScore)
    {
        String Result = "-1";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PM_PKG.CALC_OVERALL_RATING (:2,:3,:4); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(1, pAppraisalId);
                oraclecallablestatement.setString(2, pCompetenciesTotalScore);
                oraclecallablestatement.setString(3, pObjectivesTotalScore);
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
    
    public void FinalRatingprocessRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        OAMessageChoiceBean vOverallRating = (OAMessageChoiceBean) oawebbean.findChildRecursive("OverallRating"); 
        if (vOverallRating != null)
        {
            vOverallRating.setReadOnly(true);
        }
        AppraisalsAMImpl vAppraisalsAM  = (AppraisalsAMImpl) oapagecontext.getApplicationModule(oawebbean);
        if (vAppraisalsAM != null)
        {
            AppraisalVOImpl vAppraisalVO = vAppraisalsAM.getAppraisalVO() ;
            if (vAppraisalVO != null)
            {
                AppraisalVORowImpl vAppraisalRowVO = (AppraisalVORowImpl) vAppraisalVO.getCurrentRow() ;
                if (vAppraisalRowVO!= null)
                {
                    Number vAppraisalId = vAppraisalRowVO.getAppraisalId() ;
                    if (vAppraisalId != null)
                    {
                        AssessmentsAMImpl vAssessmentsAM = (AssessmentsAMImpl)vAppraisalsAM.findApplicationModule("AssessmentsAM");
                        if (vAssessmentsAM != null)
                        {
                            AssessmentVOImpl vAssessmentVO = vAssessmentsAM.getAssessmentVO();
                            if (vAssessmentVO!=null)
                            {
                                AssessmentVORowImpl vAssessmentRowVO = (AssessmentVORowImpl) vAssessmentVO.getCurrentRow() ; 
                                if (vAssessmentRowVO!=null)
                                {
                                    if (vAssessmentRowVO.getAssmtScoreStr()!=null)
                                    {
                                        String vAssmtScoreStr = vAssessmentRowVO.getAssmtScoreStr() ;
                                        if (!vAssmtScoreStr.equals(""))
                                        {
                                             ObjectivesAMImpl vObjectivesAM = (ObjectivesAMImpl)vAppraisalsAM.findApplicationModule("ObjectivesAM");
                                             if (vObjectivesAM != null)
                                             {
                                                 AssessmentVOImpl vObjAssessmentVO = vObjectivesAM.getObjAssessmentVO();
                                                 if (vObjAssessmentVO!=null)
                                                 {
                                                     AssessmentVORowImpl vObjAssessmentRowVO = (AssessmentVORowImpl) vObjAssessmentVO.getCurrentRow() ; 
                                                     if (vObjAssessmentRowVO!=null)
                                                     {
                                                         if (vObjAssessmentRowVO.getAssmtScoreStr()!=null)
                                                         {
                                                             String vObjAssmtScoreStr = vObjAssessmentRowVO.getAssmtScoreStr() ;
                                                             if (!vObjAssmtScoreStr.equals(""))
                                                             {
                                                                 OADBTransaction voadbtransaction = vAssessmentsAM.getOADBTransaction();
                                                                 if (voadbtransaction != null)
                                                                 {
                                                                     OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                                                     if (voracleconnection != null)
                                                                     {
                                                                        String vOverAllRating = CalcOverAllRating ( voracleconnection , vAppraisalId.toString() ,  vAssmtScoreStr ,vObjAssmtScoreStr )  ;
                                                                        vAppraisalRowVO.setOverallPerformanceLevelId (new Number ( Integer.parseInt(vOverAllRating))) ;
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
                        }
                    }
                }    
            }     
        }
    }
public String GetCompetenceElementProficiencyLevelId(OracleConnection poracleconnection, String pCompetenceElementId)
{
    String Result = "-1";
    OracleCallableStatement oraclecallablestatement = null;
    try
    {
        oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PM_PKG.GET_CMPTNC_ELEMENT_PROF_LVL_ID (:2); end;");
        if(oraclecallablestatement != null)
        {
            oraclecallablestatement.registerOutParameter(1, 12);
            oraclecallablestatement.setString(2, pCompetenceElementId);
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

public void LoadFinalCompListVO (AssessmentsAMImpl pAssessmentsAM)
{
    OADBTransaction voadbtransaction = pAssessmentsAM.getOADBTransaction();
    if (voadbtransaction != null)
    {
        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
        if (voracleconnection != null)
        {
            FinalCompListVOImpl vFinalCompListVO = pAssessmentsAM.getFinalCompListVO();
            if (vFinalCompListVO!=null)
            {
                if (!vFinalCompListVO.isPreparedForExecution() )
                {
                    pAssessmentsAM.initFinalCompetencies();
                }
                if (vFinalCompListVO.getRowCount() > 0)
                {
                        vFinalCompListVO.first();
                        for(FinalCompListVORowImpl vFinalCompListRowVO = (FinalCompListVORowImpl)vFinalCompListVO.getCurrentRow(); vFinalCompListRowVO != null; vFinalCompListRowVO = (FinalCompListVORowImpl)vFinalCompListVO.next()) 
                        {
                                if (vFinalCompListRowVO.getCompetenceElementId()!= null )
                                {
                                    String vProficiencyLevelId = GetCompetenceElementProficiencyLevelId(voracleconnection,vFinalCompListRowVO.getCompetenceElementId().toString()) ; 
                                    if (vProficiencyLevelId != null)
                                    {
                                        vFinalCompListRowVO.setFinalProfLevelIdOA(new Number(Integer.parseInt(vProficiencyLevelId) ));
                                    }    
                                }
                        }       
                        vFinalCompListVO.first();                                    
                }
            }
        }
    }


}

    public String ValidateAppraisalIsSharedWithEmployee(OracleConnection poracleconnection, String pAppraisalId)
    {
        String Result = "VALID";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PM_PKG.VLDT_APPRSL_SHRD_EMP (:2); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pAppraisalId);
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
    
    public String CheckAddObjective(OracleConnection poracleconnection, String pObjectiveId, String pScoreCardId)
    {
        String Result = "Y";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PER_PKG.CHECK_ADD_OBJECTIVE (:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pObjectiveId);
                oraclecallablestatement.setString(3, pScoreCardId);
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

}
