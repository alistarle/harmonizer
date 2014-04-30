package com.harmonizer.core;

import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.types.NoteType;
import com.harmonizer.utils.NoteUtils;
/**
 * Representation d'un Note dans un chant/accord/Jeux de note ou autre
 * @author alistarle
 *
 */
public class Note {
	
	/**
	 * Nom de la nom, parmis 7 possibles
	 */
	private String name;
	
	/**
	 * Octave de la note, compris entre 1 et 4
	 */
	private int octave;
	
	/**
	 * Valeur midi de la note
	 */
	private int midi;
	
	/**
	 * Code representatif de la note, entre 1 et 27
	 */
	private int code;
	
	/**
	 * Representation lilypond de la note
	 */
	private String lilypond;
	
	/**
	 * Durée de la note en temp musical
	 */
	private int duration;

	/**
	 * Initialise tout les paramètres de la note en decoupant une chaine via une expression régulière
	 * @param data Represente la note tel que lue dans le fichier chant
	 * @throws ParsingException Si la note ne peut pas être reconnue via un regex de decryptage
	 */
	public Note(String data) throws ParsingException {
		if (data.matches("^((do|re|mi|fa|sol|la|si)[1-4]|-):[1-9][0-9]*$")) {
			String[] noteData = data.split(":");
			duration = Integer.valueOf(noteData[1]);
			if (!noteData[0].equals("-")) {
				name = noteData[0].substring(0, noteData[0].length() - 1);
				octave = Integer.valueOf(noteData[0].substring(noteData[0].length() - 1, noteData[0].length()));
				midi = NoteUtils.getMidi(name, octave);
				code = NoteUtils.getCode(name, octave);
				lilypond = NoteUtils.getLilypond(name, octave, duration);
			} else {
				name = "-";
				lilypond = "r" + NoteUtils.getLilypongDuration(duration);
			}
		} else {
			throw new ParsingException(data);
		}
	}

	/**
	 * Initialise une note via son nom et son octave
	 * @param name Nom de la note parmis les 7 possibles
	 * @param octave Octave de la note entre 1 et 4
	 */
	public Note(String name, int octave) {
		this.name = name;
		this.octave = octave;
		this.duration = 1;
		this.midi = NoteUtils.getMidi(name, octave);
		this.code = NoteUtils.getCode(name, octave);
		this.lilypond = NoteUtils.getLilypond(name, octave, 1);
	}

	/**
	 * Initialise une note avec seulement son nom, utilisée principalement par les accords
	 * @param name Nom de la note parmis une énumération des 7 possibles
	 */
	public Note(NoteType name) {
		this.name = name.toString().toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + octave;
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
		Note other = (Note) obj;
		if (duration != other.duration)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (octave != other.octave)
			return false;
		return true;
	}

	/**
	 * Fonction equals ne prenant en compte que le nom de la note
	 * @param note Note à comparer
	 * @return si le nom des deux notes est identique
	 */
	public boolean nameEquals(Note note) {
		return note.getName().equals(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public int getMidi() {
		return midi;
	}

	public void setMidi(int midi) {
		this.midi = midi;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getLilypond() {
		return lilypond;
	}

	public void setLilypond(String lilypond) {
		this.lilypond = lilypond;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Note [name=" + name + ", lilypond =" + lilypond + ", octave="
				+ octave + ", midi=" + midi + ", code=" + code + ", duration="
				+ duration + "]";
	}

}