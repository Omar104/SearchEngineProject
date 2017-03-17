/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;

/**
 *
 * @author Omarove10
 */
public class DataBase {
private String url = "jdbc:mysql://localhost:3306/searchengine";
private String user = "";
private String password = "";
private Statement statementQ;
public DataBase(String User,String Password) 
{
    user=User;
    password=Password;
    try {
        // Load the Connector/J driver
        Object newInstance = Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
        statementQ = conn.createStatement();
        } catch (IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
        }     catch (ClassNotFoundException | SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
// Establish connection to MySQL
              
// Establish connection to MySQL
        
        
}
      
public  void insertSet(String url2)
{
    ResultSet urls = null;
    int counT=0;
              try {
                  urls = statementQ.executeQuery("select count(*) AS COUNT1 from crawlerset where URL='"+url2+"'");
                  urls.next();
                   counT=urls.getInt(1);
            if(counT==0)
                {
                    statementQ.executeUpdate("insert into crawlerset values('"+url2+"')");
                }
              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
             
     
}
public int getSize(String TableName)
{
    ResultSet urls = null;
    int counT=0;
              try {
                  urls = statementQ.executeQuery("select count(*) AS COUNT1 from `"+TableName+"`");
                 urls.next();
                   counT=urls.getInt(1);
            
              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
             
     return counT;
}
public boolean containsSet(String url2)
{
    ResultSet urls = null;
    int counT=0;
              try {
                  urls = statementQ.executeQuery("select count(*) AS COUNT1 from crawlerset where URL='"+url2+"'");
                   urls.next();
                   counT=urls.getInt(1);
                   
                  
            if(counT==0)
                return false;
                
            else
                return true;
              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
             return false;
     
}
public  void deleteAll(String TableName)
{
        try {
            statementQ.executeUpdate("DELETE FROM `"+TableName+"` WHERE 1");

        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
            
}
public void insertMap(String url2)
{
    ResultSet urls = null;
    int counT=0;
        try {
            urls = statementQ.executeQuery("select count(*) AS COUNT1 from basecount where BaseUrl='"+url2+"'");
            urls.next();   
             counT=urls.getInt("COUNT1");
            
      if(counT==0)
          {
              statementQ.executeUpdate("insert into basecount values('"+url2+"','1')");
          }
      else
      {
          statementQ.executeUpdate("update basecount set `count`= count+1 where BaseUrl = '"+url2+"' ");
      }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

     
}
public int getCountMap(String url2)
{
    ResultSet urls = null;
    int counT=0;
              try {
                  urls = statementQ.executeQuery("select count from basecount where BaseUrl='"+url2+"'");
                  
                  if( urls.first())
                  {
                      counT=urls.getInt("count");
                  }

              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
            
     return counT;
}
public void insertQueue(String url2)
{
    
        try {
            statementQ.executeUpdate("INSERT INTO `crawlerqueue`(`UrlName`) VALUES ('"+url2+"')");
            
      
        }
        catch (SQLException ex) {
            // Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        

     
}
public void deleteRecord(String url2,String TableName,String col)
{
    
        try {
            statementQ.executeUpdate("DELETE FROM `"+TableName+"` WHERE `"+col+"` ='"+url2+"'");
            
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

     
}
public  Queue<String> getQueue()
{
    Queue<String> q= new LinkedList<>();
    ResultSet urls = null;
     try {
            urls=statementQ.executeQuery("Select * from `crawlerqueue` where 1 ORDER BY `qID` ASC");
            while(urls.next()){
             q.add(urls.getString("UrlName"));
            }
            
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    

return q;
}
public void setAutoIncQueue()
{
    
     try {
           statementQ.executeUpdate("ALTER TABLE `crawlerqueue` auto_increment = 1");
            
            
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public  int getQueueFrontID()
{
    ResultSet urls = null;
    int qID=0;
     try {
            urls=statementQ.executeQuery("SELECT * FROM crawlerqueue Limit 1");
            urls.next();  
            qID=urls.getInt("qID");
            
            
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    

return qID;
}
public  void deleteQueueFront()
{
    int id=getQueueFrontID();
    deleteRecord(Integer.toString(id),"crawlerqueue","qID");

}

}

