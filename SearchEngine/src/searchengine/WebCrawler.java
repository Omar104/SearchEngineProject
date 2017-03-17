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
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    
    private DataBase DB1;
    private int TotalSize;
    private float PercentageTotalSize=0.01f;
    private int BaseCounter;
    public  WebCrawler(String temp,int TS)
    {
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
        if(DB1.getSize("crawlerqueue")!=0)
        {
            queue=DB1.getQueue();
        }
        else{   
        queue.add(seed);
        DB1.insertQueue(seed);
        DB1.insertSet(seed);
        }
        while(!queue.isEmpty()){
            if(DB1.getSize("crawlerset")>=TotalSize)
            {
                DB1.deleteAll("crawlerqueue");
                DB1.setAutoIncQueue();
                return;
            }
            
            String web_link=queue.poll();
            
           
           
           // System.out.println(web_link);//remove

      try
        {
           
            Connection connection = Jsoup.connect(web_link).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            WebCrawler.htmlDocument = htmlDocument;
            
           
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                
            }
           
             //--------------------
        //    String linkOnPage = htmlDocument.select("html").attr("lang");
       //     System.out.println(linkOnPage);
       //     if(!linkOnPage.contains("en"))
      //      {
     //           System.out.println("Removed--------"+web_link);
       //      DS.RemoveElement_Set(web_link);
         //                     continue;
//          }
            
           // System.out.println(web_link);
            //-----------------
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            HashSet<String> h1 = Robot.disallowed(new URL(web_link));
            
            for(Element link : linksOnPage)
            {
                if(DB1.getSize("crawlerset")>=TotalSize)
                {
                    DB1.deleteAll("crawlerqueue");
                    DB1.setAutoIncQueue();
                    return;
                }
                 
               
                String LinkHTTPS=link.attr("abs:href" );//link.absUrl("href")
                URL LinkURL=new URL(LinkHTTPS);
                boolean flag = true ;
                for(String disallowed : h1)
                {
                    flag = LinkHTTPS.contains(disallowed) ? false :  flag; 
                }
                if(flag)
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
                
                    //&&LangEn(LinkHTTPS)
                if(!DB1.containsSet(LinkHTTPS)&&DB1.getCountMap(tempp)<BaseCounter){
                   queue.add(LinkHTTPS);
                   DB1.insertQueue(LinkHTTPS);
                    DB1.insertSet(LinkHTTPS);
              // System.out.println(LinkHTTPS);
                   // System.out.println(LinkURL.getHost());
              
              // System.out.println((t).getFile());
                }
               
               
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
    
    private boolean LangEn(String LinkHTTPS)
    {
         try
        {
            Connection connection2 = Jsoup.connect(LinkHTTPS).userAgent(USER_AGENT);
            Document htmlDocument2 = connection2.get();
            WebCrawler.htmlDocument2 = htmlDocument2; 
            
            if(!connection2.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
               
            }
            String linksOnPage = htmlDocument2.select("html").attr("lang");
            
            //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+ linksOnPage );
            if(linksOnPage.contains("en"))
                return true;
            else
                return false;
              
           


        }

        
      catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            System.out.print(ioe.getMessage());
        }
      catch(IllegalArgumentException e){
            System.out.print(e.getMessage());
        }
         return true;
    }
}