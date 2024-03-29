package com.harmonizer.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.types.NoteType;
/**
 * Class utilitaire sur les accord
 * @author alistarle
 *
 */
public class ChordUtils {

	private static Chord I = new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL));
	private static Chord II = new Chord(new Note(NoteType.RE), new Note(NoteType.FA), new Note(NoteType.LA));
	private static Chord III = new Chord(new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.SI));
	private static Chord IVa = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
	private static Chord IVb = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
	private static Chord V = new Chord(new Note(NoteType.SOL), new Note(NoteType.SI), new Note(NoteType.RE));
	private static Chord VI = new Chord(new Note(NoteType.LA), new Note(NoteType.DO), new Note(NoteType.MI));
	private static Chord VII = new Chord(new Note(NoteType.SI), new Note(NoteType.RE), new Note(NoteType.FA));

	private static ArrayList<Chord> nextI = new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextII = new ArrayList<Chord>(Arrays.asList(V, VII));
	private static ArrayList<Chord> nextIII = new ArrayList<Chord>(Arrays.asList(II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextIVa = new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, V, VI, VII));
	private static ArrayList<Chord> nextIVb = new ArrayList<Chord>(Arrays.asList(I));
	private static ArrayList<Chord> nextV = new ArrayList<Chord>(Arrays.asList(I, III, IVb, VI));
	private static ArrayList<Chord> nextVI = new ArrayList<Chord>(Arrays.asList(II, III, IVa, V));
	private static ArrayList<Chord> nextVII = new ArrayList<Chord>(Arrays.asList(I, III));

	/**
	 * Hashtable associant un accord static a une liste d'accord suivant
	 */
	private static Hashtable<Chord, ArrayList<Chord>> nextChord = new Hashtable<Chord, ArrayList<Chord>>() {
		{
			put(I, nextI);
			put(II, nextII);
			put(III, nextIII);
			put(IVa, nextIVa);
			put(IVb, nextIVb);
			put(V, nextV);
			put(VI, nextVI);
			put(VII, nextVII);
		}
	};

	/**
	 * Retourne la liste de tous les accords possibles
	 * @return
	 */
	public static ArrayList<Chord> getChords() {
		return new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, IVb, V, VI,
				VII));
	}

	/**
	 * Retourne la liste de tous les accords possibles a defaut du IVb inutilisable en premiere et derniere position dans l'harmonisation
	 * @return
	 */
	public static ArrayList<Chord> getEdges() {
		return new ArrayList<Chord>(Arrays.asList(I, II, III, IVa, V, VI, VII));
	}

	/**
	 * Verifie si un acord est consideté comme un bord (c.a.d un IVb)
	 * @param c
	 * @return Si l'accord est consideré comme un bord ou non
	 */
	public static boolean isEdge(Chord c) {
		return c.equals(IVb);
	}

	/**
	 * Retourne la liste des accords suivant un accord
	 * @param chord
	 * @return
	 */
	public static ArrayList<Chord> getNext(Chord chord) {
		return nextChord.get(chord);
	}

	/**
	 * Retourne la liste des accords dans laquelle la note est présente
	 * @param note
	 * @return
	 */
	public static ArrayList<Chord> getChords(Note note) {
		ArrayList<Chord> list = new ArrayList<Chord>();
		for (Chord chord : getChords()) {
			if (chord.contains(note)) {
				list.add(chord);
			}
		}
		return list;
	}

	/**
	 * Renvoie la liste des accord suivant dans lesquelles la note fournie est présente
	 * @param chord
	 * @param note
	 * @return
	 */
	public static ArrayList<Chord> getChords(Chord chord, Note note) {
		ArrayList<Chord> list = new ArrayList<Chord>();
		for (Chord c : getNext(chord)) {
			if (c.contains(note)) {
				list.add(c);
			}
		}
		return list;
	}
}
