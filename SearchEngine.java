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
import java.util.LinkedList;
import java.util.Queue;
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
       
        DataBase DB2 = new DataBase("root","");
        
//        System.out.println(DB2.getsetId("https://chromecast.com"));
        DB2.deleteAll("basecount");
        //DB2.deleteAll("crawlerqueue");
       // DB2.deleteAll("crawlerset");
        int maxCrawler=DB2.getMaxCrawler();
        WebCrawler seed1=new WebCrawler("https://en.wikipedia.org/",50,maxCrawler+1);
        WebCrawler seed2=new WebCrawler("https://google.com.eg/",500,maxCrawler+1);
   
        try{
            seed1.join();
            seed2.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
       
        
        System.out.println("get "+DB2.getSize("crawlerset",maxCrawler+1));
       
    }
    
}
    
