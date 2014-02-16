import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;


public class Song {

	private ArrayList<ArrayList<Note>> trackList = new ArrayList<ArrayList<Note>>();
	private String name;
	private File input;
	private int tick;
	
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
	
	public void harmonize() {
		
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
			for(String note : noteArray) {
				try {
					noteList.add(new Note(note));
				} catch (ParsingException e) {
					e.printStackTrace();
				}
			}
			trackList.add(noteList);
			trackList.add(noteList);
			trackList.add(noteList);
		}
	}

}
