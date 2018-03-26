package xxpon.oracle.apps.pon.response.creation.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.server.OAViewObjectImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.server.FndAttachedDocumentsDomExtensionVOImpl;
import oracle.apps.fnd.server.FndAttachedDocumentsDomExtensionVORowImpl;
import oracle.apps.fnd.server.OAAttachmentsAMImpl;
import oracle.apps.pon.response.creation.server.BidAttributesVORowImpl;
import oracle.apps.pon.response.creation.server.BidHeaderSectionsVORowImpl;
import oracle.apps.pon.response.creation.server.ResponseAMImpl;
import oracle.apps.pon.response.creation.webui.ResponsePageLevelCO;

import oracle.jbo.RowIterator;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxResponsePageLevelCO extends ResponsePageLevelCO {
    public xxResponsePageLevelCO() {
    }
  
    
   private String CheckAucationAttributeIsAtt(OracleConnection poracleconnection, String PAuctionheaderId,String PAttributeName)
           {
               String Result = "";
               OracleCallableStatement oraclecallablestatement = null;
               try
               {
                   oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_PON_PKG.CHK_AUCTION_ATTRIBUTE_IS_ATT (:2,:3); end;");
                   if(oraclecallablestatement != null)
                   {
                       oraclecallablestatement.registerOutParameter(1, 12);
                       oraclecallablestatement.setString(2, PAuctionheaderId);
                       oraclecallablestatement.setString(3, PAttributeName);
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
    public void processFormRequest(OAPageContext oapagecontext, OAWebBean oawebbean)
    {    
           
           if (oapagecontext.getParameter("ContinueBtn") != null)
           {
                ResponseAMImpl vResponseAM  = (ResponseAMImpl) oapagecontext.getApplicationModule(oawebbean); 
                if (vResponseAM != null)
                {
                    OADBTransaction voadbtransaction = vResponseAM.getOADBTransaction();
                    if (voadbtransaction != null)
                    {
                        OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                        OAViewObjectImpl  vBidHeaderSectionsVO= vResponseAM.getBidHeaderSectionsVO() ; 
                        if (vBidHeaderSectionsVO != null)
                        {
                           vBidHeaderSectionsVO.first();
                           for(BidHeaderSectionsVORowImpl vBidHeaderSectionsVORow = (BidHeaderSectionsVORowImpl)vBidHeaderSectionsVO.getCurrentRow(); vBidHeaderSectionsVORow != null; vBidHeaderSectionsVORow = (BidHeaderSectionsVORowImpl)vBidHeaderSectionsVO.next())                         
                           { 
                              RowIterator  vBidHeaderAttributesVO =vBidHeaderSectionsVORow.getBidHeaderAttributesVO();
                              if (vBidHeaderAttributesVO != null)
                              {
                                vBidHeaderAttributesVO.first();
                                for(BidAttributesVORowImpl  vBidHeaderAttributesVORow = (BidAttributesVORowImpl)vBidHeaderAttributesVO.getCurrentRow(); vBidHeaderAttributesVORow != null; vBidHeaderAttributesVORow = (BidAttributesVORowImpl )vBidHeaderAttributesVO.next())                         
                                {
                                   String vAuctionHeaderId ="";
                                   if (vBidHeaderAttributesVORow.getAuctionHeaderId() != null )
                                   {
                                      if (!vBidHeaderAttributesVORow.getAuctionHeaderId().equals (""))
                                      { 
                                        vAuctionHeaderId=vBidHeaderAttributesVORow.getAuctionHeaderId().toString();
                                        String vAttributeName ="";
                                        if (vBidHeaderAttributesVORow.getAttributeName() != null )
                                        {
                                          if (!vBidHeaderAttributesVORow.getAttributeName().equals (""))
                                          {
                                             vAttributeName=vBidHeaderAttributesVORow.getAttributeName();
                                             boolean VcheckauctionAtt = false ; 
                                             String vCheckAucationAttributeIsAtt= CheckAucationAttributeIsAtt (voracleconnection,vAuctionHeaderId,vAttributeName ) ; 
                                             if (vCheckAucationAttributeIsAtt.equals("Y"))
                                             {
                                                VcheckauctionAtt = true ;  
                                             }                
                                             else
                                             {
                                                 if (vCheckAucationAttributeIsAtt.equals("NA"))
                                                 {
                                                    VcheckauctionAtt = false ;  
                                                 }
                                                 else
                                                 {
                                                     if (vCheckAucationAttributeIsAtt.equals("N"))
                                                     {  
                                                          if (vBidHeaderAttributesVORow.getValueDisplayString() != null )
                                                          {    
                                                             if (!vBidHeaderAttributesVORow.getValueDisplayString().equals (""))
                                                             {
                                                                  VcheckauctionAtt = true ;        
                                                             }
                                                           }
                                                      }
                                                 }    
                                             }                                              
                                             if (VcheckauctionAtt)
                                             {
                                                String vValueDisplayString  ="";
                                                boolean vFoundAttachements = false ;
                                                if (vBidHeaderAttributesVORow.getValueDisplayString() != null )
                                                {       
                                                  if (!vBidHeaderAttributesVORow.getValueDisplayString().equals (""))
                                                  {
                                                      vValueDisplayString= vBidHeaderAttributesVORow.getValueDisplayString() ;
                                                      OAMessageStyledTextBean UserEntityName = (OAMessageStyledTextBean) oawebbean.findChildRecursive("UserEntityName") ;
                                                      if (UserEntityName != null)
                                                      {
                                                          OAAttachmentsAMImpl vOAAttachmentsAM = (OAAttachmentsAMImpl) oapagecontext.getApplicationModule(UserEntityName); 
                                                          if (vOAAttachmentsAM !=null)
                                                          {
                                                             
                                                                  FndAttachedDocumentsDomExtensionVOImpl vAttach_0_FndAttachedDocumentsDomExtensionVO = (FndAttachedDocumentsDomExtensionVOImpl) vOAAttachmentsAM.findViewObject(UserEntityName.getViewUsageName()) ;
                                                                  if (vAttach_0_FndAttachedDocumentsDomExtensionVO != null)
                                                                  {
                                                                      
                                                                          if (vAttach_0_FndAttachedDocumentsDomExtensionVO.getRowCount() > 0 )
                                                                          {
                                                                                  vAttach_0_FndAttachedDocumentsDomExtensionVO.first();
                                                                                  for(FndAttachedDocumentsDomExtensionVORowImpl vAttach_0_FndAttachedDocumentsDomExtensionVORow = (FndAttachedDocumentsDomExtensionVORowImpl)vAttach_0_FndAttachedDocumentsDomExtensionVO.getCurrentRow(); vAttach_0_FndAttachedDocumentsDomExtensionVORow != null; vAttach_0_FndAttachedDocumentsDomExtensionVORow = (FndAttachedDocumentsDomExtensionVORowImpl)vAttach_0_FndAttachedDocumentsDomExtensionVO.next()) 
                                                                                  {
                                                                                          
                                                                                    if (vAttach_0_FndAttachedDocumentsDomExtensionVORow.getTitle().equals(vValueDisplayString))
                                                                                    {
                                                                                        vFoundAttachements = true ;     
                                                                                    }
                                                                              
                                                                                  }    
                                                                          }
                                                                  }
                                                          }
                                                        }
                                                    }
                                                }
                                                if (!vFoundAttachements)
                                                {
                                                    throw new OAException(oapagecontext.getMessage("XXSCM" , "XX_SCM_SUP_NO_ATT"  , null).concat(" ").concat(vValueDisplayString) ,OAException.ERROR); 
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
                          
        super.processFormRequest(oapagecontext, oawebbean);
  }
}