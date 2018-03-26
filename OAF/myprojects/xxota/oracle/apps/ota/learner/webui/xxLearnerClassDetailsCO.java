package xxota.oracle.apps.ota.learner.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.ota.learner.server.CourseDetailsAMImpl;
import oracle.apps.ota.learner.webui.LearnerClassDetailsCO;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxLearnerClassDetailsCO extends LearnerClassDetailsCO {
    public xxLearnerClassDetailsCO() {
    }
    
    String UnEnRollStatus = "" ;
    String UnEnRollDetailes = "" ;
    
    private void UnEnRollBooking(OracleConnection poracleconnection, String pBookingId)
    {
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin XX_OTA_BOOKING_UNENROLL_AP_PKG.UNENROLL_BOOKING (:1,:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.setString(1, pBookingId);
                oraclecallablestatement.registerOutParameter(2, 12);
                oraclecallablestatement.registerOutParameter(3, 12);
                oraclecallablestatement.execute();
                UnEnRollStatus = oraclecallablestatement.getString(2);
                UnEnRollDetailes = oraclecallablestatement.getString(3);
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
        String vBookingId = oapagecontext.getDecryptedParameter("pEnrollmentId");
        if(vBookingId == null || "".equals(vBookingId))
        {
            vBookingId = (String)oapagecontext.getTransactionValue("pEnrollmentId");
        }
        if (vBookingId != null && !vBookingId.equals("") )
        {
            if(oapagecontext.getParameter("Go") != null)
            {
                OAMessageChoiceBean vSelActChoice = (OAMessageChoiceBean)oawebbean.findChildRecursive("SelActChoice");
                if(vSelActChoice != null)
                {
                    String vSelActChoiceValue = (String)vSelActChoice.getValue(oapagecontext);
                    if("UE".equals(vSelActChoiceValue)) /*UnEnroll*/
                    {
                        CourseDetailsAMImpl vCourseDetailsAM = (CourseDetailsAMImpl) oapagecontext.getApplicationModule(oawebbean);
                        if (vCourseDetailsAM != null)
                        {
                            OADBTransaction voadbtransaction = vCourseDetailsAM.getOADBTransaction();
                            if (voadbtransaction != null)
                            {
                                OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                                if (voracleconnection != null)
                                {
                                    UnEnRollBooking (voracleconnection,vBookingId) ; 
                                    if (UnEnRollStatus.equals("PENDING") || UnEnRollStatus.equals("ERROR") )
                                    {
                                        CallSuper = false ;
                                        throw new OAException(UnEnRollDetailes,OAException.ERROR);
                                    }
                                    else if (UnEnRollStatus.equals("REJECTED") || UnEnRollStatus.equals("NA") ) 
                                    {
                                        CallSuper = false ;
                                        throw new OAException(UnEnRollDetailes,OAException.INFORMATION);
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
        //throw new OAException(S,OAException.INFORMATION);
    }    
}
