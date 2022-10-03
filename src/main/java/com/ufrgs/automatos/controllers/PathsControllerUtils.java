package com.ufrgs.automatos.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

public class PathsControllerUtils {

	public static Map<String, Object> loadYamlObject(File fileName) {
		try {
			return loadYamlObject(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> loadYamlObject(InputStream stream) {
		Yaml yaml = new Yaml();

		return convertObjectToMap(yaml.load(stream));
	}
	
	public static String getStringFromFile(File f) {
		try {
			return FileUtils.readFileToString(f, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertObjectToMap(Object obj) {
		return (Map<String, Object>) obj;
	}
}
