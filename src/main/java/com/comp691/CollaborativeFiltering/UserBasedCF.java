package com.comp691.CollaborativeFiltering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
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
				bw.write(userID + " {");
				List<RecommendedItem> recommendations = recommender.recommend(userID, Config.CF_NO_OF_TOP_PREDICTION);
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
			
//			RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
//				  public Recommender buildRecommender(DataModel model) throws TasteException {
//				    UserSimilarity similarity = new UncenteredCosineSimilarity(model);
//				    UserNeighborhood neighborhood =
//				     new NearestNUserNeighborhood (2, similarity, model);
//				      return
//				      new GenericUserBasedRecommender (model, neighborhood, similarity);
//				     }
//				   };
//			
//			RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
//			IRStatistics stats = evaluator.evaluate(recommenderBuilder, null, model, null, 10, org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
//			System.out.println(stats.getPrecision());
//			System.out.println(stats.getRecall());
//			System.out.println(stats.getF1Measure());
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
