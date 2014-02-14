import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;


public class Harmonizer {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		File song = new File("song.txt");
		Song test = new Song(song,25);
		test.writeToMidi();

		Sequence sequence;
		try {
			sequence = MidiSystem.getSequence(new File("song.mid" ));
			Sequencer sequencer = MidiSystem.getSequencer(); 
			sequencer.open(); 
			sequencer.setSequence(sequence); 
			sequencer.start(); 
		} catch (InvalidMidiDataException | IOException e) {
		} catch (MidiUnavailableException e) { } 
	}

}
