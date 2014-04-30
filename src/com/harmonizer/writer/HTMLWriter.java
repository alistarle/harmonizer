package com.harmonizer.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.harmonizer.core.Song;

public class HTMLWriter {
	
	private String HTML;
	private String CSS;
	private File outputHTML;
	private File outputCSS;
	private ArrayList<Song> songList;
	private ArrayList<Integer> harmonisation;
	
	public HTMLWriter(String output, ArrayList<Song> songList, ArrayList<Integer> harmonisation) {
		this.songList = songList;
		this.harmonisation = harmonisation;
		this.HTML = ""; this.CSS = "h1 { text-align:center; }";
		this.outputHTML = new File(output+File.separator+"index.html");
		this.outputCSS = new File(output+File.separator+"style.css");
		generateHTML();
		writeHTML();
	}
	
	public void generateHTML() {
		HTML+="<!DOCTYPE html>\n"
				+ "<html lang=\"fr\">\n"
				+ "<head>\n"
				+ "\t<meta charset=\"UTF-8\"/>\n"
				+ "\t<link rel=\"stylesheet\" href=\"style.css\"/>\n"
				+ "\t<title>Rapport d'analyse de chant</title>\n"
				+ "</head>\n"
				+ "<h1>Rapport d'analyse de chant</h1>\n"
				+ "<table>\n"
				+ "\t<caption>Liste des chants</caption>\n"
				+ "\t<tr><th>Nom du chant</th><th>Nombre d'harmonisations</th><th>Midi</th><th>Lilypond</th></tr>\n";
		for(Song song : songList) {
			HTML+="\t<tr><td>"+song.getName()+"</td><td>"+harmonisation.get(songList.indexOf(song))+"</td><td><a href=\""+song.getOutput()+".mid\">Midi</a></td><td><a href=\""+song.getOutput()+".ly\">Lilypond</a></td></tr>\n";
		}
		HTML+="</table>";
	}
	
	public void writeHTML() {
		try {
			System.out.println(HTML);
			FileWriter fw = new FileWriter(outputHTML);
			fw.write(HTML);
			fw.close();
			
			fw = new FileWriter(outputCSS);
			fw.write(CSS);
			fw.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
