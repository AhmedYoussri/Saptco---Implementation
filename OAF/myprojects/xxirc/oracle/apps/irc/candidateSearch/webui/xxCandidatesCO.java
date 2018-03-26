package xxirc.oracle.apps.irc.candidateSearch.webui;

import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.irc.candidateSearch.server.SearchAMImpl;
import oracle.apps.irc.candidateSearch.webui.CandidatesCO;
import oracle.apps.irc.candidateSearch.server.CandidateListVOImpl;


public class xxCandidatesCO extends CandidatesCO 
{
    public xxCandidatesCO() 
    {
    }

    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        super.processFormRequest(oapagecontext,oawebbean);
        String vIrcAction = getIrcAction(oapagecontext);
        if ("Go".equals(vIrcAction))
        {    
            String vXXSAPTCO_National_Identifier_Value = "X";
            String vXXSAPTCO_Nationality_Value = "X";
            String vXXSAPTCO_GENDER_Value = "X";
            String vXXSAPTCO_QUALIFICATION_Value = "X";

            OAMessageTextInputBean vXXSAPTCO_National_Identifier = (OAMessageTextInputBean) oawebbean.findChildRecursive("XXSAPTCO_National_Identifier") ;        
            OAMessageChoiceBean vXXSAPTCO_Nationality = (OAMessageChoiceBean) oawebbean.findChildRecursive("XXSAPTCO_Nationality");                     
            OAMessageChoiceBean vXXSAPTCO_GENDER = (OAMessageChoiceBean) oawebbean.findChildRecursive("XXSAPTCO_GENDER");                     
            OAMessageTextInputBean vXXSAPTCO_QUALIFICATION = (OAMessageTextInputBean) oawebbean.findChildRecursive("XXSAPTCO_QUALIFICATION") ;        

            if ( vXXSAPTCO_National_Identifier != null )
              {
                if ( vXXSAPTCO_National_Identifier.getValue(oapagecontext) != null )
                   { 
                       vXXSAPTCO_National_Identifier_Value = vXXSAPTCO_National_Identifier.getValue(oapagecontext).toString();
                   }
              }
            if ( vXXSAPTCO_Nationality != null )
              {
                if ( vXXSAPTCO_Nationality.getValue(oapagecontext) != null )
                   { 
                       vXXSAPTCO_Nationality_Value = vXXSAPTCO_Nationality.getValue(oapagecontext).toString();
                   }
              }
            if ( vXXSAPTCO_GENDER != null )
              {
                if ( vXXSAPTCO_GENDER.getValue(oapagecontext) != null )
                   { 
                       vXXSAPTCO_GENDER_Value = vXXSAPTCO_GENDER.getValue(oapagecontext).toString();
                   }
              }
            if ( vXXSAPTCO_QUALIFICATION != null )
              {
                if ( vXXSAPTCO_QUALIFICATION.getValue(oapagecontext) != null )
                   { 
                       vXXSAPTCO_QUALIFICATION_Value = vXXSAPTCO_QUALIFICATION.getValue(oapagecontext).toString();
                   }
              }
            
            if (!vXXSAPTCO_National_Identifier_Value.equals("X") || !vXXSAPTCO_Nationality_Value.equals("X") || !vXXSAPTCO_GENDER_Value.equals("X")|| !vXXSAPTCO_QUALIFICATION_Value.equals("X") )
            {
                SearchAMImpl vSearchAM  = (SearchAMImpl) oapagecontext.getApplicationModule(oawebbean); 
                if (vSearchAM != null )
                {
                    CandidateListVOImpl vCandidateListVOImpl = vSearchAM.getCandidateListVO();                    
                    if ( vCandidateListVOImpl != null )
                         {       
                             vCandidateListVOImpl.setWhereClause(vCandidateListVOImpl.getWhereClause()
                             .concat(" AND XX_HR_IRC_PKG.HNDL_CANDIDATES_SEARCH ( PPF.PERSON_ID , '"
                             .concat(vXXSAPTCO_National_Identifier_Value).concat("' , '")
                             .concat(vXXSAPTCO_Nationality_Value).concat("' , '")
                             .concat(vXXSAPTCO_GENDER_Value).concat("' , '")
                             .concat(vXXSAPTCO_QUALIFICATION_Value).concat("' ) = 'Y' ")));
                             vCandidateListVOImpl.executeQuery(); 
                         }
                }        
            }
        }
    }
    
               
}
