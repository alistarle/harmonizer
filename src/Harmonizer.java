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
			traitOption(op.getOption());
		} catch (IllegalArgumentException | UnknownOptionException e) {
			System.out.println("Il est impossible d'éxécuter ce programme avec ces arguments :\n  - "+e.getMessage());
		}
	}
	
	public static void traitOption(Hashtable<OptionType,ArrayList<String>> option) throws UnknownOptionException, MidiUnavailableException, InvalidMidiDataException, IOException {
		if(option.containsKey(OptionType.NAME)) {		
			System.out.println("============= Credits =============");
			System.out.println("\tVictor Coutellier");
			System.out.println("\tPaul Lecacheur");
			System.out.println("\tJérémy Coudoux");
			System.out.println("\tMerieme Belmeknassi");		
		} else if(option.containsKey(OptionType.HELP)) {		
			System.out.println("============= AIDE =============");
			System.out.println("-name\t: Affiche les credits du programme");
			System.out.println("-h\t: Affiche la page d'aide du programme");
			System.out.println("-midi input.chant output.mid\t: produit une harmonisation de input.chant sous format midi dans output.mid");
			System.out.println("-ly input.chant output.ly\t: produit une harmonisation de input.chant sous format lilypond dans output.ly");
			System.out.println("-nombre input.chant\t: Affiche le nombre d'harmonisation possible de input.chant");
			System.out.println("-beaute 1-4\t: Optimise l'harmonisation d'un chant selon 4 règles possibles, à coupler avec un argument de type -midi ou -ly");
			System.out.println("-w inputFolder outputFolder\t: produit une harmonisation de tous les chants de l'inputFolder dans l'outputFolder sous format lilypond et midi, ainsi qu'une page de synthèse HTML");
		} else if(option.containsKey(OptionType.NUMBER)) {
			Song song = new Song(new File(option.get(OptionType.NUMBER).get(0)));
			System.out.println("Il y a "+song.getHarmonisation()+" harmonisations possible de "+song.getName());
		} else if(option.containsKey(OptionType.MIDI)) {
			if(option.containsKey(OptionType.BEAUTY)) {
				System.out.println("Vous avez demandé un critères de beauté de "+option.get(OptionType.BEAUTY).get(0)
						+", malheuresement cela n'est pas encore disponible, sans doute dans un futur DLC? Stay tuned on https://code.google.com/p/harmonizer/source");
			}
			Song song = new Song(new File(option.get(OptionType.MIDI).get(0)),new File(option.get(OptionType.MIDI).get(1)));
			song.writeToMidi();
			song.playSong();
		} else if(option.containsKey(OptionType.LILYPOND)) {
			if(option.containsKey(OptionType.BEAUTY)) {
				System.out.println("Vous avez demandé un critères de beauté de "+option.get(OptionType.BEAUTY).get(0)
						+", malheuresement cela n'est pas encore disponible, sans doute dans un futur DLC? Stay tuned on https://code.google.com/p/harmonizer/source");				
			}
			Song song = new Song(new File(option.get(OptionType.LILYPOND).get(0)),new File(option.get(OptionType.LILYPOND).get(1)));
			song.writeToLily();
		} else if(option.containsKey(OptionType.FOLDER)) {
			
		} else {
			throw new UnknownOptionException("Template introuvable");
		}
	}

}
