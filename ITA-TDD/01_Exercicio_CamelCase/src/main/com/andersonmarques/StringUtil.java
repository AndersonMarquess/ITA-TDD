package main.com.andersonmarques;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static List<String> converterCamelCase(String original) {
		List<String> palavras = new ArrayList<>();
		
		for(String teste : original.split("(?=[A-Z])")) {
			palavras.add(teste.toLowerCase());
		}
		
		return palavras;
	}
}
