package com.andersonmarques;

import java.util.HashMap;
import java.util.Map;

public class Tradutor {
	
	private Map<String, String> traducoes = new HashMap<>();

	public boolean isEmpty() {
		return traducoes.isEmpty();
	}

	public void addTraducao(String palavra, String traducao) {
		if(traducoes.containsKey(palavra)) {
			traducao = traduzir(palavra)+", "+traducao;
		}
		
		traducoes.put(palavra, traducao);
	}

	public String traduzir(String palavra) {
		return traducoes.get(palavra);
	}

	public String traduzirFrase(String frase) {
		String[] palavras = frase.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String palavra : palavras) {
			sb.append(recuperarPrimeiraPalavraDaTraducao(palavra));
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	private String recuperarPrimeiraPalavraDaTraducao(String palavra) {
		return traduzir(palavra).split(",")[0];
	}
}
