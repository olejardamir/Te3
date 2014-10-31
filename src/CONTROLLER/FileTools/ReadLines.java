/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER.FileTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author damir
 */
public class ReadLines {
    public int countLines(String filename){
    		try{
		    BufferedReader br = new BufferedReader(new FileReader(filename));
		        String line = "";
                        int count = 0;
		        while ((line=br.readLine()) != null) {
		        	count++;
		        }  br.close();
		return count;        
		}
		catch(Exception e){return 0;}
    }
    
    
    	public String readFile(String filename){
		String ret = "";
		try{
		    BufferedReader br = new BufferedReader(new FileReader(filename));
		        String line = br.readLine();
		        while (line != null) {
		        	ret = ret+" "+line;
		            line = br.readLine();
		           
		        }  br.close();
		return ret;        
		}
		catch(Exception e){return null;}
		
	}
        
        
    public static void copyFile(String sourcee, String destt) throws Exception {
        
        File sourceFile = new File(sourcee);
        File destFile = new File(destt);
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

    FileChannel source = null;
    FileChannel destination = null;

    try {
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source, 0, source.size());
    }
    finally {
        if(source != null) {
            source.close();
        }
        if(destination != null) {
            destination.close();
        }
    }
}
    
}
