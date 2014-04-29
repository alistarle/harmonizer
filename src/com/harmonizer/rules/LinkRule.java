package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;

public abstract class LinkRule {
	public abstract boolean validate(NoteSet ns, NoteSet ns2);
	
	public ArrayList<Integer> simplify(NoteSet noteSet, ArrayList<NoteSet> nsl2) {
		ArrayList<Integer> noteSetList = new ArrayList<Integer>();
		for(int i = 0; i < nsl2.size(); i++) {
			if(validate(noteSet, nsl2.get(i))) {
				noteSetList.add(i);
			}
		}
		return noteSetList;
	}
}
