package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;
import com.harmonizer.utils.ChordUtils;
/**
 * Implementation de la IVème règle locale
 * @author alistarle
 *
 */
public class RuleIV extends LocalRule {

	@Override
	public boolean validate(NoteSet ns) {
		return ChordUtils.isEdge(ns.getChord());
	}

}
