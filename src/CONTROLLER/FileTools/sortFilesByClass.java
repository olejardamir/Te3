/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER.FileTools;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author damir
 */
public class sortFilesByClass {
    public String[][] sort2DArray(String[][] theArray){
    		Arrays.sort(theArray, new Comparator<String[]>() {
		    public int compare(String[] int1, String[] int2) {
		        Double numOfKeys1 = Double.parseDouble(int1[0]); //System.out.println(numOfKeys1);
		        Double numOfKeys2 = Double.parseDouble(int2[0]);//System.out.println(numOfKeys2);
		        return -1*numOfKeys1.compareTo(numOfKeys2);
		    }
		});
                return theArray;        
    }
}
