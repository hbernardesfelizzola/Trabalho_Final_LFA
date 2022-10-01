package com.ufrgs.automatos.controllers;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.ufrgs.automatos.JogoMain;

public class PathsControllerUtils {

	public static Map<String, Object> loadYamlObject(String fileName) {
		Yaml yaml = new Yaml();
		InputStream stream = JogoMain.class.getClassLoader().getResourceAsStream(fileName);

		return convertObjectToMap(yaml.load(stream));
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertObjectToMap(Object obj) {
		return (Map<String, Object>) obj;
	}
}
