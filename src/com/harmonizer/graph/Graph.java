package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.core.NoteSetManager;
import com.harmonizer.rules.Rule;
import com.harmonizer.utils.ChordUtils;

public class Graph {
	private ArrayList<Node> root;
	
	private ArrayList<ArrayList<Note>> tracklist;
	private ArrayList<Integer> timeline;
	private ArrayList<ArrayList<NoteSet>> noteSetsList;
	
	private int time;
	private int duration;
	
	public Graph(ArrayList<ArrayList<Note>> trackList, ArrayList<Integer> timeline) {
		this.time = 1;
		this.tracklist = trackList;
		this.timeline = timeline;
		this.root = new ArrayList<Node>();
		this.noteSetsList = new ArrayList<ArrayList<NoteSet>>();
		
		initNoteSets();
		//initGraph();
		//runThroughGraph(root);
		
		
	}
	
	private void initNoteSets() {
		noteSetsList.add(new NoteSetManager(tracklist.get(0).get(0)).getNoteSets());
		for(int i = 0; i < timeline.size(); i++) {
			noteSetsList.add(new NoteSetManager(tracklist.get(0).get(timeline.get(i))).getNoteSets());
		}
	}

	private void runThroughGraph(ArrayList<Node> nodeList) {
		for(Node node : nodeList) {
			runThroughGraph(node.getNext());
		}
	}

	private void initGraph() {
		for(Chord chord : ChordUtils.getEdges()) {
			root.add(new Node(chord, 1));
		}
	}
	
	public void simplify(Rule rule) {
		
	}
}
