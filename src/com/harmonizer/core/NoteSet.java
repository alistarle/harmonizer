package com.harmonizer.core;

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
	
}
