package com.ufrgs.automatos.entity;

public enum Terminal {
	
	TERMINAL_W('w', 225, 320),
	TERMINAL_S('s', 298, 320),
	TERMINAL_D('d', 370, 320),
	TERMINAL_E('e', 444, 320),
	TERMINAL_B('b', 513, 320);
	
	
	private int x, z;
	
	private char w;
	
	
	private Terminal(char w, int x, int z) {
		this.x = x;
		this.z = z;
		this.w = w;
	}
	

	public int getX() {return x;}

	public int getZ() {return z;}
	
	public char getLetter() {return this.w;}
	
	
	public static Terminal toTerminal(char c) {
		for (Terminal t : Terminal.values()) {
			if (t.getLetter() == c) {
				return t;
			}
		}
		
		return null;
	}

}
