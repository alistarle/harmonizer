package com.harmonizer.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.graph.Graph;
import com.harmonizer.writer.LilyWriter;
import com.harmonizer.writer.MidiWriter;


public class Song {

	private ArrayList<ArrayList<Note>> trackList = new ArrayList<ArrayList<Note>>();
	private ArrayList<Integer> timeline = new ArrayList<Integer>();
	private String name;
	private Graph graph;
	private File input;
	private int tick;
	private int duration;
	
	public Song(File input, int tick) {
		this.input = input;
		this.tick = tick;
		this.readFromFile();
	}
	
	public void writeToMidi() {
		MidiWriter mw = new MidiWriter("song",tick);
		mw.writeTrack(trackList.get(0));
		mw.close();
		mw.write();
	}
	
	public void writeToLily() {
		LilyWriter lw = new LilyWriter("song",trackList);
		lw.generateLily();
		lw.writeLily();
	}
	
	public String harmonize(int i, int timeline, int timelineTemp) {
		if(timeline < this.duration) {
			this.timeline.add(i);
			if(trackList.get(0).get(i).getDuration()+timelineTemp-1 == timeline) {
				return i+" "+harmonize(i+1, timeline+1, timeline+1);
			} else {
				return i+" "+harmonize(i, timeline+1, timelineTemp);
			}
		} else {
			return "";
		}
	}
	
	public void generateGraph() {
		this.graph = new Graph(trackList,timeline);
	}
	
	public boolean writeTrack(Chord chord, int timeline) {
		trackList.get(1).add(chord.getThird());
		trackList.get(2).add(chord.getFifth());
		trackList.get(3).add(chord.getTonic());
		if(timeline+1 != duration) {
			return writeTrack(new Chord(chord, trackList.get(0).get(this.timeline.get(timeline))), timeline+1);
		} else {
			return true;
		}
	}
	
	public void playSong() throws MidiUnavailableException, InvalidMidiDataException, IOException {
		Sequence sequence = MidiSystem.getSequence(new File("song.mid"));
		Sequencer sequencer = MidiSystem.getSequencer(); 
		sequencer.open(); 
		sequencer.setSequence(sequence); 
		sequencer.start(); 
	}
	
	private void readFromFile() {
		String content = "";
		try(FileInputStream fis = new FileInputStream(input)) {
			byte[] buf = new byte[1];
			int n = 0;
			while((n = fis.read(buf)) >= 0){    
				content+=((char)buf[0]);         
			}
			System.out.println("Lecture terminée : "+content);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(content != null) {
			String[] noteArray = content.split(" ");
			ArrayList<Note> noteList = new ArrayList<Note>();
			for(String stringNote : noteArray) {
				try {
					Note note = new Note(stringNote);
					noteList.add(note);
					this.duration+=note.getDuration();
				} catch (ParsingException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Durée du chant : "+duration);
			trackList.add(noteList);
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
		}
	}

}
