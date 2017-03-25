/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public void insertIndexerUrl(int id)
{
    
        try {
            statementQ.executeUpdate("INSERT INTO `indexerurl` values('"+id+"')");
            
      
        }
        catch (SQLException ex) {
            // Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        

     
}

public  void insertSet(String url2,int crawlID) throws IOException
{
    ResultSet urls = null;
    int counT=0;
              try {
                  url2= url2.replaceAll("'","\\\\'" );
                  urls = statementQ.executeQuery("select count(*) AS COUNT1 from crawlerset where URL='"+url2+"'");
                  urls.next();
                   counT=urls.getInt(1);
            if(counT==0)
                {
                    statementQ.executeUpdate("insert into crawlerset (CrawlerID,URL)  values('"+crawlID+"','"+url2 +"')");
                }
            else 
            {
                
                int id= getsetId(url2);
                     File oldFile = new File("DOCUMENTS/"+id+".txt");
                    oldFile.delete();
                    
                statementQ.executeUpdate("Update  crawlerset SET `CrawlerID` ='"+crawlID+"' where URL='"+url2+"'");
            }
                
              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
             
     
}

public void clearWordCount(String s)
{
    try {
        statementQ.executeUpdate("DELETE FROM word_cnt where url_id = "+s);
    } catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void pageIsIndexed(String url_id)
{
    try {
        statementQ.executeUpdate("DELETE FROM  indexerurl where url_id = "+url_id);
    } catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void insertWordIntoWordCnt(String word,String url_id,boolean title,int position)
{
    try {
        boolean flag = true;
         for(int i =0 ; i <word.length(); i++)
         {
             if(word.charAt(i)!= '?')
             {
                 flag = false;
                 break;
             }
         }
        if(word.length() > 20 || flag)
            return;
        statementQ.executeUpdate("INSERT INTO `word_cnt`(`word`, `url_id`, `title`, `position`) VALUES ('"+ word +"',"+url_id+","+ String.valueOf(title)+","+ String.valueOf(position)+ ")");
    } catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(word);
    }
} 
public void insertIntoWordCntPerPage(String s , int cnt)
{
    String Tmp = String.valueOf(cnt);
    try {
        statementQ.executeUpdate("delete from word_cnt_perpage where url_id = " + s);
        statementQ.executeUpdate("INSERT INTO `word_cnt_perpage`(`url_id`, `cnt`) VALUES ("+"'"+ s +"'" +"," +Tmp + ")");
    } catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public ResultSet getIndexerUrl()
{
    ResultSet Res = null;
    
    try {
         Res = statementQ.executeQuery("select * From  indexerurl");
    } catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Res;
}
public void refreshUrlWordCnt(int cnt,String url)
{
    ResultSet Res = null;
    
    try {
        Res = statementQ.executeQuery("select * from word_cnt_perpage where url_id = "+url);
      } 
        catch (SQLException ex) {
        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public int getSize(String TableName, int crawlID)
{
    ResultSet urls = null;
    int counT=0;
              try {
                  
                  urls = statementQ.executeQuery("select count(*) AS COUNT1 from `"+TableName+"` WHERE crawlerID='"+crawlID+"'");
                 urls.next();
                   counT=urls.getInt(1);
            
              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
             
     return counT;
}
public boolean containsSet(String url2,int CrawlerID)
{
    ResultSet urls = null;
    int counT=0;
              try {
                 url2= url2.replaceAll("'","\\\\'" );
                  urls = statementQ.executeQuery("select count(*)  from crawlerset where URL ='"+url2+"' AND  CrawlerID ='"+CrawlerID+"'");
                   urls.next();
                   counT=urls.getInt(1);
                   
                  
            if(counT==0)
            {
               
                 statementQ.executeUpdate("UPDATE  `crawlerset` SET `CrawlerID` ='" +CrawlerID+"' where `URL` ='"+url2+"'" );
                 
                return false;
            }
                
            else
                return true;
              } catch (SQLException ex) {
                  System.out.print("el error f om l link da "+ url2);
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
public int getMaxCrawler()
{
    int count=0;
    ResultSet urls= null;
 try {
                  urls = statementQ.executeQuery("SELECT max(`CrawlerID`) FROM `crawlerset`");
                  
                  urls.next();
                  
                  count=urls.getInt(1);
                  

              } catch (SQLException ex) {
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
            return count;
}
public int getsetId(String url2)
{
    int id=0;
     url2= url2.replaceAll("'","\\\\'" );
    ResultSet urls= null;
 try {
                  urls = statementQ.executeQuery("SELECT setID FROM `crawlerset`  where `URL` ='"+url2+"'");
                  
                  urls.next();
                  
                  id=urls.getInt("setID");
                  

              } catch (SQLException ex) {
                 // System.out.println("SELECT setID FROM `crawlerset`  where `URL` ='"+url2+"'");
                  Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
              }
            return id;
}

}

    