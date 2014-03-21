package com.harmonizer.core;

import java.util.ArrayList;

import com.harmonizer.exceptions.ParsingException;
import com.harmonizer.utils.ChordUtils;


public class Chord {

	private Note tonic;
	private Note third;
	private Note fifth;
	
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
			//int random = Math.random()*
			this.third= (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.fifth = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
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
