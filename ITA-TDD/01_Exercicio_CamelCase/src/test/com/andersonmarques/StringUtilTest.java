package test.com.andersonmarques;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.com.andersonmarques.StringUtil;

class StringUtilTest {

	@Test
	void converterUmaPalavraSemCamelCase() {
		List<String> resultado = StringUtil.converterCamelCase("teste");
		assertTrue(resultado.contains("teste"));
	}
	
	@Test
	void converterUmaPalavraComCamelCase() {
		List<String> resultado = StringUtil.converterCamelCase("Primeiro");
		assertTrue(resultado.contains("primeiro"));
	}
	
	@Test
	void converterPalavraCompostaComCamelCase() {
		List<String> resultado = StringUtil.converterCamelCase("PrimeiroSegundoTerceiro");
		assertTrue(resultado.contains("segundo"));
	}
	
	@Test
	void converterPalavraCompostaIniciandoSemCamelCase() {
		List<String> resultado = StringUtil.converterCamelCase("primeiroSegundoTerceiro");
		assertTrue(resultado.contains("primeiro"));
	}

}
