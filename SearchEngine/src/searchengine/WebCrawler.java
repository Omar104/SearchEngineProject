/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package searchengine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler extends Thread{
    private Queue<String> queue=new LinkedList<>();
    private static Document htmlDocument;  
     private static Document htmlDocument2;  
    private String root;
    private DataBase DB1;
     private int CrawlerID;
    private int TotalSize;
    private float PercentageTotalSize=0.01f;
    private int BaseCounter;
    public  WebCrawler(String temp,int TS,int CrawlID)
    {
        CrawlerID=CrawlID;
        DB1=new DataBase("root","");
        TotalSize=TS;
        //BaseCounter=(int)(TotalSize*PercentageTotalSize);
        BaseCounter=10;
        this.root = temp;
        this.start();
        
    }

    
    
    @Override
    public  void run()
    {
        try {
            this.BFS(this.root);
        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void BFS(String seed)  throws IOException 
    {
        
            queue=DB1.getQueue(); 
            queue.add(seed);
            DB1.insertQueue(seed);
        
        while(!queue.isEmpty()){
            
            if(DB1.getSize("crawlerset")>=TotalSize)
            {
                DB1.deleteAll("crawlerqueue");
                DB1.setAutoIncQueue();
                return;
            }
            
            String web_link=queue.poll();
            web_link=NormalizeURL.normalize(web_link);
           
      try
        {
            String tempp=LangEn(new URL(web_link));
            Connection connection;
             if(!DB1.containsSet(web_link,CrawlerID)&&DB1.getCountMap(tempp)<BaseCounter)
            {
                connection = Jsoup.connect(web_link);
                WebCrawler.htmlDocument = connection.get();
                
                DB1.insertSet(web_link,CrawlerID); // mtnsa4 ya omar ya sayed w ya omar ya said t-edito 3l insert
            }
             
            else continue;
          
           
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                
            }
           
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links from "+web_link);
            HashSet<String> h1 = Robot.disallowed(new URL(web_link));
            
            for(Element link : linksOnPage)
            {
                String LinkHTTPS=link.attr("abs:href" );//link.absUrl("href")
                LinkHTTPS=NormalizeURL.normalize(LinkHTTPS);
                boolean flag = true ;
                for(String disallowed : h1)
                {
                    flag = LinkHTTPS.contains(disallowed) ? false :  flag; 
                }
                if(flag)
                {
                    
                 
                      queue.add(LinkHTTPS);
                      DB1.insertQueue(LinkHTTPS);
                   
                     
               
               
            }
            }
            //delete front of the queue from DB
            DB1.deleteQueueFront();
            
            
        }
      catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            System.out.print(ioe.getMessage());
        }
      catch(IllegalArgumentException e){
            System.out.print(e.getMessage());
        }
        //catch(MalformedURLException e){
        //    System.out.print(e.getMessage());
       // }
            
    }
        //reset autoInc to 1
        DB1.setAutoIncQueue();
    }
    
    private String LangEn(URL LinkURL)
    {
         String tempp=LinkURL.getHost();
                int cnt=0;
                String u="";
                for(int c=0;c<tempp.length();c++)
                {
                    if(cnt>=1)
                        u+=tempp.charAt(c);
                    if(tempp.charAt(c)=='.')
                    {
                        cnt++;
                    }
                    
                    
                }
              
                if(cnt>=2)
                {
                    DB1.insertMap(u);
               // System.out.println(u);
                tempp=u;
                }
                else
                    DB1.insertMap(tempp);
     
                return tempp;
    }
}