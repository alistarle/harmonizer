package com.harmonizer.core;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteSet {
	private Note soprano;
	private Note alto;
	private Note tenor;
	private Note basse;

	private Chord chord;

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

	public boolean contains(Note note) {
		return soprano.equals(note) || alto.equals(note) || tenor.equals(note)
				|| basse.equals(note);
	}

	public ArrayList<Note> getNote() {
		return new ArrayList<Note>(Arrays.asList(soprano, alto, tenor, basse));
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
