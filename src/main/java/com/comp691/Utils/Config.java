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
	
	// Evaluation
	public static final String EVA_BASED_PREDICTIONS_FOLDER = "//Users//midnightblur//comp691//mahout//usercf//";
	public static final String EVA_RETRIEVED_PREDICTIONS_FOLDER = "//Users//midnightblur//comp691//mahout//itemcf//";
}
