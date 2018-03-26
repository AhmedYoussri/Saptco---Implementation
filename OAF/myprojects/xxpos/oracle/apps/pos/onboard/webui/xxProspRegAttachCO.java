package xxpos.oracle.apps.pos.onboard.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.server.FndAttachedDocumentsDomExtensionVOImpl;
import oracle.apps.fnd.server.FndAttachedDocumentsDomExtensionVORowImpl;
import oracle.apps.fnd.server.OAAttachmentsAMImpl;
import oracle.apps.pos.onboard.webui.ProspRegAttachCO;

public class xxProspRegAttachCO extends ProspRegAttachCO {
    public xxProspRegAttachCO() {
    }

    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        if (oapagecontext.getParameter("RegisterButton") != null)
        {
            OAMessageStyledTextBean UserEntityName = (OAMessageStyledTextBean) oawebbean.findChildRecursive("UserEntityName") ;
            if (UserEntityName != null)
            {
                OAAttachmentsAMImpl vOAAttachmentsAM = (OAAttachmentsAMImpl) oapagecontext.getApplicationModule(UserEntityName); 
                if (vOAAttachmentsAM !=null)
                {
                    FndAttachedDocumentsDomExtensionVOImpl vAttach_0_FndAttachedDocumentsDomExtensionVO = (FndAttachedDocumentsDomExtensionVOImpl) vOAAttachmentsAM.findViewObject(UserEntityName.getViewUsageName()) ;
                    boolean vFoundAttachements = false ;
                    if (vAttach_0_FndAttachedDocumentsDomExtensionVO != null)
                    {
                        if (vAttach_0_FndAttachedDocumentsDomExtensionVO.getRowCount() > 0 )
                        {
                            vAttach_0_FndAttachedDocumentsDomExtensionVO.first();
                            for(FndAttachedDocumentsDomExtensionVORowImpl vAttach_0_FndAttachedDocumentsDomExtensionVORow = (FndAttachedDocumentsDomExtensionVORowImpl)vAttach_0_FndAttachedDocumentsDomExtensionVO.getCurrentRow(); vAttach_0_FndAttachedDocumentsDomExtensionVORow != null; vAttach_0_FndAttachedDocumentsDomExtensionVORow = (FndAttachedDocumentsDomExtensionVORowImpl)vAttach_0_FndAttachedDocumentsDomExtensionVO.next()) 
                            {
                                if ( vAttach_0_FndAttachedDocumentsDomExtensionVORow.getDatatypeId() != null )
                                {
                                    if (vAttach_0_FndAttachedDocumentsDomExtensionVORow.getDatatypeId().equals(6) /*File*/)
                                    {
                                        vFoundAttachements = true ;     
                                    }
                                }
                            }    
                        }
                    }
                    if (!vFoundAttachements)
                    {
                      throw new OAException(oapagecontext.getMessage("XXSCM" , "XX_SCM_SUP_NO_ATT"  , null),OAException.ERROR);
                    }
                     
                }
            }
           
        }
        super.processFormRequest(oapagecontext, oawebbean);
    }
}