package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.rules.Rule;

public class Graph {
	private ArrayList<Node> nodeList;
	private ArrayList<Edges> edgesList;
	
	private ArrayList<ArrayList<Note>> tracklist;
	private ArrayList<Integer> timeline;
	
	private int time;
	
	public Graph(ArrayList<ArrayList<Note>> trackList, ArrayList<Integer> timeline) {
		this.time = 0;
		this.tracklist = trackList;
		this.timeline = timeline;
		
		initGraph();
	}
	
	private void initGraph() {
		nodeList.add(new Node(new Chord(tracklist.get(0).get(0)), time));
		time++;
		for(Note note : tracklist.get(0)) {
			nodeList.add(new Node(new Chord(nodeList.get(time-1).getChord(),note), time));
			for(Chord chord : nodeList.get(time).getChord().getNext()) {
				//nodeList.add()
			}
			time++;
		}
	}
	
	private boolean recursiveWriteGraph() {
		//if(!recursiveWriteGraph())
		return false;
	}
	
	public void simplify(Rule rule) {
		
	}
}
