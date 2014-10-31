package mobile.Generics;

import database.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import mobile.DTO.Empleado;

import oracle.adfmf.util.Utility;

public class Queryable {
    public  ResultSet query(GenericCriteria criteria)
    {
        
        return doQuery(criteria.toString());
    }
    
    public  void insert(GenericCriteria criteria)
    {
         doQuery(criteria.getInsertQuery());
        
    }
    
    public  void update(GenericCriteria criteria)
    {
        
        doQuery(criteria.getUpdateQuery());
    }
    
    public  void delete(GenericCriteria criteria)
    {
        doQuery(criteria.getDeleteQuery());
    }
    
    private  ResultSet doQuery(String query){
        Connection conn = null;
       
            ResultSet result = null;
        try {
            conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(query);

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return result;

    }
    
    
}
