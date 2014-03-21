package com.harmonizer.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteType;

public class ChordUtils {

	private static Chord I = new Chord(new Note(NoteType.DO),new Note(NoteType.MI),new Note(NoteType.SOL));
	private static Chord II = new Chord(new Note(NoteType.RE),new Note(NoteType.FA),new Note(NoteType.LA));
	private static Chord III = new Chord(new Note(NoteType.MI),new Note(NoteType.SOL),new Note(NoteType.SI));
	private static Chord IVa = new Chord(new Note(NoteType.FA),new Note(NoteType.LA),new Note(NoteType.DO));
	private static Chord IVb = new Chord(new Note(NoteType.FA),new Note(NoteType.LA),new Note(NoteType.DO));
	private static Chord V = new Chord(new Note(NoteType.SOL),new Note(NoteType.SI),new Note(NoteType.RE));
	private static Chord VI = new Chord(new Note(NoteType.LA),new Note(NoteType.DO),new Note(NoteType.MI));
	private static Chord VII = new Chord(new Note(NoteType.SI),new Note(NoteType.RE),new Note(NoteType.FA));
	
	private static ArrayList<Chord> nextI = new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextII = new ArrayList<Chord>(Arrays.asList(V, VII));
	private static ArrayList<Chord> nextIII = new ArrayList<Chord>(Arrays.asList(II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextIVa = new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextIVb = new ArrayList<Chord>(Arrays.asList(I));
	private static ArrayList<Chord> nextV = new ArrayList<Chord>(Arrays.asList(I, III, IVb, VI));
	private static ArrayList<Chord> nextVI = new ArrayList<Chord>(Arrays.asList(II, III, IVa, V));
	private static ArrayList<Chord> nextVII = new ArrayList<Chord>(Arrays.asList(I, III));
	
	private static Hashtable<Chord, ArrayList<Chord>> nextChord = new Hashtable<Chord, ArrayList<Chord>>(){{
	    put(I, nextI);	put(II, nextII); put(III, nextIII); put(IVa, nextIVa); put(IVb, nextIVb); put(V, nextV); put(VI, nextVI); put(VII, nextVII);
	}};	
	
	public static ArrayList<Chord> getNext(Chord chord) {
		return nextChord.get(chord);
	}
}
