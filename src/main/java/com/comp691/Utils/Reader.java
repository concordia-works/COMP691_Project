package com.comp691.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Reader {
	static Graph graph = new Graph();
	static HashMap<Integer, Integer> userDegree = new HashMap<>();
	static HashMap<Integer, Integer> itemDegree = new HashMap<>();
	
	public static void readInput() {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			String inputFileName = Config.DATASET;
			//String inputFileName = "G:\\workspace\\Comp691\\testInput.txt";
			br = new BufferedReader(new FileReader(inputFileName));
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(",");
				int sourceNode = Integer.parseInt(parts[0]);
				int destinationNode = Integer.parseInt(parts[1]);
				double rating = Double.parseDouble(parts[2]);
				
				// Dataset may have or don't have timestamp
				long timeStamp;
				if (parts.length == 4)
					timeStamp = Long.parseLong(parts[3]);
				else timeStamp = 0;
				
				// for user degree calculation
				if(!userDegree.containsKey(sourceNode)) {
					userDegree.put(sourceNode, 1);
				} 
				else
				{
					userDegree.put(sourceNode, userDegree.get(sourceNode) + 1);
				}
				
				// for item degree calculation
				if(!itemDegree.containsKey(destinationNode)) {
					itemDegree.put(destinationNode, 1);
				} 
				else
				{
					itemDegree.put(destinationNode, itemDegree.get(destinationNode) + 1);
				}
				
				
				Edge e = new Edge(sourceNode, destinationNode, rating, timeStamp);
				graph.add(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static Graph process() {
		Iterator<Edge> it = graph.getEdges().iterator();
		
		Graph userUserGraph = new Graph();
		
		
		while(it.hasNext()) {
			Edge currEdge = it.next();
			int sourceNode1 = currEdge.getSourceNode();
			int destinationNode = currEdge.getDestinationNode();
			double rating1 = currEdge.getRating();
			double rating;
			System.out.println(sourceNode1 + " " + destinationNode);
			List<Edge> otherEdges = graph.findWithDestination(destinationNode);
			
			Iterator<Edge> itOtherEdges = otherEdges.iterator();
			while(itOtherEdges.hasNext()) {
				Edge tempEdge = itOtherEdges.next();
				int sourceNode2 = tempEdge.getSourceNode();
				int itemId = tempEdge.getDestinationNode();
				if(sourceNode1 != sourceNode2) {
					double rating2 = tempEdge.getRating();

					// method 1 to calculate edge weight
//					double diff = rating2 - rating1;
//					if(diff == 0) {
//						rating = 5;
//					} else {
//						rating = 5 - Math.abs(diff);
//					}
					
					// method 2 to calculate edge weight (users who disagree have negative weight, users who agree have positive weight)
					double diff = Math.abs(rating2 - rating1);
					boolean sameOpinion = (((rating2 >= 2.5) && (rating1 >= 2.5)) || ((rating2 < 2.5) && (rating1 < 2.5)));
					if (sameOpinion)
						rating = 5 - diff;
					else
						rating = 0 - diff;
					//rating = rating1 + rating2;
					
					Double ratingU1, ratingU2;
					
					rating = rating / itemDegree.get(itemId);
					
					ratingU1 = rating / userDegree.get(sourceNode1);
					
					ratingU2 = rating / userDegree.get(sourceNode2);
					
					double finalRating = (ratingU1 + ratingU2)/2;
					
					userUserGraph.addUU(new Edge(sourceNode1, sourceNode2, finalRating));
					//System.out.println(sourceNode1 + " " + sourceNode2 + " " + finalRating);
				}
			}
		}
		return userUserGraph;
	}

	/*
	 * double rating2 = tempEdge.getRating();
					
					double diff = rating2 - rating1;
					if(diff == 0) {
						rating = 5;
					} else {
						rating = 5 - Math.abs(diff);
					}
	 */
	
	public static void execute() {
		readInput();
		//graph.print();
		Graph UUgraph = process();
		//UUgraph.scaling();
		UUgraph.print();
	}
}