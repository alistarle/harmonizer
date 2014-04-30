package com.harmonizer.writer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.harmonizer.core.Note;

public class LilyWriter {

	private File file;
	private FileWriter fw;
	private String lilypond = "";
	private String name;
	private ArrayList<ArrayList<Note>> trackList;

	public LilyWriter(String fileName, String name, ArrayList<ArrayList<Note>> trackList) {
		this.file = new File(fileName + ".ly");
		this.name = name;
		this.trackList = trackList;
	}

	public void generateLily() {
		lilypond += "\\header {\n title =\""+name+"\" \n}\n\\include \"italiano.ly\" \n" + "\\version \"2.18.0\" \n"
				+ "{ \n" + " << \n";
		for (ArrayList<Note> noteList : trackList) {
			lilypond += "{ ";
			for (Note note : noteList) {
				lilypond += note.getLilypond() + " ";
			}
			lilypond += "} \n";
		}
		lilypond += " >> \n" + "}";
	}

	public void writeLily() {
		try {
			System.out.println(lilypond);
			fw = new FileWriter(file);
			fw.write(lilypond);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
