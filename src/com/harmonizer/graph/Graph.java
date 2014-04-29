package com.harmonizer.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.core.NoteSetManager;
import com.harmonizer.core.Song;
import com.harmonizer.rules.LinkRule;
import com.harmonizer.rules.LocalRule;
import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.RuleUtils;

public class Graph {
	private Node root;
	
	private ArrayList<ArrayList<Note>> tracklist;
	private ArrayList<Integer> timeline;
	private ArrayList<ArrayList<NoteSet>> noteSetsList;
	private ArrayList<ArrayList<ArrayList<Integer>>> linkNoteSets;
	
	private int time;
	private int duration;
	
	public Graph(ArrayList<ArrayList<Note>> trackList, ArrayList<Integer> timeline) {
		this.time = 1;
		this.tracklist = trackList;
		this.timeline = timeline;
		this.noteSetsList = new ArrayList<ArrayList<NoteSet>>();
		this.linkNoteSets = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		initNoteSets();
		localSimplify();
		//displayNoteSetStats();
		initLinking();
		//simplifyLinking();
		//displayLinkingStats();
		initGraph();
		writeTrack((Node) root.getNext().toArray()[0],0);
		//runThroughGraph(root.getNext());
	}
	
	private void initNoteSets() {
		noteSetsList.add(new NoteSetManager(tracklist.get(0).get(timeline.get(0))).getNoteSets());
		for(int i = 1; i < timeline.size(); i++) {
			noteSetsList.add(new NoteSetManager(tracklist.get(0).get(timeline.get(i))).getNoteSets());
		}
	}

	private void runThroughGraph(HashSet<Node> nodeList) {
		for(Node node : nodeList) {
			runThroughGraph(node.getNext());
		}
	}

	private void initGraph() {
		HashSet<NoteSet> next = new HashSet<NoteSet>();
		for(NoteSet ns : noteSetsList.get(0)) {
			next.add(ns);
		}
		root = new Node(null, -1,next);
		genGraph(root.getNext(), 0);
	}
	
	public void genGraph(HashSet<Node> nodeList, int time) {
		for(Node node : nodeList) {
			HashSet<NoteSet> next = new HashSet<NoteSet>();
			for(Integer i : linkNoteSets.get(time).get(noteSetsList.get(time).indexOf(node.getNoteSet()))) {
				next.add(noteSetsList.get(time+1).get(i));
			}
			node.genNext(next);
		}		
		if(time < Song.duration-2) {
			HashSet<Node> nextNode = new HashSet<Node>();
			for(Node node : nodeList) {
				nextNode.addAll(node.getNext());
			}
			genGraph(nextNode, time+1);
		}
	}
	
	public void initLinking() {
		for(int i = 0; i < noteSetsList.size()-1; i++) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(NoteSet ns : noteSetsList.get(i)) {
				ArrayList<ArrayList<Integer>> byRuleList = new ArrayList<ArrayList<Integer>>();
				for(LinkRule rule : RuleUtils.getLinkRules()) {
					byRuleList.add(rule.simplify(ns, noteSetsList.get(i+1)));
				}
				
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				for(Integer j : byRuleList.get(0)) {
					if(RuleUtils.containsAll(j, byRuleList)) tempList.add(j);
				}
				temp.add(tempList);
			}
			linkNoteSets.add(temp);
		}
	}
	
	public void simplifyLinking() {
		int harmonisation = 0;
		for(int i = linkNoteSets.size()-1; i >= 0; i--) {
			for(int j = linkNoteSets.get(i).size()-1; j >= 0; j--) {
				harmonisation+=linkNoteSets.get(i).get(j).size();
				if(linkNoteSets.get(i).get(j).size() == 0) {
					linkNoteSets.get(i).remove(j);
				}
			}
		}
		System.out.println("Il y a "+harmonisation+" harmonisations possibles");
	}
	
	public void localSimplify() {
		for(LocalRule rule : RuleUtils.getLocalRules()) {
			for(ArrayList<NoteSet> ns : noteSetsList) {
				rule.simplify(ns);
			}
		}
	}
	
	public void writeTrack(Node node, int time) {
		tracklist.get(1).add(node.getNoteSet().getAlto());
		tracklist.get(2).add(node.getNoteSet().getTenor());
		tracklist.get(3).add(node.getNoteSet().getBasse());
		if(node.getNext().size() != 0) {
			boolean passed = false;
			for(Node n : node.getNext()){
				if(n.getNext().size() != 0 && time < Song.duration-1 && !passed) {
					passed = true;
					writeTrack(n,time+1);
				}
			}
		}
	}
	
	public void displayNoteSetStats() {
		System.out.println("Taille des noteSetList :");
		for(int i = 0; i < noteSetsList.size(); i++) {
			System.out.println("\t"+i+" : "+noteSetsList.get(i).size());
		}
	}
	
	public void displayLinkingStats() {
		System.out.println("Affichage des lien entre noteSet :");
		for(int c = 0; c < linkNoteSets.size(); c++) {
			System.out.print(c+" : ");
			for(ArrayList<Integer> i : linkNoteSets.get(c)) {
				System.out.print("\t"+i.size());
			}
			System.out.println();
		}
	}
}
