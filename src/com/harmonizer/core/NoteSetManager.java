package com.harmonizer.core;

import java.util.ArrayList;

import com.harmonizer.types.TrackType;
import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.NoteUtils;
/**
 * Génère la liste des jeux de notes possible a partir d'une note de soprano et/ou d'un accord
 * @author alistarle
 *
 */
public class NoteSetManager {
	
	/**
	 * Note de soprano à l'origine des jeux de notes
	 */
	private Note soprano;
	
	/**
	 * NoteSets générés a partir d'une note ou d'un accord
	 */
	private ArrayList<NoteSet> noteSets;

	/**
	 * Génère les noteSets possible a partir d'une note de soprano
	 * @param soprano Note de soprano
	 */
	public NoteSetManager(Note soprano) {
		this.soprano = soprano;
		this.noteSets = new ArrayList<NoteSet>();
		genNoteSets(ChordUtils.getChords(soprano));
	}

	/**
	 * Génère les noteSets possible a partir d'une note de soprano et d'un accord
	 * @param soprano 
	 * @param chord
	 */
	public NoteSetManager(Note soprano, Chord chord) {
		this.soprano = soprano;
		this.noteSets = new ArrayList<NoteSet>();
		genNoteSets(ChordUtils.getChords(chord, soprano));
	}

	/**
	 * Genère la liste des jeux de note possible
	 * @param chord Liste des accord suivant 
	 */
	private void genNoteSets(ArrayList<Chord> chord) {
		for (Chord c : chord) {
			genNoteSets(c);
		}
	}

	/**
	 * Genère une partie de la liste des jeu de note possible pour chaque accord
	 * @param chord Un accord donné
	 */
	private void genNoteSets(Chord chord) {
		for (Note alto : NoteUtils.getNote(chord, TrackType.ALTO, soprano)) {
			for (Note tenor : NoteUtils.getNote(chord, TrackType.TENOR, soprano)) {
				for (Note basse : NoteUtils.getNote(chord, TrackType.BASSE,soprano)) {
					noteSets.add(new NoteSet(soprano, alto, tenor, basse, chord));
				}
			}
		}
	}

	public ArrayList<NoteSet> getNoteSets() {
		return noteSets;
	}
}
