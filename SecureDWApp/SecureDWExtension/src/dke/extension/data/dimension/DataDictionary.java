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
    
    public List<String> getDimensionList(String prev){
              Connection con;
              
        List<String> dimensionList = new LinkedList<String>();
        try {
            con = ConnectionManager.getInstance().localConnect();
            Statement stmt = con.createStatement();
            String query = "Select DIMNAME From DIMENSIONSCHEMA Where PREVDIM = " + prev;
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
    
    public List<String> getDimensionAttributes(String name){
      Connection con;
      
      List<String> dimensionAttributeList = new LinkedList<String>();
      try {
      con = ConnectionManager.getInstance().localConnect();
      Statement stmt = con.createStatement();
      String query = "Select DIMATTRIBUTE From DIMENSIONATTRIBUTE Where DIMNAME = " + name;
      ResultSet rs = stmt.executeQuery(query);
          while (rs.next()) {
              String dimAttribute = (String)rs.getObject(2);
              dimensionAttributeList.add(dimAttribute);
      }
      } catch (SQLException e) {
      MyLogger.logMessage(e.getMessage());
      }
      return dimensionAttributeList;
      
    }
 
  public String getFactTableName(){
    Connection con;
    
    String name = "";
    try {
    con = ConnectionManager.getInstance().localConnect();
    Statement stmt = con.createStatement();
    String query = "Select DIMNAME From DIMENSIONSCHEMA Where PREVDIM = null";
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
