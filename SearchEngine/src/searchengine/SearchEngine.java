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
        DataBase DB2=new DataBase("root","");
//        DB2.deleteAll("crawlerset");
//        DB2.deleteAll("crawlerqueue");
//        DB2.deleteAll("basecount");

        WebCrawler seed1=new WebCrawler("https://en.wikipedia.org/",20);//total size as input
        WebCrawler seed2=new WebCrawler("https://www.google.com.eg/",20);
        WebCrawler seed3=new WebCrawler("https://DMOZ.org/",20);
        
        try{
            seed1.join();
            seed2.join();
            seed3.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
         System.out.println("Size:");
        
        System.out.println(DB2.getSize("crawlerqueue"));
       // System.out.println(m1.MapCount.get("www.google.com.eg"));
       
    }
    
}
    
