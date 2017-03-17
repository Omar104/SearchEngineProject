/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;


import java.util.TreeMap;


/**
 *
 * @author Omarove10
 */
public class DataMap {
    public static TreeMap<String,Integer> MapCount=new TreeMap<String,Integer>();
    
    public synchronized void AddBucket(String UrlBase)
    {
        if(MapCount.get(UrlBase)==null)
            MapCount.put(UrlBase, 1);
        else
            MapCount.put(UrlBase, MapCount.get(UrlBase)+1);
        
    }
    public synchronized Integer GetCount(String UrlBase)
    {
        if(MapCount.get(UrlBase)==null)
            return -1;
        
        return MapCount.get(UrlBase);
        
    }
}
