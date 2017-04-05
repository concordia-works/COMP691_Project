package com.comp691.Utils;

public class Edge {
	private int sourceNode;
	private int destinationNode;
	private double rating;
	private long timeStamp;

	public Edge(int sourceNode, int destinationNode, double rating, long timeStamp) {
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.rating = rating;
		this.timeStamp = timeStamp;
	}
	
	public Edge(int sourceNode, int destinationNode, double rating) {
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.rating = rating;
		this.timeStamp = 0;
	}
	
	public int getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(int sourceNode) {
		this.sourceNode = sourceNode;
	}

	public int getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(int destinationNode) {
		this.destinationNode = destinationNode;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean a = this.getSourceNode() == ((Edge)obj).getSourceNode() && this.getDestinationNode() == ((Edge)obj).getDestinationNode();
		boolean b = this.getSourceNode() == ((Edge)obj).getDestinationNode() && this.getDestinationNode() == ((Edge)obj).getSourceNode();
		return (a || b);
	}
	
	@Override
	public String toString() {
		return "Edge [sourceNode=" + sourceNode + ", destinationNode=" + destinationNode + ", rating=" + rating
				+ ", timeStamp=" + timeStamp + "]";
	}
}
