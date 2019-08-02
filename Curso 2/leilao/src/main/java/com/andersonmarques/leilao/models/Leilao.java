package com.andersonmarques.leilao.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		if (lancePermitido(lance.getUsuario()))
			lances.add(lance);
	}

	private boolean lancePermitido(Usuario usuario) {
		return this.lances.isEmpty() || 
			!esteUsuarioFezUltimoLance(usuario) && 
			(getQtdLancesDoUsuario(usuario) < 5);
	}

	private long getQtdLancesDoUsuario(Usuario usuario) {
		return filtrarLancesDoUsuario(usuario).count();
	}

	private boolean esteUsuarioFezUltimoLance(Usuario usuario) {
		return getUltimoLance(this.lances).getUsuario().equals(usuario);
	}

	private Lance getUltimoLance(List<Lance> lances) {
		return lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void dobrarUltimoLanceDoUsuario(Usuario usuario) {
		List<Lance> lancesDoUsuario = filtrarLancesDoUsuario(usuario)
			.collect(Collectors.toList());
			Double valorUltimoLance = getUltimoLance(lancesDoUsuario).getValor();
			propoe(new Lance(usuario, valorUltimoLance * 2));
	}

	private Stream<Lance> filtrarLancesDoUsuario(Usuario usuario) {
		return lances.stream().filter(l -> l.getUsuario().equals(usuario));
	}

}
