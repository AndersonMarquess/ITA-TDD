package com.andersonmarques.leilao.infra.interfaces;

import com.andersonmarques.leilao.models.Pagamento;

public interface RepositorioDePagamentos {

	public void salvar(Pagamento pagamento);
}