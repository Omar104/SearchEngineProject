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
import java.io.File;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import com.google.common.base.CharMatcher;
import org.jsoup.nodes.Document;


/**
 *
 * @author khaledd
 */
public class Indexer {
    
    private DataBase DB ;
    private ResultSet Crawler_result ;
    private HashSet<String> Stopwords;
    private CharMatcher Cm;
    public  Indexer(DataBase D)
    {
        this.DB = D;
        Stopwords = new HashSet(); 
        Cm = CharMatcher.JAVA_LETTER_OR_DIGIT;
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
        LinkedList<String> L = new LinkedList(Splitter.on(" ").trimResults(Cm.negate()).omitEmptyStrings().splitToList(Html_content));
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
           String Tmp = It.next().toLowerCase();
           Porterstemmer P = new Porterstemmer();
           P.add(Tmp.toCharArray(),Tmp.length());
           P.stem();
           Tmp = P.toString();
           if (Tmp.charAt(Tmp.length()-1) == 'i' )
           {
               Tmp = Tmp.substring(0, Tmp.length()-1)+"y";
               
           }
           It.set(Cm.retainFrom(Tmp));
        }
        return TokenizedCleanStrings;
    }
    
    public LinkedList<String> cleanString(String s)
    {
        return removeStopWords(stemm(removeStopWords(tokenize(s))));
    }   
    
 
    public void run()
    {
        try{
            this.go();
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
    }
    public void go() throws SQLException
    {
       
        if(Stopwords.isEmpty())
            this.readStopwords();
        ResultSet Res = DB.getIndexerUrl();
       while(Res.next())
       {    
          
          String Id = Res.getString("url_id");
          if(Id.equals("0"))
              continue;
          System.out.println(Id);
          try{
          Document Content = Jsoup.parse(new File("DOCUMENTS/"+Id+".txt"),null);
          LinkedList<String> L = cleanString(Content.title());
          int sz ;  sz = L.size();
          int cnt = 1; 
          L = cleanString(Content.title()+" "+Content.body().text());
          DB.insertIntoWordCntPerPage(Id, L.size());
          DB.clearWordCount(Id);
          
           for(String word : L)
           {
               
               if(cnt <= sz)
               {
                   DB.insertWordIntoWordCnt(word, Id, true, cnt++);
               }
               else
               {
                    DB.insertWordIntoWordCnt(word, Id, false,cnt++);
               }
           }
          DB.pageIsIndexed(Id);
          Res = DB.getIndexerUrl();
          }
          catch(IOException e)
          {
              System.out.println(e.toString());
          }
       }
        
    }
}
