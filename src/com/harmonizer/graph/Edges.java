package com.harmonizer.graph;

public class Edges {
	private Node from;
	private Node to;
	
	public Edges(Node from, Node to) {
		this.from = from;
		this.to = to;
	}
	
	private Node getFrom() {
		return from;
	}
	
	private Node getTo() {
		return to;
	}
}
