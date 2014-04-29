package com.harmonizer.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.types.TrackType;

public class NoteUtils {

	// Tableau de correspondance
	private static ArrayList<String> code = new ArrayList<String>(
			Arrays.asList("do", "re", "mi", "fa", "sol", "la", "si"));
	private static ArrayList<String> lilypond = new ArrayList<String>(
			Arrays.asList(",", "", "'", "''"));
	private static ArrayList<String> lilypondDuration = new ArrayList<String>(
			Arrays.asList("4", "2", "2.", "1"));
	private static Hashtable<String, Integer> midi = new Hashtable<String, Integer>() {
		{
			put("do1", 24);
			put("re1", 26);
			put("mi1", 28);
			put("fa1", 29);
			put("sol1", 31);
			put("la1", 33);
			put("si1", 35);
			put("do2", 36);
			put("re2", 38);
			put("mi2", 40);
			put("fa2", 41);
			put("sol2", 43);
			put("la2", 45);
			put("si2", 47);
			put("do3", 48);
			put("re3", 50);
			put("mi3", 52);
			put("fa3", 53);
			put("sol3", 55);
			put("la3", 57);
			put("si3", 59);
			put("do4", 60);
			put("re4", 62);
			put("mi4", 64);
			put("fa4", 65);
			put("sol4", 67);
			put("la4", 69);
			put("si4", 71);
		}
	};

	private static Hashtable<String, Hashtable<String, int[]>> trackSorted = new Hashtable<String, Hashtable<String, int[]>>() {
		{
			put("ALTO", new Hashtable<String, int[]>() {
				{
					put("do", new int[] { 3, 4 });
					put("re", new int[] { 3, 4 });
					put("mi", new int[] { 3 });
					put("fa", new int[] { 3 });
					put("sol", new int[] { 2, 3 });
					put("la", new int[] { 2, 3 });
					put("si", new int[] { 2, 3 });
				}
			});

			put("TENOR", new Hashtable<String, int[]>() {
				{
					put("do", new int[] { 2, 3 });
					put("re", new int[] { 2, 3 });
					put("mi", new int[] { 2, 3 });
					put("fa", new int[] { 2, 3 });
					put("sol", new int[] { 2, 3 });
					put("la", new int[] { 2, 3 });
					put("si", new int[] { 2 });
				}
			});

			put("BASSE", new Hashtable<String, int[]>() {
				{
					put("do", new int[] { 2, 3 });
					put("re", new int[] { 2, 3 });
					put("mi", new int[] { 2 });
					put("fa", new int[] { 1, 2 });
					put("sol", new int[] { 1, 2 });
					put("la", new int[] { 1, 2 });
					put("si", new int[] { 1, 2 });
				}
			});
		}
	};

	public static int getCode(String name, int octave) {
		return code.indexOf(name) + (7 * (octave - 1));
	}

	public static int getMidi(String name, int octave) {
		return midi.get(name + octave);
	}

	public static String getLilypond(String name, int octave, int duration) {
		return name + lilypond.get(octave - 1)
				+ lilypondDuration.get(duration - 1);
	}

	public static String getLilypongDuration(int duration) {
		return lilypondDuration.get(duration - 1);
	}

	public static ArrayList<Note> getNote(Chord chord, TrackType track, Note soprano) {
		ArrayList<Note> noteList = new ArrayList<Note>(), resultList = new ArrayList<Note>();
		if (track == TrackType.BASSE) {
			noteList.add(chord.getTonic());
		} else {
			if (!chord.getTonic().nameEquals(soprano))
				noteList.add(chord.getTonic());
			if (!chord.getThird().nameEquals(soprano))
				noteList.add(chord.getThird());
			if (!chord.getFifth().nameEquals(soprano))
				noteList.add(chord.getFifth());
		}

		for (Note note : noteList) {
			for (Integer i : trackSorted.get(track.toString()).get(
					note.getName())) {
				Note temp = new Note(note.getName(), i);
				resultList.add(new Note(note.getName(), i));
			}
		}
		return resultList;
	}

}
