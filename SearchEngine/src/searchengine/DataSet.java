/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.print.DocFlavor;


/**
 *
 * @author Omarove10
 */
public class DataSet {
    public static  TreeSet<String> web_links = new TreeSet<String>();
    
    
    public synchronized void Add_Set(String temp)
     {
         web_links.add(temp);
     }
    public synchronized void RemoveElement_Set(String temp)
     {
         web_links.remove(temp);
     }
    public synchronized Integer Get_Size()
     {
       return web_links.size();
     }
     public synchronized boolean Contains_Set(String temp)
     {
         return web_links.contains(temp);
     }
     public synchronized void Print_Set(){
         
         for (String s :web_links) {
          System.out.println(s);
         }
             
     }

   
     
}
