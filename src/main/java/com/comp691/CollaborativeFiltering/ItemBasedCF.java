package com.comp691.CollaborativeFiltering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import com.comp691.Utils.Config;

public class ItemBasedCF {
	private static long startTime;
	private static long endTime;
	private ItemBasedCF() {}
	
	private static void cleanDirectory() {
		try {
			FileUtils.cleanDirectory(new File(Config.ITEMCF_OUTPUT_FOLDER));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void makeReport() {
		endTime = System.currentTimeMillis();
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(Config.ITEMCF_OUTPUT_FOLDER + Config.ITEMCF_REPORT_FILE));
			bw.write("StartTime: " + startTime + "\n");
			bw.write("EndTime: " + endTime + "\n");
			bw.write("Duration: " + (endTime - startTime)/1000 + "s\n");
			bw.close();
			System.out.println("Item-based CF finished");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void computePrediction() {
		try {
    		// Create the data model from dataset
    		DataModel model = new FileDataModel(new File(Config.DATASET_AMZ_ALL_1_1999));
    		
    		// Compute Items' similarities
    		ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
    		
    		// Compute the prediction for every user
    		Recommender recommender= new GenericItemBasedRecommender(model, similarity);
    		
    		// Get the list of all users
    		LongPrimitiveIterator userIDs = model.getUserIDs();
    		
    		// Get users' predictions
    		while (userIDs.hasNext()) {
				long userID = userIDs.next();
				List<RecommendedItem> recommendations = recommender.recommend(userID, Config.CF_NO_OF_TOP_PREDICTION);
				if (!recommendations.isEmpty()) {
					BufferedWriter bw = new BufferedWriter(new FileWriter(Config.ITEMCF_OUTPUT_FOLDER + userID + ".txt"));
					for (RecommendedItem recommendation : recommendations) {
						float predictedRating = recommendation.getValue();
						if (predictedRating > Config.CF_RATING_THRESHOLD) {
							bw.write(recommendation.getItemID() + ",");
						}
				    }
					bw.close();
				}	
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public static void execute() {
		startTime = System.currentTimeMillis();
		//BasicConfigurator.configure();
    	cleanDirectory();
    	computePrediction();
		makeReport();
	}
}
