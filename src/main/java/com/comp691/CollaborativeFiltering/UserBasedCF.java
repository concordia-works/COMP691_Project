package com.comp691.CollaborativeFiltering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.comp691.Utils.Config;

public class UserBasedCF {
	private static long startTime;
	private static long endTime;
	private UserBasedCF() {}
	
	private static void computePrediction() {
		try {
        	// Create the data model from dataset
			DataModel model = new FileDataModel(new File(Config.DATASET));
			// Compute Users' similarities
			UserSimilarity similarity = new UncenteredCosineSimilarity(model);
			
			// Produce users' neighborhood
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(Config.USERCF_NEIGHBORHOOD_SMILARITY_THRESHOLD, similarity, model);
			//UserNeighborhood neighborhood = new NearestNUserNeighborhood(Config.USERCF_NEAREST_USER_NEIGHBORHOOD, similarity, model);
			
			// Compute the prediction for every user
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			// Get the list of all users
			LongPrimitiveIterator userIDs = model.getUserIDs();
			
			// Get users' recommendations
			BufferedWriter bw = new BufferedWriter(new FileWriter(Config.USERCF_RESULT_FILE));
			while (userIDs.hasNext()) {
				long userID = userIDs.next();
				
				// Print users' neighbors and similarity
//				long[] neighbors = neighborhood.getUserNeighborhood(userID);
//				if (neighbors.length == 0)
//					System.out.println(userID + " has no neighbor");
//				else
//					for (int i = 0; i < neighbors.length; i++)
//						System.out.println(userID + " " + neighbors[i] + " " + similarity.userSimilarity(userID, neighbors[i]));
				
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
    	System.out.println("User-based CF starts");
    	//BasicConfigurator.configure();
    	computePrediction();
    	System.out.println("User-based CF finishes");
    	endTime = System.currentTimeMillis();
    	System.out.println("User-based CF duration: " + Math.round(endTime - startTime)/1000 + "s" + System.lineSeparator());
    }
}
