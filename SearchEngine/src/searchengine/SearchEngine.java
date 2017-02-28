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
        WebCrawler seed1=new WebCrawler("https://www.google.com.eg/",s1);
        WebCrawler seed2=new WebCrawler("https://www.google.com.eg/",s1);
        try{
            seed1.join();
            seed2.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("seed1  interputed"); 
        }
         System.out.println("Size:");
        System.out.println(s1.web_links.size());
    }
    
}
