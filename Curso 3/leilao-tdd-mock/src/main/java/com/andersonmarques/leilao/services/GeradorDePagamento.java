package com.andersonmarques.leilao.services;

import java.util.Calendar;

import com.andersonmarques.leilao.infra.dao.RepositorioDeLeiloes;
import com.andersonmarques.leilao.infra.interfaces.Relogio;
import com.andersonmarques.leilao.infra.interfaces.RelogioImpl;
import com.andersonmarques.leilao.infra.interfaces.RepositorioDePagamentos;
import com.andersonmarques.leilao.models.Pagamento;

public class GeradorDePagamento {

	private RepositorioDeLeiloes leiloes;
	private RepositorioDePagamentos pagamentos;
	private Avaliador avaliador;
	private Relogio relogio;

	public GeradorDePagamento(RepositorioDeLeiloes leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador,
			Relogio relogio) {
		this.leiloes = leiloes;
		this.pagamentos = pagamentos;
		this.avaliador = avaliador;
		this.relogio = relogio;
	}

	public GeradorDePagamento(RepositorioDeLeiloes leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador) {
		this(leiloes, pagamentos, avaliador, new RelogioImpl());
	}

	public void gerar() {
		this.leiloes.encerrados().forEach(leilao -> {
			avaliador.avaliar(leilao);
			this.pagamentos.salvar(new Pagamento(avaliador.getMaiorLance(), getPrimeiroDiaUtil()));
		});
	}

	private Calendar getPrimeiroDiaUtil() {
		Calendar data = relogio.hoje();
		int diaSemana = data.get(Calendar.DAY_OF_WEEK);

		if(Calendar.SATURDAY == diaSemana) 
			data.add(Calendar.DAY_OF_MONTH, 2);
		if(Calendar.SUNDAY == diaSemana) 
			data.add(Calendar.DAY_OF_MONTH, 1);

		return data;
	}
}