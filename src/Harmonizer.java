import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteType;
import com.harmonizer.core.Song;
import com.harmonizer.exceptions.ParsingException;


public class Harmonizer {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		File song = new File("future.txt");
		Song test = new Song(song,9);
		System.out.println(test.harmonize(0,0,0));
		//test.writeToMidi();
		test.writeTrack(new Chord(new Note(NoteType.DO),new Note(NoteType.MI),new Note(NoteType.SOL)), 0);
		//test.writeToLily();
		try {
			test.playSong();
		} catch (MidiUnavailableException | InvalidMidiDataException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
