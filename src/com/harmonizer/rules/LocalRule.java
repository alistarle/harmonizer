package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;

public abstract class LocalRule {
	public abstract boolean validate(NoteSet ns);

	public void simplify(ArrayList<NoteSet> nsl) {
		for (int i = 0; i < nsl.size(); i++) {
			if (!validate(nsl.get(i))) {
				nsl.remove(i);
			}
		}
	}
}
