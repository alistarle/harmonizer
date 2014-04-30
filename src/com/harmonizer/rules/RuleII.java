package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;

public class RuleII extends LocalRule {

	public boolean validate(NoteSet ns) {
		return (ns.getBasse().getCode() < ns.getTenor().getCode()
				&& ns.getTenor().getCode() < ns.getAlto().getCode() && ns
				.getAlto().getCode() < ns.getSoprano().getCode());
	}

}
