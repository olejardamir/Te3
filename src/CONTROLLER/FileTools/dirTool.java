/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER.FileTools;

import java.util.ArrayList;
import java.io.File;
/**
 *
 * @author damir
 */
public class dirTool {
    
    
    public ArrayList<String> getTXTFiles(String input, boolean subDirs){
        ArrayList<String> ret = new ArrayList<String>();
//visit each dir and get each file as ArrayList
    File f = new File(input);
    File[] listfiles = f.listFiles();
    for (int i = 0; i < listfiles.length; i++) {
        if (listfiles[i].isDirectory() && subDirs) {
            File[] internalFile = listfiles[i].listFiles();
            for (int j = 0; j < internalFile.length; j++) {
                System.out.println(internalFile[j]);
                if (internalFile[j].isDirectory()) {
                    String name = internalFile[j].getAbsolutePath();
                    getTXTFiles(name, subDirs);
                }

            }
        } else {
           //DBG: System.out.println(listfiles[i]);
            String filename = listfiles[i].toString();
            if (filename.toLowerCase().endsWith(".txt")){
            ret.add(filename);
            }
        }
    }
    
    return ret;
}
    
     
 public boolean deleteDirectory(File path) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      for(int i=0; i<files.length; i++) {
         if(files[i].isDirectory()) {
           deleteDirectory(files[i]);
         }
         else {
           files[i].delete();
         }
      }
    }
    return( path.delete() );
  }



    
    
}
