package com.andersonmarques.leilao.services;

import java.util.List;
import java.util.stream.Collectors;

import com.andersonmarques.leilao.models.Lance;
import com.andersonmarques.leilao.models.Leilao;

public class Avaliador {

	private Double menorLance = 0d;
	private Double maiorLance = 0d;
	private Double mediaLance = 0d;
	private List<Double> tresMaioresLances;

	public void avaliar(Leilao leilao) {
		if(leilao.getLances().isEmpty()) {
			throw new RuntimeException("Não pode avaliar leilão sem lances");
		}
		this.maiorLance = verificarMaiorLance(leilao);
		this.menorLance = verificarMenorLance(leilao);
		this.mediaLance = verificarMediaDosLances(leilao);
		this.tresMaioresLances = verificarTresMaioresLances(leilao);
	}

	private List<Double> verificarTresMaioresLances(Leilao leilao) {
		List<Double> lances = leilao.getLances()
			.stream()
			.map(Lance::getValor)
			.collect(Collectors.toList());

		lances.sort((l1, l2) -> {
			if (l1 < l2)
				return 1;
			if (l1 > l2)
				return -1;
			return 0;
		});

		if (lances.size() >= 3)
			return lances.subList(0, 3);

		return lances;
	}

	private double verificarMediaDosLances(Leilao leilao) {
		return leilao.getLances()
			.stream()
			.mapToDouble(Lance::getValor)
			.average()
			.orElse(0);
	}

	private double verificarMenorLance(Leilao leilao) {
		return leilao.getLances()
			.stream()
			.mapToDouble(Lance::getValor)
			.min()
			.orElse(0);
	}

	private double verificarMaiorLance(Leilao leilao) {
		return leilao.getLances()
			.stream()
			.mapToDouble(Lance::getValor)
			.max()
			.orElse(0);
	}

	public Double getMaiorLance() {
		return maiorLance;
	}

	public Double getMenorLance() {
		return menorLance;
	}

	public Double getMediaDosLances() {
		return mediaLance;
	}

	public List<Double> getTresMaioresLances() {
		return this.tresMaioresLances;
	}
}