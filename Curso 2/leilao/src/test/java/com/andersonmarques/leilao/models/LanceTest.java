package com.andersonmarques.leilao.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LanceTest {

	@Test
	public void lancaExcecaoAoFazerLanceIgualOuMenorQueZero() {
		assertThrows(IllegalArgumentException.class, () -> new Lance(new Usuario("jp"), 0));
		assertThrows(IllegalArgumentException.class, () -> new Lance(new Usuario("jp"), -3));
	}
}