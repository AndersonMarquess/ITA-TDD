package com.andersonmarques.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andersonmarques.selenium.builders.CriadorDeCenario;
import com.andersonmarques.selenium.models.pages.DetalhesDoLeilaoPage;
import com.andersonmarques.selenium.models.pages.LeiloesPage;
import com.andersonmarques.selenium.models.pages.UsuariosPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LanceSystemTest {
	private WebDriver chromeDriver;
	private UsuariosPage usuarios;
	private LeiloesPage leiloes;

	@BeforeEach
	public void prepararObjetos() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anderson\\Downloads\\chromedriver.exe");
		this.chromeDriver = new ChromeDriver();
		limparBancoDeDados();
		criarCenario();

		this.usuarios = new UsuariosPage(this.chromeDriver);
		this.leiloes = new LeiloesPage(this.chromeDriver);
	}

	private void criarCenario() {
		CriadorDeCenario criarCenarios = new CriadorDeCenario(this.chromeDriver);
		criarCenarios
			.comUsuario("Anderson", "anderson@email.com")
			.comUsuario("Outro Anderson", "outro_anderson@email.com")
			.comLeilao("Leilão Padrão", 500d, "Anderson", true);
	}

	private void limparBancoDeDados() {
		this.chromeDriver.get("http://localhost:8080/apenas-teste/limpa");
	}

	@AfterEach
	public void finalizarCenarios() {
		this.usuarios.visitar().deletarUsuario();
		this.chromeDriver.close();
	}
	
	@Test
	public void deveFazerUmLance() {
		DetalhesDoLeilaoPage lances = leiloes.visitar().detalhesDoLeilaoNaPosicao(1);
		lances.darLanceCom("Outro Anderson", 150d);
		assertTrue(lances.existeLanceDe("Outro Anderson", 150d));
	}
}