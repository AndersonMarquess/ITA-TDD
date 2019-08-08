package com.andersonmarques.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andersonmarques.selenium.models.pages.NovoUsuarioPage;
import com.andersonmarques.selenium.models.pages.UsuariosPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class UsuarioSystemTest {
	private WebDriver chromeDriver;
	private UsuariosPage usuarios;

	@BeforeEach
	public void prepararObjetos() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anderson\\Downloads\\chromedriver.exe");
		this.chromeDriver = new ChromeDriver();
		this.usuarios = new UsuariosPage(this.chromeDriver);
		limparBancoDeDados();
	}

	private void limparBancoDeDados() {
		this.chromeDriver.get("http://localhost:8080/apenas-teste/limpa");
	}

	@AfterEach
	public void finalizarCenarios() {
		this.chromeDriver.close();
	}

	@Test
	public void deveAdicionarUmUsuario() {
		this.usuarios.visitar().novo().cadastrar("Anderson", "anderson@email.com");

		assertTrue(this.usuarios.existeNaListagem("Anderson", "anderson@email.com"));
		this.usuarios.deletarUsuario();
	}

	@Test
	public void deveEditarUmUsuario() {
		this.usuarios.visitar().novo().cadastrar("Anderson", "anderson@email.com");
		
		this.usuarios.visitar()
			.editar()
			.novoNome("Anderson update")
			.novoEmail("anderson@email.com.br")
			.confirmar();
		
		assertTrue(this.usuarios.existeNaListagem("Anderson update", "anderson@email.com.br"));
		this.usuarios.deletarUsuario();
	}

	@Test
	public void deveDeletarUmUsuario() {
		this.usuarios.visitar().novo().cadastrar("Anderson", "anderson@email.com");
		this.usuarios.deletarUsuario();

		assertFalse(this.usuarios.existeNaListagem("Anderson", "anderson@email.com"));
	}

	@Test
	public void naoDeveAdicionarUsuarioSemNome() {
		NovoUsuarioPage novo = this.usuarios.visitar().novo();

		novo.cadastrar("", "anderson@email.com");

		assertTrue(novo.contemMensagem("Nome obrigatorio!"));
	}

	@Test
	public void naoDeveAdicionarUsuarioSemNomeESemEmail() {
		NovoUsuarioPage novo = this.usuarios.visitar().novo();

		novo.cadastrar("", "");

		assertTrue(novo.contemMensagem("Nome obrigatorio!"));
		assertTrue(novo.contemMensagem("E-mail obrigatorio!"));
	}
}