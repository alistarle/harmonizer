package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;
/**
 * Class representant les règles d'enchainement
 * @author alistarle
 *
 */
public abstract class LinkRule {
	/**
	 * Fonction redefinie dans les classes filles, elle renvoie un booleen en fonction de la validité des 2 jeux de notes vis a vis de la règle en question
	 * @param ns jeu de note a analyser
	 * @param ns2 second jeu de note a analyser
	 * @return si les 2 jeux respectent la règle d'enchainement
	 */
	public abstract boolean validate(NoteSet ns, NoteSet ns2);
	
	/**
	 * Retourne la liste des index des jeux de note verifiant la règle d'enchainement avec un second jeu
	 * @param noteSet Un jeu de note vis a vis duquel on veut tester une liste de jeu de note
	 * @param nsl2 Une liste de jeu
	 * @return La liste des index des jeux verifiant la règle avec noteSet
	 */
	public ArrayList<Integer> simplify(NoteSet noteSet, ArrayList<NoteSet> nsl2) {
		ArrayList<Integer> noteSetList = new ArrayList<Integer>();
		for(int i = 0; i < nsl2.size(); i++) {
			if(validate(noteSet, nsl2.get(i))) {
				noteSetList.add(i);
			}
		}
		return noteSetList;
	}
}
