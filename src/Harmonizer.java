import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.Song;
import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.NoteType;


public class Harmonizer {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidMidiDataException 
	 * @throws MidiUnavailableException 
	 */
	public static boolean launch = false;
	
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
		File song = new File("dummy.txt");
		Song test = new Song(song,9);
		if(launch) {
			test.writeToMidi();
			test.writeToLily();
			test.playSong();
		}
	}

}
