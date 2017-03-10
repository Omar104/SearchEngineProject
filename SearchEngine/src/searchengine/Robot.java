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
import java.util.HashSet;

/**
 *
 * @author khaledd
 */
public class Robot {
    // function to figure out the disallowed sub-websites of the given base url
     public static HashSet<String> disallowed(URL url)
    {
        String base_urlstr = "https://"+url.getHost()+"/robots.txt";
        HashSet<String> dis = new HashSet();
        
        try{
            URL  robot_url = new URL(base_urlstr);
            
            
            try{
                
             BufferedReader reader = new BufferedReader(new InputStreamReader(robot_url.openStream()));
             String line =" ";
             
             while((line = reader.readLine()) != null)
              {
                 if(line.equalsIgnoreCase("User-agent: *"))
                    {
                       while((line = reader.readLine())!= null)
                       {
                          if(line.contains("Disallow: ")) 
                          {
                              dis.add(line.substring(10 ,line.length()));
                          }
                          else if(!line.contains("Allow: "))
                              break;
                       }
                    }
            
              }
            
            
          
              reader.close();
            }
            catch (IOException e)
            {
             System.err.print(e);
            }
            
        }
        
        catch(MalformedURLException e){
            System.err.print(e);
        }

       return dis;
    }
    
}
