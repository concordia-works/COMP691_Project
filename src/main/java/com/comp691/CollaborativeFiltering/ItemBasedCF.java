package com.comp691.CollaborativeFiltering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

//import org.apache.log4j.BasicConfigurator;
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
	
	private static void computePrediction() {
		try {
    		// Create the data model from dataset
    		DataModel model = new FileDataModel(new File(Config.DATASET));
    		
    		// Compute Items' similarities
    		ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
    		
    		// Compute the prediction for every user
    		Recommender recommender= new GenericItemBasedRecommender(model, similarity);
    		
    		// Get the list of all users
    		LongPrimitiveIterator userIDs = model.getUserIDs();
    		
    		// Get users' predictions
    		BufferedWriter bw = new BufferedWriter(new FileWriter(Config.ITEMCF_RESULT_FILE));
			while (userIDs.hasNext()) {
				long userID = userIDs.next();
				bw.write(userID + " {");
				List<RecommendedItem> recommendations = recommender.recommend(userID, Config.NO_TOP_PREDICTIONS);
				if (!recommendations.isEmpty()) {
					for (RecommendedItem recommendation : recommendations) {
						float predictedRating = recommendation.getValue();
						if (predictedRating > Config.CF_RATING_THRESHOLD) {
							bw.write(recommendation.getItemID() + ",");
						}
				    }
				}
				bw.write("}" + System.lineSeparator());
			}
			bw.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public static void execute() {
		startTime = System.currentTimeMillis();
		System.out.println("Item-based CF starts");
		//BasicConfigurator.configure();
    	computePrediction();
    	System.out.println("Item-based CF finishes");
    	endTime = System.currentTimeMillis();
    	System.out.println("Item-based CF duration: " + Math.round(endTime - startTime)/1000 + "s" + System.lineSeparator());
	}
}
