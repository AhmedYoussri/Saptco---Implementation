package xxpon.oracle.apps.pon.negotiation.inquiry.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.pon.negotiation.inquiry.server.AuctionHeadersAllVOImpl;
import oracle.apps.pon.negotiation.inquiry.server.AuctionHeadersAllVORowImpl;
import oracle.apps.pon.negotiation.inquiry.server.NegSummaryAMImpl;
import oracle.apps.pon.negotiation.inquiry.webui.NegotiationSummaryCO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxNegotiationSummaryCO extends NegotiationSummaryCO {
    public xxNegotiationSummaryCO() {
    }
    
    String UnlockStatus = "" ;
    String UnlockDetailes = "" ;
    
    private void UnlockRFX(OracleConnection poracleconnection, String pAuctionId,String pUnlockType)
    {
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin XX_RFX_UNLOCK_APRVL_PKG.UNLOCK_RFX (:1,:2,:3,:4); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.setString(1, pAuctionId);
                oraclecallablestatement.setString(2, pUnlockType);
                oraclecallablestatement.registerOutParameter(3, 12);
                oraclecallablestatement.registerOutParameter(4, 12);
                oraclecallablestatement.execute();
                UnlockStatus = oraclecallablestatement.getString(3);
                UnlockDetailes = oraclecallablestatement.getString(4);
                oraclecallablestatement.close();
            }
        }
        catch(Exception sqlexception)
        {
            throw OAException.wrapperException(sqlexception);
        }
    }

    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {
        boolean CallSuper = true ;
        int i =-1;
        if(oapagecontext.getParameter("GoBtnTop") != null)
            try
            {
                i = Integer.parseInt(oapagecontext.getParameter("ActionListTop"));
            }
            catch(Exception exception)
            {
                i = -1;
            }
        else
        if(oapagecontext.getParameter("GoBtnBottom") != null)
            try
            {
                i = Integer.parseInt(oapagecontext.getParameter("ActionListBottom"));
            }
            catch(Exception exception1)
            {
                i = -1;
            }
        if ( i == 4 /*Unlock*/  || i == 62 /*Unlock Tech*/  || i == 64 /*Unlock Com*/ )
        {
            NegSummaryAMImpl vNegSummaryAM = (NegSummaryAMImpl) oapagecontext.getApplicationModule(oawebbean);
            if (vNegSummaryAM != null)
            {
                OADBTransaction voadbtransaction = vNegSummaryAM.getOADBTransaction();
                if (voadbtransaction != null)
                {
                    OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                    if (voracleconnection != null)
                    {
                        AuctionHeadersAllVOImpl vAuctionHeadersAllVO = (AuctionHeadersAllVOImpl) vNegSummaryAM.getAuctionHeadersAllVO() ;
                        if (vAuctionHeadersAllVO != null)
                        {
                            AuctionHeadersAllVORowImpl vAuctionHeadersAllVORow = (AuctionHeadersAllVORowImpl) vAuctionHeadersAllVO.getCurrentRow() ;
                            if (vAuctionHeadersAllVORow!= null)
                            {
                                if (vAuctionHeadersAllVORow.getAuctionHeaderId()!=null)
                                {
                                    UnlockRFX (voracleconnection,vAuctionHeadersAllVORow.getAuctionHeaderId().toString(), Integer.toString(i) ) ; 
                                    if (UnlockStatus.equals("PENDING") || UnlockStatus.equals("ERROR") )
                                    {
                                        CallSuper = false ;
                                        throw new OAException(UnlockDetailes,OAException.ERROR);
                                    }
                                    else if (UnlockStatus.equals("REJECTED") || UnlockStatus.equals("NA") ) 
                                    {
                                        CallSuper = false ;
                                        throw new OAException(UnlockDetailes,OAException.INFORMATION);
                                    }
                                }    
                            }    
                        }    
                    }
                }    
            }    
        }
        if (CallSuper)
        {
            super.processFormRequest(oapagecontext, oawebbean);
        }    
    }
}
