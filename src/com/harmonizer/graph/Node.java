package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Song;
import com.harmonizer.utils.ChordUtils;

public class Node {
	private Chord chord;
	private ArrayList<Node> next;
	private int time;
	
	public Node(Chord chord, int time) {
		this.chord = chord;
		this.time = time;
	}
	
	public ArrayList<Node> getNext() {
		next = new ArrayList<Node>();
		if(this.time < Song.duration) {
			for(Chord c : ChordUtils.getNext(chord)) {
				if(c.contains(Song.trackList.get(0).get(Song.timeline.get(time)))) {
					if(time == Song.duration-1) Song.harmonisation++;
					next.add(new Node(c, time+1));
				}
			}
		}
		return next;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	public int getTime() {
		return time;
	}
}
