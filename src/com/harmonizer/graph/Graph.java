package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.rules.Rule;
import com.harmonizer.utils.ChordUtils;

public class Graph {
	private ArrayList<Node> root;
	
	private ArrayList<ArrayList<Note>> tracklist;
	private ArrayList<Integer> timeline;
	
	private int time;
	private int duration;
	
	public Graph(ArrayList<ArrayList<Note>> trackList, ArrayList<Integer> timeline) {
		this.time = 1;
		this.tracklist = trackList;
		this.timeline = timeline;
		this.root = new ArrayList<Node>();
		
		initGraph();
	}
	
	private void initGraph() {
		for(Chord chord : ChordUtils.getChords()) {
			//System.out.println(chord);
			root.add(new Node(chord, 1));
		}
	}
	
	public void simplify(Rule rule) {
		
	}
}
