package com.harmonizer.core;

import java.util.ArrayList;
import java.util.Arrays;

import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.rules.LocalRule;
import com.harmonizer.rules.RuleII;
import com.harmonizer.types.ChordType;
import com.harmonizer.utils.ChordUtils;

public class Chord implements Cloneable {

	private Note tonic;
	private Note third;
	private Note fifth;

	public Chord(Note tonic, Note third, Note fifth) {
		this.tonic = tonic;
		this.third = third;
		this.fifth = fifth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fifth == null) ? 0 : fifth.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
		result = prime * result + ((tonic == null) ? 0 : tonic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chord other = (Chord) obj;
		if (fifth == null) {
			if (other.fifth != null)
				return false;
		} else if (!fifth.equals(other.fifth))
			return false;
		if (third == null) {
			if (other.third != null)
				return false;
		} else if (!third.equals(other.third))
			return false;
		if (tonic == null) {
			if (other.tonic != null)
				return false;
		} else if (!tonic.equals(other.tonic))
			return false;
		return true;
	}

	public boolean contains(Note note) {
		return note.getName().equals(this.tonic.getName())
				|| note.getName().equals(this.third.getName())
				|| note.getName().equals(this.fifth.getName());
	}

	@Override
	public String toString() {
		return "Chord [tonic=" + tonic.getName() + ", third=" + third.getName()
				+ ", fifth=" + fifth.getName() + "]";
	}
	
	public ChordType getNature(Note note) {
		if (note.getName().equals(this.tonic.getName()))
			return ChordType.TONIC;
		if (note.getName().equals(this.third.getName()))
			return ChordType.THIRD;
		if (note.getName().equals(this.fifth.getName()))
			return ChordType.FIFTH;
		return null;
	}

	public Note getTonic() {
		return tonic;
	}

	public Note getThird() {
		return third;
	}

	public Note getFifth() {
		return fifth;
	}
}
