package com.repdev.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 /*
 * Loads and provides fast helper methods for function content assist type stuff
 * 
 * @author Jake Poznanski
 * 
 */
public class FunctionLayout {
	private static FunctionLayout functionLayout = new FunctionLayout();
	private HashMap<String, Function> functions = new HashMap<String, Function>();
	
	private FunctionLayout() {
		Pattern funcPattern = Pattern.compile("(.*)\\|(.*)\\|(.*)");
		Matcher funcMatcher;
		
		Pattern argPattern = Pattern.compile("\\t(.*)\\|(.*)\\|(.*)");
		Matcher argMatcher;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("functions.txt")));
			String line;
			Function cur;
			
			while ((line = br.readLine()) != null) {
				line = line.trim();

				if (line.length() != 0) {
					funcMatcher = funcPattern.matcher(line);
					
					if (funcMatcher.matches()) {
						ArrayList<VariableType> types = new ArrayList<VariableType>();
						String[] typeNames = funcMatcher.group(3).split(",");
						
						for( String name : typeNames)
							types.add(VariableType.valueOf(name.toUpperCase()));
						
						cur = new Function( funcMatcher.group(1), funcMatcher.group(2), types);
						functions.put(cur.getName().toLowerCase(), cur);
					}
				}
			}

			br.close();
		} catch (IOException e) {
		}
	}
	
	public boolean containsName(String name){
		return functions.containsKey(name.toLowerCase());
	}
	
	public static FunctionLayout getInstance(){
		return functionLayout;
	}
}
