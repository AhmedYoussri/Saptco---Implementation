package xxicx.oracle.apps.icx.icatalog.shopping.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.server.OAViewObjectImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.icx.icatalog.shopping.webui.SearchResultsCO;
import oracle.apps.icx.por.req.server.RequisitionAMImpl;
import oracle.apps.icx.por.req.server.ShoppingCartVOImpl;

import oracle.apps.icx.por.req.server.ShoppingCartVORowImpl;

import oracle.jbo.Row;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxSearchResultsCO extends SearchResultsCO 
{
    public xxSearchResultsCO() 
    {
    }
    
    private String CheckIfCanAddItemInCart(OracleConnection poracleconnection, String  pHeaderId , String pItemId)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_PR_PKG.CHK_IF_CAN_ADD_ITM_IN_CART (:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2,pHeaderId );
                oraclecallablestatement.setString(3,pItemId );
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
        String vevent = oapagecontext.getParameter("event");
        if("addToCart".equals(vevent) || "addToCartMultiple".equals(vevent))
        {
            RequisitionAMImpl vRequisitionAM = (RequisitionAMImpl) oapagecontext.getApplicationModule(oawebbean);
            if (vRequisitionAM!= null)
            {
                OADBTransaction voadbtransaction = vRequisitionAM.getOADBTransaction();
                if (voadbtransaction != null)
                {
                    OracleConnection voracleconnection = (OracleConnection)voadbtransaction.getJdbcConnection();
                    String vResultsVOName = oapagecontext.getParameter("ResultsVOName") ; 
                    OAViewObjectImpl vCategoryBrowseVO = (OAViewObjectImpl) vRequisitionAM.findViewObject(vResultsVOName) ;
                    if (vCategoryBrowseVO!=null)
                    {
                        boolean flag = false ;
                        if ("addToCart".equals(vevent) )
                        {
                              flag = true ;                       
                        }
                        Row arow[] = super.getSelectedVORows(oapagecontext, vRequisitionAM, vResultsVOName, flag);
                        int i = arow.length;
                        if(i > 0)
                        {
                            for(int j = 0; j < i; j++)
                            {
                                if (arow[j].getAttribute("ItemId") != null )
                                {
                                    String vItemId = arow[j].getAttribute("ItemId").toString();
                                    if (!vItemId.equals(""))
                                    {
                                        ShoppingCartVOImpl vShoppingCartVO = vRequisitionAM.getShoppingCartVO() ;
                                        if ( vShoppingCartVO != null )
                                        {
                                            vShoppingCartVO.first(); 
                                            ShoppingCartVORowImpl  vShoppingCartVORow = (ShoppingCartVORowImpl) vShoppingCartVO.getCurrentRow() ;
                                            if (vShoppingCartVORow != null)
                                            {
                                                if (vShoppingCartVORow.getHeaderId() != null)
                                                {
                                                    String vHeaderId = vShoppingCartVORow.getHeaderId().toString() ;
                                                    if (!vHeaderId.equals(""))
                                                    {
                                                        String vResult = CheckIfCanAddItemInCart (voracleconnection,vHeaderId,vItemId) ; 
                                                        if (! vResult.equals("VALID"))
                                                        {
                                                            throw new OAException(vResult,OAException.ERROR);
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
