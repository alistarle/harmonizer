package com.harmonizer.core;

import java.util.ArrayList;

import com.harmonizer.exceptions.ParsingException;
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

	@Override
	public String toString() {
		return "Chord [tonic=" + tonic.getName() + ", third=" + third.getName() + ", fifth="
				+ fifth.getName() + "]";
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
