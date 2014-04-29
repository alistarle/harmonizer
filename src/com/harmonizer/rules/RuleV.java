package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;
import com.harmonizer.utils.ChordUtils;

public class RuleV extends LinkRule {

	public boolean validate(NoteSet ns, NoteSet ns2) {
		return ChordUtils.getNext(ns.getChord()).contains(ns2.getChord());
	}

}
