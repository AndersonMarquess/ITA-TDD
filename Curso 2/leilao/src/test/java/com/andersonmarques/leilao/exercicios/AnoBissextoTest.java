package com.andersonmarques.leilao.exercicios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AnoBissextoTest {
	int anosBissexto[] = { 1064, 1068, 1072, 1132, 1136, 1144, 1200, 1344, 1348, 1352, 1412, 1416, 1420, 1424, 2016 };

	@Test
	public void verificaAnoBissexto() {
		AnoBissexto avaliaAnoBissexto = new AnoBissexto();

		for (int i = 0; i < anosBissexto.length; i++) {
			assertTrue(avaliaAnoBissexto.eAnoBissexto(anosBissexto[i]));
		}
	}

	@Test
	public void verificaAnoNaoBissexto() {
		AnoBissexto avaliaAnoBissexto = new AnoBissexto();

		for (int i = 0; i < anosBissexto.length; i++) {
			assertFalse(avaliaAnoBissexto.eAnoBissexto(++anosBissexto[i]));
		}
	}

}