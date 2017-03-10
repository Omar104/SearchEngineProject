/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omarove10
 */
public class SearchEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    DataBase DB1=new DataBase("root","");
    System.out.println(DB1.getSize("crawlerset"));
    DB1.insertSet("Barca");
    DB1.insertSet("Barca");
    System.out.println(DB1.containsSet("Barcelona"));
    DB1.insertSet("Barcelona");
    DB1.insertSet("Barcelona");
    DB1.insertSet("Barcelona");
    System.out.println(DB1.containsSet("Barcelona"));
    DB1.insertSet("MSN");
    DB1.insertSet("MSN");
    DB1.insertSet("6-5");
    System.out.println(DB1.getSize("crawlerset"));
    DB1.deleteAll("crawlerset");
    DB1.insertMap("BARCA$EVER");
    
    System.out.println(DB1.getCountMap("BARCA$EVER"));
    DB1.deleteAll("crawlerqueue");
    DB1.insertQueue("YESWECAN");
    DB1.insertQueue("YESWECAN2");
    DB1.deleteRecord("YESWECAN","crawlerqueue","UrlName");
    
    }
//        
//        
//        
//        
//        
//        
//        
//        
//        // TODO code application logic here
//        DataSet s1 = new DataSet();
//        DataMap m1 = new DataMap();
//  
//      //  System.out.println(m1.MapCount.get("h"));
//        WebCrawler seed1=new WebCrawler("https://en.wikipedia.org/",s1,m1,200);//total size as input
//        WebCrawler seed2=new WebCrawler("https://www.goolge.com.eg/",s1,m1,200);
//        try{
//            seed1.join();
//            seed2.join();
//        }
//        catch (InterruptedException e)
//        {
//            System.out.println("seed1  interputed"); 
//        }
//         System.out.println("Size:");
//        System.out.println(s1.Get_Size());
//       // System.out.println(m1.MapCount.get("www.google.com.eg"));
//       s1.Print_Set();
//    }
//    
}
    
