package com.harmonizer.graph;

import java.util.ArrayList;
import java.util.HashSet;

import com.harmonizer.core.NoteSet;
import com.harmonizer.core.Song;
import com.harmonizer.utils.NodeUtils;

public class Node {
	private NoteSet noteSet;
	private HashSet<Node> next;
	private int time;

	public Node(NoteSet noteSet, int time) {
		this.noteSet = noteSet;
		this.time = time;
		this.next = new HashSet<Node>();
	}
	
	public Node(NoteSet noteSet, int time, HashSet<NoteSet> next) {
		this.noteSet = noteSet;
		this.time = time;
		this.next = new HashSet<Node>();
		genNext(next);
	}
	
	public void genNext(HashSet<NoteSet> next) {
		for(NoteSet ns : next) {
			this.next.add(NodeUtils.getNode(new Node(ns,time+1)));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((noteSet == null) ? 0 : noteSet.hashCode());
		result = prime * result + time;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (noteSet == null) {
			if (other.noteSet != null)
				return false;
		} else if (!noteSet.equals(other.noteSet))
			return false;
		if (time != other.time)
			return false;
		return true;
	}

	public HashSet<Node> getNext() {
		return next;
	}

	public NoteSet getNoteSet() {
		return noteSet;
	}

	public int getTime() {
		return time;
	}
}
