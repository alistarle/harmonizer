import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.omg.CORBA.SystemException;

import com.harmonizer.core.Song;
import com.harmonizer.exceptions.UnknownOptionException;
import com.harmonizer.types.OptionType;
import com.harmonizer.writer.HTMLWriter;

public class Harmonizer {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidMidiDataException
	 * @throws MidiUnavailableException
	 */
	public static boolean launch = false;
	public static Hashtable<OptionType,ArrayList<String>> option;

	public static void main(String[] args) throws MidiUnavailableException,
			InvalidMidiDataException, IOException {
		try {
			OptionParser op = new OptionParser(args);
			option = op.getOption();
			traitOption(option);
		} catch (IllegalArgumentException | UnknownOptionException e) {
			System.out.println("Il est impossible d'éxécuter ce programme avec ces arguments :\n  - "+e.getMessage());
		}
	}
	
	public static void traitOption(Hashtable<OptionType,ArrayList<String>> option) throws UnknownOptionException, MidiUnavailableException, InvalidMidiDataException, IOException {
		if(option.containsKey(OptionType.NAME)) {		
			displayCredit();
		} else if(option.containsKey(OptionType.HELP)) {		
			displayHelp();
		} else if(option.containsKey(OptionType.NUMBER)) {
			calcNumber();
		} else if(option.containsKey(OptionType.MIDI)) {
			calcMidi();
		} else if(option.containsKey(OptionType.LILYPOND)) {
			calcLily();
		} else if(option.containsKey(OptionType.FOLDER)) {
			calcFolder();
		} else {
			throw new UnknownOptionException("Template introuvable");
		}
	}
	
	public static void displayCredit() {
		System.out.println("============= Credits =============");
		System.out.println("\tVictor Coutellier");
		System.out.println("\tPaul Lecacheur");
		System.out.println("\tJérémy Coudoux");
		System.out.println("\tMerieme Belmeknassi");			
	}
	
	public static void displayHelp() {
		System.out.println("============= AIDE =============");
		System.out.println("-name\t: Affiche les credits du programme");
		System.out.println("-h\t: Affiche la page d'aide du programme");
		System.out.println("-midi input.chant output.mid\t: produit une harmonisation de input.chant sous format midi dans output.mid");
		System.out.println("-ly input.chant output.ly\t: produit une harmonisation de input.chant sous format lilypond dans output.ly");
		System.out.println("-nombre input.chant\t: Affiche le nombre d'harmonisation possible de input.chant");
		System.out.println("-beaute 1-4\t: Optimise l'harmonisation d'un chant selon 4 règles possibles, à coupler avec un argument de type -midi ou -ly");
		System.out.println("-w inputFolder outputFolder\t: produit une harmonisation de tous les chants de l'inputFolder dans l'outputFolder sous format lilypond et midi, ainsi qu'une page de synthèse HTML");
	
	}
	
	public static void calcNumber() {
		Song song = new Song(new File(option.get(OptionType.NUMBER).get(0)));
		System.out.println("Il y a "+song.getHarmonisation()+" harmonisations possible de "+song.getName());
	}
	
	public static void calcMidi() throws MidiUnavailableException, InvalidMidiDataException, IOException {
		if(option.containsKey(OptionType.BEAUTY)) {
			System.out.println("Vous avez demandé un critères de beauté de "+option.get(OptionType.BEAUTY).get(0)
					+", malheuresement cela n'est pas encore disponible, sans doute dans un futur DLC? Stay tuned on https://code.google.com/p/harmonizer/source");
		}
		Song song = new Song(new File(option.get(OptionType.MIDI).get(0)),new File(option.get(OptionType.MIDI).get(1)));
		song.writeToMidi();
		song.playSong();
	}
	
	public static void calcLily() {
		if(option.containsKey(OptionType.BEAUTY)) {
			System.out.println("Vous avez demandé un critères de beauté de "+option.get(OptionType.BEAUTY).get(0)
					+", malheuresement cela n'est pas encore disponible, sans doute dans un futur DLC? Stay tuned on https://code.google.com/p/harmonizer/source");				
		}
		Song song = new Song(new File(option.get(OptionType.LILYPOND).get(0)),new File(option.get(OptionType.LILYPOND).get(1)));
		song.writeToLily();		
	}
	
	public static void calcFolder() {
		File inFolder = new File(option.get(OptionType.FOLDER).get(0));
		File outFolder = new File(option.get(OptionType.FOLDER).get(1));
		if(!outFolder.exists() || !outFolder.isDirectory()) outFolder.mkdir();
		ArrayList<Song> songList = new ArrayList<Song>();
		ArrayList<Integer> harmonisation = new ArrayList<Integer>();
		for(File file : inFolder.listFiles()) {
			if(file.getName().matches(".*\\.chant$")) {
				Song song = new Song(file, file, option.get(OptionType.FOLDER).get(1));
				song.writeToLily();
				song.writeToMidi();
				harmonisation.add(song.getHarmonisation());
				songList.add(song);
			}
		}
		
		if(!outFolder.exists() || !outFolder.isDirectory()) outFolder.mkdir();
		new HTMLWriter(option.get(OptionType.FOLDER).get(1), songList, harmonisation);
	}

}
