/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER.FileTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author damir
 */
public class ReadForExpansion {
 private   BufferedReader br;
 private   String previous;
 private   String next;
    
    public ReadForExpansion(String filename){
    try{
    br = new BufferedReader(new FileReader(new File(filename)));
    previous = br.readLine();
    if (previous!=null){next=br.readLine();}
    }catch(Exception e){e.printStackTrace();}
    }
    
    public String[] getNextPair(){
    String[]s=new String[2];
    s[0] = previous;
    s[1] = next;
    try{
       if (previous==null){return null;}
       previous=next;
       next=br.readLine();
       return s; 
    }catch(Exception e){e.printStackTrace();}
    return null;
    }
    
    public void close(){try{br.close();}catch(Exception e){e.printStackTrace();}}
    
}
