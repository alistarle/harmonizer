package com.harmonizer.exceptions;
public class ParsingException extends Exception {
	public ParsingException(String note) {
		System.out.println("Une erreur est survenu lors du parsing de cette note : " +
				note);
	}
}
