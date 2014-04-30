package test.harmonizer.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.RuleII;
import com.harmonizer.types.NoteType;

public class RuleIITest {

	private static RuleII rule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rule = new RuleII();
	}
	@Test
	public void testValidate() {
		NoteSet ns;
		ns = new NoteSet(new Note("sol",4), new Note("si",3), new Note("fa",2), new Note("do",2), null);
		assertTrue(rule.validate(ns));
		
		ns = new NoteSet(new Note("sol",4), new Note("fa",2), new Note("si",3), new Note("fa",2), null);
		assertFalse(rule.validate(ns));
		
		ns = new NoteSet(new Note("sol",4), new Note("si",3), new Note("do",2), new Note("do",2), null);
		assertFalse(rule.validate(ns));
		
		ns = new NoteSet(new Note("sol",4), new Note("sol",4), new Note("sol",4), new Note("sol",4), null);
		assertFalse(rule.validate(ns));
	}

}
