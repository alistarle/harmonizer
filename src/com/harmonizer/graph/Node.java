package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Song;
import com.harmonizer.utils.ChordUtils;

public class Node {
	private Chord chord;
	private ArrayList<Node> next = new ArrayList<Node>();
	private int time;
	
	public Node(Chord chord, int time) {
		this.chord = chord;
		this.time = time;
		if(time <= Song.duration) {
			for(Chord c : ChordUtils.getNext(chord)) {
				//for(int i=0; i<time; i++) System.out.print("\t");
				//System.out.println(c);
				if(time == Song.duration) Song.harmonisation++;
				next.add(new Node(c, time+1));
			}
		}
	}
	
	public ArrayList<Node> getNext() {
		return next;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	public int getTime() {
		return time;
	}
}
