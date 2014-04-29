import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.text.html.Option;

import com.harmonizer.exceptions.UnknownOptionException;
import com.harmonizer.types.OptionType;


public class OptionParser {
	
	private static Hashtable<String, OptionType> optionMatching = new Hashtable<String, OptionType>() {
		{
			put("-name",OptionType.NAME); put("-h",OptionType.HELP); put("-midi",OptionType.MIDI); put("-ly",OptionType.LILYPOND); put("-nombre",OptionType.NUMBER); put("-beaute",OptionType.BEAUTY); put("-w",OptionType.FOLDER);
		}
	};
	
	private static Hashtable<OptionType, ArrayList<String>> argsMatching = new Hashtable<OptionType, ArrayList<String>>() {
		{
			put(OptionType.NAME, new ArrayList<String>());
			put(OptionType.HELP, new ArrayList<String>());
			put(OptionType.MIDI, new ArrayList<String>(Arrays.asList("\\.chant$","\\.mid$")));
			put(OptionType.LILYPOND, new ArrayList<String>(Arrays.asList("\\.chant$","\\.ly$")));
			put(OptionType.NUMBER, new ArrayList<String>(Arrays.asList("\\.chant$")));
			put(OptionType.BEAUTY, new ArrayList<String>(Arrays.asList("^[1-9][0-9]*$")));
			put(OptionType.FOLDER, new ArrayList<String>(Arrays.asList("^[a-zA-Z]+$","^[a-zA-Z]+$")));
		}
	};
	
	private ArrayList<OptionType> optionList;
	
	public OptionParser(String[] args) throws UnknownOptionException {
		optionList = new ArrayList<OptionType>();
		/*for(String str : args) {
			if(str.startsWith("-")) {
				if(optionMatching.get(str) != null) {
					checkArgs(optionMatching.get(str));
				} else {
					throw new UnknownOptionException(str);
				}
			}
		}*/
	}
	
	/*
	 * Lève une exception si la liste d'argument donnée n'est pas conforme
	 */
	public void checkArg(ArrayList<String> arg) throws UnknownOptionException, IllegalArgumentException {
		OptionType option = optionMatching.get(arg.get(0));
		if(option != null) {
			if(arg.size()-1 == argsMatching.get(option).size()) {
				for(int i = 1; i < argsMatching.get(option).size(); i++) {
					if(!arg.get(i).matches(argsMatching.get(option).get(i))) throw new IllegalArgumentException("Argument invalide : "+arg.get(i));			
				}
			} else {
				throw new IllegalArgumentException("Nombre d'argument invalide pour : "+arg.get(0));
			}
		} else {
			throw new UnknownOptionException(arg.get(0));
		}
	}
}