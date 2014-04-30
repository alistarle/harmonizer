import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import com.harmonizer.exceptions.UnknownOptionException;
import com.harmonizer.types.OptionType;


public class OptionParser {
	
	/**
	 * Hashtable associant des chaines brutes a un élément d'une liste fini d'Option
	 * @see OptionType
	 */
	private static Hashtable<String, OptionType> optionMatching = new Hashtable<String, OptionType>() {
		{
			put("-name",OptionType.NAME); put("-h",OptionType.HELP); put("-midi",OptionType.MIDI); put("-ly",OptionType.LILYPOND); put("-nombre",OptionType.NUMBER); put("-beaute",OptionType.BEAUTY); put("-w",OptionType.FOLDER);
		}
	};
	
	/**
	 * Hashtable associant une Option à des regex representant ses arguments
	 * @see OptionType
	 */
	private static Hashtable<OptionType, ArrayList<String>> argsMatching = new Hashtable<OptionType, ArrayList<String>>() {
		{
			put(OptionType.NAME, new ArrayList<String>());
			put(OptionType.HELP, new ArrayList<String>());
			put(OptionType.MIDI, new ArrayList<String>(Arrays.asList(".*\\.chant$",".*\\.mid$")));
			put(OptionType.LILYPOND, new ArrayList<String>(Arrays.asList(".*\\.chant$",".*\\.ly$")));
			put(OptionType.NUMBER, new ArrayList<String>(Arrays.asList(".*\\.chant$")));
			put(OptionType.BEAUTY, new ArrayList<String>(Arrays.asList("^[1-4]$")));
			put(OptionType.FOLDER, new ArrayList<String>(Arrays.asList("^[a-zA-Z0-9]+$","^[a-zA-Z0-9]+$")));
		}
	};
	
	/**
	 * Liste de combinaisons possible d'Option utilisés par le programme
	 * @see OptionType
	 */
	private static ArrayList<ArrayList<OptionType>> template = new ArrayList<ArrayList<OptionType>>() {
		{
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.NAME)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.HELP)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.MIDI)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.LILYPOND)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.NUMBER)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.BEAUTY,OptionType.MIDI)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.BEAUTY,OptionType.LILYPOND)));
			add(new ArrayList<OptionType>(Arrays.asList(OptionType.FOLDER)));
		}
	};
	
	/**
	 * Hashtable des options et de ses arguments une fois traité
	 */
	private Hashtable<OptionType,ArrayList<String>> optionList;
	
	/**
	 * Arguments brut sous forme de tableau de String
	 */
	private String[] args;
	
	/**
	 * Constructeur qui traite automatiquement le tableau de String en Hashtable
	 * @param args Arguments brut donné lors de l'éxécution du programme
	 * @throws IllegalArgumentException
	 * @throws UnknownOptionException
	 */
	public OptionParser(String[] args) throws IllegalArgumentException, UnknownOptionException {
		this.optionList = new Hashtable<OptionType,ArrayList<String>>();
		this.args = args;
		parseOption();
		validateTemplate();
	}
	
	public void validateTemplate() {
		for(ArrayList<OptionType> list : template) {
			if(list.containsAll(optionList.keySet())) {
				return;
			}
		}
		throw new IllegalArgumentException("Cette combinaison d'argument n'est pas éxécutable");
	}
	
	public void parseOption() throws IllegalArgumentException, UnknownOptionException {
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList arg;
		for(String str : args) {
			if(str.startsWith("-") && !str.equals(args[0])) {
				arg = checkArg(temp);
				optionList.put((OptionType)arg.get(0),(ArrayList<String>) arg.get(1));
				temp = new ArrayList<String>();
			}
			temp.add(str);
		}
		arg = checkArg(temp);
		optionList.put((OptionType)arg.get(0),(ArrayList<String>) arg.get(1));
	}
	
	/*
	 * Lève une exception si la liste d'argument donnée n'est pas conforme
	 */
	public ArrayList checkArg(ArrayList<String> arg) throws UnknownOptionException, IllegalArgumentException {
		ArrayList<String> params = new ArrayList<String>();
		OptionType option = optionMatching.get(arg.get(0));
		if(option != null) {
			if(arg.size()-1 == argsMatching.get(option).size()) {
				for(int i = 1; i <= argsMatching.get(option).size(); i++) {
					if(arg.get(i).matches(argsMatching.get(option).get(i-1))) {
						params.add(arg.get(i));
					} else { throw new IllegalArgumentException("Argument invalide : "+arg.get(i)); }
				}
			} else {
				throw new IllegalArgumentException("Nombre d'argument invalide pour : "+arg.get(0));
			}
		} else {
			throw new UnknownOptionException(arg.get(0));
		}
		return new ArrayList(Arrays.asList(option,params));
	}
	
	public Hashtable<OptionType,ArrayList<String>> getOption() {
		return optionList;
	}
}