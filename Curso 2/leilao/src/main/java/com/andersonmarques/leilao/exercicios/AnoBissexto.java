package com.andersonmarques.leilao.exercicios;

public class AnoBissexto {

	public Boolean eAnoBissexto(int ano) {
		boolean divisivelPorQuatro = ano % 4 == 0;
		boolean divisivelPorQuatrocentos = ano % 400 == 0;
		boolean divisivelPorCem = ano % 100 == 0;

		return (divisivelPorQuatrocentos || (divisivelPorQuatro && !divisivelPorCem));
	}

}