package com.harmonizer.exceptions;

public class UnknownOptionException extends Exception{
	public UnknownOptionException(String option) {
		System.out
				.println("Cette option est inconnue : "
						+ option);
	}
}
