package com.andersonmarques.leilao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import com.andersonmarques.leilao.builders.CriadorDeLeilao;
import com.andersonmarques.leilao.infra.dao.RepositorioDeLeiloes;
import com.andersonmarques.leilao.infra.interfaces.Relogio;
import com.andersonmarques.leilao.infra.interfaces.RepositorioDePagamentos;
import com.andersonmarques.leilao.models.Leilao;
import com.andersonmarques.leilao.models.Pagamento;
import com.andersonmarques.leilao.models.Usuario;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class GeradorDePagamentoTest {

	@Test
	public void confirmaSalvamentoDoPagamento() {
		RepositorioDeLeiloes mockRepLeilao = mock(RepositorioDeLeiloes.class);
		RepositorioDePagamentos mockRepPagamento = mock(RepositorioDePagamentos.class);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("item")
			.propor(new Usuario("nome 1"), 1000d)
			.propor(new Usuario("nome 2"), 2000d)
			.propor(new Usuario("nome 3"), 3000d)
			.construir();

		when(mockRepLeilao.encerrados()).thenReturn(Arrays.asList(leilao));

		new GeradorDePagamento(mockRepLeilao, mockRepPagamento, new Avaliador()).gerar();

		// Captura o argumento informado ao mock, o tipo do argumento será o mesmo
		// informado ao forClass
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(mockRepPagamento).salvar(argumento.capture());

		assertEquals(3000d, argumento.getValue().getValor(), 0.00001d);
	}

	@Test
	public void verificarSeLeilaoCriadoNoFimDeSemanaFoiAlteradoParaSegundaFeira() {
		RepositorioDeLeiloes mockRepLeilao = mock(RepositorioDeLeiloes.class);
		RepositorioDePagamentos mockRepPagamento = mock(RepositorioDePagamentos.class);
		Relogio mockRelogio = mock(Relogio.class);

		Calendar dataSabado = Calendar.getInstance();
		dataSabado.set(2019, 8, 3);

		Leilao leilao = new CriadorDeLeilao().criarLeilaoDe("item")
			.propor(new Usuario("nome 1"), 1000d)
			.propor(new Usuario("nome 2"), 2000d)
			.propor(new Usuario("nome 3"), 3000d)
			.construir();

		when(mockRepLeilao.encerrados()).thenReturn(Arrays.asList(leilao));
		when(mockRelogio.hoje()).thenReturn(dataSabado);

		new GeradorDePagamento(mockRepLeilao, mockRepPagamento, new Avaliador()).gerar();

		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(mockRepPagamento).salvar(argumento.capture());

		assertEquals(Calendar.MONDAY, argumento.getValue().getData().get(Calendar.DAY_OF_WEEK));
		assertEquals(5, argumento.getValue().getData().get(Calendar.DAY_OF_MONTH));
	}
}