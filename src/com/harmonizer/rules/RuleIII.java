package com.harmonizer.rules;

import java.util.HashSet;

import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.types.ChordType;

public class RuleIII extends LocalRule {

	@Override
	public boolean validate(NoteSet ns) {
		if(!ns.getBasse().getName().equals(ns.getChord().getTonic().getName())) return false;
		HashSet<ChordType> type = new HashSet<ChordType>();
		for(Note note : ns.getChordNote()) {
			type.add(ns.getChord().getNature(note));
		}
		type.remove(null);
		if(type.size() != 3) return false;
		return true;
	}

}
