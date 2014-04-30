package test.harmonizer.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.types.NoteType;
import com.harmonizer.types.TrackType;
import com.harmonizer.utils.NoteUtils;

public class NoteUtilsTest {

	@Test
	public void testGetCode() {
		String name; int octave;
		name = "do"; octave = 4;
		assertTrue(NoteUtils.getCode(name, octave) == 21);
		
		name = "bla";
		assertTrue(NoteUtils.getCode(name, octave) == 20);
		
		name = "re"; octave = -2;
		assertTrue(NoteUtils.getCode(name, octave) == -20);
	}

	@Test
	public void testGetMidi() {
		String name; int octave;
		name = "do"; octave = 4;
		assertTrue(NoteUtils.getMidi(name, octave) == 60);
	
		octave = 1;
		assertTrue(NoteUtils.getMidi(name, octave) == 24);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetMidiNull() {
		String name; int octave;
		name = "bla"; octave = -2;
		assertNull(NoteUtils.getMidi(name, octave));
	}

	@Test
	public void testGetLilypond() {
		String name; int octave; int duration;
		name = "re"; octave = 2; duration = 3;
		assertTrue(NoteUtils.getLilypond(name, octave, duration).equals("re2."));
		
		name = "si"; octave = 1;
		assertTrue(NoteUtils.getLilypond(name, octave, duration).equals("si,2."));
	}

	@Test
	public void testGetNote() {
		Chord chord; TrackType type; Note soprano;ArrayList<Note> noteList;
		type = TrackType.ALTO;
		soprano = new Note("do",4);
		chord = new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL));
		noteList = new ArrayList<Note>(Arrays.asList(new Note("mi",3),new Note("sol",2),new Note("sol",3)));
		assertTrue(NoteUtils.getNote(chord, type, soprano).equals(noteList));
		
		type = TrackType.TENOR;
		soprano = new Note("si",3);
		chord = new Chord(new Note(NoteType.SOL), new Note(NoteType.SI), new Note(NoteType.RE));
		noteList = new ArrayList<Note>(Arrays.asList(new Note("sol",2),new Note("sol",3),new Note("re",2), new Note("re",3)));
		assertTrue(NoteUtils.getNote(chord, type, soprano).equals(noteList));
		
		type = TrackType.BASSE;
		soprano = new Note("fa",4);
		chord = new Chord(new Note(NoteType.SI), new Note(NoteType.RE), new Note(NoteType.FA));
		noteList = new ArrayList<Note>(Arrays.asList(new Note("si",1),new Note("si",2)));
		assertTrue(NoteUtils.getNote(chord, type, soprano).equals(noteList));
	}

}
