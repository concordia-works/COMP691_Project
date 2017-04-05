package com.comp691.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Reader {
	static Graph graph = new Graph();
	
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
				long timeStamp;
				Edge e;
				if (parts.length == 4) {
					timeStamp = Long.parseLong(parts[3]);
					e = new Edge(sourceNode, destinationNode, rating, timeStamp);
				} else {
					e = new Edge(sourceNode, destinationNode, rating);
				}
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
			double rating = 5;
			
			List<Edge> otherEdges = graph.findWithDestination(destinationNode);
			
			Iterator<Edge> itOtherEdges = otherEdges.iterator();
			while(itOtherEdges.hasNext()) {
				Edge tempEdge = itOtherEdges.next();
				int sourceNode2 = tempEdge.getSourceNode();
				if(sourceNode1 != sourceNode2) {
					double rating2 = tempEdge.getRating();
					
					double diff = rating2 - rating1;
					if(diff == 0) {
						rating = 5;
					} else {
						rating = 5 - Math.abs(diff);
					}
					//userUserGraph.findWithSourceDest(sourceNode1, sourceNode2);
					userUserGraph.addUU(new Edge(sourceNode1, sourceNode2, rating, 0));
				}
			}
		}
		
		return userUserGraph;
	}

	public static void execute() {
		System.out.println("Start generate louvain input");
		readInput();
		//graph.print();
		Graph UUgraph = process();
		UUgraph.print();
		System.out.println("Louvain input generated");
	}
}