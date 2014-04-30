package test.harmonizer.rules;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.RuleV;
import com.harmonizer.types.NoteType;

public class RuleVTest {

	private static RuleV rule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rule = new RuleV();
	}
	
	@Test
	public void testValidate() {
		NoteSet ns; NoteSet ns2;
		ns = new NoteSet(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.DO), new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)));
		ns2 = new NoteSet(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.DO), new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)));
		assertTrue("I => I",rule.validate(ns, ns2));
		
		ns = new NoteSet(new Note(NoteType.RE), new Note(NoteType.LA), new Note(NoteType.FA), new Note(NoteType.RE), new Chord(new Note(NoteType.RE), new Note(NoteType.FA), new Note(NoteType.LA)));
		ns2 = new NoteSet(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO), new Note(NoteType.FA), new Chord(new Note(NoteType.FA), new Note(NoteType.LA), new Note(NoteType.DO)));
		assertFalse("II => IV",rule.validate(ns, ns2));
	}

}
