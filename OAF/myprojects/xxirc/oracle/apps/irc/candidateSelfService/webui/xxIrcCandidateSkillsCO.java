package xxirc.oracle.apps.irc.candidateSelfService.webui;

import oracle.apps.irc.candidateSelfService.webui.IrcCandidateSkillsCO;
import oracle.apps.irc.candidateSelfService.server.IrcCandidatePersonalAccountAMImpl;
import oracle.apps.irc.candidateSelfService.server.IrcCandidateSkillsVOImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;


public class xxIrcCandidateSkillsCO extends IrcCandidateSkillsCO {
    public xxIrcCandidateSkillsCO() {
    }
    
    public void processRequest ( OAPageContext pageContext, OAWebBean webBean)
    {
        super.processRequest(pageContext,webBean);
         IrcCandidatePersonalAccountAMImpl vCandidatePersonalAccountAM = (IrcCandidatePersonalAccountAMImpl)pageContext.getApplicationModule(webBean);
         IrcCandidateSkillsVOImpl vCandidateSkillsVO = vCandidatePersonalAccountAM.getIrcCandidateSkillsVO();
        if ( vCandidateSkillsVO != null)
        {
           vCandidateSkillsVO.setOrderByClause("SkillNameInt");
           vCandidateSkillsVO.executeQuery();
        }
    }
}
