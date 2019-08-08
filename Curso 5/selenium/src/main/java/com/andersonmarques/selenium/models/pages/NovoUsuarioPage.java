package com.andersonmarques.selenium.models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NovoUsuarioPage {

	private WebDriver driver;

	public NovoUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public NovoUsuarioPage visitar() {
		this.driver.get("http://localhost:8080/usuarios/new");
		return this;
	}

	public void cadastrar(String nome, String email) {
		WebElement inputNome = this.driver.findElement(By.name("usuario.nome"));
		WebElement inputEmail = this.driver.findElement(By.name("usuario.email"));

		inputNome.sendKeys(nome);
		inputEmail.sendKeys(email);

		WebElement btnSalvar = this.driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
	}

	public boolean contemMensagem(String mensagem) {
		return this.driver.getPageSource().contains(mensagem);
	}

}