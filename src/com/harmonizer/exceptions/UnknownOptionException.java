package com.harmonizer.exceptions;
/**
 * Exception levée lorsqu'une option n'a pas été reconnue
 * @author alistarle
 *
 */
public class UnknownOptionException extends Exception{
	public UnknownOptionException(String option) {
		System.out
				.println("Cette option est inconnue : "
						+ option);
	}
}
