package xxirc.oracle.apps.irc.vacancy.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.vacancy.server.IrcEditVacancyVOImpl;
import oracle.apps.irc.vacancy.server.IrcEditVacancyVORowImpl;
import oracle.apps.irc.vacancy.server.VacancyAMImpl;
import oracle.apps.irc.vacancy.webui.VacNewDetsPageCO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

import oracle.jbo.domain.Number;

public class xxVacNewDetsPageCO extends VacNewDetsPageCO{
    public xxVacNewDetsPageCO() 
    {
    }
    private String GetPositionGrade(OracleConnection poracleconnection, String pPositionId )
     {
         String Result = "";
         OracleCallableStatement oraclecallablestatement = null;
         try
         {
             oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PKG.GET_POSITION_GRADE_ID (:2); end;");
             if(oraclecallablestatement != null)
             {
                 oraclecallablestatement.registerOutParameter(1, 12);
                 oraclecallablestatement.setString(2, pPositionId);      
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
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean )
    {
         if (oapagecontext.isLovEvent()) 
         {
            if("FndPosition".equals(oapagecontext.getLovInputSourceId()))
           {
                VacancyAMImpl vVacancyAM  = (VacancyAMImpl) oapagecontext.getApplicationModule(oawebbean);
                if (vVacancyAM != null)
                {
                     OADBTransaction voadbtransaction = vVacancyAM.getOADBTransaction();
                     if (voadbtransaction != null)
                     {
                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                        IrcEditVacancyVOImpl vIrcEditVacancyVO = vVacancyAM.getIrcEditVacancyVO() ;
                        if ( vIrcEditVacancyVO != null)
                        {
                            IrcEditVacancyVORowImpl vIrcEditVacancyVORow = (IrcEditVacancyVORowImpl) vIrcEditVacancyVO.getCurrentRow() ;
                            if (vIrcEditVacancyVORow != null )
                            {
                             String vPositionId = "" ;
                             vPositionId = vIrcEditVacancyVORow.getPositionId().toString();
                             if (!vPositionId.equals(""))
                             {
                               String vGradeid = "" ;
                               vGradeid= GetPositionGrade (voracleconnection,vPositionId) ;  
                               if (vGradeid != null )
                               {
                                if (!vGradeid.equals(""))
                                {
                                    vIrcEditVacancyVORow.setGradeId(new Number ( Integer.parseInt(vGradeid)) );
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
                   

    
             
       
     


      