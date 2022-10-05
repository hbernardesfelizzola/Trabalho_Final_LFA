package com.ufrgs.automatos.entity;

import java.util.HashMap;

import com.ufrgs.automatos.JogoMain;

public class PlayerData {
	
	private HashMap<String, HashMap<Terminal, ResponseWord>> playerTerminalsCache = new HashMap<>();
	
	private String word = "";
	
	private int currentIndex = 0;
	
	
	public HashMap<Terminal, ResponseWord> getCurrentCache() {
		return playerTerminalsCache.get(word);
	}
	
	public ResponseWord getCache(String s, Terminal t) {
		if (playerTerminalsCache.containsKey(s) && playerTerminalsCache.get(s).containsKey(t)) {
			return playerTerminalsCache.get(s).get(t);
		}
		return null;
	}

	public String getWord() {return word;}
	

	public String getCurrentIndexWord() {
		return getCurrentIndexWord(this.currentIndex);
	}
	
	public String getCurrentIndexWord(int index) {
		String s = "";
		
		for (int i = 0; i < index; i++) {
			if (this.getWord().length() <= i) break;
			
			s = s + this.getWord().charAt(i);
		}
		
		return s;
	}
	
	public int getCurrentIndex() {return this.currentIndex;}
	
	public boolean isLastIndex() {
		return this.currentIndex >= this.word.length() || JogoMain.getGamePathsController().executeWord(getCurrentIndexWord(this.currentIndex)).equals(ResponseWord.INDEFINITION);
	}
	
	public boolean isIndefinitionNext() {
		return JogoMain.getGamePathsController().executeWord(getCurrentIndexWord(this.currentIndex+1)).equals(ResponseWord.INDEFINITION);
	}
	
	
	
	public void addChar(String c) {this.word = this.word + c;}
	
	public void addChar(char c) {this.word = this.word + c;}
	
	public void removeLastChar() {
		if (this.word.length() > 0) {
			this.word = this.word.substring(0, this.word.length()-1);
		}
	}
	
	
	public void increaseCurrentIndex() {this.currentIndex++;}
	
	public void resetCurrentIndex() {this.currentIndex = 0;}
	
	public void resetWord() {this.word = "";}
	
	
	public void addToCache(String s, Terminal t, ResponseWord w) {
		playerTerminalsCache.computeIfAbsent(s, h -> new HashMap<>()).put(t, w);
	}
	
	

}
