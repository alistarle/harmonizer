package com.harmonizer.graph;

import java.util.ArrayList;
import java.util.HashSet;

import com.harmonizer.core.NoteSet;
import com.harmonizer.core.Song;
import com.harmonizer.utils.NodeUtils;
/**
 * Representation d'un noeuds dans le graphe des harmonisation possible, il est caractérisé par un jeu de note, un temp, et la liste des ses suivants
 * @author alistarle
 *
 */
public class Node {
	/**
	 * Jeu de note du noeud
	 */
	private NoteSet noteSet;
	
	/**
	 * Liste des noeud suivant
	 */
	private HashSet<Node> next;
	
	/**
	 * Temps auquel le noeud se rapporte dans la timeline
	 */
	private int time;

	/**
	 * Crée un nouveau noeud a partir d'un jeu de note et d'un temps
	 * @param noteSet
	 * @param time
	 */
	public Node(NoteSet noteSet, int time) {
		this.noteSet = noteSet;
		this.time = time;
		this.next = new HashSet<Node>();
	}
	
	/**
	 * Crée un nouveau noeud en spécifiant un jeu de note, un temps et un la liste de ses noeuds suivant
	 * @param noteSet
	 * @param time
	 * @param next
	 */
	public Node(NoteSet noteSet, int time, HashSet<NoteSet> next) {
		this.noteSet = noteSet;
		this.time = time;
		this.next = new HashSet<Node>();
		genNext(next);
	}
	
	/**
	 * Génère la liste des noeuds suivant a partir des jeu de note suivant possible
	 */
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
