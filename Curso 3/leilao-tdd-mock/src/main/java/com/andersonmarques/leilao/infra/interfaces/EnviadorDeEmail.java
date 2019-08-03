package com.andersonmarques.leilao.infra.interfaces;

import com.andersonmarques.leilao.models.Leilao;

public interface EnviadorDeEmail {
	void envia(Leilao leilao);
}
