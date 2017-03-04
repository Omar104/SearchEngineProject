/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package searchengine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String root;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private DataSet DS;
    private DataMap DM;
    public  WebCrawler(String temp, DataSet Data_Temp,DataMap Data_Map)
    {
        DS = Data_Temp;
        DM=Data_Map;
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
        DS.Add_Set(seed);
        while(!queue.isEmpty()){
            if(DS.web_links.size()>=5)
                return;
            String web_link=queue.poll();
            /**/
           
            System.out.println(web_link);//remove
            
            
            
      try
        {
            Connection connection = Jsoup.connect(web_link).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            WebCrawler.htmlDocument = htmlDocument; 
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + web_link);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
               
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                if(DS.web_links.size()>=5)
                    return;
                DM.AddBucket(new URL(link.absUrl("href")).getHost());
                
                    
                if(!DS.Contains_Set(link.absUrl("href"))&&DM.GetCount(new URL(link.absUrl("href")).getHost())<3){
                   queue.add(link.absUrl("href"));
               DS.Add_Set(link.absUrl("href"));
               System.out.println(link.absUrl("href"));
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
        
            
    }
    }
}