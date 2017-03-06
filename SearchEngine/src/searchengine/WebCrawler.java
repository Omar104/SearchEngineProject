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
    private DataSet DS;
    private DataMap DM;
    private int TotalSize;
    private float PercentageTotalSize=0.01f;
    private int BaseCounter;
    public  WebCrawler(String temp, DataSet Data_Temp,DataMap Data_Map,int TS)
    {
        DS = Data_Temp;
        DM=Data_Map;
        TotalSize=TS;
        BaseCounter=(int)(TotalSize*PercentageTotalSize);
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
            if(DS.Get_Size()>=TotalSize)
                return;
            String web_link=queue.poll();
            /**/
           
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
            if(DS.Get_Size()>=TotalSize)//malhaash lazma
                   return;
           // System.out.println(web_link);
            //-----------------
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                if(DS.Get_Size()>=TotalSize)
                    return;
                  
                String LinkHTTPS=link.attr("abs:href" );//link.absUrl("href")
                URL LinkURL=new URL(LinkHTTPS);
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
                    DM.AddBucket(u);
                System.out.println(u);
                tempp=u;
                }
                else
                    DM.AddBucket(tempp);
                
                    //&&LangEn(LinkHTTPS)
                if(!DS.Contains_Set(LinkHTTPS)&&DM.GetCount(tempp)<BaseCounter){
                   queue.add(LinkHTTPS);
                    DS.Add_Set(LinkHTTPS);
              // System.out.println(LinkHTTPS);
                   // System.out.println(LinkURL.getHost());
              
              // System.out.println((t).getFile());
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