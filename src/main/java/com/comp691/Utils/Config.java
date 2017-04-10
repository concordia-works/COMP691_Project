package com.comp691.Utils;

public class Config {
	// Dataset
	//public static final String DATASET = "//Users//midnightblur//comp691//sample.csv";
	//public static final String DATASET = "//Users//midnightblur//comp691//amazon_musical_instruments_rating.txt";
	//public static final String DATASET = "//Users//midnightblur//comp691//amazon-ratings.edges";
	//public static final String DATASET = "//Users//midnightblur//comp691//1-199.edge";
	public static final String DATASET = "//Users//midnightblur//comp691//movielens_small.csv";
	//public static final String DATASET = "//Users//midnightblur//comp691//simple.txt";
	//public static final String DATASET = "//Users//midnightblur//comp691//FilmTrust.csv";
	
	public static final String RESULT_FOLDER = "//Users//midnightblur//comp691//results//";
	public static final String REPORT_FILE = "//Users//midnightblur//comp691//results//report.txt";
	
	// Collaborative Filtering
	public static final float CF_RATING_THRESHOLD = 2.5f;
	//public static final int CF_NO_OF_TOP_PREDICTION = 30;
	
	// User-based Collaborative Filtering
	public static final double USERCF_NEIGHBORHOOD_SMILARITY_THRESHOLD = 0.5;
	public static final int USERCF_NEAREST_USER_NEIGHBORHOOD = 30;
	public static final String USERCF_RESULT_FILE = "//Users//midnightblur//comp691//results//usercf_output.txt";
	public static final String USERCF_REPORT_FILE = "//Users//midnightblur//comp691//results//usercf_report.txt";
	
	// Item-based Collaborative Filtering
	public static final String ITEMCF_RESULT_FILE = "//Users//midnightblur//comp691//results//itemcf_output.txt";
	public static final String ITEMCF_REPORT_FILE = "//Users//midnightblur//comp691//results//itemcf_report.txt";
	
	// Evaluation
	public static final int NO_TOP_PREDICTIONS = 9125;

	// Louvain
	public static final String LOUVAIN_INPUT = "//Users//midnightblur//comp691//results//louvain_input.txt";
	public static final String LOUVAIN_OUTPUT = "//Users//midnightblur//comp691//results//louvain_output.txt";
	public static final int LOUVAIN_MODULARITY_FUNCTION = 1; // Modularity function (1 = standard; 2 = alternative)
	public static final double LOUVAIN_RESOLUTION = 7.0; // Resolution parameter (e.g., 1.0)
	public static final int LOUVAIN_ALGORITHM = 1; // Algorithm (1 = Louvain; 2 = Louvain with multilevel refinement; 3 = smart local moving)
	public static final int LOUVAIN_NO_RANDOM_STARTS = 10; // Number of random starts (e.g., 10)
	public static final int LOUVAIN_NO_ITERATIONS = 10; // Number of iterations (e.g., 10)
	public static final long LOUVAIN_RANDOM_SEED = 0; // Random seed (e.g., 0)
	public static final int LOUVAIN_PRINT_OUTPUT = 1; // Print output (0 = no; 1 = yes)
	public static final String LOUVAIN_PREDICTION = "//Users//midnightblur//comp691//results//louvain-prediction.txt";
}
