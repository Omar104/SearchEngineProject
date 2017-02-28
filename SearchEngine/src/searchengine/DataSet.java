/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


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
     public synchronized boolean Contains_Set(String temp)
     {
         return web_links.contains(temp);
     }

    public synchronized static Set<String> GetWebLinks() {
        return web_links;
    }
     
}
