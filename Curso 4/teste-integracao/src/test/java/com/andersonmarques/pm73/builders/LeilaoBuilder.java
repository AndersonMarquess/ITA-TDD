package com.andersonmarques.pm73.builders;

import java.util.Calendar;

import com.andersonmarques.pm73.dominio.Lance;
import com.andersonmarques.pm73.dominio.Leilao;
import com.andersonmarques.pm73.dominio.Usuario;

public class LeilaoBuilder {

	private Usuario dono;
	private double valor;
	private String nome;
	private boolean usado;
	private Calendar dataAbertura;
	private boolean encerrado;
	private Leilao leilao;

	public LeilaoBuilder() {
		this.dono = new Usuario("Joao da Silva", "joao@silva.com.br");
		this.valor = 1500.0;
		this.nome = "XBox";
		this.usado = false;
		this.dataAbertura = Calendar.getInstance();
		this.leilao = new Leilao(nome, valor, dono, usado);
	}

	public LeilaoBuilder comDono(Usuario dono) {
		this.leilao.setDono(dono);
		return this;
	}

	public LeilaoBuilder comValor(double valor) {
		this.leilao.setValorInicial(valor);
		return this;
	}

	public LeilaoBuilder comNome(String nome) {
		this.leilao.setNome(nome);
		return this;
	}

	public LeilaoBuilder usado() {
		this.leilao.setUsado(true);
		return this;
	}

	public LeilaoBuilder encerrado() {
		this.encerrado = true;
		return this;
	}

	public LeilaoBuilder diasAtras(int dias) {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, -dias);

		this.dataAbertura = data;

		return this;
	}

	public Leilao construir() {
		leilao.setDataAbertura(dataAbertura);
		if (encerrado)
			leilao.encerrar();

		return leilao;
	}

	public LeilaoBuilder comLance(Usuario usuario, Double valor) {
		this.leilao.adicionaLance(new Lance(Calendar.getInstance(), usuario, valor, leilao));
		return this;
	}
}