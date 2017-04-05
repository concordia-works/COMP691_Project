package com.comp691.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph {
	private List<Edge> edges = new ArrayList<Edge>();

	public void add(Edge edge) {
		edges.add(edge);
	}
	
	public void addUU(Edge edge) {
		if(edges.contains(edge)) {
			Edge oldEdge = edges.get(edges.indexOf(edge));
//			System.out.println("Duplicate edge with rating :" + oldEdge.getRating() + ", " + edge.getRating());
			oldEdge.setRating(oldEdge.getRating() + edge.getRating());
//			System.out.println("Modified edge : " + oldEdge);
		} else {
			edges.add(edge);
		}
	}
	
	public List<Edge> getEdges() {
		return edges;
	}

	/*public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}*/

	public List<Edge> findWithSource(int sourceNode) {
		List<Edge> result = new ArrayList<Edge>();
		
		Iterator<Edge> it = edges.iterator();
		while(it.hasNext()) {
			Edge currEdge = it.next();
			if(currEdge.getSourceNode() == sourceNode) {
				result.add(currEdge);
			}
		}
		return result;
	}
	
	public List<Edge> findWithDestination(int destinationNode) {
		List<Edge> result = new ArrayList<Edge>();
		
		Iterator<Edge> it = edges.iterator();
		while(it.hasNext()) {
			Edge currEdge = it.next();
			if(currEdge.getDestinationNode() == destinationNode) {
				result.add(currEdge);
			}
		}
		return result;
	}
	
	public Edge findWithSourceDest(int sourceNode, int destinationNode) {
		
		Edge result = null;
		Iterator<Edge> it = edges.iterator();
		while(it.hasNext()) {
			Edge currEdge = it.next();
			if(currEdge.getSourceNode() == sourceNode && currEdge.getDestinationNode() == destinationNode) {
				result = currEdge;
			}
		}
		return result;
	}
	
	public void print() {
		String outputFileName = Config.LOUVAIN_INPUT;
		try(BufferedWriter br = new BufferedWriter(new FileWriter(outputFileName));) {
			for (int i = 0; i < edges.size(); i++) {
				//System.out.println(edges.get(i));
				//br.write(edges.get(i).toString() + "\n");
				Edge edge = edges.get(i);
				br.write(edge.getSourceNode() + "\t" + edge.getDestinationNode() + "\t" +
						edge.getRating() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
