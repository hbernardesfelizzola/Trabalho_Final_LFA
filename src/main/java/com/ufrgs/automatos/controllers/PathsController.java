package com.ufrgs.automatos.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PathsController {
	
	public PathsController() {
		this("paths.yaml");
	}
	
	public PathsController(String fileName) {
		this.paths = PathsControllerUtils.loadYamlObject(fileName);
	}
	
	
	private Map<String, Object> paths;
	
	
	public String getInitialState() {return (String) paths.get("estado_inicial");}
	
	public Map<String, Object> getEstados() {return PathsControllerUtils.convertObjectToMap(paths.get("estados"));}
	
	public String getPathsAsString() {
		return "";
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
