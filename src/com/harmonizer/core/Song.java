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
import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.TrackType;
import com.harmonizer.writer.LilyWriter;
import com.harmonizer.writer.MidiWriter;
import com.harmonizer.utils.*;

public class Song {

	public static ArrayList<ArrayList<Note>> trackList = new ArrayList<ArrayList<Note>>();
	public static ArrayList<Integer> timeline = new ArrayList<Integer>();
	private String name;
	private Graph graph;
	private File input;
	private int tick;
	public static int duration;
	public static int harmonisation;
	
	public Song(File input, int tick) {
		this.input = input;
		this.tick = tick;
		this.readFromFile();
		System.out.print("Timeline : ");
		this.generateTimeline(0, 0, 0);
		System.out.println();
		this.generateGraph();
		System.out.println("Il y a "+harmonisation+" harmonisations possibles");
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
	
	public boolean generateTimeline(int i, int timeline, int timelineTemp) {
		if(timeline < this.duration) {
			this.timeline.add(i);
			System.out.print(i+" ");
			if(trackList.get(0).get(i).getDuration()+timelineTemp-1 == timeline && (i != trackList.get(0).size()-1 && trackList.get(0).get(i+1).getName() != "-")) {
				return generateTimeline(i+1, timeline+1, timeline+1);
			} else if(i != trackList.get(0).size()-1 && trackList.get(0).get(i).getDuration()+trackList.get(0).get(i+1).getDuration()+timelineTemp-1 == timeline) {
				return generateTimeline(i+2, timeline+1, timeline+1);
			} else {
				return generateTimeline(i, timeline+1, timelineTemp);
			}
		} else {
			return false;
		}
	}
	
	public void generateGraph() {
		this.graph = new Graph(trackList,timeline);
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
		try {
			FileInputStream fis = new FileInputStream(input);
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
			Song.duration = duration;
			trackList.add(noteList);
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
		}
	}
	
	//fonction demandé: //pseudo-language ArrayList<Note> getNote (accord, piste, note soprano){
	//si la piste est alto
	//pour toute les note n de 11 a 22 faire:
		//creer une note(n) notepossible
		//si notepossible appartient a l'accord faire:
			//si notepossible est differente de la note tonic correspondant a l'accord et de la note soprano faire:
				//ajouter la note a la liste des possiblilité
			//finsi
		//finsi
	//finpour
//si la piste est tenor ...
//sinon (piste=basse) notepossiblie est le note tonic de l'accord
	
	public static ArrayList<Note> getNote (Chord chord,TrackType track, Note soprano) throws ParsingException{
	
		
	//je cree un tableau contenant les notes triées en fonction de leur tonalité {1 a 4}
		/*[do1|ré1|mi1|fa1|sol1|la1|si1]
		 *[do2|ré2|mi2|fa2|sol2|la2|si2]
		 *[do3|ré3|mi3|fa3|sol3|la3|si3]
		 *[do4|ré4|mi4|fa4|sol4|la4|si4]
	pour accéder a une case n on doit decaler le numerotation en -1 ex |fa3| a pour coordonnée TableNote[3][2]
		 */
		String[] noteSerie = {"do","ré","mi","fa","sol","la","si"};//cette table me permet d'insérer directement la note
		Note[][] TableNote = new Note[7][4];
		//
		for(int i=0; i<=4; i++){
			for(int j=0; j<=6; j++){
				//il manque peut-etre un try
				TableNote[i][j]=new Note(noteSerie[j]);
				TableNote[i][j].setOctave(i+1);
			}
		}
		int i,j;
		ArrayList<Note> NotePossible = new ArrayList<Note>();
		if(track==TrackType.ALTO){
			for(i=1; i<=3; i++){//on parcourt le tableau en largeur
				if(i==1){
					for(j=4; j<=6; j++){//on parcourt le tableau en longueur
						Note notePossible;
						notePossible = TableNote[i][j];
						if(chord.contains(notePossible)){//si la note est dans l'acccord 
							NotePossible.add(notePossible);//alors elle est ajouté a la liste de notepossible
						}
					}
				}
				if(i==2){
					for(j=0; j<=6; j++){
						Note notePossible;
						notePossible = TableNote[i][j];
						if(chord.contains(notePossible)){
							NotePossible.add(notePossible);
						}
					}
				}
				else{
					for(j=0; j<=1; j++){
						Note notePossible;
						notePossible = TableNote[i][j];
						if(chord.contains(notePossible)){
							NotePossible.add(notePossible);
						}
					}
				}
			}
		}
		
		if(track==TrackType.TENOR){
			for(i=1; i<=2; i++){
				if(i==1){//les notes sont 
					for(j=0; j<=6; j++){
						Note notePossible;
						notePossible = TableNote[i][j];
						if(chord.contains(notePossible)){
							NotePossible.add(notePossible);
						}
					}
				}
				else{
					for(j=0; j<=5; j++){
						Note notePossible;
						notePossible = TableNote[i][j];
						if(chord.contains(notePossible)){
							NotePossible.add(notePossible);
						}
					}
				}
			}
			
		}
		if(track==TrackType.BASSE){
			Note notePossible = chord.getTonic();;
			NotePossible.add(notePossible);
		}
		
		
		return NotePossible;
	}
}
