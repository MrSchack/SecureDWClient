package dke.extension.data.dimension;

import dke.extension.data.dbConnection.ConnectionManager;

import dke.extension.logging.MyLogger;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;

public class DataDictionary {
    public DataDictionary() {
        super();
    }
    
    /**
     * Returns a List with successors of a Dimension
     * @param prev is the "parent" of the list
     * @return a List of Strings which presents the successors
     */
    public List<String> getDimensionList(String prev){
              Connection con;
              
        List<String> dimensionList = new LinkedList<String>();
        try {
            con = ConnectionManager.getInstance().localConnect();
            Statement stmt = con.createStatement();
            String query = "Select DISTINCT DIMNAME From DIMENSIONSCHEMA Where PREVDIM = '" + prev+"'";
            // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA
            ResultSet rs = stmt.executeQuery(query);
                  while (rs.next()) {
                      String dimName = (String)rs.getObject(1);
                      dimensionList.add(dimName);
            }
        } catch (SQLException e) {
            MyLogger.logMessage(e.getMessage());
        }
            return dimensionList;
    }
    
    /**
     * This method returns a List of Attributes of a Dimension.
     * @param name is the name of the Dimension you want to know the attributes of
     * @return a List of Strings which represents the attributes
     */
    public List<String> getDimensionAttributes(String name){
      Connection con;
      
      List<String> dimensionAttributeList = new LinkedList<String>();
      try {
      con = ConnectionManager.getInstance().localConnect();
      Statement stmt = con.createStatement();
      String query = "Select DISTINCT DIMATTRIBUTE From DIMENSIONATTRIBUTE Where DIMNAME = '" + name + "'";
      // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA

      ResultSet rs = stmt.executeQuery(query);
          while (rs.next()) {
              String dimAttribute = (String)rs.getObject(1);
              dimensionAttributeList.add(dimAttribute);
      }
      } catch (SQLException e) {
      MyLogger.logMessage(e.getMessage());
      }
      return dimensionAttributeList;
      
    }
 
  /**
     * Returns you the name of the FactTable
     * @return the name of the FactTable as a String
     */
  public String getFactTableName(){
    Connection con;
    
    String name = "";
    try {
    con = ConnectionManager.getInstance().localConnect();
    Statement stmt = con.createStatement();
    String query = "Select DIMNAME From DIMENSIONSCHEMA Where PREVDIM is null";
    //The Facttable is the root and has no previous dimensions -> PREVDIM = null
    // You can find all the relations between Dimensions if you look in the table DIMENSIONSCHEMA

    ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            name = (String)rs.getObject(1);
    }
    } catch (SQLException e) {
    MyLogger.logMessage(e.getMessage());
    }
    return name;
  }
}
