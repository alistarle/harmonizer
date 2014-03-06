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
		Song test = new Song(song,75);
		System.out.println(test.harmonize(0,0,0));
		test.writeToMidi();
		try {
			test.writeTrack(new Chord(new Note("do4:1")), 1);
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
