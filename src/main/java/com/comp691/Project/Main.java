package com.comp691.Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.comp691.CollaborativeFiltering.*;
import com.comp691.Louvain.ModularityOptimizer;
import com.comp691.Utils.*;

public class Main {
	private static long startTime;
	private static long endTime;
	private static double[] usercfVSlouvain;
	private static double[] itemcfVSlouvain;
	// Stop Apache Mahout from displaying logging to console
	static {
		System.setProperty("org.apache.commons.logging.Log",
							"org.apache.commons.logging.impl.NoOpLog");
		}

	public static void main(String[] args) throws IOException {
		// Generate user-user graph for Louvain method
		Reader.execute();
		
		// Run Recommendation Algorithms
//		startTime = System.currentTimeMillis();
//		//cleanDirectory();
//		UserBasedCF.execute();
//		ItemBasedCF.execute();
//		ModularityOptimizer.execute(); // Required to generate the user-user graph in the 1st run
//		usercfVSlouvain = Evaluation.calcMetrics(Config.USERCF_RESULT_FILE, Config.LOUVAIN_PREDICTION);
//		itemcfVSlouvain = Evaluation.calcMetrics(Config.ITEMCF_RESULT_FILE, Config.LOUVAIN_PREDICTION);
//		writeReport();
//		System.out.println("OPERATION FINISHED");
	}
	
	private static void cleanDirectory() {
		try {
			FileUtils.cleanDirectory(new File(Config.RESULT_FOLDER));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeReport() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(Config.REPORT_FILE));
			bw.write("Dataset = " + Config.DATASET + System.lineSeparator());
			bw.write("CF Rating threshold = " + Config.CF_RATING_THRESHOLD + System.lineSeparator());
			bw.write("CF Number of top predictions = " + Config.CF_NO_OF_TOP_PREDICTION + System.lineSeparator());
			bw.write("User-based CF neighborhood similarity threshold = " + Config.USERCF_NEIGHBORHOOD_SMILARITY_THRESHOLD + System.lineSeparator());
			bw.write("User-based CF Number of nearest neighbors = " + Config.USERCF_NEAREST_USER_NEIGHBORHOOD + System.lineSeparator());
			bw.write("Louvain no of random start = " + Config.LOUVAIN_NO_RANDOM_STARTS + System.lineSeparator());
			bw.write("Louvain no of iterations = " + Config.LOUVAIN_NO_ITERATIONS + System.lineSeparator());
			endTime = System.currentTimeMillis();
			bw.write("Duration: " + (endTime - startTime)/1000 + "s" + System.lineSeparator());
			bw.write(System.lineSeparator());
			bw.write("Louvain vs UserCF: precision = " + usercfVSlouvain[0] + "; recall = " + usercfVSlouvain[1] + "; fscore = " + usercfVSlouvain[2] + System.lineSeparator());
			bw.write("Louvain vs ItemCF: precision = " + itemcfVSlouvain[0] + "; recall = " + itemcfVSlouvain[1] + "; fscore = " + itemcfVSlouvain[2] + System.lineSeparator());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}