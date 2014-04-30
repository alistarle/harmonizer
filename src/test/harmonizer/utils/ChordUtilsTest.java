package test.harmonizer.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.types.NoteType;
import com.harmonizer.utils.ChordUtils;

public class ChordUtilsTest {

	@Test
	public void testGetChords() {
		Chord chord;
		assertTrue("Nombre incorrect d'accord", ChordUtils.getChords().size() == 8);
		
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
		assertTrue("Accord IVb", ChordUtils.getChords().contains(chord));
		
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.SI), new Note(NoteType.DO));
		assertFalse("Accord fake", ChordUtils.getChords().contains(chord));
	}

	@Test
	public void testGetEdges() {
		Chord chord;
		assertTrue("Nombre incorrect d'accord", ChordUtils.getEdges().size() == 7);
		
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.SI), new Note(NoteType.DO));
		assertFalse("Accord fake", ChordUtils.getChords().contains(chord));
	}

	@Test
	public void testIsEdge() {
		Chord chord;
		chord = new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL));
		assertFalse("accord I", ChordUtils.isEdge(chord));
		
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
		assertTrue("accord IVb", ChordUtils.isEdge(chord));
		
		chord = new Chord(new Note(NoteType.DO), new Note(NoteType.FA), new Note(NoteType.SI));
		assertFalse("accord fake", ChordUtils.isEdge(chord));
	}

	@Test
	public void testGetNextChord() {
		Chord chord; ArrayList<Chord> next;
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
		next = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL))));
		assertTrue("accord IVb", ChordUtils.getNext(chord).equals(next));
		
		next = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.RE), new Note(NoteType.MI), new Note(NoteType.SOL))));
		assertFalse("next fake", ChordUtils.getNext(chord).equals(next));
		
		chord = new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL));
		assertFalse("accord I", ChordUtils.getNext(chord).equals(next));
	}
	
	@Test (expected = NullPointerException.class)
	public final void testGetNextChordNull() {
		Chord chord; ArrayList<Chord> next;
		chord = new Chord(new Note(NoteType.RE), new Note(NoteType.MI), new Note(NoteType.SOL));
		next = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.RE), new Note(NoteType.MI), new Note(NoteType.SOL))));
		assertFalse("accord I", ChordUtils.getNext(chord).equals(next));	
	}

	@Test
	public void testGetChordsNote() {
		Note note; ArrayList<Chord> chordList;
		note = new Note(NoteType.DO);
		chordList = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)),new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)),
				new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)),new Chord(new Note(NoteType.LA), new Note(NoteType.DO), new Note(NoteType.MI))));
		assertTrue("note DO", ChordUtils.getChords(note).equals(chordList));
		
		chordList = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)),new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)),
				new Chord(new Note(NoteType.LA), new Note(NoteType.DO), new Note(NoteType.MI))));
		assertFalse("note DO", ChordUtils.getChords(note).equals(chordList));
		
		note = new Note(NoteType.RE);
		chordList = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)),new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)),
				new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)),new Chord(new Note(NoteType.LA), new Note(NoteType.DO), new Note(NoteType.MI))));
		assertFalse("note RE", ChordUtils.getChords(note).equals(chordList));
	}

	@Test
	public void testGetChordsChordNote() {
		Note note; ArrayList<Chord> chordList; Chord chord;
		note = new Note(NoteType.DO);
		chord = new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO));
		chordList = new ArrayList<Chord>(Arrays.asList(new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL))));
		assertTrue("note DO accord IVb", ChordUtils.getChords(chord, note).equals(chordList));
		
		chordList = new ArrayList<Chord>();
		assertFalse("note DO accord IVb", ChordUtils.getChords(chord, note).equals(chordList));
		
		note = new Note(NoteType.FA);
		assertTrue("note FA accord I", ChordUtils.getChords(chord, note).equals(chordList));
	}
}
