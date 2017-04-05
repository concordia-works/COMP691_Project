package com.comp691.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Evaluation {
	private Evaluation() {}
	
	public static double[] calcMetrics(String basedFile, String retrievedFile) {
		double[] evaluations = new double[3]; // Precision i=0, Recall i=1, F1Score i=2
		try {
			BufferedReader basedReader = new BufferedReader(new FileReader(basedFile));
			BufferedReader retrievedReader = new BufferedReader(new FileReader(retrievedFile));
			String basedLine, retrievedLine;
			int noUsers = 0;
			double precision = 0.0, recall = 0.0, fscore = 0.0;
			
			// Calculate precision, recall and f-score for each user
			while (((basedLine = basedReader.readLine()) != null) && ((retrievedLine = retrievedReader.readLine()) != null)) {
				int noBasedPredictions, noRetrievedPredictions;
				String tmpBasedPredict = basedLine.substring(basedLine.indexOf('{') + 1,basedLine.indexOf('}'));
				String tmpRetrievedPredict = retrievedLine.substring(retrievedLine.indexOf('{') + 1,retrievedLine.indexOf('}'));
				
				String[] basedPredict = tmpBasedPredict.split(",");
				String[] retrievedPredict = tmpRetrievedPredict.split(",");
				
				if (basedPredict[0].compareTo("") != 0)
					noBasedPredictions = basedPredict.length;
				else noBasedPredictions = 0;
				
				if (retrievedPredict[0].compareTo("") != 0)
					noRetrievedPredictions = retrievedPredict.length;
				else noRetrievedPredictions = 0;
				
				int noCommonPredict = 0;
				for (int i = 0; i < basedPredict.length; i++) {
					for (int j = 0; j < retrievedPredict.length; j++) {
						if (basedPredict[i].compareTo(retrievedPredict[j]) == 0)
							noCommonPredict++;
					}
				}
				
				if (noRetrievedPredictions != 0)
					precision += noCommonPredict/noRetrievedPredictions;
				
				if (noBasedPredictions != 0)
					recall += noCommonPredict/noBasedPredictions;
				
				if ((precision != 0.0) || (recall != 0.0))
					fscore += 2 * precision * recall / (precision + recall);
				
				noUsers++;
			}
			
			// Calculate average precision, recall and f-score
			evaluations[0] = precision/noUsers;
			evaluations[1] = recall/noUsers;
			evaluations[2] = fscore/noUsers;
			
			basedReader.close();
			retrievedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return evaluations;
	}
	
	public static float[] calcMetrics(Set<Long> retrievedDocs, Set<Long> relevantDocs) {
		float[] evaluations = new float[3]; // Precision i=0, Recall i=1, F1Score i=2
		float precision, recall, fscore;
		int noRetrievedDocs = retrievedDocs.size();
		int noRelevantDocs = relevantDocs.size();
		
		relevantDocs.retainAll(retrievedDocs);
		int noJointDocs = relevantDocs.size();
		
		// Compute Precision https://en.wikipedia.org/wiki/Precision_and_recall
		if (noRetrievedDocs == 0)
			precision = 0.0f;
		else precision = noJointDocs/noRetrievedDocs;
		
		// Compute Recall https://en.wikipedia.org/wiki/Precision_and_recall
		if (noRelevantDocs == 0)
			recall = 0.0f;
		else recall = noJointDocs/noRelevantDocs;
		
		// Compute balanced F-score https://en.wikipedia.org/wiki/F1_score
		fscore = 2 * precision * recall / (precision + recall);
		
		evaluations[0] = precision;
		evaluations[1] = recall;
		evaluations[2] = fscore;
		return evaluations;
	}
	
	public static void compareAlgos(String retrievedFolder, String basedFolder) {
		
	}
}
