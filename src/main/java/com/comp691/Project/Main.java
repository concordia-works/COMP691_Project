package com.comp691.Project;

import com.comp691.CollaborativeFiltering.*;

public class Main {
	// Stop Apache Mahout from displaying logging to console
	static {
	      System.setProperty("org.apache.commons.logging.Log",
	                         "org.apache.commons.logging.impl.NoOpLog");
	   }
	
	public static void main(String[] args) {
		UserBasedCF.execute();
		//ItemBasedCF.execute();
	}

}
