package test.harmonizer.rules.mock;

import com.harmonizer.core.NoteSet;
import com.harmonizer.rules.LocalRule;

public class LocalMock extends LocalRule {

	@Override
	public boolean validate(NoteSet ns) {
		return true;
	}

}
