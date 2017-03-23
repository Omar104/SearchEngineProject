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
import java.util.Scanner;
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
        
        DB2.deleteAll("basecount");
        DB2.deleteAll("crawlerqueue");
        int maxCrawler=DB2.getMaxCrawler();
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter number of threads");
        int NumberOfThreads=scanner.nextInt();
        int TotalSize=5000;
        String URLSeeds[]={"https://en.wikipedia.org/","https://www.google.com.eg/","https://www.youtube.com","https://www.yahoo.com/"};
        WebCrawler[] Seeds =new WebCrawler[6];
        
        for(int i=0;i<NumberOfThreads;i++){
            Seeds[i]=(new WebCrawler(URLSeeds[i%URLSeeds.length],TotalSize,maxCrawler+1));
            
        }
        
       
        try{
            for (WebCrawler Seed : Seeds) {
                Seed.join();
            }
        }
   
      
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
       
        
        System.out.println("get "+DB2.getSize("crawlerset",maxCrawler+1));
       
    }
    
}
    
