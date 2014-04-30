package test.harmonizer.utils;
import static org.junit.Assert.*;
import org.junit.Test;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.graph.Node;
import com.harmonizer.types.NoteType;
import com.harmonizer.utils.NodeUtils;

public class NodeUtilsTest {

	@Test
	public void testGetNode() {
		Node node; Node node2; NoteSet noteset; int time;
		time = 1;
		noteset = new NoteSet(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL), new Note(NoteType.DO), new Chord(new Note(NoteType.DO), new Note(NoteType.MI), new Note(NoteType.SOL)));
		node = new Node(noteset, time);
		NodeUtils.getNode(node);
		assertTrue("Taille 1",NodeUtils.nodeList.size() == 1);
		
		NodeUtils.getNode(node);
		assertTrue("Taille 1",NodeUtils.nodeList.size() == 1);
		
		node2 = NodeUtils.getNode(node);
		assertTrue("Noeuds egaux",node == node2);
		
		time = 2;
		node = new Node(noteset, time);
		NodeUtils.getNode(node);
		assertTrue("Taille 2",NodeUtils.nodeList.size() == 2);
	}

}
