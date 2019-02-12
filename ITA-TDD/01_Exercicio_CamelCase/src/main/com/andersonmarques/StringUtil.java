package main.com.andersonmarques;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static List<String> converterCamelCase(String original) {
		List<String> palavras = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		for (String palavra : original.split("(?=[A-Z])")) {

			if (isUmaLetraEmMinusculo(palavra)) {
				temp.append(palavra);
				continue;
			}

			temp = addPalavraSeIgualCPF(palavras, temp);
			palavras.add(palavra.toLowerCase());
		}

		addPalavraTemp(palavras, temp);

		return palavras;
	}

	private static void addPalavraTemp(List<String> palavras, StringBuilder temp) {
		if (!temp.toString().isEmpty())
			palavras.add(temp.toString());
	}

	private static StringBuilder addPalavraSeIgualCPF(List<String> palavras, StringBuilder temp) {
		if (temp.toString().equals("CPF")) {
			addPalavraTemp(palavras, temp);
			temp = new StringBuilder();
		}
		return temp;
	}

	private static boolean isUmaLetraEmMinusculo(String palavra) {
		return palavra.length() == 1 && !Character.isLowerCase(palavra.toCharArray()[0]);
	}
}
