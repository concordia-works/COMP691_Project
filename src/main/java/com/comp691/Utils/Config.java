package com.comp691.Utils;

public class Config {
	// Dataset
	public static final String DATASET_SAMPLE = "data/dataset.csv";
	public static final String DATASET_AMZ_MUSIC_INSTRUMENTS = "data/amazon_musical_instruments_rating.txt";
	public static final String DATASET_AMZ_ALL = "data/amazon-ratings.edges";
	public static final String DATASET_AMZ_ALL_1_1999 = "data/1-1999.edge";
	
	// Collaborative Filtering
	public static final float CF_RATING_THRESHOLD = 3.5f;
	public static final int CF_NO_OF_TOP_PREDICTION = 10;
	
	// User-based Collaborative Filtering
	public static final double USERCF_NEIGHBORHOOD_SMILARITY_THRESHOLD = 0.1;
	public static final int USERCF_NEAREST_USER_NEIGHBORHOOD = 30;
	public static final String USERCF_OUTPUT_FOLDER = "//Users//midnightblur//comp691//mahout//usercf//";
	public static final String USERCF_REPORT_FILE = "report.txt";
	
	// Item-based Collaborative Filtering
	public static final String ITEMCF_OUTPUT_FOLDER = "//Users//midnightblur//comp691//mahout//itemcf//";
	public static final String ITEMCF_REPORT_FILE = "report.txt";
	
	// Louvain
	public static final String LOUVAIN_INPUT = "data/louvain-input.txt";
	public static final String LOUVAIN_OUTPUT = "//Users//midnightblur//comp691//cishell//louvain_output.txt";
	public static final int LOUVAIN_MODULARITY_FUNCTION = 1; // Modularity function (1 = standard; 2 = alternative)
	public static final double LOUVAIN_RESOLUTION = 1.0; // Resolution parameter (e.g., 1.0)
	public static final int LOUVAIN_ALGORITHM = 1; // Algorithm (1 = Louvain; 2 = Louvain with multilevel refinement; 3 = smart local moving)
	public static final int LOUVAIN_NO_RANDOM_STARTS = 100; // Number of random starts (e.g., 10)
	public static final int LOUVAIN_NO_ITERATIONS = 10; // Number of iterations (e.g., 10)
	public static final long LOUVAIN_RANDOM_SEED = 0; // Random seed (e.g., 0)
	public static final int LOUVAIN_PRINT_OUTPUT = 1; // Print output (0 = no; 1 = yes)
}
