package com.harmonizer.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import com.harmonizer.core.Note;
/**
 * Class generant un fichier midi representant une harmonisation d'un chant
 * @author alistarle
 *
 */
public class MidiWriter {

	private Sequence sec;
	
	/**
	 * Liste des notes pour chaque voix
	 */
	private ArrayList<Track> trackList;
	
	/**
	 * Nom de la piste
	 */
	private String name;

	/**
	 * Permet de se reperer dans l'avancement du temps lors de l'écriture
	 */
	private long timeline = 0;

	private MidiEvent me;
	private MetaMessage mt;
	private ShortMessage mm;

	/**
	 * Initialise les differentes track afin d'écrire les notes, une track represente une voix
	 * @param name
	 */
	public MidiWriter(String name) {
		this.trackList = new ArrayList<Track>();
		this.name = name;
		try {
			sec = new Sequence(javax.sound.midi.Sequence.PPQ, 1);
			trackList.add(sec.createTrack());
			trackList.add(sec.createTrack());
			trackList.add(sec.createTrack());
			trackList.add(sec.createTrack());

			mt = new MetaMessage();
			String TrackName = new String(name);
			mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt, timeline);
			CreateEventForAll(me);

			mm = new ShortMessage();
			mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 1, 0);
			me = new MidiEvent(mm, timeline);
			CreateEventForAll(me);
		} catch (InvalidMidiDataException e) {
		}

	}

	/**
	 * Ecris les notes sur les differentes track
	 * @param noteList Liste des notes par voix
	 */
	public void writeTrack(ArrayList<ArrayList<Note>> noteList) {
		for(int i = 0; i < noteList.size(); i++) {
			timeline = 0;
			for (Note note : noteList.get(i)) {
				if (!note.getName().equals("-"))
					this.CreateOnEvent(note.getMidi(),trackList.get(i));
				timeline += note.getDuration();
				if (!note.getName().equals("-"))
					this.CreateOffEvent(note.getMidi(),trackList.get(i));
			}
		}
	}

	/**
	 * Ferme les differentes track
	 */
	public void close() {
		try {
			mt = new MetaMessage();
			byte[] bet = {}; // empty array
			mt.setMessage(0x2F, bet, 0);
			me = new MidiEvent(mt, timeline);
			CreateEventForAll(me);
		} catch (InvalidMidiDataException e) {
		}
	}

	/**
	 * Ecris la sequence ainsi crée dans un fichier midi
	 * @param fileName Nom du fichier midi a creer
	 */
	public void write(String fileName) {
		try {
			File f = new File(fileName + ".mid");
			MidiSystem.write(sec, 1, f);
		} catch (IOException e) {
		}
	}

	/**
	 * Ajoute l'appuie sur une note dans la track precisée
	 * @param note Entier representant la note à appuyer
	 * @param track Track sur laquelle ajouter la note
	 */
	public void CreateOnEvent(int note,Track track) {
		ShortMessage myMsg = new ShortMessage();
		try {
			myMsg.setMessage(ShortMessage.NOTE_ON, 0, note, 127);
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MidiEvent me = new MidiEvent(myMsg, timeline);
		track.add(me);
	}
	
	/**
	 * Ajoute le relachement d'une note dans la track precisée
	 * @param note Entier representant la note à relacher
	 * @param track Track sur laquelle retirer la note
	 */
	public void CreateOffEvent(int note,Track track) {
		ShortMessage myMsg = new ShortMessage();
		try {
			myMsg.setMessage(ShortMessage.NOTE_OFF, 0, note, 127);
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MidiEvent me = new MidiEvent(myMsg, timeline);
		track.add(me);
	}
	
	/**
	 * Ajoute un event standard donnée en paramètres sur toutes les Tracks
	 * @param me
	 */
	public void CreateEventForAll(MidiEvent me) {
		for(Track track : trackList) {
			track.add(me);
		}
	}

	/**
	 * Change l'instrument utilisé par toutes les track
	 * @param instr Un entier representant l'instrument voulu
	 * @throws InvalidMidiDataException
	 */
	public void changeProgram(int instr) throws InvalidMidiDataException {
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instr, 0);
		MidiEvent me = new MidiEvent(mm, timeline);
		CreateEventForAll(me);
	}

}
