package test.harmonizer.rules;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.RuleIII;
import com.harmonizer.types.NoteType;

public class RuleIIITest {

	private static RuleIII rule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rule = new RuleIII();
	}
	@Test
	public void testValidate() {
		NoteSet ns;
		ns = new NoteSet(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.DO), new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)));
		assertTrue(rule.validate(ns));
		
		ns = new NoteSet(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.SOL), new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)));
		assertFalse(rule.validate(ns));
		
		ns = new NoteSet(new Note(NoteType.LA), new Note(NoteType.FA), new Note(NoteType.FA), new Note(NoteType.RE), new Chord(new Note(NoteType.RE), new Note(NoteType.FA), new Note(NoteType.LA)));
		assertFalse(rule.validate(ns));
		
		ns = new NoteSet(new Note(NoteType.LA), new Note(NoteType.SOL), new Note(NoteType.RE),new Note(NoteType.RE), new Chord(new Note(NoteType.RE), new Note(NoteType.FA), new Note(NoteType.LA)));
		assertFalse(rule.validate(ns));
	}

}
