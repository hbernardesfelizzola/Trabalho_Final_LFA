package com.ufrgs.automatos.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PathsControllerLucio {
	
	public PathsControllerLucio(File file) {
		this.content = PathsControllerUtils.getStringFromFile(file);
	}
	
	private String content;
	
	
	
	public String getInitialState() {
		String partes[] = content.split("\n");
		String correctLine = findSomeInitialString(partes, "i:");
		
		return correctLine.replaceAll("i: ", "");
	}
	
	public Map<String, HashMap<String, String>> getTransitions() {
		String partes[] = content.split("\n");
		Map<String, HashMap<String, String>> map = new HashMap<>();
		
		for (int i = 0; i < partes.length; i++) {
			if (partes[i].startsWith("(")) {
				Transition transition = new Transition(partes[i]);
				System.out.println("Parseando: " + transition.getFrom() + " " + transition.getTerminal() + " " + transition.getTo());
				addTransitionToMap(transition, map);
			}
		}
		
		
		return map;
	}
	
	private void addTransitionToMap(Transition transition, Map<String, HashMap<String, String>> map) {
		if (!map.containsKey(transition.getFrom())) {
			map.put(transition.getFrom(), new HashMap<>());
		}
		
		HashMap<String, String> terminals = (HashMap<String, String>) map.get(transition.getFrom());
		
		terminals.put(transition.getTerminal(), transition.getTo());
	}
	
	public Set<String> getDeclaredStates() {
		return getGenericStates(content, "F: ");
	}
	
	public Set<String> getFinalStates() {
		return getGenericStates(content, "F: ");
	}
	
	private Set<String> getGenericStates(String content, String toFind) {
		Set<String> states = new HashSet<>();
		String partes[] = content.split("\n");
		String correctLine = findSomeInitialString(partes, toFind);
		
		for (String s : correctLine.replaceAll(toFind, "").replaceAll(" ", "").split(",")) {
			states.add(s);
		}
		
		return states;
	}
	
	private String findSomeInitialString(String partes[], String searching) {
		for (int i = 0; i < partes.length; i++) {
			if (partes[i].startsWith(searching)) {
				return partes[i].replace("\r", "").replace("\n", "");
			}
		}
			
		
		return null;
	}
	
	
	class Transition {
		
		public Transition(String s) {
			String partes[] = s.replaceAll(Pattern.quote("("), "").replaceAll(Pattern.quote(")"), "").replace("\n", "").replace("\r", "").replaceAll(" ", "").split(",");
			
			this.from = partes[0];
			this.terminal = partes[1];
			this.to = partes[2];
		}
		
		
		private String from, terminal, to;

		public String getFrom() {
			return from;
		}

		public String getTerminal() {
			return terminal;
		}

		public String getTo() {
			return to;
		}
		
		
		
	}
}
