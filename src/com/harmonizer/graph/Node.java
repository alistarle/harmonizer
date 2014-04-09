package com.harmonizer.graph;

import java.util.ArrayList;

import com.harmonizer.core.Chord;
import com.harmonizer.core.NoteSet;
import com.harmonizer.core.Song;
import com.harmonizer.utils.ChordUtils;

public class Node {
	private NoteSet noteSet;
	private ArrayList<Node> next;
	private int time;
	
	public Node(NoteSet noteset, int time) {
		this.noteSet = noteSet;
		this.time = time;
		
	}
	
	public ArrayList<Node> getNext() {
		next = new ArrayList<Node>(1);
		if(this.time < Song.duration) {
			for(Chord c : ChordUtils.getNext(chord,Song.trackList.get(0).get(Song.timeline.get(time)))){
				if(time == Song.duration-1) Song.harmonisation++;
				if(time != Song.duration-1 || !ChordUtils.isEdge(c)) next.add(new Node(c, time+1));
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
