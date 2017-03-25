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

    /**6
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       
        
        DataBase DB2 = new DataBase("root","");
        Queue<String> Q1 = DB2.getQueue();
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter number of threads");
        Indexer Indexer = new Indexer(DB2);
        int NumberOfThreads=scanner.nextInt();
        int TotalSize=5000;
        
        while(true){
        int maxCrawler=DB2.getMaxCrawler();
        String URLSeeds[]={"https://en.wikipedia.org/","https://www.google.com.eg/","https://dmoztools.net/","https://www.yahoo.com/","https://www.theguardian.com/"," https://steamcommunity.com/","https://www.amazon.com/"};
        for(int i = NumberOfThreads; i<URLSeeds.length; i++)
         Q1.add(URLSeeds[i]);
        WebCrawler[] Seeds =new WebCrawler[NumberOfThreads];
        
        for(int i=0;i<NumberOfThreads;i++){
            Seeds[i]=(new WebCrawler(URLSeeds[i%URLSeeds.length],TotalSize,maxCrawler+1,Q1));
            
        }
        
        try{
            for (WebCrawler Seed : Seeds) {
                Seed.join();
            }
           Indexer.go();
        }
        
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
        
       
        
        System.out.println("get "+DB2.getSize("crawlerset",maxCrawler+1));
        
       }
    }
    
}
    
