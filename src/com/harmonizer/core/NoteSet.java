package com.harmonizer.core;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Jeu de note, comprenant 1 notes pour chaque voix ainsi qu'un accord
 * @author alistarle
 *
 */
public class NoteSet {
	
	/**
	 * Note soprano du jeu de note
	 */
	private Note soprano;
	
	/**
	 * Note alto du jeu de note
	 */
	private Note alto;
	
	/**
	 * Note tenor du jeu de note
	 */
	private Note tenor;
	
	/**
	 * Note basse du jeu de note
	 */
	private Note basse;

	/**
	 * Accord du jeu de note
	 */
	private Chord chord;

	/**
	 * Crée un jeu de note a partir de 4 note et un accord
	 * @see Note
	 * @see Chord
	 * @param soprano
	 * @param alto
	 * @param tenor
	 * @param basse
	 * @param chord
	 */
	public NoteSet(Note soprano, Note alto, Note tenor, Note basse, Chord chord) {
		this.soprano = soprano;
		this.alto = alto;
		this.tenor = tenor;
		this.basse = basse;
		this.chord = chord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alto == null) ? 0 : alto.hashCode());
		result = prime * result + ((basse == null) ? 0 : basse.hashCode());
		result = prime * result + ((soprano == null) ? 0 : soprano.hashCode());
		result = prime * result + ((tenor == null) ? 0 : tenor.hashCode());
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
		NoteSet other = (NoteSet) obj;
		if (alto == null) {
			if (other.alto != null)
				return false;
		} else if (!alto.equals(other.alto))
			return false;
		if (basse == null) {
			if (other.basse != null)
				return false;
		} else if (!basse.equals(other.basse))
			return false;
		if (soprano == null) {
			if (other.soprano != null)
				return false;
		} else if (!soprano.equals(other.soprano))
			return false;
		if (tenor == null) {
			if (other.tenor != null)
				return false;
		} else if (!tenor.equals(other.tenor))
			return false;
		return true;
	}

	/**
	 * Indique si une note appartient ou non au jeu
	 * @param note note a comparer
	 * @return si la note appartient au jeu
	 */
	public boolean contains(Note note) {
		return soprano.equals(note) || alto.equals(note) || tenor.equals(note)
				|| basse.equals(note);
	}

	/**
	 * Retourne la liste des notes composant le jeu
	 * @return Liste des notes composant le jeu
	 */
	public ArrayList<Note> getNote() {
		return new ArrayList<Note>(Arrays.asList(soprano, alto, tenor, basse));
	}
	
	/**
	 * Retourne la liste des notes du jeu, sauf la basse
	 * @return Liste des notes composant l'accord
	 */
	public ArrayList<Note> getChordNote() {
		return new ArrayList<Note>(Arrays.asList(soprano, alto, tenor));
	}

	public Note getSoprano() {
		return soprano;
	}

	public Note getAlto() {
		return alto;
	}

	public Note getTenor() {
		return tenor;
	}

	public Note getBasse() {
		return basse;
	}

	public Chord getChord() {
		return chord;
	}

}
