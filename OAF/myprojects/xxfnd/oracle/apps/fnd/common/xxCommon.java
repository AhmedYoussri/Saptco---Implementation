package xxfnd.oracle.apps.fnd.common;

import oracle.apps.fnd.framework.OAException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;

public class xxCommon {
    public xxCommon() {
    }
    
    public String GetLookUpValueMeaning(OracleConnection poracleconnection, String pApplicationShortName , String pLookUpType , String pLookUpCode )
    {
       return GetLookUpValueMeaning(poracleconnection, pApplicationShortName , pLookUpType , pLookUpCode , "") ;
    }
    public String GetLookUpValueMeaning(OracleConnection poracleconnection, String pApplicationShortName , String pLookUpType , String pLookUpCode , String pLanguage)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_FND_PKG.GET_LOOKUP_VALUE_MEANING (:2,:3,:4,:5); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pApplicationShortName);
                oraclecallablestatement.setString(3, pLookUpType);
                oraclecallablestatement.setString(4, pLookUpCode);
                oraclecallablestatement.setString(5, pLanguage);
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
        
        //test
        
    }
    
    public String GetValueSetValueMeaning(OracleConnection poracleconnection, String pValueSet , String pValue)
    {
       return GetValueSetValueMeaning(poracleconnection, pValueSet , pValue, "", "", "") ;
    }
    public String GetValueSetValueMeaning(OracleConnection poracleconnection, String pValueSet , String pValue , String pParentLow , String pParentHigh ,String pLanguage)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_FND_PKG.GET_FLEX_VALUE_MEANING (:2,:3,:4,:5,:6); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pValueSet);
                oraclecallablestatement.setString(3, pValue);
                oraclecallablestatement.setString(4, pParentLow);
                oraclecallablestatement.setString(5, pParentHigh);
                oraclecallablestatement.setString(6, pLanguage);
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
    public String GetValueSetValueDescription(OracleConnection poracleconnection, String pValueSet , String pValue)
    {
       return GetValueSetValueDescription(poracleconnection, pValueSet , pValue, "", "", "") ;
    }
    public String GetValueSetValueDescription(OracleConnection poracleconnection, String pValueSet , String pValue , String pParentLow , String pParentHigh ,String pLanguage)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_FND_PKG.GET_FLEX_VALUE_DESCRIPTION (:2,:3,:4,:5,:6); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pValueSet);
                oraclecallablestatement.setString(3, pValue);
                oraclecallablestatement.setString(4, pParentLow);
                oraclecallablestatement.setString(5, pParentHigh);
                oraclecallablestatement.setString(6, pLanguage);
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

    public String GetHRLocationCode(OracleConnection poracleconnection, String pLocationID )
    {
       return GetHRLocationCode(poracleconnection, pLocationID,"") ;
    }
    public String GetHRLocationCode(OracleConnection poracleconnection, String pLocationID , String pLanguage)
    {
        String Result = "";
        OracleCallableStatement oraclecallablestatement = null;
        try
        {
            oraclecallablestatement = (OracleCallableStatement)poracleconnection.prepareCall(" begin :1 := XX_HR_PKG.GET_LOCATION_CODE (:2,:3); end;");
            if(oraclecallablestatement != null)
            {
                oraclecallablestatement.registerOutParameter(1, 12);
                oraclecallablestatement.setString(2, pLocationID);
                oraclecallablestatement.setString(3, pLanguage);
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
}
