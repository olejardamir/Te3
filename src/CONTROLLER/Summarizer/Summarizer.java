package CONTROLLER.Summarizer;

import CONTROLLER.FileTools.ReadLines;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Summarizer {
	
private TrainSummarizer ts;	
private ArrayList<String> document = new ArrayList<String>();
private ArrayList<Double> values = new ArrayList<Double>();	
 

public Summarizer(){}

public Summarizer(TrainSummarizer t){ts=t;}

public void summarize(String summarize, String output, int sentences){
    int countSentences = new ReadLines().countLines(summarize);
    if (countSentences<=sentences){
    try{new ReadLines().copyFile(summarize, output);}catch(Exception e){}
    }
    else{
	try{
		ts = new TrainSummarizer(summarize);
		//evaluate sentences
		BufferedReader br = new BufferedReader(new FileReader(summarize));

		String line;
		while ( (line = br.readLine()) != null) {
			double d=ts.getSentenceImportance(line);
			document.add(line);
			values.add(d);
		}
		br.close();
		
		//now sort
		int docsize = document.size();
		String[][] s = new String[docsize][2];
		for (int t=0;t<docsize;t++){
			s[t][0]=document.get(t);
			s[t][1]=""+values.get(t);
		}
		s = sortArray(s);
		//now select the best n
		document = new ArrayList<String>();
		for (int t=0;t<sentences;t++){
			document.add(s[t][0]);
		}
		//now, write to file
		br = new BufferedReader(new FileReader(summarize));
		BufferedWriter wr = new BufferedWriter(new FileWriter(output));

		
		while ( (line = br.readLine()) != null) {
			if (document.contains(line)){wr.write(line+"\n");}
		}
		br.close();
		wr.close();
		}catch(Exception e){e.printStackTrace();}
}
}


public Summarizer(String train, String summarize, String output, int sentences){
    int countSentences = new ReadLines().countLines(summarize);
    if (countSentences<=sentences){
    try{new ReadLines().copyFile(summarize, output);}catch(Exception e){}
    }
    else{
	ts = new TrainSummarizer(train);
	try{
		
		//evaluate sentences
		BufferedReader br = new BufferedReader(new FileReader(summarize));

		String line;
		while ( (line = br.readLine()) != null) {
			double d=ts.getSentenceImportance(line);
			document.add(line);
			values.add(d);
		}
		br.close();
		
		//now sort
		int docsize = document.size();
		String[][] s = new String[docsize][2];
		for (int t=0;t<docsize;t++){
			s[t][0]=document.get(t);
			s[t][1]=""+values.get(t);
		}
		s = sortArray(s);
		//now select the best n
		document = new ArrayList<String>();
		for (int t=0;t<sentences;t++){
			document.add(s[t][0]);
		}
		//now, write to file
		br = new BufferedReader(new FileReader(summarize));
		BufferedWriter wr = new BufferedWriter(new FileWriter(output));

		
		while ( (line = br.readLine()) != null) {
			if (document.contains(line)){wr.write(line+"\n");}
		}
		br.close();
		wr.close();
		}catch(Exception e){e.printStackTrace();}
    }
}

public String toString(String train, String summarize,int sentences){
    
    int countSentences = new ReadLines().countLines(summarize);
    if (countSentences<=sentences){return new ReadLines().readFile(summarize);}
    
	ts = new TrainSummarizer(train);
	try{
		
		//evaluate sentences
		BufferedReader br = new BufferedReader(new FileReader(summarize));

		String line;
		while ( (line = br.readLine()) != null) {
			double d=ts.getSentenceImportance(line);
			document.add(line);
			values.add(d);
		}
		br.close();
		
		//now sort
		int docsize = document.size();
		String[][] s = new String[docsize][2];
		for (int t=0;t<docsize;t++){
			s[t][0]=document.get(t);
			s[t][1]=""+values.get(t);
		}
		s = sortArray(s);
		//now select the best n
		document = new ArrayList<String>();
		for (int t=0;t<sentences;t++){
                    System.out.println(s[t][0]);
			document.add(s[t][0]);
		}
		//now, write to String
		String ret = "";
		br = new BufferedReader(new FileReader(summarize));


		
		while ( (line = br.readLine()) != null) {
			if (document.contains(line)){ret=ret+line+"\n";}
		}
		br.close();
		return ret;
		}catch(Exception e){e.printStackTrace();}
	return null;
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

 

    public void TrainWithFile(String filename) {
        ts = new TrainSummarizer(filename);   
    }
    
    public String expand (String str){
    return ts.rangeToSentence(ts.sentenceToRange(str));
    }

}
