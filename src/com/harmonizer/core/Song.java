package com.harmonizer.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	public static ArrayList<ArrayList<Note>> trackList = new ArrayList<ArrayList<Note>>();
	public static ArrayList<Integer> timeline = new ArrayList<Integer>();
	private String name;
	private Graph graph;
	private File input;
	private File output;
	public static int duration;
	public static int node;
	public static int harmonisation;
	
	public Song(File input) {
		this(input, null);
	}
	
	public Song(File input, File output) {
		this.input = input;
		this.output = output;
		this.readFromFile();
		System.out.print("Timeline : ");
		this.generateTimeline(0, 0, 0);
		System.out.println();
		this.generateGraph();
	}

	public void writeToMidi() {
		MidiWriter mw = new MidiWriter(name);
		mw.writeTrack(trackList);
		mw.close();
		mw.write(output.getName().split("\\.")[0]);
	}

	public void writeToLily() {
		LilyWriter lw = new LilyWriter(output.getName().split("\\.")[0], name, trackList);
		lw.generateLily();
		lw.writeLily();
	}

	public boolean generateTimeline(int i, int timeline, int timelineTemp) {
		if (timeline < this.duration) {
			this.timeline.add(i);
			System.out.print(i + " ");
			if (trackList.get(0).get(i).getDuration() + timelineTemp - 1 == timeline
					&& (i != trackList.get(0).size() - 1 && trackList.get(0)
							.get(i + 1).getName() != "-")) {
				return generateTimeline(i + 1, timeline + 1, timeline + 1);
			} else if (i != trackList.get(0).size() - 1
					&& trackList.get(0).get(i).getDuration()
							+ trackList.get(0).get(i + 1).getDuration()
							+ timelineTemp - 1 == timeline) {
				return generateTimeline(i + 2, timeline + 1, timeline + 1);
			} else {
				return generateTimeline(i, timeline + 1, timelineTemp);
			}
		} else {
			return false;
		}
	}

	public void generateGraph() {
		this.graph = new Graph(trackList, timeline);
	}
	
	public int getHarmonisation() {
		return this.harmonisation;
	}
	
	public String getName() {
		return this.name;
	}

	public void playSong() throws MidiUnavailableException,
			InvalidMidiDataException, IOException {
		Sequence sequence = MidiSystem.getSequence(new File(output.getName().split("\\.")[0]+".mid"));
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(sequence);
		sequencer.start();
	}

	private void readFromFile() {
		String content = "";
		
		try{
			InputStream ips=new FileInputStream(input); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				content+=ligne+"\n";
			}
			br.close(); 
			this.name = content.split("\n")[0];
			System.out.println(name+" : " + content.split("\n")[1]);
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}

		if (content != null) {
			String[] noteArray = content.split("\n")[1].split(" ");
			ArrayList<Note> noteList = new ArrayList<Note>();
			for (String stringNote : noteArray) {
				try {
					Note note = new Note(stringNote);
					noteList.add(note);
					this.duration += note.getDuration();
				} catch (ParsingException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Durée du chant : " + duration);
			trackList.add(noteList);
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
		}
	}
}
