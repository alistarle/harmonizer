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
import com.harmonizer.graph.Node;
import com.harmonizer.utils.NodeUtils;
import com.harmonizer.writer.LilyWriter;
import com.harmonizer.writer.MidiWriter;
/**
 * Class representative d'un chant dans le programme
 * @author alistarle
 *
 */
public class Song {

	/**
	 * Liste des note par voix
	 */
	private ArrayList<ArrayList<Note>> trackList = new ArrayList<ArrayList<Note>>();
	
	/**
	 * Liste associant a chaque temps une note de soprano a utiliser comme reference
	 */
	private ArrayList<Integer> timeline = new ArrayList<Integer>();
	
	/**
	 * Nom du chant a écrire dans les fichiers
	 */
	private String name;
	
	/**
	 * Dossier dans lequel ecrire les fichiers (utilisé uniquement lors de l'option -w)
	 */
	private String folder;
	
	/**
	 * Graphe relatif au chant
	 */
	private Graph graph;
	
	/**
	 * Fichier d'entrée dans lequel lire le chant
	 */
	private File input;
	
	/**
	 * Fichier de sortie dans lequel écrire le midi/lilypond
	 */
	private File output;
	
	/**
	 * Durée du chant, accessible partout dans le programme
	 */
	public static int duration;
	
	/**
	 * Nombres des harmonisation, accessible partout dans le programme
	 */
	public static int harmonisation;
	
	/**
	 * Crée un chant a partir d'un fichier d'entrée, utile lorsqu'il n'y a pas de sortie (option -nombre)
	 * @param input
	 */
	public Song(File input) {
		this(input, null);
	}
	
	/**
	 * Crée un chant a partir d'un fichier d'entrée et de sortie, utile lorsqu'il s'agit d'écrire une sortie midi/lilypond
	 * @param input
	 * @param output
	 */
	public Song(File input, File output) {
		this(input,output,"");
	}
	
	/**
	 * Crée un chant a partir d'un fichier d'entrée, de sortie, et d'un dossier dans lequel écrire les sortie, utile lors l'option -w,
	 * le chant est automatiquement lu a partir du fichier d'entrée, la timeline puis le graphe generé
	 * @param input
	 * @param output
	 * @param folder
	 */
	public Song(File input, File output, String folder) {
		this.duration = 0;
		this.input = input;
		this.output = output;
		this.folder = folder+File.separator;
		this.readFromFile();
		this.generateTimeline(0, 0, 0);
		NodeUtils.nodeList = new ArrayList<Node>();
		this.generateGraph();		
	}

	/**
	 * Ecris la liste des notes par voix dans un fichier .mid
	 * @see MidiWriter
	 */
	public void writeToMidi() {
		MidiWriter mw = new MidiWriter(name);
		mw.writeTrack(trackList);
		mw.close();
		mw.write(folder+output.getName().split("\\.")[0]);
	}

	/**
	 * Ecris la liste des notes par voix dans un fichier .ly
	 * @see LilyWriter
	 */
	public void writeToLily() {
		LilyWriter lw = new LilyWriter(folder+output.getName().split("\\.")[0], name, trackList);
		lw.generateLily();
		lw.writeLily();
	}

	/**
	 * Genère récursivement la timeline du chant
	 * @param i
	 * @param timeline
	 * @param timelineTemp
	 * @return
	 */
	public boolean generateTimeline(int i, int timeline, int timelineTemp) {
		if (timeline < this.duration) {
			this.timeline.add(i);
			if (trackList.get(0).get(i).getDuration() + timelineTemp - 1 == timeline && (i != trackList.get(0).size() - 1 && trackList.get(0).get(i + 1).getName() != "-")) {
				return generateTimeline(i + 1, timeline + 1, timeline + 1);
			} else if (i != trackList.get(0).size() - 1 && trackList.get(0).get(i).getDuration() + trackList.get(0).get(i + 1).getDuration() + timelineTemp - 1 == timeline) {
				return generateTimeline(i + 2, timeline + 1, timeline + 1);
			} else {
				return generateTimeline(i, timeline + 1, timelineTemp);
			}
		} else {
			return false;
		}
	}

	/**
	 * Genère le graphe relatif au champ en question
	 */
	public void generateGraph() {
		this.graph = new Graph(trackList, timeline);
	}
	
	public int getHarmonisation() {
		return this.harmonisation;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOutput() {
		return output.getName().split("\\.")[0];
	}

	/**
	 * Joue le fichier midi generé
	 * @throws MidiUnavailableException
	 * @throws InvalidMidiDataException
	 * @throws IOException
	 */
	public void playSong() throws MidiUnavailableException,
			InvalidMidiDataException, IOException {
		Sequence sequence = MidiSystem.getSequence(new File(folder+output.getName().split("\\.")[0]+".mid"));
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(sequence);
		sequencer.start();
	}

	/**
	 * Lit le fichier .chant, qui contient donc le nom du chant et la ligne de soprano
	 */
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
			trackList.add(noteList);
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
			trackList.add(new ArrayList<Note>());
		}
	}
}
