package com.harmonizer.writer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.harmonizer.core.Note;
/**
 * Class generant un fichier .ly executable par lilypond afin de produire une partition .pdf
 * @author alistarle
 *
 */
public class LilyWriter {
	/**
	 * Fichier de sortie .ly
	 */
	private File file;
	private FileWriter fw;
	
	/**
	 * Contenu du fichier lilypond qui sera écris dans le fichier une fois generé
	 */
	private String lilypond = "";
	
	/**
	 * Nom de la partition une fois generée sous format .pdf
	 */
	private String name;
	
	/**
	 * Liste des notes des differentes voix a écrire sur les partitions
	 */
	private ArrayList<ArrayList<Note>> trackList;

	/**
	 * Genère un fichier .ly a partir d'un nom de fichier, d'un nom de partion et d'une liste de notes a écrire
	 * @param fileName Nom du fichier de sortie (sans l'extenstion)
	 * @param name Nom de la partition (écris sur le .pdf)
	 * @param trackList Liste des notes pour chaque voix a écrire
	 */
	public LilyWriter(String fileName, String name, ArrayList<ArrayList<Note>> trackList) {
		this.file = new File(fileName + ".ly");
		this.name = name;
		this.trackList = trackList;
	}

	/**
	 * Genère le contenu du fichier .ly a partir du titre et des notes fournies
	 */
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

	/**
	 * Ecris le contenu du fichier dans ce dernier à l'emplacement designé lors du constructeur
	 */
	public void writeLily() {
		try {
			fw = new FileWriter(file);
			fw.write(lilypond);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
