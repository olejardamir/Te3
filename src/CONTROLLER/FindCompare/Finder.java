package CONTROLLER.FindCompare;

import CONTROLLER.Summarizer.Summarizer;
import CONTROLLER.Summarizer.TrainSummarizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Finder {

	public String findClosestFirstSentence(String filename, String sentence, double cutoff, TrainSummarizer tts){
		CompareSentences c = new CompareSentences();
		double max = 0;
                //double min = 100;
		String chosen = "";
		String sentence2 = tts.rangeToSentence(tts.sentenceToRange(sentence));
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ( (line = br.readLine()) != null) {
                        String line2 = tts.rangeToSentence(tts.sentenceToRange(line));
			double value = (double)c.getSimilarity(line2, sentence2);
			if (max<value && value>cutoff ){  
				max=value; 
				chosen=line;
                               // System.out.println(":"+sentence+"\n"+line+":"+value+"---");
				}
			}
			br.close();
		}catch(Exception e){}
		return chosen;
	}
	
        	public String[] getBestSentence(ArrayList<String> s, String compare, TrainSummarizer tts){
		CompareSentences c = new CompareSentences();
		double max = 0;
                String[] ret = new String[2];
                compare = tts.rangeToSentence(tts.sentenceToRange(compare));
		for (int t=0;t<s.size();t++){
			String str = s.get(t);
                        int fileindex = str.indexOf("<filename:");
                       // System.out.println("*"+str+"*");
                        String file = str.substring(fileindex+10,str.indexOf(":>"));
                        //System.out.println(str);
                        String str2 = str.substring(0,fileindex);
                        String str3 = tts.rangeToSentence(tts.sentenceToRange(str2));
			double value = (double)c.getSimilarity(compare, str3);
			if (max<value){ret[0] = str2; ret[1]=file; max=value;}
		}
		return ret;
	}
        
        
	public String getParagraph(String filename, String begin, String end, double cutoff, int summarize){ //todo insert summarizer
		if (begin.equals("")){return "";}
		String paragraph="";
		Calculate c = new Calculate();
		double max = 0;
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));

			String line;
			String tempChunk="";
			
			while ( !(line = br.readLine()).equals(begin)) {}
			paragraph = paragraph+line;
			while ( (line = br.readLine()) != null) {
			tempChunk = tempChunk+line+"\n";
			double value = c.calculateRBFSimilarityFast(line, end);
			if (max<=value && value>=cutoff){ //&& value>=cutoff
				//TODO INSERT NN CALCULATE AS A SECOND CHECK!
				max=value; paragraph = paragraph+"\n"+tempChunk; tempChunk="";
                                //System.out.println(max+":"+line);
                        }
			}
			br.close();
		}catch(Exception e){}
		while(paragraph.contains("\n\n")){paragraph=paragraph.replace("\n\n", "\n");}
		
		//TODO, SUMMARIZE !!!
                Summarizer sm = new Summarizer();
                
                
                
                
		return paragraph;
	}
	
}
