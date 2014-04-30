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
	private String CSS = new String("body {background: #1F3544; text-align: center; margin: 0; padding: 0;}\n"
			+ "h1 {font-size: 30px; color: #EBEBEB; font-family: Open Sans; font-weight: normal; margin: 0px; margin-bottom: 20px; }\n"
			+ "#main {padding:50px; margin: auto; width: 800px; }\n"
			+ ".item_resp {margin: 20px; display: inline-block; text-align: left; background:#f7f7f7; font-family: Open Sans,Arial,sans-serif; font-size: 13px; padding: 0; box-shadow: 0 2px 12px rgba(0,0,0,.7); }\n"
			+ ".item_resp h3{margin: 0; padding: 5px 10px; text-transform: uppercase; font-family: Open Sans,Arial,sans-serif; font-size: 20px; font-weight: normal; color: #eee;border-top: 1px solid rgba(255,255,255,0.3); }\n"
			+ ".item_resp .inner {padding: 20px 10px; }\n"
			+ ".item_resp table td, .item_resp table th {padding: 6px 16px; }\n"
			+ ".item_resp a {color: #09c; text-decoration: none; }\n"
			+ ".item_resp a:hover {color: #247992; text-decoration: underline; }\n"
			+ ".spacer {width: 100px; height:2px; background: #DDD; margin:auto; margin-bottom: 18px; }\n"
			+ ".item_resp h3 {\n"
			+ "\tbackground: #3498db;\n"
			+ "}");
	
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
		this.HTML = "";
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
				+ "<div id=\"main\">\n<h1>Rapport d'analyse de chant</h1>\n<div class=\"spacer\"></div>\n<div class=\"item_resp\">\n"
				+ "<h3>Liste des chants</h3>\n<div class=\"inner\">\n"
				+ "<table cellspacing=\"0\" cellpadding=\"6\">\n"
				+ "\t<tr><th>Nom du chant</th><th>Nombre d'harmonisations</th><th>Midi</th><th>Lilypond</th></tr>\n";
		for(Song song : songList) {
			HTML+="\t<tr><td>"+song.getName()+"</td><td>"+harmonisation.get(songList.indexOf(song))+"</td><td><a href=\""+song.getOutput()+".mid\">Midi</a></td><td><a href=\""+song.getOutput()+".ly\">Lilypond</a></td></tr>\n";
		}
		HTML+="</table>\n</div>\n</div>\n</div>";
	}
	
	/**
	 * Ecris les fichiers index.html et style.css dans le dossier spécifié
	 */
	public void writeHTML() {
		try {
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
