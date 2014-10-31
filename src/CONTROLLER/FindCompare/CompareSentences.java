package CONTROLLER.FindCompare;

import CONTROLLER.FindCompare.Metrics;
import CONTROLLER.Summarizer.Interpolator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


public class CompareSentences {

	ArrayList<Character> ch = new ArrayList<Character>();
	ArrayList<Integer> rating = new ArrayList<Integer>();
	String bigStr = "";
	String smallStr = "";
	String[][] toSort;


	public float getSimilarity(String a, String b){
 
		Metrics m = new Metrics();
		float vv = m.getVectorSpaceAnalysis(a, b);
 
		return vv;
		
	}
	
	
	
	public void filterBig(){
		Float[] a = new Float[toSort.length];
		for (int t=0;t<toSort.length;t++){a[t]=Float.parseFloat(toSort[t][1]);}
		bigStr = "";
		//make smallStr
		for (int t=0;t<a.length;t++){
			int index = getIndex(a[t]); //System.out.println(index);
			if (index !=-1){
				bigStr = bigStr+toSort[index][0];
			}
		}
		
	}
	
	
	
	//index of char in array toSort
	public int getIndex(char r){
		for (int t=0;t<toSort.length;t++){
		if (toSort[t][0].equals(""+r)){return t;}
		}
		return -1;
	}
	
	//index of value in arrattoSort
	public int getIndex(Float f){ 
		int value = (int)Math.round(f);
		for (int t=0;t<toSort.length;t++){
			  
			if (Integer.parseInt(toSort[t][1])==value){return t;}
		}
		
		return -1;
		
	}
	
	
	public void makeSmallStr(String b){
		Float a[] = new Float[toSort.length];
		a[0] = Float.parseFloat(toSort[0][1]);
		a[toSort.length-1] = Float.parseFloat(toSort[toSort.length-1][1]);
	 
		
		for (int t=0;t<b.length();t++){
			char r = b.charAt(t);
			int index = getIndex(r);
			if (index != -1){
				a[index] = Float.parseFloat(toSort[index][1]);
			}
		}
		

		Interpolator i = new Interpolator();
		a = i.Interpolate(a, "cosine");
		

		
		
		//make smallStr
                smallStr="";
		for (int t=0;t<a.length;t++){
			int index = getIndex(a[t]); //System.out.println(index);
			if (index !=-1){
				smallStr = smallStr+toSort[index][0];
			}
		}
		
	}
	
 
	
	
	public void loadCh(String a){
		
		for (int t=0;t<a.length();t++){
			char r = a.charAt(t); //System.out.println(r);
			int index = ch.indexOf(r);
			if (index == -1){
				ch.add(r); 
			rating.add(0);
			}
			
			else{
				int get = rating.get(index);
				get++;
				rating.set(index, get);
				
			}
		}
	}
	
	
	private String[][] sortArray(String[][]arr){
		Arrays.sort(arr, new Comparator<String[]>() {
	    public int compare(String[] int1, String[] int2) {
	        Double numOfKeys1 = Double.parseDouble(int1[1]); //System.out.println(numOfKeys1);
	        Double numOfKeys2 = Double.parseDouble(int2[1]);//System.out.println(numOfKeys2);
	        return -1*numOfKeys1.compareTo(numOfKeys2);
	    }
	});
	        return arr; 	
	}
	
}
