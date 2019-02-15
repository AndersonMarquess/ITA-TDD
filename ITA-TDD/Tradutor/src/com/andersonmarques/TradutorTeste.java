package com.andersonmarques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradutorTeste {

	private Tradutor tradutor;

	@BeforeEach
	public void inicializarTradutor() {
		tradutor = new Tradutor();
	}

	@Test
	public void tradutorVazio() {
		assertTrue(tradutor.isEmpty());
	}

	@Test
	public void traduzirUmaPlavra() {
		tradutor.addTraducao("Casa", "House");
		assertFalse(tradutor.isEmpty());
		assertEquals("House", tradutor.traduzir("Casa"));
	}
	
	@Test
	public void traduzirDuasPlavras() {
		tradutor.addTraducao("Casa", "House");
		tradutor.addTraducao("Olá", "Hello");
		assertFalse(tradutor.isEmpty());
		assertEquals("House", tradutor.traduzir("Casa"));
		assertEquals("Hello", tradutor.traduzir("Olá"));
	}
	
	@Test
	public void criarDuasTraducoesParaMesmaPalavra() {
		tradutor.addTraducao("Bom", "Good");
		tradutor.addTraducao("Bom", "Nice");
		assertFalse(tradutor.isEmpty());
		assertEquals("Good, Nice", tradutor.traduzir("Bom"));
	}
	
	@Test
	public void traduzirUmaFrase() {
		tradutor.addTraducao("Olá", "Hello");
		tradutor.addTraducao("Como", "How");
		tradutor.addTraducao("Você", "You");
		tradutor.addTraducao("Vai", "Going");
		assertFalse(tradutor.isEmpty());
		assertEquals("Hello How You Going", tradutor.traduzirFrase("Olá Como Você Vai"));
	}
	
	@Test
	public void traduzirUmaFraseContendoPalavraComDuasTraducoes() {
		tradutor.addTraducao("Bom", "Good");
		tradutor.addTraducao("Bom", "Nice");
		tradutor.addTraducao("Ver", "See");
		tradutor.addTraducao("Você", "You");
		assertFalse(tradutor.isEmpty());
		assertEquals("Good See You", tradutor.traduzirFrase("Bom Ver Você"));
	}
	
	
}
