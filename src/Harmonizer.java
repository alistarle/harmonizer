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
		File song = new File("dummy.txt");
		Song test = new Song(song,100);
		//test.harmonize();
		test.writeToMidi();
		test.writeToLily();
		try {
			test.playSong();
		} catch (MidiUnavailableException | InvalidMidiDataException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
