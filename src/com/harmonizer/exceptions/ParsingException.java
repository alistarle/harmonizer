package com.harmonizer.exceptions;
/**
 * Exception levée lorsqu'une note n'a pas été reconnue
 * @author alistarle
 *
 */
public class ParsingException extends Exception {
	public ParsingException(String note) {
		System.out
				.println("Une erreur est survenu lors du parsing de cette note : "
						+ note);
	}
}
