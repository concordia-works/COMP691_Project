package com.comp691.Utils;

import java.util.Set;

public class Evaluation {
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
}
