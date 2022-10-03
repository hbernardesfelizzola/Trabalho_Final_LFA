package com.ufrgs.automatos.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ufrgs.automatos.JogoMain;

public class PathsController {
	
	public PathsController() {
		this("paths.yaml");
	}
	
	public PathsController(File fileName) {
		try {
			this.paths = PathsControllerUtils.loadYamlObject(fileName);
		} catch (Exception e) {
			this.paths = new HashMap<>();
			
			PathsControllerLucio lucioController = new PathsControllerLucio(fileName);
			
			Set<String> finalStates = lucioController.getFinalStates();
			Set<String> declaredStates = lucioController.getDeclaredStates();
			
			paths.put("estado_inicial", lucioController.getInitialState());
			
			Map<String, HashMap<String, String>> transitions = lucioController.getTransitions();
			Map<String, Object> statesMap = new HashMap<>();
			
			for (String s : transitions.keySet()) {
				Map<String, Object> stateMap = new HashMap<>();
				
				stateMap.put("caminhos", transitions.get(s));
				stateMap.put("final", finalStates.contains(s));
				
				statesMap.put(s, stateMap);
			}
			
			for (String s : declaredStates) {
				if (!statesMap.containsKey(s)) {
					Map<String, Object> stateMap = new HashMap<>();

					stateMap.put("final", finalStates.contains(s));
					
					statesMap.put(s, stateMap);
				}
			}
			
			paths.put("estados", statesMap);
		}
		
		System.out.println(getEstados());
	}
	
	public PathsController(String fileName) {
		this.paths = PathsControllerUtils.loadYamlObject(JogoMain.class.getClassLoader().getResourceAsStream(fileName));
	}
	
	
	private Map<String, Object> paths;
	
	
	public String getInitialState() {return (String) paths.get("estado_inicial");}
	
	public Map<String, Object> getEstados() {return PathsControllerUtils.convertObjectToMap(paths.get("estados"));}
	
	public String getPathsAsString() {
		
		String s = "<html>S: ";
		String movimentos = "";
		
		for (String state : getEstados().keySet()) {
			
			s = s + state + ",";
			
			if (this.getPaths(state) == null) continue;
			
			Map<String, Object> map = this.getPaths(state);
			for (String terminal : map.keySet()) {
				movimentos = movimentos + "(" + state + "," + terminal + "," + map.get(terminal) + ")<br>";
			}
		}
		
		s = s.substring(0, s.length()-1) + "<br>";
		s = s + "A: ";
		
		for (String terminal : getTerminals()) {
			s = s + terminal + ",";
		}

		s = s.substring(0, s.length()-1) + "<br>";
		s = s + "i: " + this.getInitialState() + "<br>";
		s = s + "F: ";
		
		for (String finalState : this.getEstadosFinais()) {
			s = s + finalState + ",";
		}

		s = s.substring(0, s.length()-1) + "<br><br>";
		s = s + movimentos + "</html>";
		
		return s;
	}
	
	private Set<String> getTerminals() {
		Set<String> terminals = new HashSet<>(); 
		for (String state : getEstados().keySet()) {
			Map<String,Object> paths = this.getPaths(state);
			
			if (paths == null) continue;
			
			for (String s : paths.keySet()) {terminals.add(s);}
		}
		
		return terminals;
	}
	
	private Set<String> getEstadosFinais() {
		Set<String> estadosFinais = new HashSet<>();
		
		for (String s : this.getEstados().keySet()) {
			if (isNodeFinal(s)) {
				estadosFinais.add(s);
			}
		}
		
		return estadosFinais;
	}
	
	
	
	public ResponseWord executeWord(String str) {
		String current = getInitialState();
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			Map<String, Object> terminals = getPaths(current);
			
			if (terminals == null || terminals.get(c + "") == null) {
				return ResponseWord.INDEFINITION;
			}
			
			current = (String) terminals.get(c + "");
			
			System.out.println("Andou para " + current);
		}
		
		return ResponseWord.convertBooleanToResponse(isNodeFinal(current));
	}
	
	public TreeMap<String, ResponseWord> executeWord(ArrayList<String> words) {
		TreeMap<String, ResponseWord> responses = new TreeMap<>();
		
		for (String s : words) {
			responses.put(s, executeWord(s));
		}
		
		return responses;
	}
	
	public boolean isLangFinite() {
		for (String s : getEstados().keySet()) {
			if (hasWayToItSelf(s)) return false;
		}
		
		return true;
	}
	
	public boolean isLangEmpty() {
		Set<String> estadosFinais = getEstadosFinais();

		for (String s : new ArrayList<>(this.getEstados().keySet())) {
			for (String estadoFinal : estadosFinais) {
				if (this.verifyPathToOther(estadoFinal, s, new HashSet<>())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean hasWayToItSelf(String node) {
		Set<String> already = new HashSet<>();
		
		return verifyPathToOther(node, node, already);
	}

	private boolean verifyPathToOther(String other, String node, Set<String> already) {
		Map<String, Object> map = getPaths(node);
		
		if (map == null) return false;
		
		for (String terminal : map.keySet()) {
			String to = (String) map.get(terminal);
			
			if (to.equals(other)) return true;
			
			if (!already.contains(to)) {
				already.add(to);
				
				if (verifyPathToOther(other, to, already)) return true;
			}
		}
		
		return false;
	}
	
	public void minimazeAutomate() {
		Set<String> estadosFinaisAlcancados = new HashSet<>();
		Set<String> estadosFinais = getEstadosFinais();
		
		for (String s : new ArrayList<>(this.getEstados().keySet())) {
			boolean has = false;
			
			for (String estadoFinal : estadosFinais) {
				if (this.verifyPathToOther(estadoFinal, s, new HashSet<>())) {
					has = true;
					estadosFinaisAlcancados.add(estadoFinal);
					
					if (estadosFinaisAlcancados.size() == estadosFinais.size()) break;
				}
			}
			
			if (!has) {
				if (this.getInitialState().equals(s) || estadosFinais.contains(s)) continue;
				
				this.getEstados().remove(s);
			}
		}
		
		if (estadosFinaisAlcancados.size() != estadosFinais.size()) {
			for (String s : estadosFinais) {
				if (!estadosFinaisAlcancados.contains(s)) {
					this.getEstados().remove(s);
				}
			}
		}
		
		for (String s : this.getEstados().keySet()) {
			Map<String, Object> map = getPaths(s);
			
			if (map == null) continue;
			
			for (String terminal : new ArrayList<>(map.keySet())) {
				if (this.getEstados().get(map.get(terminal)) == null) {
					map.remove(terminal);
				}
			}
		}
	}
	
	private Map<String, Object> getPaths(String nodo) {
		if (getEstados().get(nodo) != null) {
			Map<String, Object> nodoPaths = PathsControllerUtils.convertObjectToMap(getEstados().get(nodo));
			
			if (nodoPaths.get("caminhos") != null) {
				return PathsControllerUtils.convertObjectToMap(nodoPaths.get("caminhos"));
			}
		}
		
		return null;
	}
	
	private boolean isNodeFinal(String node) {
		if (getEstados().get(node) != null) {
			Map<String, Object> nodoPaths = PathsControllerUtils.convertObjectToMap(getEstados().get(node));

			if (nodoPaths.get("final") != null) {
				return (boolean) nodoPaths.get("final");
			}
		}
		return false;
	}
	
	
}
