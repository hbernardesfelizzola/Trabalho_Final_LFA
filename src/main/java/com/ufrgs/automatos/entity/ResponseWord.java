package com.ufrgs.automatos.entity;

public enum ResponseWord {
	
	INDEFINITION,
	ACCEPTED,
	REJECTED;
	
	public static ResponseWord convertBooleanToResponse(boolean bool) {
		return bool ? ACCEPTED : REJECTED;
	}
}
