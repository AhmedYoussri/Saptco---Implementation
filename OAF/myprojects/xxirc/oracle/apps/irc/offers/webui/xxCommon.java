package xxirc.oracle.apps.irc.offers.webui;

import oracle.apps.fnd.framework.OAException;

import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.irc.applicant.server.ApplicationDetailsVOImpl;
import oracle.apps.irc.applicant.server.ApplicationDetailsVORowImpl;
import oracle.apps.irc.offers.server.IrcOfferDetailsVOImpl;
import oracle.apps.irc.offers.server.IrcOfferDetailsVORowImpl;
import oracle.apps.irc.offers.server.OffersAMImpl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

import oracle.cabo.ui.UIConstants;

public class xxCommon implements UIConstants{
    public xxCommon() {
    }
    private String CheckIsPersonNationalityIsSA(OracleConnection poracleconnection, String pPersonId)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PKG.CHK_EMP_IS_SA (:2); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pPersonId);
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

    public void ValidateTicketsForSAOffers(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        String strEvent= oapagecontext.getParameter(EVENT_PARAM) ;
        if(strEvent.equals("goto")) //Continue
        {
            OffersAMImpl vOffersAM  = (OffersAMImpl) oapagecontext.getApplicationModule(oawebbean);
            if (vOffersAM != null)
            {
                OADBTransaction voadbtransaction = vOffersAM.getOADBTransaction();
                if (voadbtransaction != null)
                {
                    OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                    ApplicationDetailsVOImpl vApplicationDetailsVO = vOffersAM.getApplicationDetailsVO() ;
                    if ( vApplicationDetailsVO != null)
                    {
                        ApplicationDetailsVORowImpl vApplicationDetailsVORow = (ApplicationDetailsVORowImpl) vApplicationDetailsVO.getCurrentRow() ;
                        if (vApplicationDetailsVORow != null )
                        {
                            String vPersonId = "" ;
                            vPersonId = vApplicationDetailsVORow.getPersonId().toString();
                            if (!vPersonId.equals(""))
                            {
                                String vIsPersonNationalityIsSA = CheckIsPersonNationalityIsSA (voracleconnection,vPersonId ) ; 
                                if (vIsPersonNationalityIsSA.equals("Y"))
                                {
                                    IrcOfferDetailsVOImpl vIrcOfferDetailsVO  = vOffersAM.getIrcOfferDetailsVO() ;
                                    if ( vApplicationDetailsVO != null)
                                    {
                                        IrcOfferDetailsVORowImpl vIrcOfferDetailsVORow = (IrcOfferDetailsVORowImpl) vIrcOfferDetailsVO.getCurrentRow() ;
                                        if (vIrcOfferDetailsVORow != null )
                                        {
                                            String vTicketType = "" ;
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vTicketType = vIrcOfferDetailsVORow.getAttribute4() ;
                                            }    
                                            String vTicketClass = "" ; 
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vTicketClass = vIrcOfferDetailsVORow.getAttribute5() ;
                                            }    
                                            String vTravelRoute = "" ;
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vTravelRoute = vIrcOfferDetailsVORow.getAttribute6() ;
                                            }    
                                            String vEmployeeTickets = "" ;
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vEmployeeTickets = vIrcOfferDetailsVORow.getAttribute7() ;
                                            }    
                                            String vSpouseTickets = "" ;
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vSpouseTickets = vIrcOfferDetailsVORow.getAttribute8() ;
                                            }    
                                            String vDependentsTickets = "" ;
                                            if (vIrcOfferDetailsVORow.getAttribute4() != null)
                                            {
                                                vDependentsTickets = vIrcOfferDetailsVORow.getAttribute9() ;
                                            }
                                            
                                            if (!vTicketType.equals("") || !vTicketClass.equals("") || !vTravelRoute.equals("") || !vEmployeeTickets.equals("")|| !vSpouseTickets.equals("")|| !vDependentsTickets.equals(""))
                                            {
                                                throw new OAException(oapagecontext.getMessage("XXHCM" , "XX_HR_SA_TICKETS" , null),OAException.ERROR);
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
