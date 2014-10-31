/*
 * The purpose of this class is to classify any two text files without worrying about training etc
 * For the best results, the best value for the sentences variable should be 60
 * This means that any two documents should have at least 60 sentences available
 * Anything less than 60 would give results with a great possibility of having an error
 * Created by Damir Olejar on 
 */


package CONTROLLER.classifier;


import java.io.BufferedReader;
import java.io.FileReader;

import CONTROLLER.Summarizer.*;
import net.sf.classifier4J.vector.HashMapTermVectorStorage;
import net.sf.classifier4J.vector.TermVectorStorage;
import net.sf.classifier4J.vector.VectorClassifier;



public class classify {

 
    
	public double classifyDocuments(String doc1, String doc2, int sentences){	
		//read the TXT to a string
		String doc1String = readFile(doc1);
		String doc2String = readFile(doc2);
		
		//now summarize the strings and extract the structures
		
		String struct1 = new Summarizer().toString(doc1, doc2, 60); //summariser.summarise(doc1String, doc2String, sentences);
		String struct2 = new Summarizer().toString(doc2, doc1, 60); //summariser.summarise(doc2String, doc1String, sentences);
		
		//now compare the differences with the Vector-Space analysis
		TermVectorStorage storage = new HashMapTermVectorStorage();		
		VectorClassifier vc = new VectorClassifier(storage);		
		try {
			vc.teachMatch("category", struct1);
			return vc.classify("category", struct2);
		} catch (Exception e) {
			return 0;
		}

	}
	
	
	
	/*
	 * Read the file to a String*
	 */
	private String readFile(String filename){
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
	
}
