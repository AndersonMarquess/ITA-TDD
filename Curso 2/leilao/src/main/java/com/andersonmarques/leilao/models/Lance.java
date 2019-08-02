package com.andersonmarques.leilao.models;

import java.util.Objects;

public class Lance {

	private Usuario usuario;
	private double valor;

	public Lance(Usuario usuario, double valor) {
		if(valor <= 0) {
			throw new IllegalArgumentException("O Valor do lance não pode ser menor ou igual a zero.");
		}
		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public double getValor() {
		return valor;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Lance))
			return false;

		Lance lance = (Lance) o;
		return Objects.equals(usuario, lance.usuario) && valor == lance.valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, valor);
	}

}
