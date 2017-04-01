package com.comp691.Project;

import com.comp691.CollaborativeFiltering.*;
import com.comp691.Utils.*;

public class Main {
	// Stop Apache Mahout from displaying logging to console
<<<<<<< HEAD
		static {
		      System.setProperty("org.apache.commons.logging.Log",
		                         "org.apache.commons.logging.impl.NoOpLog");
		   }

=======
	static {
	      System.setProperty("org.apache.commons.logging.Log",
	                         "org.apache.commons.logging.impl.NoOpLog");
	   }
	
>>>>>>> master
	public static void main(String[] args) {
		// Run Recommendation Algorithms
		UserBasedCF.execute();
		//ItemBasedCF.execute();
<<<<<<< HEAD
		
		// Compare Recommendation Algorithms
		//Evaluation.compareAlgos(Config.EVA_RETRIEVED_PREDICTIONS_FOLDER, Config.EVA_BASED_PREDICTIONS_FOLDER);
=======
>>>>>>> master
	}

}