package com.harmonizer.graph;

import com.harmonizer.core.Chord;

public class Node {
	private Chord chord;
	private int time;
	
	public Node(Chord chord, int time) {
		this.chord = chord;
		this.time = time;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	public int getTime() {
		return time;
	}
}
