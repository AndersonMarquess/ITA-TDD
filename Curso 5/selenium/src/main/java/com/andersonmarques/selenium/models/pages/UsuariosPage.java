package com.andersonmarques.selenium.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Padrão de projeto chamado de "Page object".
 */
public class UsuariosPage {

	private WebDriver driver;

	public UsuariosPage(WebDriver driver) {
		this.driver = driver;
	}

	public UsuariosPage visitar() {
		this.driver.get("http://localhost:8080/usuarios");
		return this;
	}

	public NovoUsuarioPage novo() {
		this.driver.findElement(By.linkText("Novo Usuário")).click();
		return new NovoUsuarioPage(this.driver);
	}

	public boolean existeNaListagem(String nome, String email) {
		return this.driver.getPageSource().contains(nome) && this.driver.getPageSource().contains(email);
	}

	public void deletarUsuario() {
		this.driver.findElements(By.tagName("button")).get(0).click();
		this.driver.switchTo().alert().accept();
	}

	public EditarUsuarioPage editar() {
		this.driver.findElement(By.linkText("editar")).click();
		return new EditarUsuarioPage(this.driver);
	}
}