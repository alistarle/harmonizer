package com.harmonizer.rules;

import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;

public class RuleVI extends LinkRule {

	private NoteSet ns, ns2;

	public boolean validate(NoteSet ns, NoteSet ns2) {
		this.ns = ns;
		this.ns2 = ns2;
		return assert1() && assert3();
	}

	public boolean assert1() {
		for (int i = 0; i < ns.getNote().size(); i++) {
			if (Math.abs(ns.getNote().get(i).getCode()
					- ns2.getNote().get(i).getCode()) > 6)
				return false;
		}
		return true;
	}

	public boolean assert2() {
		for (Note note : ns.getNote()) {
			if (ns2.getChord().contains(note))
				if (!ns2.contains(note))
					return false;
		}
		return true;
	}

	public boolean assert3() {
		for (int i = 0; i < ns.getNote().size(); i++) {
			if (Math.abs(ns.getNote().get(i).getCode()
					- ns2.getNote().get(i).getCode()) > 2)
				if (ns.getChord().getNature(ns.getNote().get(i)) != ns2
						.getChord().getNature(ns2.getNote().get(i)))
					return false;
		}
		return true;
	}

}
