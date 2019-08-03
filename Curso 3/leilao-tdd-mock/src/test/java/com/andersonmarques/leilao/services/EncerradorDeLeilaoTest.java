package com.andersonmarques.leilao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.andersonmarques.leilao.builders.CriadorDeLeilao;
import com.andersonmarques.leilao.infra.dao.RepositorioDeLeiloes;
import com.andersonmarques.leilao.infra.interfaces.EnviadorDeEmail;
import com.andersonmarques.leilao.models.Leilao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public class EncerradorDeLeilaoTest {

	private EnviadorDeEmail enviadorMock;
	private RepositorioDeLeiloes daoMock;

	@BeforeEach
	public void prepararObjetos() {
		// Mock da classe
		this.enviadorMock = mock(EnviadorDeEmail.class);
		this.daoMock = mock(RepositorioDeLeiloes.class);
	}

	@Test
	public void deveEncerrarLeileosQueComecaramASeteDias() {
		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.set(1999, 1, 20);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataAntiga).construir();
		Leilao leilao2 = new CriadorDeLeilao().criarLeilaoDe("XPTO 2").comData(dataAntiga).construir();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao, leilao2);

		// Ao chamar o método correntes será retornado a lista leiloesAntigos.
		when(daoMock.correntes()).thenReturn(leiloesAntigos);

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();

		assertEquals(2, encerradorDeLeilao.getTotalEncerrados());
		assertTrue(leilao.isEncerrado());
		assertTrue(leilao2.isEncerrado());
	}

	@Test
	public void naoDeveEncerrarLeileosQueComecaramEmMenosDESeteDias() {
		Calendar dataDeOntem = Calendar.getInstance();
		dataDeOntem.add(Calendar.DAY_OF_MONTH, -1);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataDeOntem).construir();
		Leilao leilao2 = new CriadorDeLeilao().criarLeilaoDe("XPTO 2").comData(dataDeOntem).construir();
		List<Leilao> leiloesCriados = Arrays.asList(leilao, leilao2);

		when(daoMock.correntes()).thenReturn(leiloesCriados);

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();

		assertEquals(0, encerradorDeLeilao.getTotalEncerrados());
		assertFalse(leilao.isEncerrado());
		assertFalse(leilao2.isEncerrado());

		// Garante que o método nunca foi chamado com o seguinte argumento
		verify(daoMock, never()).atualiza(leilao);
		verify(daoMock, never()).atualiza(leilao2);
	}

	@Test
	public void naoEncerraLeiloesVazios() {

		when(daoMock.correntes()).thenReturn(new ArrayList<Leilao>());

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();

		assertEquals(0, encerradorDeLeilao.getTotalEncerrados());
	}

	@Test
	public void deveAtualizarLeiloesEncerrados() {
		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.set(1999, 1, 20);
		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataAntiga).construir();

		when(daoMock.correntes()).thenReturn(Arrays.asList(leilao));

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();

		// verificar se um método "atualiza" do "daoMock" foi invocado uma vez ou então
		// falha
		verify(daoMock, times(1)).atualiza(leilao);
	}

	@Test
	public void verificarOrdemDeEncerramento() {
		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.set(1999, 1, 20);
		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataAntiga).construir();

		when(daoMock.correntes()).thenReturn(Arrays.asList(leilao));

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();

		InOrder inOrder = inOrder(daoMock, enviadorMock);
		inOrder.verify(daoMock, times(1)).atualiza(leilao);
		inOrder.verify(enviadorMock, times(1)).envia(leilao);
	}

	@Test
	public void lancaExcecaoAoAtualizarPrimeiroLeilaoEContinuaExecucao() {
		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.set(1999, 1, 20);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataAntiga).construir();
		Leilao leilao2 = new CriadorDeLeilao().criarLeilaoDe("XPTO 2").comData(dataAntiga).construir();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao, leilao2);

		when(daoMock.correntes()).thenReturn(leiloesAntigos);
		doThrow(new RuntimeException()).when(daoMock).atualiza(leilao);

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();
		
		assertTrue(leilao.isEncerrado());
		assertTrue(leilao2.isEncerrado());

		verify(daoMock, times(1)).atualiza(leilao);
		verify(enviadorMock, never()).envia(leilao);
		
		verify(daoMock, times(1)).atualiza(leilao2);
		verify(enviadorMock, times(1)).envia(leilao2);
	}

	@Test
	public void lancaExcecaoAoAtualizarLeilaoEContinuaExecucao() {
		Calendar dataAntiga = Calendar.getInstance();
		dataAntiga.set(1999, 1, 20);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("XPTO").comData(dataAntiga).construir();
		Leilao leilao2 = new CriadorDeLeilao().criarLeilaoDe("XPTO 2").comData(dataAntiga).construir();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao, leilao2);

		when(daoMock.correntes()).thenReturn(leiloesAntigos);
		doThrow(new RuntimeException()).when(daoMock).atualiza(any(Leilao.class));

		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoMock, enviadorMock);
		encerradorDeLeilao.encerra();
		
		assertTrue(leilao.isEncerrado());
		assertTrue(leilao2.isEncerrado());

		verify(daoMock, times(2)).atualiza(any(Leilao.class));
		verify(enviadorMock, never()).envia(any(Leilao.class));
	}
}