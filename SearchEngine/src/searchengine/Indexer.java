/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashSet;
import com.google.common.base.Splitter;
import java.util.ListIterator;
import java.util.LinkedList;


/**
 *
 * @author khaledd
 */
public class Indexer {
    
    private DataBase DB ;
    private ResultSet Crawler_result ;
    private HashSet<String> Stopwords;
    public  Indexer(DataBase D)
    {
        this.DB = D;
        Stopwords = new HashSet(); 
    }
    public void readStopwords()
    {
        try{
                BufferedReader B1 = new BufferedReader(new FileReader("././stop words.txt"));
                String Tmp ;
                try{
                while((Tmp = B1.readLine()) != null)
                {
                    Stopwords.add(Tmp);
                }
         
               B1.close();
                }
                
         catch (IOException e)
          {
            System.out.println(e.toString());
          }
       }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
     
    }
    
    public LinkedList<String> tokenize(String Html_content)
    {
        LinkedList<String> L = new LinkedList(Splitter.on(" ").omitEmptyStrings().splitToList(Html_content));
        return L;
    }
    
    public LinkedList<String> removeStopWords(LinkedList<String> TokenizedStrings)
    {
        ListIterator<String> It = TokenizedStrings.listIterator();
        while(It.hasNext())
        {
            String Tmp = It.next();
            if(Stopwords.contains(Tmp))
                It.remove();
        }
        return TokenizedStrings ;
    }
    
    public LinkedList<String> stemm(LinkedList<String> TokenizedCleanStrings)
    {
        ListIterator<String> It = TokenizedCleanStrings.listIterator();
        while(It.hasNext())
        {
           String Tmp = It.next();
           Porterstemmer P = new Porterstemmer();
           P.add(Tmp.toCharArray(),Tmp.length());
           P.stem();
           It.set(P.toString());
        }
        return TokenizedCleanStrings;
    }
    
    public void run()
    {
        
    }
}
