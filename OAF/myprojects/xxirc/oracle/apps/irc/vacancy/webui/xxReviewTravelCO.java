package xxirc.oracle.apps.irc.vacancy.webui;

import oracle.apps.irc.vacancy.server.VacancyAMImpl;
import oracle.apps.irc.vacancy.webui.ReviewTravelCO;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.irc.vacancy.server.IrcVacancyCompetenceVOImpl;

public class xxReviewTravelCO extends ReviewTravelCO {

    public xxReviewTravelCO() {
    }

    public void processRequest ( OAPageContext pageContext, OAWebBean webBean)
    {
        super.processRequest(pageContext,webBean);
        VacancyAMImpl vVacancyAM = (VacancyAMImpl)pageContext.getApplicationModule(webBean);
        if (vVacancyAM != null)
        {
            IrcVacancyCompetenceVOImpl vIrcVacancyCompetenceVO = vVacancyAM.getPerCompetenceElementsVO();
            if ( vIrcVacancyCompetenceVO != null)
            {
               vIrcVacancyCompetenceVO.setOrderByClause("SkillName");
               vIrcVacancyCompetenceVO.executeQuery();
            }
        }
    }
}
