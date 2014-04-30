package com.harmonizer.rules;

import java.util.ArrayList;

import com.harmonizer.core.NoteSet;
/**
 * Class representant les règles locales
 * @author alistarle
 *
 */
public abstract class LocalRule {
	/**
	 * Fonction redefinie dans les classes filles, elle renvoie un booleen en fonction de la validité du jeu de note vis a vis de la règle en question
	 * @param ns jeu de note a analyser
	 * @return si le jeu de note verifie la règle
	 */
	public abstract boolean validate(NoteSet ns);

	/**
	 * Supprime de la liste les jeux de notes ne vérifiant pas la règle
	 * @param nsl liste de jeux de notes
	 */
	public void simplify(ArrayList<NoteSet> nsl) {
		for (int i = 0; i < nsl.size(); i++) {
			if (!validate(nsl.get(i))) {
				nsl.remove(i);
			}
		}
	}
}
