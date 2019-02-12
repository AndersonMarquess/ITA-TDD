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
		assertTrue(resultado.contains("primeiro"));
		assertTrue(resultado.contains("segundo"));
		assertTrue(resultado.contains("terceiro"));
	}
	
	@Test
	void converterPalavraCompostaIniciandoSemCamelCase() {
		List<String> resultado = StringUtil.converterCamelCase("primeiroSegundoTerceiro");
		assertTrue(resultado.contains("primeiro"));
		assertTrue(resultado.contains("segundo"));
		assertTrue(resultado.contains("terceiro"));
	}
	
	@Test
	void naoPodeConverterPalavraApenasEmMaiusculo() {
		List<String> resultado = StringUtil.converterCamelCase("CPF");
		assertTrue(resultado.contains("CPF"));
	}
	
	@Test
	void converterPalavraCompostaTerminadaEmMaiusculo() {
		List<String> resultado = StringUtil.converterCamelCase("primeiroCPF");
		assertTrue(resultado.contains("primeiro"));
		assertTrue(resultado.contains("CPF"));
	}

	@Test
	void converterPalavraEmCamelCaseCompostaPorPalavraEmMaiusculoNoMeio() {
		List<String> resultado = StringUtil.converterCamelCase("numeroCPFContribuinte");
		assertTrue(resultado.contains("numero"));
		assertTrue(resultado.contains("CPF"));
		assertTrue(resultado.contains("contribuinte"));
	}

}
