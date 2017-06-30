package it.polito.ai.signal.exception;

import static java.lang.String.format;

public class FailedToAuthenticate extends Exception {

	private static final long serialVersionUID = -4054597124848897187L;

	public FailedToAuthenticate() {
        super(format("Failed to authenticate"));
    }
	
}
