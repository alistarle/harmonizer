package com.harmonizer.core;

import java.util.ArrayList;

import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.NoteUtils;
import com.harmonizer.utils.TrackType;

public class NoteSetManager {
	private Note soprano;	
	private ArrayList<NoteSet> noteSets;

	public NoteSetManager(Note soprano) {
		this.soprano = soprano;
		genNoteSets(ChordUtils.getChords(soprano));
	}
	
	public NoteSetManager(Note soprano, Chord chord) {
		this.soprano = soprano;
		genNoteSets(ChordUtils.getChords(chord, soprano));
	}
	
	private void genNoteSets(ArrayList<Chord> chord) {
		for(Chord c : chord) {
			genNoteSets(c);
		}
	}

	private void genNoteSets(Chord chord) {
		for(Note alto : NoteUtils.getNote(chord, TrackType.ALTO, soprano)) {
			for(Note tenor : NoteUtils.getNote(chord, TrackType.TENOR, soprano)) {
				for(Note basse : NoteUtils.getNote(chord, TrackType.BASSE, soprano)) {
					noteSets.add(new NoteSet(soprano,alto,tenor,basse,chord));
				}
			}
		}	
	}
	
	public ArrayList<NoteSet> getNoteSets() {
		return noteSets; 
	}
}
