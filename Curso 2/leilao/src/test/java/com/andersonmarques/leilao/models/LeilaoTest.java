package com.andersonmarques.leilao.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.andersonmarques.leilao.builders.CriadorDeLeilao;

import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.andersonmarques.leilao.hamcrestcustom.LeilaoHamcrestUtil.temUmLance;

import org.junit.jupiter.api.Test;

public class LeilaoTest {

	Usuario usuario;
	Usuario usuario2;
	Usuario usuario3;

	@BeforeEach
	public void prepararObjetos() {
		usuario = new Usuario("João");
		usuario2 = new Usuario("José");
		usuario3 = new Usuario("Maria");
	}

	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("XPTO");
		Usuario usuario = new Usuario("nome");
		Lance lance = new Lance(usuario, 10d);

		leilao.propoe(lance);

		// Teste customizado usando o Hamcrest
		assertThat(leilao, temUmLance(lance)); 
	}

	@Test
	public void deveReceberVariosLances() {
		Leilao leilao =  new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(usuario, 10d)
			.propor(usuario2, 100d)
			.propor(usuario3, 120d)
			.construir();

		assertEquals(3, leilao.getLances().size());
		assertEquals(10d, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(100d, leilao.getLances().get(1).getValor(), 0.0001);
		assertEquals(120d, leilao.getLances().get(2).getValor(), 0.0001);
	}

	@Test
	public void naoPodeAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao =  new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(usuario, 10d)
			.propor(usuario, 100d)
			.propor(usuario3, 300d)
			.construir();

		assertEquals(2, leilao.getLances().size());
		assertEquals(10d, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(300d, leilao.getLances().get(1).getValor(), 0.0001);
	}

	@Test
	public void naoPodeAceitarMaisQueCincoLancesDoUsuarioNoMesmoLeilao() {
		Leilao leilao =  new CriadorDeLeilao()
			.criarLeilaoDe("Playstation 3 Novo")
			.propor(usuario, 10d)
			.propor(usuario2, 100d)
			.propor(usuario, 200d)
			.propor(usuario2, 210d)
			.propor(usuario, 300d)
			.propor(usuario2, 310d)
			.propor(usuario, 400d)
			.propor(usuario2, 410d)
			.propor(usuario, 500d)
			.propor(usuario2, 510d)
			.propor(usuario, 600d)
			.propor(usuario2, 600d)
			.construir();

		assertEquals(10, leilao.getLances().size());
		assertEquals(10d, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(100d, leilao.getLances().get(1).getValor(), 0.0001);
	}

	@Test
	public void dobraUltimoLanceDoUsuario() {
		Leilao leilao = new Leilao("XPTO");

		Usuario usuario = new Usuario("nome");
		Usuario usuario2 = new Usuario("nome 2");

		leilao.propoe(new Lance(usuario, 10d));
		leilao.propoe(new Lance(usuario2, 100d));
		leilao.dobrarUltimoLanceDoUsuario(usuario);


		assertEquals(3, leilao.getLances().size());
		assertEquals(10d, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(100d, leilao.getLances().get(1).getValor(), 0.0001);
		assertEquals(20d, leilao.getLances().get(2).getValor(), 0.0001);
	}
}