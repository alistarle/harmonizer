package com.harmonizer.core;

import java.util.ArrayList;

import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.utils.ChordUtils;


public class Chord implements Cloneable {

	private Note tonic;
	private Note third;
	private Note fifth;
	
	private String name;
	
	private ArrayList<Chord> next;
	
	public Chord(Note note) {
		this.tonic = note;
		try {
			this.third = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.fifth= (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.next = ChordUtils.getNext(this);
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chord(Chord chord, Note note) {
		this.tonic = note;
		try {
			ArrayList<Chord> nextChord = chord.getNext();
			int random = (int) (Math.random() * (nextChord.size() + 1));
			this.third= (note.getName() != "-") ? new Note(nextChord.get(random).getThird().getName()+nextChord.get(random).getThird().getOctave()+":1") : new Note("do4:1");
			this.fifth = (note.getName() != "-") ? new Note(nextChord.get(random).getFifth().getName()+nextChord.get(random).getFifth().getOctave()+":1") : new Note("do4:1");
			this.next = ChordUtils.getNext(this);
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chord(Note tonic, Note third, Note fifth) {
		this.tonic = tonic;
		this.third = third;
		this.fifth = fifth;
		this.next = ChordUtils.getNext(this);
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

	public Note getTonic() {
		return tonic;
	}

	public Note getThird() {
		return third;
	}

	public Note getFifth() {
		return fifth;
	}
	
	public ArrayList<Chord> getNext() {
		return next;
	}
}
