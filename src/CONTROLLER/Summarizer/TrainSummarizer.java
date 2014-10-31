package CONTROLLER.Summarizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.vector.HashMapTermVectorStorage;
import net.sf.classifier4J.vector.TermVectorStorage;
import net.sf.classifier4J.vector.VectorClassifier;


public class TrainSummarizer {
	private	ArrayList<String> words = new ArrayList<String>();
	private	ArrayList<Integer> num = new ArrayList<Integer>();
	private	ArrayList<String> wordsRange = new ArrayList<String>();
	private	ArrayList<Integer> numRange = new ArrayList<Integer>();
	private	String bigString = "";
	private	TermVectorStorage storage = new HashMapTermVectorStorage();
	private	VectorClassifier vc = new VectorClassifier(storage);
 
public TrainSummarizer(){}

public TrainSummarizer(String filename){
	readFile(filename);
	makeRangeArray();
	for (int t=0;t<wordsRange.size()  ;t++){
		bigString = bigString+wordsRange.get(t)+" ";
		//if (bigString.length()>1000){break;}
	}
	
	try {
		vc.teachMatch("category", ""+bigString);
	} catch (ClassifierException e) {e.printStackTrace();}
	
}


public void TrainWithString(String trainer){
readString(trainer);
	makeRangeArray();
	for (int t=0;t<wordsRange.size()  ;t++){
		bigString = bigString+wordsRange.get(t)+" ";
		//if (bigString.length()>1000){break;}
	}
	
	try {
		vc.teachMatch("category", ""+bigString);
	} catch (ClassifierException e) {e.printStackTrace();}
}

public double getSentenceImportance(String sentence){
	try{
	Float[]a = sentenceToRange(sentence);
	String str = rangeToSentence(a);
	//System.out.println(str);
    double result = vc.classify("category", str);
    return result;
	}catch(Exception e){e.printStackTrace();}
	return 0;
}


public String getBigString(){return bigString;}

public String rangeToSentence(Float[]a){
	String str = "";
	for (int t=0;t<a.length  ;t++){
		int index = (int)Math.round(a[t]);
		String word = getClosest(index);
		if(word!=null){
			if (word.length()>0){
		str = str+word+" "; 
		if (str.length()>1000){break;}
			}
		}
	}
	//System.out.println(str);
	return str;
	
}

private String getClosest(int a){
	String ret = "";
	int index = numRange.indexOf(a);
	if (index==-1){
		double diff = Double.MAX_VALUE;
		for (int t=0;t<numRange.size();t++){
			if (diff>Math.abs(a-numRange.get(t))){
				diff = Math.abs(a-numRange.get(t));
				ret = wordsRange.get(t);
			}
		}
	}
	else{ret = wordsRange.get(index);}
	return ret;
	
}


public Float[] sentenceToRange(String sentence){
	
	//initiate the float array
	int rangesize = wordsRange.size();
	Float[] a = new Float[rangesize+1];
	a[0] = (float)numRange.get(0);
	a[rangesize] = (float)numRange.get(rangesize-1);
	
	sentence = sentence.toLowerCase();
	StringTokenizer tok = new StringTokenizer(sentence, " ~!@#$%^&*()_+`1234567890-={}\\[]|;':\",./<>?");
	while(tok.hasMoreTokens()){
		String tk = tok.nextToken();
		int index =  wordsRange.indexOf(tk);
		if (index!=-1){int value = numRange.get(index); a[index]=(float)value;}
	}
	
	a = Interpolator.Interpolate(a, "cosine");
	return a;
	
}


public int sentenceToFrequencySum(String sentence){
	sentence = sentence.toLowerCase();
	StringTokenizer tok = new StringTokenizer(sentence, " ~!@#$%^&*()_+`1234567890-={}\\[]|;':\",./<>?");
	int sum=0;
	while(tok.hasMoreTokens()){
		int index = words.indexOf(tok.nextToken());
		if (index!=-1){sum=sum+num.get(index);}
	}
	return sum;
}

private void makeRangeArray(){
	String[][] range = sortArray(getArray());
	for (int t=0;t<range.length;t++){
		wordsRange.add(range[t][0]);
		numRange.add(Integer.parseInt(range[t][1]));
	}
}

private void readFile(String filename){
	
	try{
		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line;
		while ( (line = br.readLine()) != null) {
		line = line.replaceAll("[^\\x00-\\x7F]", "");
		line = line.toLowerCase();
		StringTokenizer tok = new StringTokenizer(line, " ~!@#$%^&*()_+`1234567890-={}\\[]|;':\",./<>?");
		while(tok.hasMoreTokens()){
			String tk = tok.nextToken();
			int index = words.indexOf(tk);
			if (index!=-1){
				int add = num.get(index);
				add++;
				num.set(index, add);
			}
			else{words.add(tk);num.add(0);}
		}
		}
		br.close();
		}catch(Exception e){} //or write your own exceptions
	
}
	
private void readString(String str){
str = str.replaceAll("[^\\x00-\\x7F]", "");
StringTokenizer tok = new StringTokenizer(str, " ~!@#$%^&*()_+`1234567890-={}\\[]|;':\",./<>?");
		while(tok.hasMoreTokens()){
			String tk = tok.nextToken();
			int index = words.indexOf(tk);
			if (index!=-1){
				int add = num.get(index);
				add++;
				num.set(index, add);
			}
			else{words.add(tk);num.add(0);}
		}
}


private String[][] getArray(){
	int size = num.size();
	String[][]ret = new String[size][2];
	for (int t=0;t<size;t++){
		ret[t][0]=words.get(t);
		ret[t][1]=""+num.get(t);
	}
	return ret;
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
