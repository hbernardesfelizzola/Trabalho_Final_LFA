package com.ufrgs.automatos.controllers;

public enum ResponseWord {
	
	INDEFINITION,
	ACCEPTED,
	REJECTED;
	
	public static ResponseWord convertBooleanToResponse(boolean bool) {
		return bool ? ACCEPTED : REJECTED;
	}
}
