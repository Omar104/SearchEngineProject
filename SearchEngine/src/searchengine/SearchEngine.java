/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.io.IOException;


/**
 *
 * @author Omarove10
 */
public class SearchEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        DataSet s1 = new DataSet();
        DataMap m1 = new DataMap();
  
      //  System.out.println(m1.MapCount.get("h"));
        WebCrawler seed1=new WebCrawler("https://en.wikipedia.org/",s1,m1,200);//total size as input
        WebCrawler seed2=new WebCrawler("https://www.goolge.com.eg/",s1,m1,200);
        try{
            seed1.join();
            seed2.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
         System.out.println("Size:");
        System.out.println(s1.Get_Size());
       // System.out.println(m1.MapCount.get("www.google.com.eg"));
       s1.Print_Set();
    }
    
}
