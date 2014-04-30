package test.harmonizer.rules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import test.harmonizer.rules.mock.LinkMock;

import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.RuleV;

public class LinkTest {
	private static LinkMock rule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rule = new LinkMock();
	}
	@Test
	public void testSimplify() {
		ArrayList<NoteSet> nsl; ArrayList<Integer> intList;
		nsl = new ArrayList<NoteSet>(Arrays.asList(new NoteSet(null,null,null,null,null),new NoteSet(null,null,null,null,null),new NoteSet(null,null,null,null,null),new NoteSet(null,null,null,null,null)));
		intList = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
		assertTrue(rule.simplify(null, nsl).equals(intList));
		
		nsl = new ArrayList<NoteSet>();
		assertFalse(rule.simplify(null, nsl).equals(intList));
		
		intList = new ArrayList<Integer>();
		assertTrue(rule.simplify(null, nsl).equals(intList));
	}

}
