package test.harmonizer.rules;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import test.harmonizer.rules.mock.LocalMock;

import com.harmonizer.core.NoteSet;

public class LocalTest {
	private static LocalMock rule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rule = new LocalMock();
	}
	@Test
	public void testSimplify() {
		ArrayList<NoteSet> nsl;
		nsl = new ArrayList<NoteSet>(Arrays.asList(new NoteSet(null,null,null,null,null),new NoteSet(null,null,null,null,null),new NoteSet(null,null,null,null,null)));
		rule.simplify(nsl);
		assertTrue(nsl.size() == 3);
		
		nsl = new ArrayList<NoteSet>();
		rule.simplify(nsl);
		assertTrue(nsl.size() == 0);
	}

}
