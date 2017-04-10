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
			double precision = 0.0, recall = 0.0;
			
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

				noUsers++;
			}
			
			// Calculate average precision, recall and f-score
			evaluations[0] = precision/noUsers;
			evaluations[1] = recall/noUsers;
			evaluations[2] = 2 * evaluations[0] * evaluations[1] / (evaluations[0] + evaluations[1]);
			
			basedReader.close();
			retrievedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return evaluations;
	}
	
	public static void compareAlgos(String retrievedFolder, String basedFolder) {
		
	}
}
