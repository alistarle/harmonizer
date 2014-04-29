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

public class MidiWriter {

	private Sequence sec;
	private Track track;
	private String name;
	private int tick;

	private long timeline = 0;

	private MidiEvent me;
	private MetaMessage mt;
	private SysexMessage sm;
	private ShortMessage mm;

	public MidiWriter(String name, int tick) {
		this.name = name;
		this.tick = tick;
		try {
			sec = new Sequence(javax.sound.midi.Sequence.PPQ, 50);
			track = sec.createTrack();

			mt = new MetaMessage();
			String TrackName = new String("Piste de test");
			mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt, timeline);
			track.add(me);

			mm = new ShortMessage();
			mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 15, 0);
			me = new MidiEvent(mm, timeline);
			track.add(me);
			timeline += 100;
		} catch (InvalidMidiDataException e) {
		}

	}

	public void writeTrack(ArrayList<Note> noteList) {
		for (Note note : noteList) {
			System.out.println(note.toString());
			if (!note.getName().equals("-"))
				this.CreateOnEvent(note.getMidi());
			timeline += tick * note.getDuration();
			if (!note.getName().equals("-"))
				this.CreateOffEvent(note.getMidi());
			timeline += tick;
		}
	}

	public void close() {
		try {
			timeline += 20;
			mt = new MetaMessage();
			byte[] bet = {}; // empty array
			mt.setMessage(0x2F, bet, 0);
			me = new MidiEvent(mt, timeline);
			track.add(me);
		} catch (InvalidMidiDataException e) {
		}
	}

	public void write() {
		try {
			File f = new File(name + ".mid");
			MidiSystem.write(sec, 1, f);
		} catch (IOException e) {
		}
	}

	public void CreateOnEvent(int note) {
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

	public void CreateOffEvent(int note) {
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

	public void changeProgram(int instr) throws InvalidMidiDataException {
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instr, 0);
		MidiEvent me = new MidiEvent(mm, timeline);
		track.add(me);
	}

}
