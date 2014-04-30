package test.harmonizer.rules.mock;

import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.LinkRule;

public class LinkMock extends LinkRule {

	@Override
	public boolean validate(NoteSet ns, NoteSet ns2) {
		return true;
	}

}
