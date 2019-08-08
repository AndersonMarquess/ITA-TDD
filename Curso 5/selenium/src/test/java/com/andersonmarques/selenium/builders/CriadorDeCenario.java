package com.andersonmarques.selenium.builders;

import com.andersonmarques.selenium.models.pages.LeiloesPage;
import com.andersonmarques.selenium.models.pages.UsuariosPage;

import org.openqa.selenium.WebDriver;

public class CriadorDeCenario {

	private WebDriver driver;

	public CriadorDeCenario(WebDriver driver) {
		this.driver = driver;
	}

	public CriadorDeCenario comUsuario(String nome, String email) {
		UsuariosPage usuarios = new UsuariosPage(driver);
		usuarios.visitar();
		usuarios.novo().cadastrar(nome, email);

		return this;
	}

	public CriadorDeCenario comLeilao(String produto, double valor, String nomeDoCriador, boolean usado) {
		LeiloesPage leiloes = new LeiloesPage(driver);
		leiloes.visitar();
		leiloes.novo().preencherComDados(produto, valor, nomeDoCriador, usado);

		return this;
	}

}