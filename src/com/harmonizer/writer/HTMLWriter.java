package com.harmonizer.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.harmonizer.core.Song;
/**
 * Class generant une synthèse HTML dans le dossier donné, sachant la liste des Chant ainsi que le nombre de leur harmonisations
 * @author alistarle
 *
 */
public class HTMLWriter {
	
	/**
	 * Chaine representant l'HTML à écrire dans l'index.html
	 */
	private String HTML;
	
	/**
	 * Chaine representant le CSS à écrire dans le style.css
	 */
	private String CSS;
	
	/**
	 * Fichier de sortie HTML
	 */
	private File outputHTML;
	
	/**
	 * Fichier de sortie CSS
	 */
	private File outputCSS;
	
	/**
	 * Liste des chant a integrer a la synthèse
	 */
	private ArrayList<Song> songList;
	
	/**
	 * Nombre des harmonisations pour chaque chant
	 */
	private ArrayList<Integer> harmonisation;
	
	/**
	 * Genère un fichier HTML et CSS de synthèse du programme
	 * @param output Le nom du dossier de sortie
	 * @param songList La liste des chant
	 * @param harmonisation La liste du nombre des harmonisation de chaque chant
	 */
	public HTMLWriter(String output, ArrayList<Song> songList, ArrayList<Integer> harmonisation) {
		this.songList = songList;
		this.harmonisation = harmonisation;
		this.HTML = ""; this.CSS = "h1 { text-align:center; }";
		this.outputHTML = new File(output+File.separator+"index.html");
		this.outputCSS = new File(output+File.separator+"style.css");
		generateHTML();
		writeHTML();
	}
	
	/**
	 * Genère l'HTML en fonction des chant et harmonisation données
	 */
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
	
	/**
	 * Ecris les fichiers index.html et style.css dans le dossier spécifié
	 */
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
