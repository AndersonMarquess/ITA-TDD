package com.andersonmarques.leilao.infra.dao;

import java.util.List;

import com.andersonmarques.leilao.models.Leilao;

public interface RepositorioDeLeiloes {
	void salva(Leilao leilao);

	List<Leilao> encerrados();

	List<Leilao> correntes();

	void atualiza(Leilao leilao);
}