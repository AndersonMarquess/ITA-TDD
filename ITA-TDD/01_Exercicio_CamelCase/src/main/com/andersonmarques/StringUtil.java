package main.com.andersonmarques;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static List<String> converterCamelCase(String original) {
		List<String> palavras = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		lancarExceptionSeStringInvalida(original);
		for (String palavra : original.split("(?=[A-Z])|(?=[0-9])")) {
			addNumeroOuLetraAoTemp(palavra, temp);
			temp = addPalavraSeIgualCPF(palavras, temp);
			palavras.add(palavra.toLowerCase());
		}
		addPalavraTemp(palavras, temp);
		limparStringBuilder(temp);
		return palavras;
	}
	
	private static void addNumeroOuLetraAoTemp(String palavra, StringBuilder temp) {
		if(Character.isDigit(palavra.toCharArray()[0])) {
			temp.append(palavra);
			return;
		}
		if (isUmaLetraEmMinusculo(palavra)) {
			temp.append(palavra);
			return;
		}
	}

	/**
	 * Se a string começar com dígito ou contiver qualquer outro caractere que não seja uma letra ou número @throws{IllegalArgumentException}.
	 * @param original
	 */
	private static void lancarExceptionSeStringInvalida(String original) {
		if(isStringIniciandaComNumero(original) || isStringComCaracteresEspeciais(original)) {
			throw new IllegalArgumentException("Não são permitidas strings que começem com números ou possuam caracteres especiais.");
		}
	}
	
	private static boolean isStringIniciandaComNumero(String original) {
		return Character.isDigit(original.toCharArray()[0]);
	}
	
	private static boolean isStringComCaracteresEspeciais(String original) {
		return Pattern.compile("[^a-zA-Z0-9]").matcher(original).find();
	}

	private static void addPalavraTemp(List<String> palavras, StringBuilder temp) {
		if (!temp.toString().isEmpty())
			palavras.add(temp.toString());
	}

	private static StringBuilder addPalavraSeIgualCPF(List<String> palavras, StringBuilder temp) {
		if (temp.toString().equals("CPF")) {
			addPalavraTemp(palavras, temp);
			limparStringBuilder(temp);
		}
		return temp;
	}
	
	private static void limparStringBuilder(StringBuilder sb) {
		sb.setLength(0);
	}

	private static boolean isUmaLetraEmMinusculo(String palavra) {
		return palavra.length() == 1 && !Character.isLowerCase(palavra.toCharArray()[0]);
	}
}
