package com.andersonmarques.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andersonmarques.selenium.models.pages.LeiloesPage;
import com.andersonmarques.selenium.models.pages.NovoLeilaoPage;
import com.andersonmarques.selenium.models.pages.UsuariosPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LeiloesSystemTest {

	private WebDriver chromeDriver;
	private UsuariosPage usuarios;
	private LeiloesPage leiloes;

	@BeforeEach
	public void prepararObjetos() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anderson\\Downloads\\chromedriver.exe");
		this.chromeDriver = new ChromeDriver();
		this.usuarios = new UsuariosPage(this.chromeDriver);
		this.leiloes = new LeiloesPage(this.chromeDriver);
		limparBancoDeDados();
		criarUsuarioPadrao();
	}

	private void limparBancoDeDados() {
		this.chromeDriver.get("http://localhost:8080/apenas-teste/limpa");
	}

	private void criarUsuarioPadrao() {
		this.usuarios.visitar().novo().cadastrar("Anderson", "anderson@email.com");
	}

	@AfterEach
	public void finalizarCenarios() {
		this.usuarios.visitar().deletarUsuario();
		this.chromeDriver.close();
	}

	@Test
	public void deveCriarUmLeilao() {
		NovoLeilaoPage novoLeilao = this.leiloes.visitar().novo();
		novoLeilao.preencherComDados("XPTO", 500d, "Anderson", true);
		assertTrue(this.leiloes.existeNaListagem("XPTO", 500d, "Anderson", true));
	}

	@Test
	public void naoDeveCriarUmLeilaoSemNomeComValorMenorIgualZero() {
		NovoLeilaoPage novoLeilao = this.leiloes.visitar().novo();
		novoLeilao.preencherComDados("", 0d, "Anderson", true);
		assertTrue(novoLeilao.contemMensagem("Nome obrigatorio!"));
		assertTrue(novoLeilao.contemMensagem("Valor inicial deve ser maior que zero!"));
	}
}