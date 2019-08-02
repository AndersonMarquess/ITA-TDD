package com.andersonmarques.leilao.exercicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.andersonmarques.leilao.exercicios.MatematicaMaluca;
import org.junit.jupiter.api.Test;

public class MatematicaMalucaTest {

	@Test
	public void verificarCalculoComValorMaiorQueTrinta() {
		int contaMaluca = new MatematicaMaluca().contaMaluca(40);
		assertEquals(160, contaMaluca);
	}

	@Test
	public void verificarCalculoComValorMenorQueTrinta() {
		assertEquals(60, new MatematicaMaluca().contaMaluca(20));
	}

	@Test
	public void verificarCalculoComValorMenorQueDez() {
		assertEquals(10, new MatematicaMaluca().contaMaluca(5));
	}

	@Test
	public void verificarCalculoComValorNegativo() {
		assertEquals(-6, new MatematicaMaluca().contaMaluca(-3));
	}

}