/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package searchengine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
    private  Document htmlDocument;  
     private  Document htmlDocument2;  
    private String root;
    private DataBase DB1;
     private int CrawlerID;
     private Connection connection;
    private int TotalSize;
    private float PercentageTotalSize=0.07f;
    private int BaseCounter;
    public  WebCrawler(String temp,int TS,int CrawlID,Queue<String> Q1)
    {
        this.queue = Q1;
        CrawlerID=CrawlID;
        DB1=new DataBase("root","");
        TotalSize=TS;
        BaseCounter=(int)(TotalSize*PercentageTotalSize);
       // BaseCounter= 300;
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
        
          
            queue.add(seed);
            DB1.insertQueue(seed);
        
            while(!queue.isEmpty()){
            
            if(DB1.getSize("crawlerset",CrawlerID)>=TotalSize)
            {  
                DB1.deleteAll("crawlerqueue");
                 DB1.setAutoIncQueue();
                queue.clear();
              
               // CrawlerID++;
              //  DB1.insertSet(root, CrawlerID);
//                queue.add(root);
//                DB1.insertQueue(root); bdl de dnay l indexer
                DB1.deleteAll("basecount");
                return ;
            }
            System.out.println(Thread.currentThread().getName() ) ;
            
            String web_link=queue.poll();
             //delete front of the queue from DB
            DB1.deleteQueueFront();
           // web_link=NormalizeURL.normalize(web_link);
           
      try
        {
            String tempp=LangEn(new URL(web_link));
            
             if(!DB1.containsSet(web_link,CrawlerID)&&DB1.getCountMap(tempp)<BaseCounter)
            {
                DB1.insertMap(tempp);
                DownloadDocument(web_link);
               int id=DB1.getsetId(web_link);
               DB1.insertIndexerUrl(id);
               
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
                {    if(!DB1.containsSet(LinkHTTPS,CrawlerID))
                    { queue.add(LinkHTTPS);
                      DB1.insertQueue(LinkHTTPS);
                    }
                
                }
            }
           
            
            
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
                    
               // System.out.println(u);
                tempp=u;
                }
               
                    
     
                return tempp;
    }
    
    private synchronized void  DownloadDocument(String web_link) throws IOException
    {
            connection = Jsoup.connect(web_link).timeout(7000);
                this.htmlDocument = connection.get();
                DB1.insertSet(web_link,CrawlerID); // mtnsa4 ya omar ya sayed w ya omar ya said t-edito 3l insert
                 int id= DB1.getsetId(web_link);
                    BufferedWriter out = new BufferedWriter(new FileWriter("DOCUMENTS/"+id+".txt"));
                    out.write(this.htmlDocument.toString());
                    out.close();
    }
}