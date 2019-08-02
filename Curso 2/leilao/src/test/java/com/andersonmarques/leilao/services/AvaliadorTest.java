package com.andersonmarques.leilao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.andersonmarques.leilao.builders.CriadorDeLeilao;
import com.andersonmarques.leilao.models.Leilao;
import com.andersonmarques.leilao.models.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AvaliadorTest {

	Avaliador leiloeiro;
	Usuario joao;
	Usuario jose;
	Usuario maria;

	@BeforeEach
	public void prepararObjetos() {
		this.leiloeiro = new Avaliador();
		joao = new Usuario("João");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
	}

	@Test
	public void verificarMenorEMaiorLance() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(maria, 250.0)
			.propor(joao, 300.0)
			.propor(jose, 400.0)
			.construir();


		leiloeiro.avaliar(leilao);

		assertEquals(400, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(250, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void verificarValorMedioDosLances() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(maria, 290.0)
			.propor(joao, 300.0)
			.propor(jose, 400.0)
			.construir();

		leiloeiro.avaliar(leilao);

		assertEquals(330, leiloeiro.getMediaDosLances(), 0.0001);
	}

	@Test
	public void naoDeveAvaliarLeilaoSemLances() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.construir();

		assertThrows(RuntimeException.class, () -> leiloeiro.avaliar(leilao));
	}

	@Test
	public void verificarLeilaoComUmLance() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(joao, 300.0)
			.construir();

		leiloeiro.avaliar(leilao);

		assertEquals(300, leiloeiro.getMediaDosLances(), 0.0001);
		assertEquals(300, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(300, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void verificarTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(jose, 400.0) 
			.propor(maria, 290.0)
			.propor(joao, 291.0) 
			.propor(maria, 430.0)
			.construir();

		leiloeiro.avaliar(leilao);

		List<Double> tresMaiores = leiloeiro.getTresMaioresLances();
		assertEquals(3, tresMaiores.size());
		assertEquals(430, tresMaiores.get(0), 0.00001);
		assertEquals(400, tresMaiores.get(1), 0.00001);
		assertEquals(291, tresMaiores.get(2), 0.00001);
	}

	@Test
	public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
		Leilao leilao = new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(jose, 100.0) 
			.propor(maria, 200.0)
			.construir();

		leiloeiro.avaliar(leilao);

		List<Double> tresMaiores = leiloeiro.getTresMaioresLances();
		assertEquals(2, tresMaiores.size());
		assertEquals(200, tresMaiores.get(0), 0.00001);
		assertEquals(100, tresMaiores.get(1), 0.00001);
	}
}
