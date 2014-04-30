package com.harmonizer.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.types.TrackType;
/**
 * Class utilitaire sur les notes
 * @author alistarle
 *
 */
public class NoteUtils {

	/**
	 * Liste associant un nom de note à un indice, utile dans le calcule du code de la note
	 */
	private static ArrayList<String> code = new ArrayList<String>(Arrays.asList("do", "re", "mi", "fa", "sol", "la", "si"));
	
	/**
	 * Liste associant une octave a une octave au format lilypond
	 */
	private static ArrayList<String> lilypond = new ArrayList<String>(Arrays.asList(",", "", "'", "''"));
	
	/**
	 * Liste associant une durée a une durée au format lilypond
	 */
	private static ArrayList<String> lilypondDuration = new ArrayList<String>(Arrays.asList("4", "2", "2.", "1"));
	
	/**
	 * Hashtable faisant correspondre une note a sa valeur midi
	 */
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
	
	/**
	 * Hashtable triée faisant correspondre une liste d'octave avec une liste de note, regroupé par Voix
	 */
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

	/**
	 * Retourne le code de la note a partir de son nom et son octave
	 * @param name
	 * @param octave
	 * @return
	 */
	public static int getCode(String name, int octave) {
		return code.indexOf(name) + (7 * (octave - 1));
	}

	/**
	 * Retourne la valeur midi de la note a partir de son nom et son octave
	 * @param name
	 * @param octave
	 * @return
	 */
	public static int getMidi(String name, int octave) {
		return midi.get(name + octave);
	}

	/**
	 * Retourne la chaine lilypond de la note a partir de son nom, son octave et sa durée
	 * @param name
	 * @param octave
	 * @param duration
	 * @return
	 */
	public static String getLilypond(String name, int octave, int duration) {
		return name + lilypond.get(octave - 1)
				+ lilypondDuration.get(duration - 1);
	}

	/**
	 * Retourne la durée lilypond d'une note a partir de sa durée en temps musical
	 * @param duration
	 * @return
	 */
	public static String getLilypongDuration(int duration) {
		return lilypondDuration.get(duration - 1);
	}

	/**
	 * Retourne la liste des note possible sur une Voix avec un accord donné, sachant déjà la note de soprano utilisée
	 * @param chord L'accord utilisé
	 * @param track La voix voulu
	 * @param soprano La note de soprano connu
	 * @return La liste des notes possibles de jouer
	 */
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
			for (Integer i : trackSorted.get(track.toString()).get(note.getName())) {
				resultList.add(new Note(note.getName(), i));
			}
		}
		return resultList;
	}

}
